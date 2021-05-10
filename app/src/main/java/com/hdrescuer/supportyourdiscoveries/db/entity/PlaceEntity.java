package com.hdrescuer.supportyourdiscoveries.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PLACE")
public class PlaceEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;


    public String title;
    Double rating;
    public int author_id;
    public String main_photo_url;

    public PlaceEntity(String title, Double rating, int author_id, String main_photo_url) {
        this.title = title;
        this.rating = rating;
        this.author_id = author_id;
        this.main_photo_url = main_photo_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getMain_photo_url() {
        return main_photo_url;
    }

    public void setMain_photo_url(String main_photo_url) {
        this.main_photo_url = main_photo_url;
    }
}
