package com.hdrescuer.supportyourdiscoveries.ui.ui.placedetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hdrescuer.supportyourdiscoveries.R;
import com.hdrescuer.supportyourdiscoveries.common.ScreenSlidePagerAdapter;
import com.hdrescuer.supportyourdiscoveries.data.MyPlacesListViewModel;
import com.hdrescuer.supportyourdiscoveries.data.PlaceDetailsViewModel;
import com.hdrescuer.supportyourdiscoveries.db.entity.AuthorPlaceValorationEntity;
import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;
import com.hdrescuer.supportyourdiscoveries.ui.ui.myplaces.createplace.ScreenSlidePageFragment;
import com.hdrescuer.supportyourdiscoveries.ui.ui.places.LatestPlacesRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlaceDetailsActivity extends AppCompatActivity implements View.OnClickListener {

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        getSupportActionBar().hide();

        this.id = this.getIntent().getIntExtra("id",0);

        findViews();
        initViewModel();
        loadData();



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

        if(this.authorPlaceValorationEntity!= null && !this.authorPlaceValorationEntity.isVisited()){
            this.vote.setText("Aún no has visitado este lugar. ¡Llega hasta el lugar para valorarlo!");
        }else{
            this.vote.setText("¿Qué te parece el lugar visitado?");
        }

    }



    private void findViews() {
        this.btnBack = findViewById(R.id.backDetails);
        this.title = findViewById(R.id.titleDetails);
        this.address = findViewById(R.id.addressDetails);
        this.description = findViewById(R.id.detailsDescription);
        this.viewpager = findViewById(R.id.viewPagerDetails);
        this.btnMaps = findViewById(R.id.btnMaps);
        this.ratingBar = findViewById(R.id.ratingBarPlace);
        this.vote = findViewById(R.id.tvVoteThisPlace);

        this.btnBack.setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.backDetails:

                finish();

                break;
        }

    }



}