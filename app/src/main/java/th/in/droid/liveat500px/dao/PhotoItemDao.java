package th.in.droid.liveat500px.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
public class PhotoItemDao {

    @JsonProperty("id") private Integer id;
    @JsonProperty("link") private String link;
    @JsonProperty("image_url") private String imageUrl;
    @JsonProperty("caption") private String caption;
    @JsonProperty("user_id") private Integer userId;
    @JsonProperty("username") private String username;
    @JsonProperty("profile_picture") private String profilePicture;
    @JsonProperty("tags") private List<String> tags = null;
    @JsonProperty("created_time") private String createdTime;
    @JsonProperty("camera") private Object camera;
    @JsonProperty("lens") private Object lens;
    @JsonProperty("focal_length") private Object focalLength;
    @JsonProperty("iso") private Object iso;
    @JsonProperty("shutter_speed") private Object shutterSpeed;
    @JsonProperty("aperture") private Object aperture;

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
    public String getCreatedTime() {
        return createdTime;
    }

    @JsonProperty("created_time")
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    @JsonProperty("camera")
    public Object getCamera() {
        return camera;
    }

    @JsonProperty("camera")
    public void setCamera(Object camera) {
        this.camera = camera;
    }

    @JsonProperty("lens")
    public Object getLens() {
        return lens;
    }

    @JsonProperty("lens")
    public void setLens(Object lens) {
        this.lens = lens;
    }

    @JsonProperty("focal_length")
    public Object getFocalLength() {
        return focalLength;
    }

    @JsonProperty("focal_length")
    public void setFocalLength(Object focalLength) {
        this.focalLength = focalLength;
    }

    @JsonProperty("iso")
    public Object getIso() {
        return iso;
    }

    @JsonProperty("iso")
    public void setIso(Object iso) {
        this.iso = iso;
    }

    @JsonProperty("shutter_speed")
    public Object getShutterSpeed() {
        return shutterSpeed;
    }

    @JsonProperty("shutter_speed")
    public void setShutterSpeed(Object shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
    }

    @JsonProperty("aperture")
    public Object getAperture() {
        return aperture;
    }

    @JsonProperty("aperture")
    public void setAperture(Object aperture) {
        this.aperture = aperture;
    }

}