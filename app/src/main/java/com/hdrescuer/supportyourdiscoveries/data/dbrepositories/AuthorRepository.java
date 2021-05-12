package com.hdrescuer.supportyourdiscoveries.data.dbrepositories;

import android.app.Application;
import android.os.AsyncTask;

import com.hdrescuer.supportyourdiscoveries.db.DiscoveriesDataBase;
import com.hdrescuer.supportyourdiscoveries.db.dao.AuthorDao;
import com.hdrescuer.supportyourdiscoveries.db.dao.PlaceDao;
import com.hdrescuer.supportyourdiscoveries.db.entity.AuthorEntity;
import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;

import java.util.List;

public class AuthorRepository{


    private AuthorDao authorDao;




    public AuthorRepository(Application application) {

        DiscoveriesDataBase db = DiscoveriesDataBase.getDataBase(application);
        authorDao = db.getAuthorDao();

    }


    public List<AuthorEntity> getAllAuthors(){ return authorDao.getAllAuthors();}

    public AuthorEntity getAuthorById(int id){return authorDao.getAuthorById(id);}

    public AuthorEntity getAuthorByUserName(String username){return authorDao.getAuthorByUserName(username);}

    public void deleteAllAuthors(){
        authorDao.deleteAll();}

    public void deleteById(int id){
        authorDao.deleteById(id);
    }


    public void insertAuthor(AuthorEntity author){
        new insertAuthorAsyncTask(authorDao).execute(author);
    }

    public void updateAuthor(AuthorEntity author){
        new updateAuthorAsyncTask(authorDao).execute(author);
    }

    private static class insertAuthorAsyncTask extends AsyncTask<AuthorEntity, Void, Void> {

        private AuthorDao authorDatoAsyncTask;


        insertAuthorAsyncTask(AuthorDao authorDao){
            authorDatoAsyncTask = authorDao;
        }

        @Override
        protected Void doInBackground(AuthorEntity... authorEntities) {

            authorDatoAsyncTask.insert(authorEntities[0]);
            return null;
        }
    }

    private static class updateAuthorAsyncTask extends AsyncTask<AuthorEntity, Void, Void>{

        private AuthorDao authorDatoAsyncTask;


        updateAuthorAsyncTask(AuthorDao authorDao){
            authorDatoAsyncTask = authorDao;
        }

        @Override
        protected Void doInBackground(AuthorEntity... authorEntities) {

            authorDatoAsyncTask.update(authorEntities[0]);
            return null;
        }
    }

}