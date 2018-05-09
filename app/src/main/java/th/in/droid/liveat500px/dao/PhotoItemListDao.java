package th.in.droid.liveat500px.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "success",
        "data"
})
public class PhotoItemListDao {

    @JsonProperty("success") private Boolean success;
    @JsonProperty("data") private List<PhotoItemDao> data = null;

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