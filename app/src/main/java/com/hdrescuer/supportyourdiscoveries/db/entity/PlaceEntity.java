package com.hdrescuer.supportyourdiscoveries.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.Instant;
import java.util.ArrayList;

@Entity(tableName = "PLACE")
public class PlaceEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;


    public String title;
    public String description;
    double rating;
    public int author_id;
    public ArrayList<String> photo_paths;
    public String instant;
    double latitud;
    double longitud;
    String address;


    public PlaceEntity(String title, String description, double rating, int author_id, ArrayList<String> photo_paths, String instant, double latitud, double longitud, String address) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.author_id = author_id;
        this.photo_paths = photo_paths;
        this.instant = instant;
        this.latitud = latitud;
        this.longitud = longitud;
        this.address = address;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
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

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
