package th.in.droid.liveat500px.manager.http;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import th.in.droid.liveat500px.dao.PhotoItemListDao;

public interface ApiService {

    @POST("list")
    Call<PhotoItemListDao> loadPhotoList();

    @POST("list/after/{id}")
    Call<PhotoItemListDao> loadPhotoListAfterId(@Path("id") int id);

    @POST("list/before/{id}")
    Call<PhotoItemListDao> loadPhotoListBeforeId(@Path("id") int id);
}
