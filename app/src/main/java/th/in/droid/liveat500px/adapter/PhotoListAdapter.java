package th.in.droid.liveat500px.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;

import th.in.droid.liveat500px.R;
import th.in.droid.liveat500px.dao.PhotoItemDao;
import th.in.droid.liveat500px.dao.PhotoItemListDao;
import th.in.droid.liveat500px.datatype.MutableInteger;
import th.in.droid.liveat500px.view.PhotoListItem;

public class PhotoListAdapter extends BaseAdapter {

    private PhotoItemListDao dao;
    private MutableInteger lastPositionInteger;

    public PhotoListAdapter(MutableInteger lastPositionInteger) {
        this.lastPositionInteger = lastPositionInteger;
    }

    public void setDao(PhotoItemListDao dao) {
        this.dao = dao;
    }

    @Override
    public int getCount() {
        if (dao == null) {
            return 1;
        }
        if (dao.getData() == null) {
            return 1;
        }

        return dao.getData().size() + 1;
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position == getCount() - 1 ? 1 : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == 1) {
            ProgressBar item;
            if (convertView != null) {
                item = (ProgressBar) convertView;
            } else {
                item = new ProgressBar(parent.getContext());
            }
            return item;
        }
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

        if (position > lastPositionInteger.getValue()) {
            Animation animation = AnimationUtils.loadAnimation(parent.getContext(), R.anim.up_from_bottom);
            item.setAnimation(animation);
            lastPositionInteger.setValue(position);
        }

        return item;
    }

    public void increaseLastPosition(int amount) {
        lastPositionInteger.setValue(lastPositionInteger.getValue() + amount);
    }
}
