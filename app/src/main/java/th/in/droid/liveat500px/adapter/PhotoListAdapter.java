package th.in.droid.liveat500px.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import th.in.droid.liveat500px.R;
import th.in.droid.liveat500px.dao.PhotoItemDao;
import th.in.droid.liveat500px.dao.PhotoItemListDao;
import th.in.droid.liveat500px.view.PhotoListItem;

public class PhotoListAdapter extends BaseAdapter {

    private PhotoItemListDao dao;
    private int lastPosition = -1;

    public void setDao(PhotoItemListDao dao) {
        this.dao = dao;
    }

    @Override
    public int getCount() {
        if (dao == null) {
            return 0;
        }
        if (dao.getData() == null) {
            return 0;
        }

        return dao.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return dao.getData().get(position);
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
        PhotoItemDao dao = (PhotoItemDao) getItem(position);
        item.setNameText(dao.getCaption());
        item.setDescriptionText(dao.getUsername() + "\n" + dao.getCamera());
        item.setImageUrl(dao.getImageUrl());

        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(parent.getContext(), R.anim.up_from_bottom);
            item.setAnimation(animation);
            lastPosition = position;
        }

        return item;
    }
}
