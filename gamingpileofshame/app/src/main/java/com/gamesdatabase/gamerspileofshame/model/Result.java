
package com.gamesdatabase.gamerspileofshame.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("releaseDate")
    @Expose
    private String releaseDate;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("genre")
    @Expose
    private List<String> genre = null;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("developer")
    @Expose
    private String developer;
    @SerializedName("publisher")
    @Expose
    private List<String> publisher = null;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("alsoAvailableOn")
    @Expose
    private List<Object> alsoAvailableOn = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public List<String> getPublisher() {
        return publisher;
    }

    public void setPublisher(List<String> publisher) {
        this.publisher = publisher;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<Object> getAlsoAvailableOn() {
        return alsoAvailableOn;
    }

    public void setAlsoAvailableOn(List<Object> alsoAvailableOn) {
        this.alsoAvailableOn = alsoAvailableOn;
    }

}
