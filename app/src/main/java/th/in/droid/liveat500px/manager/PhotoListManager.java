package th.in.droid.liveat500px.manager;

import android.content.Context;
import android.widget.ListView;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.ArrayList;

import th.in.droid.liveat500px.dao.PhotoItemDao;
import th.in.droid.liveat500px.dao.PhotoItemListDao;

public class PhotoListManager {

    private Context mContext;
    private PhotoItemListDao dao;

    public PhotoListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public PhotoItemListDao getDao() {
        return dao;
    }

    public void setDao(PhotoItemListDao dao) {
        this.dao = dao;
    }

    public void insertDaoAtTopPosition(PhotoItemListDao newDao) {
        if (dao == null) {
            dao = new PhotoItemListDao();
        }
        if (dao.getData() == null) {
            dao.setData(new ArrayList<PhotoItemDao>());
        }
        dao.getData().addAll(0, newDao.getData());
    }

    public int getMaxId() {
        if (dao == null) {
            return 0;
        }
        if (dao.getData() == null) {
            return 0;
        }
        if (dao.getData().size() == 0) {
            return 0;
        }

        int maxId = dao.getData().get(0).getId();
        for (int i = 1; i < dao.getData().size(); i++) {
            maxId = Math.max(maxId, dao.getData().get(i).getId());
        }

        return maxId;
    }

    public int getCount() {
        if (dao == null) {
            return 0;
        }
        if (dao.getData() == null) {
            return 0;
        }
        return dao.getData().size();
    }
}
