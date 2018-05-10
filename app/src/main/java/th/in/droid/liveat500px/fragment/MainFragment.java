package th.in.droid.liveat500px.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
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
import th.in.droid.liveat500px.manager.HttpManager;
import th.in.droid.liveat500px.manager.PhotoListManager;


public class MainFragment extends Fragment {

    private ListView listView;
    private PhotoListAdapter listAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PhotoListManager photoListManager;
    private Button btnNewPhotos;

    boolean isLoadingMore = false;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        photoListManager = new PhotoListManager();

        btnNewPhotos = rootView.findViewById(R.id.btn_new_photos);
        btnNewPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.smoothScrollToPosition(0);
                hideButtonNewPhotos();
            }
        });

        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        listView = rootView.findViewById(R.id.listview);
        listAdapter = new PhotoListAdapter();
        listView.setAdapter(listAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                swipeRefreshLayout.setEnabled(firstVisibleItem == 0);
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                    if (photoListManager.getCount() > 0) {
                        loadMoreData();
                    }
                }
            }
        });

        refreshData();
    }

    private void refreshData() {
        if (photoListManager.getCount() == 0) {
            reloadData();
        } else {
            reloadNewerData();
        }
    }

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
                    isLoadingMore = false;
                } else {
                    photoListManager.setDao(dao);
                }
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

                Toast.makeText(getActivity().getApplicationContext(), "Load Completed", Toast.LENGTH_SHORT).show();
            } else {
                if (mode == MODE_LOAD_MORE) {
                    isLoadingMore = false;
                }
                swipeRefreshLayout.setRefreshing(false);
                try {
                    Log.e("LoadData", response.errorBody().string());
                    Toast.makeText(getActivity().getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<PhotoItemListDao> call, Throwable t) {
            if (mode == MODE_LOAD_MORE) {
                isLoadingMore = false;
            }
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity().getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
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
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }

    public void showButtonNewPhotos() {
        btnNewPhotos.setVisibility(View.VISIBLE);
        Animation anim = AnimationUtils.loadAnimation(
                Contextor.getInstance().getContext(),
                R.anim.zoom_fade_in
        );
        btnNewPhotos.startAnimation(anim);
    }

    public void hideButtonNewPhotos() {
        btnNewPhotos.setVisibility(View.GONE);
        Animation anim = AnimationUtils.loadAnimation(
                Contextor.getInstance().getContext(),
                R.anim.zoom_fade_out
        );
        btnNewPhotos.startAnimation(anim);
    }
}
