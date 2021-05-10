package com.hdrescuer.supportyourdiscoveries.data.dbrepositories;

import android.app.Application;
import android.os.AsyncTask;

import com.hdrescuer.supportyourdiscoveries.db.DiscoveriesDataBase;
import com.hdrescuer.supportyourdiscoveries.db.dao.PlaceDao;
import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;

import java.util.List;

public class PlaceRepository{


    private PlaceDao placeDao;




    public PlaceRepository(Application application) {

        DiscoveriesDataBase db = DiscoveriesDataBase.getDataBase(application);
        placeDao = db.getPlaceDao();

    }


    public List<PlaceEntity> getAllPlaces(){ return placeDao.getAllPlaces();}

    public PlaceEntity getPlaceById(int id){return placeDao.getPlaceById(id);}

    public void deleteAllPlaces(){placeDao.deleteAll();}

    public void deleteById(int id){placeDao.deleteById(id);}


    public void insertPlace(PlaceEntity place){
        new insertPlaceAsyncTask(placeDao).execute(place);
    }

    public void updatePlace(PlaceEntity place){
        new updatePlaceAsyncTask(placeDao).execute(place);
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
    }

}