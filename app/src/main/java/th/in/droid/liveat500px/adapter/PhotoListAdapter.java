package th.in.droid.liveat500px.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import th.in.droid.liveat500px.manager.PhotoListManager;
import th.in.droid.liveat500px.view.PhotoListItem;

public class PhotoListAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        PhotoListManager listManager = PhotoListManager.getInstance();
        if (listManager.getDao() == null) {
            return 0;
        }
        if (listManager.getDao().getData() == null) {
            return 0;
        }

        return listManager.getDao().getData().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhotoListItem item;
        if (convertView != null) {
            item = (PhotoListItem) convertView;
        } else {
            item = new PhotoListItem((parent.getContext()));
        }
        return item;
    }
}
