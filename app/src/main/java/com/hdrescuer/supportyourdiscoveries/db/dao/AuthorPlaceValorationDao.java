package com.hdrescuer.supportyourdiscoveries.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hdrescuer.supportyourdiscoveries.db.entity.AuthorPlaceValorationEntity;

@Dao
public interface AuthorPlaceValorationDao {

    @Insert
    void insert(AuthorPlaceValorationEntity authorPlaceValorationEntity);

    @Update
    void update(AuthorPlaceValorationEntity authorPlaceValorationEntity);


    @Query("SELECT valoration FROM AUTHORPLACEVALORATION WHERE username = :username AND place_id = :place_id")
    int getPlaceValorationByAuthor(String username, int place_id);

    @Query("SELECT visited FROM AUTHORPLACEVALORATION WHERE username = :username AND place_id = :place_id")
    boolean getVisitedPlaceByAuthor(String username, int place_id);

    @Query("SELECT * FROM AUTHORPLACEVALORATION WHERE username = :username AND place_id = :place_id")
    AuthorPlaceValorationEntity getAuthorPlaceValoration(String username, int place_id);

    @Query("SELECT AVG(valoration) FROM AUTHORPLACEVALORATION WHERE place_id = :place_id")
    float getAVGValoration(int place_id);

}
