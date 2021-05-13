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
    float rating;
    public String author_name;
    public ArrayList<String> photo_paths;
    public String instant;
    double latitud;
    double longitud;
    String address;


    public PlaceEntity(String title, String description, float rating, String author_name, ArrayList<String> photo_paths, String instant, double latitud, double longitud, String address) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.author_name = author_name;
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
