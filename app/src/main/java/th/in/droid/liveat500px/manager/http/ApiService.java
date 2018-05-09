package th.in.droid.liveat500px.manager.http;

import retrofit2.Call;
import retrofit2.http.POST;
import th.in.droid.liveat500px.dao.PhotoItemListDao;

public interface ApiService {

    @POST
    Call<PhotoItemListDao> loadPhotoList();
}
