package com.hdrescuer.supportyourdiscoveries.db.entity;

import android.location.Address;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.Instant;
import java.util.ArrayList;

@Entity(tableName = "PLACE")
public class PlaceEntity {

    @PrimaryKey
    public int id;
    public String title;
    public String description;
    float rating;
    public String author_name;
    public ArrayList<String> photo_paths;
    public ArrayList<AddressShort> address_paths;
    public String instant;



    public PlaceEntity(int id, String title, String description, float rating, String author_name, ArrayList<String> photo_paths, ArrayList<AddressShort> address_paths, String instant) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.author_name = author_name;
        this.photo_paths = photo_paths;
        this.address_paths = address_paths;
        this.instant = instant;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public ArrayList<String> getPhoto_paths() {
        return photo_paths;
    }

    public void setPhoto_paths(ArrayList<String> photo_paths) {
        this.photo_paths = photo_paths;
    }

    public String getInstant() {
        return instant;
    }

    public void setInstant(String instant) {
        this.instant = instant;
    }


    public ArrayList<AddressShort> getAddress_paths() {
        return address_paths;
    }

    public void setAddress_paths(ArrayList<AddressShort> address_paths) {
        this.address_paths = address_paths;
    }
}
