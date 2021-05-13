package com.hdrescuer.supportyourdiscoveries.data.dbrepositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.hdrescuer.supportyourdiscoveries.common.Constants;
import com.hdrescuer.supportyourdiscoveries.db.DiscoveriesDataBase;
import com.hdrescuer.supportyourdiscoveries.db.dao.PlaceDao;
import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;

import java.util.ArrayList;
import java.util.List;

public class PlaceRepository{


    private PlaceDao placeDao;
    MutableLiveData<List<PlaceEntity>> places;




    public PlaceRepository(Application application) {

        DiscoveriesDataBase db = DiscoveriesDataBase.getDataBase(application);
        placeDao = db.getPlaceDao();

    }


    public MutableLiveData<List<PlaceEntity>> getAllPlaces(){
        if(this.places == null)
            this.places = new MutableLiveData<>();

        List<PlaceEntity> places;
        places = this.placeDao.getAllPlaces();

        this.places.setValue(places);

        return this.places;

    }

    public MutableLiveData<List<PlaceEntity>> getAllPlacesByAuthor(){
        if(this.places == null)
            this.places = new MutableLiveData<>();

        List<PlaceEntity> places;
        places = this.placeDao.getAllPlacesByAuthor(Constants.ID);

        this.places.setValue(places);

        return this.places;

    }

    public PlaceEntity getPlaceById(int id){return placeDao.getPlaceById(id);}

    public void deleteAllPlaces(){placeDao.deleteAll();}

    public void deleteById(int id){
        placeDao.deleteById(id);

        refreshPlaces(null,"DELETE");
    }


    public void insertPlace(PlaceEntity place){
        new insertPlaceAsyncTask(placeDao).execute(place);

        refreshPlaces(place,"INSERT");
    }

    public void updatePlace(PlaceEntity place){
        new updatePlaceAsyncTask(placeDao).execute(place);

        refreshPlaces(place,"UPDATE");
    }

    private static class insertPlaceAsyncTask extends AsyncTask<PlaceEntity, Void, Void>{

        private PlaceDao placeDaoAsyncTask;


        insertPlaceAsyncTask(PlaceDao placeDao){
            placeDaoAsyncTask = placeDao;
        }

        @Override
        protected Void doInBackground(PlaceEntity... placeEntities) {

            placeDaoAsyncTask.insert(placeEntities[0]);
            return null;
        }
    }

    private static class updatePlaceAsyncTask extends AsyncTask<PlaceEntity, Void, Void>{

        private PlaceDao placeDaoAsyncTask;


        updatePlaceAsyncTask(PlaceDao placeDao){
            placeDaoAsyncTask = placeDao;
        }

        @Override
        protected Void doInBackground(PlaceEntity... placeEntities) {

            placeDaoAsyncTask.update(placeEntities[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }




    public void refreshPlaces(PlaceEntity placeEntity,String action){

        switch (action){
            case "INSERT":
                ArrayList<PlaceEntity> tmp = (ArrayList<PlaceEntity>) this.places.getValue();
                tmp.add(placeEntity);
                this.places.setValue(tmp);
                break;

            case "UPDATE":
                ArrayList<PlaceEntity> tmp1 = (ArrayList<PlaceEntity>) this.places.getValue();

                for(int i = 0; i< tmp1.size(); i++)
                    if(tmp1.get(i).getId() == placeEntity.getId())
                        tmp1.set(i,placeEntity);
                this.places.setValue(tmp1);

                break;

            case "DELETE":
                this.places = getAllPlacesByAuthor();
                break;
        }

        if(placeEntity != null) {

        }else{





        }
    }

}