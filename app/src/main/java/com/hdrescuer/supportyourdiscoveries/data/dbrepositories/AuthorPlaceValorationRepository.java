package com.hdrescuer.supportyourdiscoveries.data.dbrepositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.hdrescuer.supportyourdiscoveries.db.DiscoveriesDataBase;
import com.hdrescuer.supportyourdiscoveries.db.dao.AuthorDao;
import com.hdrescuer.supportyourdiscoveries.db.dao.AuthorPlaceValorationDao;
import com.hdrescuer.supportyourdiscoveries.db.entity.AuthorEntity;
import com.hdrescuer.supportyourdiscoveries.db.entity.AuthorPlaceValorationEntity;
import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;

import java.util.List;

public class AuthorPlaceValorationRepository {


    private AuthorPlaceValorationDao authorPlaceValorationDao;




    public AuthorPlaceValorationRepository(Application application) {

        DiscoveriesDataBase db = DiscoveriesDataBase.getDataBase(application);
        authorPlaceValorationDao = db.getAuthorPlaceValorationDao();

    }

    public MutableLiveData<AuthorPlaceValorationEntity> getAuthorPlaceValoration(String username, int place_id){


        AuthorPlaceValorationEntity authorPlaceValorationEntity = authorPlaceValorationDao.getAuthorPlaceValoration(username,place_id);

        MutableLiveData<AuthorPlaceValorationEntity> authplaceval = new MutableLiveData<>();
        authplaceval.setValue(authorPlaceValorationEntity);

        return authplaceval;

    }

    public void insertAuthorPlaceValoration(AuthorPlaceValorationEntity authorPlaceValorationEntity){
        new insertAuthorPlaceValorationAsyncTask(authorPlaceValorationDao).execute(authorPlaceValorationEntity);
    }

    public void updateAuthorPlaceValoration(AuthorPlaceValorationEntity authorPlaceValorationEntity){
        new updateAuthorPlaceValorationAsyncTask(authorPlaceValorationDao).execute(authorPlaceValorationEntity);
    }

    private static class insertAuthorPlaceValorationAsyncTask extends AsyncTask<AuthorPlaceValorationEntity, Void, Void> {

        private AuthorPlaceValorationDao authorPlaceValorationDaoAsyncTask;


        insertAuthorPlaceValorationAsyncTask(AuthorPlaceValorationDao authorPlaceValorationDao){
            authorPlaceValorationDaoAsyncTask = authorPlaceValorationDao;
        }

        @Override
        protected Void doInBackground(AuthorPlaceValorationEntity... authorPlaceValorationEntities) {

            authorPlaceValorationDaoAsyncTask.insert(authorPlaceValorationEntities[0]);
            return null;
        }
    }

    private static class updateAuthorPlaceValorationAsyncTask extends AsyncTask<AuthorPlaceValorationEntity, Void, Void>{

        private AuthorPlaceValorationDao authorPlaceValorationDaoAsyncTask;


        updateAuthorPlaceValorationAsyncTask(AuthorPlaceValorationDao authorPlaceValorationDao){
            authorPlaceValorationDaoAsyncTask = authorPlaceValorationDao;
        }

        @Override
        protected Void doInBackground(AuthorPlaceValorationEntity... authorPlaceValorationEntities) {

            authorPlaceValorationDaoAsyncTask.update(authorPlaceValorationEntities[0]);
            return null;
        }
    }

}