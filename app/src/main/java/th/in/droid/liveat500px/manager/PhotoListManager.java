package th.in.droid.liveat500px.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.io.IOException;
import java.util.ArrayList;

import th.in.droid.liveat500px.dao.PhotoItemDao;
import th.in.droid.liveat500px.dao.PhotoItemListDao;

public class PhotoListManager {

    private Context mContext;
    private PhotoItemListDao dao;

    public PhotoListManager() {
        mContext = Contextor.getInstance().getContext();
        loadCache();
    }

    public PhotoItemListDao getDao() {
        return dao;
    }

    public void setDao(PhotoItemListDao dao) {
        this.dao = dao;
        saveCache();
    }

    public void insertDaoAtTopPosition(PhotoItemListDao newDao) {
        if (dao == null) {
            dao = new PhotoItemListDao();
        }
        if (dao.getData() == null) {
            dao.setData(new ArrayList<PhotoItemDao>());
        }
        dao.getData().addAll(0, newDao.getData());
        saveCache();
    }

    public void appendDaoAtBottomPosition(PhotoItemListDao newDao) {
        if (dao == null) {
            dao = new PhotoItemListDao();
        }
        if (dao.getData() == null) {
            dao.setData(new ArrayList<PhotoItemDao>());
        }
        dao.getData().addAll(dao.getData().size(), newDao.getData());
        saveCache();
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

    public int getMinId() {
        if (dao == null) {
            return 0;
        }
        if (dao.getData() == null) {
            return 0;
        }
        if (dao.getData().size() == 0) {
            return 0;
        }

        int minId = dao.getData().get(0).getId();
        for (int i = 1; i < dao.getData().size(); i++) {
            minId = Math.min(minId, dao.getData().get(i).getId());
        }

        return minId;
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

    public Bundle onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("dao", dao);
        return bundle;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        dao = savedInstanceState.getParcelable("dao");
    }

    private void saveCache() {
        PhotoItemListDao cacheDao = new PhotoItemListDao();
        String json = null;
        if (dao != null && dao.getData() != null) {
            cacheDao.setData(dao.getData().subList(0, Math.min(50, dao.getData().size())));
            try {
                ObjectMapper mapper = new ObjectMapper();
                json = mapper.writeValueAsString(cacheDao);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        SharedPreferences prefs = mContext.getSharedPreferences("photos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if (json != null) {
            editor.putString("json", json);
        }
        editor.apply();
    }

    private void loadCache() {
        SharedPreferences prefs = mContext.getSharedPreferences("photos", Context.MODE_PRIVATE);
        String json = prefs.getString("json", null);
        if (json == null) {
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            dao = mapper.readValue(json, PhotoItemListDao.class);
        } catch (IOException e) {
            dao = new PhotoItemListDao();
        }
    }
}
