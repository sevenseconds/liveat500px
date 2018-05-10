package th.in.droid.liveat500px.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "link",
        "image_url",
        "caption",
        "user_id",
        "username",
        "profile_picture",
        "tags",
        "created_time",
        "camera",
        "lens",
        "focal_length",
        "iso",
        "shutter_speed",
        "aperture"
})
public class PhotoItemDao implements Parcelable {

    @JsonProperty("id") private Integer id;
    @JsonProperty("link") private String link;
    @JsonProperty("image_url") private String imageUrl;
    @JsonProperty("caption") private String caption;
    @JsonProperty("user_id") private Integer userId;
    @JsonProperty("username") private String username;
    @JsonProperty("profile_picture") private String profilePicture;
    @JsonProperty("tags") private List<String> tags = null;
    @JsonProperty("created_time") private Date createdTime;
    @JsonProperty("camera") private String camera;
    @JsonProperty("lens") private String lens;
    @JsonProperty("focal_length") private String focalLength;
    @JsonProperty("iso") private String iso;
    @JsonProperty("shutter_speed") private String shutterSpeed;
    @JsonProperty("aperture") private String aperture;

    public PhotoItemDao() {}

    protected PhotoItemDao(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        link = in.readString();
        imageUrl = in.readString();
        caption = in.readString();
        if (in.readByte() == 0) {
            userId = null;
        } else {
            userId = in.readInt();
        }
        username = in.readString();
        profilePicture = in.readString();
        tags = in.createStringArrayList();
        camera = in.readString();
        lens = in.readString();
        focalLength = in.readString();
        iso = in.readString();
        shutterSpeed = in.readString();
        aperture = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(link);
        dest.writeString(imageUrl);
        dest.writeString(caption);
        if (userId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(userId);
        }
        dest.writeString(username);
        dest.writeString(profilePicture);
        dest.writeStringList(tags);
        dest.writeString(camera);
        dest.writeString(lens);
        dest.writeString(focalLength);
        dest.writeString(iso);
        dest.writeString(shutterSpeed);
        dest.writeString(aperture);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PhotoItemDao> CREATOR = new Creator<PhotoItemDao>() {
        @Override
        public PhotoItemDao createFromParcel(Parcel in) {
            return new PhotoItemDao(in);
        }

        @Override
        public PhotoItemDao[] newArray(int size) {
            return new PhotoItemDao[size];
        }
    };

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

    @JsonProperty("image_url")
    public String getImageUrl() {
        return imageUrl;
    }

    @JsonProperty("image_url")
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @JsonProperty("caption")
    public String getCaption() {
        return caption;
    }

    @JsonProperty("caption")
    public void setCaption(String caption) {
        this.caption = caption;
    }

    @JsonProperty("user_id")
    public Integer getUserId() {
        return userId;
    }

    @JsonProperty("user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("profile_picture")
    public String getProfilePicture() {
        return profilePicture;
    }

    @JsonProperty("profile_picture")
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @JsonProperty("tags")
    public List<String> getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @JsonProperty("created_time")
    public Date getCreatedTime() {
        return createdTime;
    }

    @JsonProperty("created_time")
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @JsonProperty("camera")
    public String getCamera() {
        return camera;
    }

    @JsonProperty("camera")
    public void setCamera(String camera) {
        this.camera = camera;
    }

    @JsonProperty("lens")
    public String getLens() {
        return lens;
    }

    @JsonProperty("lens")
    public void setLens(String lens) {
        this.lens = lens;
    }

    @JsonProperty("focal_length")
    public String getFocalLength() {
        return focalLength;
    }

    @JsonProperty("focal_length")
    public void setFocalLength(String focalLength) {
        this.focalLength = focalLength;
    }

    @JsonProperty("iso")
    public String getIso() {
        return iso;
    }

    @JsonProperty("iso")
    public void setIso(String iso) {
        this.iso = iso;
    }

    @JsonProperty("shutter_speed")
    public String getShutterSpeed() {
        return shutterSpeed;
    }

    @JsonProperty("shutter_speed")
    public void setShutterSpeed(String shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
    }

    @JsonProperty("aperture")
    public String getAperture() {
        return aperture;
    }

    @JsonProperty("aperture")
    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

}