package com.hdrescuer.supportyourdiscoveries.db.dao;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;

import java.util.List;

@Dao
public interface PlaceDao {

    @Insert
    void insert(PlaceEntity placeEntity);

    @Update
    void update(PlaceEntity placeEntity);

    @Query("DELETE FROM PLACE")
    void deleteAll();

    @Query("DELETE FROM PLACE WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM PLACE WHERE id = :id")
    PlaceEntity getPlaceById(int id);

    @Query("SELECT * FROM PLACE")
    List<PlaceEntity> getAllPlaces();

    @Query("SELECT * FROM PLACE WHERE author_name = :author_name")
    List<PlaceEntity> getAllPlacesByAuthor(String author_name);

    @Query("SELECT MAX(id) FROM PLACE")
    int getMaxId();

    @Query("SELECT COUNT(*) FROM AUTHORPLACEVALORATION WHERE username = :username AND visited = 1")
    int getNumVisisted(String username);

    @Query("SELECT (AVG(RATING)) FROM place WHERE author_name = :username")
    double getAVGValorationUser(String username);

    @Query("SELECT COUNT(*) FROM place WHERE author_name = :username")
    int getPublished(String username);


}
