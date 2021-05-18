package com.hdrescuer.supportyourdiscoveries.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hdrescuer.supportyourdiscoveries.common.MyApp;
import com.hdrescuer.supportyourdiscoveries.data.dbrepositories.PlaceRepository;
import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;

import java.util.List;

public class MyPlacesListViewModel extends ViewModel {

    private PlaceRepository placeRepository;
    private MutableLiveData<List<PlaceEntity>> places;

    public MyPlacesListViewModel() {
        this.placeRepository = new PlaceRepository(MyApp.getInstance());
        places = getPlaces();
    }


    public MutableLiveData<List<PlaceEntity>> getPlaces(){
        return this.placeRepository.getAllPlacesByAuthor();
    }

    public MutableLiveData<List<PlaceEntity>> getAllPlaces(){
        return this.placeRepository.getAllPlaces();
    }


    public void insertPlace(PlaceEntity placeEntity){
        this.placeRepository.insertPlace(placeEntity);
    }

    public void updatePlace(PlaceEntity placeEntity){
        this.placeRepository.updatePlace(placeEntity);
    }

    public int getMaxId(){
        return this.placeRepository.getMaxId();
    }

    public void deletePlace(int id){
        this.placeRepository.deleteById(id);
    }


    public PlaceEntity getPlaceDetails(int id){
        return this.placeRepository.getPlaceById_no_nut(id);
    }




}