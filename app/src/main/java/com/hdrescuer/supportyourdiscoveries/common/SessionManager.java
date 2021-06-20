package com.hdrescuer.supportyourdiscoveries.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

public class SessionManager {


    private static String TAG = SessionManager.class.getSimpleName();

    Context context;


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    int PRIVATE_MODE = 0;


    private static final String PREF_NAME = "TEST";

    private static final String KEY_IS_LOGGED_IN = "IS_LOGGED_IN";


    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);

        editor.commit();

    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void setInitials(String username, String email, String photo){
        editor.putString("USERNAME",username);
        editor.putString("EMAIL",email);
        editor.putString("PHOTO",photo);
        editor.commit();
    }

    public ArrayList<String> getInitials(){

        ArrayList<String> tmp = new ArrayList<>();
        tmp.add(sharedPreferences.getString("USERNAME",""));
        tmp.add(sharedPreferences.getString("EMAIL",""));
        tmp.add(sharedPreferences.getString("PHOTO",""));

        return  tmp;
    }

    public void updatePhoto(String photo){
        editor.putString("PHOTO",photo);
        editor.commit();
    }
}
