package th.in.droid.liveat500px.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "success",
        "data"
})
public class PhotoItemListDao implements Parcelable {

    @JsonProperty("success") private Boolean success;
    @JsonProperty("data") private List<PhotoItemDao> data = null;

    public PhotoItemListDao() {}

    protected PhotoItemListDao(Parcel in) {
        byte tmpSuccess = in.readByte();
        success = tmpSuccess == 0 ? null : tmpSuccess == 1;
        data = in.createTypedArrayList(PhotoItemDao.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success == null ? 0 : success ? 1 : 2));
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PhotoItemListDao> CREATOR = new Creator<PhotoItemListDao>() {
        @Override
        public PhotoItemListDao createFromParcel(Parcel in) {
            return new PhotoItemListDao(in);
        }

        @Override
        public PhotoItemListDao[] newArray(int size) {
            return new PhotoItemListDao[size];
        }
    };

    @JsonProperty("success")
    public Boolean getSuccess() {
        return success;
    }

    @JsonProperty("success")
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @JsonProperty("data")
    public List<PhotoItemDao> getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(List<PhotoItemDao> data) {
        this.data = data;
    }

}