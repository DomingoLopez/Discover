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

    @Query("SELECT * FROM PLACE WHERE author_id = :id")
    List<PlaceEntity> getAllPlacesByAuthor(int id);


}
