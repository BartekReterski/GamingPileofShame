
package com.gamesdatabase.gamerspileofshame.model;

import io.realm.RealmObject;

public class ResultFinished extends RealmObject {



    private String title;

    private String releaseDate;

    private String description;

    private String genre;

    private String image;

    private Integer score;

    private String developer;

    private String publisher;

    private String rating;

    private String alsoAvailableOn;
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


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAlsoAvailableOn() {
        return alsoAvailableOn;
    }

    public void setAlsoAvailableOn(String alsoAvailableOn) {
        this.alsoAvailableOn = alsoAvailableOn;
    }
}
