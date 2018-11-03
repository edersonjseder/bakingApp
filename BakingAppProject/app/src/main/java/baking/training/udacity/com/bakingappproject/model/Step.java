package baking.training.udacity.com.bakingappproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class Step implements Serializable {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("shortDescription")
    private String shortDescription;

    @JsonProperty("description")
    private String description;

    @JsonProperty("videoURL")
    private String videoURL;

    @JsonProperty("thumbnailURL")
    private String thumbnailURL;

    public Step() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Step)) return false;
        Step step = (Step) o;
        return Objects.equals(getId(), step.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
