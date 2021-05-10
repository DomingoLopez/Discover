package com.hdrescuer.supportyourdiscoveries.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyPlacesListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyPlacesListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is photo fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}