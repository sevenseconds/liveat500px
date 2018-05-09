package th.in.droid.liveat500px.manager;

import android.content.Context;
import android.widget.ListView;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import th.in.droid.liveat500px.dao.PhotoItemListDao;

public class PhotoListManager {

    private static PhotoListManager instance;

    public static PhotoListManager getInstance() {
        if (instance == null)
            instance = new PhotoListManager();
        return instance;
    }

    private Context mContext;
    private PhotoItemListDao dao;

    private PhotoListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public PhotoItemListDao getDao() {
        return dao;
    }

    public void setDao(PhotoItemListDao dao) {
        this.dao = dao;
    }
}
