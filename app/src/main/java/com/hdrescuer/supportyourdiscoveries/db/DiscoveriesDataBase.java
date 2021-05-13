package com.hdrescuer.supportyourdiscoveries.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.hdrescuer.supportyourdiscoveries.common.StringListConverter;
import com.hdrescuer.supportyourdiscoveries.db.dao.AuthorDao;
import com.hdrescuer.supportyourdiscoveries.db.dao.PlaceDao;
import com.hdrescuer.supportyourdiscoveries.db.entity.AuthorEntity;
import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;

@Database(entities = {PlaceEntity.class, AuthorEntity.class}, version = 1, exportSchema = false)
@TypeConverters({StringListConverter.class})
public abstract class DiscoveriesDataBase extends RoomDatabase {

    public abstract PlaceDao getPlaceDao();
    public abstract AuthorDao getAuthorDao();


    private static volatile DiscoveriesDataBase INSTANCE;


    public static DiscoveriesDataBase getDataBase(final Context context){

        if(INSTANCE == null){
            synchronized (DiscoveriesDataBase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DiscoveriesDataBase.class,"DISCOVERY")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }

        return INSTANCE;

    }

}