package com.hdrescuer.supportyourdiscoveries.data;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hdrescuer.supportyourdiscoveries.common.Constants;
import com.hdrescuer.supportyourdiscoveries.common.MyApp;
import com.hdrescuer.supportyourdiscoveries.data.dbrepositories.AuthorRepository;
import com.hdrescuer.supportyourdiscoveries.data.dbrepositories.PlaceRepository;
import com.hdrescuer.supportyourdiscoveries.db.entity.AuthorEntity;
import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;

public class ProfileViewModel extends ViewModel {

    private AuthorRepository authorRepository;
    public MutableLiveData<PlaceEntity> place;


    String username;

    public ProfileViewModel() {
        this.username = Constants.USERNAME;
        this.authorRepository = new AuthorRepository(MyApp.getInstance());

    }



    public AuthorEntity getAuthorByUsername(String username){
        return  this.authorRepository.getAuthorById(username);
    }

    public void updateAuthor(AuthorEntity authorEntity){
        this.authorRepository.updateAuthor(authorEntity);
    }











}
