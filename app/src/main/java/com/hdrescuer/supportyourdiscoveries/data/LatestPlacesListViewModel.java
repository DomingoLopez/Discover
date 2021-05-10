package com.hdrescuer.supportyourdiscoveries.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LatestPlacesListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LatestPlacesListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tendencias fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}