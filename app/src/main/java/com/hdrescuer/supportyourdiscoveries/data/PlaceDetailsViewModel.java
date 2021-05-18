package com.hdrescuer.supportyourdiscoveries.data;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hdrescuer.supportyourdiscoveries.common.MyApp;
import com.hdrescuer.supportyourdiscoveries.data.dbrepositories.PlaceRepository;
import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;

import java.util.List;

public class PlaceDetailsViewModel extends ViewModel {

    private PlaceRepository placeRepository;
    public MutableLiveData<PlaceEntity> place;

    int id;

    public PlaceDetailsViewModel(Application application,int id) {
        this.id = id;
        this.placeRepository = new PlaceRepository(MyApp.getInstance());
        place = getPlaceDetails();
    }



    public MutableLiveData<PlaceEntity> getPlaceDetails(){
        if(this.place == null)
            place = new MutableLiveData<>();

        return this.placeRepository.getPlaceById(id);
    }


    public int getMaxId(){
        return this.placeRepository.getMaxId();
    }

    public void deletePlace(int id){
        this.placeRepository.deleteById(id);
    }




}
