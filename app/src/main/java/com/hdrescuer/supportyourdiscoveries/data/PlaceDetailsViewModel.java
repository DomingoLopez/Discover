package com.hdrescuer.supportyourdiscoveries.data;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hdrescuer.supportyourdiscoveries.common.Constants;
import com.hdrescuer.supportyourdiscoveries.common.MyApp;
import com.hdrescuer.supportyourdiscoveries.data.dbrepositories.AuthorPlaceValorationRepository;
import com.hdrescuer.supportyourdiscoveries.data.dbrepositories.PlaceRepository;
import com.hdrescuer.supportyourdiscoveries.db.entity.AuthorPlaceValorationEntity;
import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;

import java.util.List;

public class PlaceDetailsViewModel extends ViewModel {

    private PlaceRepository placeRepository;
    public MutableLiveData<PlaceEntity> place;

    private AuthorPlaceValorationRepository authorPlaceValorationRepository;
    public MutableLiveData<AuthorPlaceValorationEntity> authorPlaceValoration;
    int id;

    public PlaceDetailsViewModel(Application application,int id) {
        this.id = id;
        this.placeRepository = new PlaceRepository(MyApp.getInstance());
        this.authorPlaceValorationRepository = new AuthorPlaceValorationRepository(MyApp.getInstance());
        place = getPlaceDetails();
        authorPlaceValoration = getAuthorPlaceValoration();
    }



    public MutableLiveData<PlaceEntity> getPlaceDetails(){
        if(this.place == null)
            place = new MutableLiveData<>();

        return this.placeRepository.getPlaceById(id);
    }

    public MutableLiveData<AuthorPlaceValorationEntity> getAuthorPlaceValoration(){

        if(this.authorPlaceValoration != null)
            authorPlaceValoration = new MutableLiveData<>();

        return this.authorPlaceValorationRepository.getAuthorPlaceValoration(Constants.USERNAME,this.id);
    }



    public int getMaxId(){
        return this.placeRepository.getMaxId();
    }

    public void deletePlace(int id){
        this.placeRepository.deleteById(id);
    }




}
