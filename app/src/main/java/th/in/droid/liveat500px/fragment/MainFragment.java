package th.in.droid.liveat500px.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        listView = rootView.findViewById(R.id.listview);
        listAdapter = new PhotoListAdapter();
        listView.setAdapter(listAdapter);

        Call<PhotoItemListDao> call = HttpManager.getInstance().getService().loadPhotoList();
        call.enqueue(new Callback<PhotoItemListDao>() {
            @Override
            public void onResponse(Call<PhotoItemListDao> call, Response<PhotoItemListDao> response) {
                if (response.isSuccessful()) {
                    PhotoItemListDao dao = response.body();
                    PhotoListManager.getInstance().setDao(dao);
                    listAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity().getApplicationContext(), dao.getData().get(0).getCaption(), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Toast.makeText(getActivity().getApplicationContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PhotoItemListDao> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
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
