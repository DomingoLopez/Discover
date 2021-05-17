package com.hdrescuer.supportyourdiscoveries.ui.ui.placedetails;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hdrescuer.supportyourdiscoveries.R;

public class PlaceDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        getSupportActionBar().hide();



    }
}