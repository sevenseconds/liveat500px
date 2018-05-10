package th.in.droid.liveat500px.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

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

        int mode;

        PhotoListLoadCallback(int mode) {
            this.mode = mode;
        }

        @Override
        public void onResponse(Call<PhotoItemListDao> call, Response<PhotoItemListDao> response) {
            swipeRefreshLayout.setRefreshing(false);
            if (response.isSuccessful()) {
                PhotoItemListDao dao = response.body();
                if (mode == MODE_RELOAD_NEWER) {
                    photoListManager.insertDaoAtTopPosition(dao);
                } else {
                    photoListManager.setDao(dao);
                }
                listAdapter.setDao(photoListManager.getDao());
                listAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity().getApplicationContext(), "Load Completed", Toast.LENGTH_SHORT).show();
            } else {
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
}
