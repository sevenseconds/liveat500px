package th.in.droid.liveat500px.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import th.in.droid.liveat500px.R;
import th.in.droid.liveat500px.adapter.PhotoListAdapter;
import th.in.droid.liveat500px.dao.PhotoItemListDao;
import th.in.droid.liveat500px.datatype.MutableInteger;
import th.in.droid.liveat500px.manager.HttpManager;
import th.in.droid.liveat500px.manager.PhotoListManager;


public class MainFragment extends Fragment {

    private ListView listView;
    private PhotoListAdapter listAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PhotoListManager photoListManager;
    private Button btnNewPhotos;

    private boolean isLoadingMore = false;
    private MutableInteger lastPositionInteger;

    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize Fragment level's variables
        init(savedInstanceState);

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        photoListManager = new PhotoListManager();
        lastPositionInteger = new MutableInteger(-1);
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        btnNewPhotos = rootView.findViewById(R.id.btn_new_photos);
        btnNewPhotos.setOnClickListener(buttonClickListener);

        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(pullToRefreshListener);

        listView = rootView.findViewById(R.id.listview);
        listAdapter = new PhotoListAdapter(lastPositionInteger);
        listAdapter.setDao(photoListManager.getDao());
        listView.setAdapter(listAdapter);
        listView.setOnScrollListener(listViewScrollListener);

        if (savedInstanceState == null) {
            refreshData();
        }
    }

    private void refreshData() {
        if (photoListManager.getCount() == 0) {
            reloadData();
        } else {
            reloadNewerData();
        }
    }

    private void reloadNewerData() {
        int maxId = photoListManager.getMaxId();
        Call<PhotoItemListDao> call = HttpManager.getInstance().getService().loadPhotoListAfterId(maxId);
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_RELOAD_NEWER));
    }

    private void reloadData() {
        Call<PhotoItemListDao> call = HttpManager.getInstance().getService().loadPhotoList();
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_RELOAD));
    }

    private void loadMoreData() {
        if (isLoadingMore) {
            return;
        }
        isLoadingMore = true;
        int minId = photoListManager.getMinId();
        Call<PhotoItemListDao> call = HttpManager.getInstance().getService().loadPhotoListBeforeId(minId);
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_LOAD_MORE));
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
        outState.putBundle("photoListManager", photoListManager.onSaveInstanceState());
        outState.putBundle("lastPositionInteger", lastPositionInteger.onSaveInstanceState());
    }

    /*
     * Restore Instance State Here
     */
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        photoListManager.onRestoreInstanceState(savedInstanceState.getBundle("photoListManager"));
        lastPositionInteger.onRestoreInstanceState(savedInstanceState.getBundle("lastPositionInteger"));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void showButtonNewPhotos() {
        btnNewPhotos.setVisibility(View.VISIBLE);
        Animation anim = AnimationUtils.loadAnimation(
                Contextor.getInstance().getContext(),
                R.anim.zoom_fade_in
        );
        btnNewPhotos.startAnimation(anim);
    }

    private void hideButtonNewPhotos() {
        btnNewPhotos.setVisibility(View.GONE);
        Animation anim = AnimationUtils.loadAnimation(
                Contextor.getInstance().getContext(),
                R.anim.zoom_fade_out
        );
        btnNewPhotos.startAnimation(anim);
    }

    private void showToast(String text) {
        Toast.makeText(Contextor.getInstance().getContext(), text, Toast.LENGTH_SHORT).show();
    }

    SwipeRefreshLayout.OnRefreshListener pullToRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshData();
        }
    };

    View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btnNewPhotos) {
                listView.smoothScrollToPosition(0);
                hideButtonNewPhotos();
            }
        }
    };

    AbsListView.OnScrollListener listViewScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (view == listView) {
                swipeRefreshLayout.setEnabled(firstVisibleItem == 0);
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                    if (photoListManager.getCount() > 0) {
                        loadMoreData();
                    }
                }
            }
        }
    };

    class PhotoListLoadCallback implements Callback<PhotoItemListDao> {

        static final int MODE_RELOAD = 1;
        static final int MODE_RELOAD_NEWER = 2;
        static final int MODE_LOAD_MORE = 3;

        int mode;

        PhotoListLoadCallback(int mode) {
            this.mode = mode;
        }

        @Override
        public void onResponse(Call<PhotoItemListDao> call, Response<PhotoItemListDao> response) {
            swipeRefreshLayout.setRefreshing(false);
            if (response.isSuccessful()) {
                PhotoItemListDao dao = response.body();

                int firstVisiblePosition = listView.getFirstVisiblePosition();
                View c = listView.getChildAt(0);
                int top = c == null ? 0 : c.getTop();

                if (mode == MODE_RELOAD_NEWER) {
                    photoListManager.insertDaoAtTopPosition(dao);
                } else if (mode == MODE_LOAD_MORE) {
                    photoListManager.appendDaoAtBottomPosition(dao);
                } else {
                    photoListManager.setDao(dao);
                }
                clearLoadingMoreFlagIfCapable(mode);
                listAdapter.setDao(photoListManager.getDao());
                listAdapter.notifyDataSetChanged();

                if (mode == MODE_RELOAD_NEWER) {
                    int additonalSize = (dao != null && dao.getData() != null) ? dao.getData().size() : 0;
                    listAdapter.increaseLastPosition(additonalSize);
                    listView.setSelectionFromTop(firstVisiblePosition + additonalSize, top);

                    if (additonalSize > 0) {
                        showButtonNewPhotos();
                    }
                } else {

                }

                showToast("Load Completed");
            } else {
                clearLoadingMoreFlagIfCapable(mode);
                swipeRefreshLayout.setRefreshing(false);
                try {
                    showToast(response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<PhotoItemListDao> call, Throwable t) {
            clearLoadingMoreFlagIfCapable(mode);
            swipeRefreshLayout.setRefreshing(false);
            showToast(t.toString());
        }

        private void clearLoadingMoreFlagIfCapable(int mode) {
            if (mode == MODE_LOAD_MORE) {
                isLoadingMore = false;
            }
        }
    }
}
