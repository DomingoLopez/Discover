package com.hdrescuer.supportyourdiscoveries.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "AUTHOR")
public class AuthorEntity {

    @PrimaryKey @NonNull
    public String username;


    String email;
    String password;
    public String main_photo_url;

    public AuthorEntity(String username, String email, String password, String main_photo_url) {
        this.username = username;
        this.email  = email;
        this.password = password;
        this.main_photo_url = main_photo_url;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMain_photo_url() {
        return main_photo_url;
    }

    public void setMain_photo_url(String main_photo_url) {
        this.main_photo_url = main_photo_url;
    }
}

