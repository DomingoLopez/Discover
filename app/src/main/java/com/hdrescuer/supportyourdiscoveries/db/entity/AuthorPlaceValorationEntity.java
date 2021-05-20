package com.hdrescuer.supportyourdiscoveries.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "AUTHORPLACEVALORATION", primaryKeys = {"username", "place_id"})
public class AuthorPlaceValorationEntity {

    @NonNull
    public String username;
    @NonNull
    public int place_id;
    public int valoration;
    public boolean visited;


    public AuthorPlaceValorationEntity(String username, int place_id, int valoration, boolean visited) {
        this.username = username;
        this.place_id = place_id;
        this.valoration = valoration;
        this.visited = visited;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public float getValoration() {
        return valoration;
    }

    public void setValoration(int valoration) {
        this.valoration = valoration;
    }

    public int getPlace_id() {
        return place_id;
    }

    public void setPlace_id(int place_id) {
        this.place_id = place_id;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}

