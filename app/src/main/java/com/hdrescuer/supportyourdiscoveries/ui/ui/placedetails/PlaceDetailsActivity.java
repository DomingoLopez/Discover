package com.hdrescuer.supportyourdiscoveries.ui.ui.placedetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.hdrescuer.supportyourdiscoveries.R;
import com.hdrescuer.supportyourdiscoveries.common.Constants;
import com.hdrescuer.supportyourdiscoveries.common.ScreenSlidePagerAdapter;
import com.hdrescuer.supportyourdiscoveries.data.MyPlacesListViewModel;
import com.hdrescuer.supportyourdiscoveries.data.PlaceDetailsViewModel;
import com.hdrescuer.supportyourdiscoveries.db.entity.AddressShort;
import com.hdrescuer.supportyourdiscoveries.db.entity.AuthorPlaceValorationEntity;
import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;
import com.hdrescuer.supportyourdiscoveries.ui.ui.myplaces.createplace.NewPlaceDialogFragment;
import com.hdrescuer.supportyourdiscoveries.ui.ui.myplaces.createplace.ScreenSlidePageFragment;
import com.hdrescuer.supportyourdiscoveries.ui.ui.places.LatestPlacesRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlaceDetailsActivity extends AppCompatActivity implements View.OnClickListener, RatingBar.OnRatingBarChangeListener {

    ImageView btnBack;
    TextView title;
    TextView address;
    TextView description;
    TextView vote;
    ViewPager2 viewpager;

    Button btnMaps;
    RatingBar ratingBar;

    PlaceDetailsViewModel placeDetailsViewModel;

    int id;
    PlaceEntity place;
    AuthorPlaceValorationEntity authorPlaceValorationEntity;

    int position_array_last_photo;
    boolean canIVote = false;

    //Parámetros para Maps
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        getSupportActionBar().hide();

        this.id = this.getIntent().getIntExtra("id",0);

        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        findViews();
        initViewModel();
        loadData();


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    //Inicializamos location
                    Location location = task.getResult();

                    if (location != null) {
                        try {
                            //Inicializamos Geocoder
                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            AddressShort address_location = new AddressShort(addresses.get(0).getAddressLine(0), addresses.get(0).getLatitude(), addresses.get(0).getLongitude());


                            Location loc1 = new Location("");
                            loc1.setLatitude(place.getAddress_paths().get(position_array_last_photo).getLatitud());
                            loc1.setLongitude(place.getAddress_paths().get(position_array_last_photo).getLongitud());

                            Location loc2 = new Location("");
                            loc2.setLatitude(address_location.getLatitud());
                            loc2.setLongitude(address_location.getLongitud());

                            float distanceInMeters = loc1.distanceTo(loc2);
                            Log.i("DISTANCE",""+Float.toString(distanceInMeters));

                            if (authorPlaceValorationEntity != null && !authorPlaceValorationEntity.isVisited()) {
                                if (distanceInMeters > 200) {
                                    vote.setText("Aún no has visitado este lugar. ¡Llega hasta el lugar para valorarlo!");
                                    ratingBar.setIsIndicator(true);
                                    canIVote = false;
                                } else {
                                    vote.setText("¿Qué te parece el luga? Estás a menos de 500 metros");
                                    ratingBar.setIsIndicator(false);
                                    canIVote = true;
                                }
                            } else if (authorPlaceValorationEntity != null && authorPlaceValorationEntity.isVisited()) {
                                vote.setText("Tu valoración");
                                ratingBar.setIsIndicator(true);
                                canIVote = true;

                            } else if (authorPlaceValorationEntity == null) {
                                if (distanceInMeters > 200) {
                                    vote.setText("Aún no has visitado este lugar. ¡Llega hasta el lugar para valorarlo!");
                                    ratingBar.setIsIndicator(true);
                                    canIVote = false;
                                } else {
                                    vote.setText("¿Qué te parece el lugar? Estás a menos de 500 metros");
                                    ratingBar.setIsIndicator(false);
                                    canIVote = true;
                                }

                            } else {
                                vote.setText("Tu valoración");
                                ratingBar.setIsIndicator(true);
                                canIVote = true;
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });

        }


        if(authorPlaceValorationEntity != null){
            ratingBar.setRating(authorPlaceValorationEntity.valoration);
        }else{
            ratingBar.setRating(0);
        }


    }






    private void initViewModel() {

        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new PlaceDetailsViewModel(getApplication(),id);
            }
        };



        this.placeDetailsViewModel = new ViewModelProvider(this, factory).get(PlaceDetailsViewModel.class);

        this.placeDetailsViewModel.place.observe(this, new Observer<PlaceEntity>() {
            @Override
            public void onChanged(PlaceEntity place_ent) {

                place = place_ent;

            }
        });

        this.placeDetailsViewModel.authorPlaceValoration.observe(this, new Observer<AuthorPlaceValorationEntity>() {
            @Override
            public void onChanged(AuthorPlaceValorationEntity authplace_ent) {

                authorPlaceValorationEntity = authplace_ent;

            }
        });


        this.place = this.placeDetailsViewModel.place.getValue();
        this.authorPlaceValorationEntity = this.placeDetailsViewModel.authorPlaceValoration.getValue();
    }

    private void loadData() {
        this.viewpager.setAdapter(new ScreenSlidePagerAdapter(this,this.place.getPhoto_paths(),this.place.getAddress_paths()));

        this.title.setText(this.place.getTitle());
        this.description.setText(this.place.getDescription());

        if(this.authorPlaceValorationEntity != null){
            this.ratingBar.setNumStars(this.authorPlaceValorationEntity.valoration);
            this.canIVote = true;
        }else{
            this.canIVote = false;
        }

    }



    private void findViews() {
        this.btnBack = findViewById(R.id.backDetails);
        this.title = findViewById(R.id.titleDetails);
        this.description = findViewById(R.id.detailsDescription);
        this.viewpager = findViewById(R.id.viewPagerDetails);
        this.btnMaps = findViewById(R.id.btnMaps);
        this.ratingBar = findViewById(R.id.ratingBarPlace);
        this.vote = findViewById(R.id.tvVoteThisPlace);

        this.ratingBar.setOnRatingBarChangeListener(this);

        this.btnBack.setOnClickListener(this);

        this.btnMaps.setOnClickListener(this);


    }




    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.backDetails:

                finish();

                break;

            case R.id.btnMaps:

                this.position_array_last_photo = this.viewpager.getCurrentItem();
                String latitud = Double.toString(this.place.getAddress_paths().get(this.position_array_last_photo).getLatitud());
                String longitud = Double.toString(this.place.getAddress_paths().get(this.position_array_last_photo).getLongitud());

                String action = "google.navigation:q="+latitud+","+longitud+"&mode=w";

                Uri gmmIntentUri = Uri.parse(action);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

                break;
        }

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

        if(fromUser){

            if(this.authorPlaceValorationEntity == null) {

                AuthorPlaceValorationEntity authorPlaceValorationEntity = new AuthorPlaceValorationEntity(
                        Constants.USERNAME, this.id, Math.round(rating), true
                );
                this.placeDetailsViewModel.insertValoration(authorPlaceValorationEntity);
                this.authorPlaceValorationEntity = authorPlaceValorationEntity;

            }else{

                AuthorPlaceValorationEntity authorPlaceValorationEntity = new AuthorPlaceValorationEntity(
                        Constants.USERNAME, this.id, Math.round(rating), true
                );
                this.placeDetailsViewModel.updateValoration(authorPlaceValorationEntity);
                this.authorPlaceValorationEntity = authorPlaceValorationEntity;

            }

        }else{
            ratingBar.setRating(rating);
        }


    }
}
