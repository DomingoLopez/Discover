package com.hdrescuer.supportyourdiscoveries.db.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hdrescuer.supportyourdiscoveries.db.entity.AuthorEntity;
import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;

import java.util.List;

@Dao
public interface AuthorDao {

    @Insert
    void insert(AuthorEntity authorEntity);

    @Update
    void update(AuthorEntity authorEntity);

    @Query("DELETE FROM AUTHOR")
    void deleteAll();

    @Query("DELETE FROM AUTHOR WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM AUTHOR WHERE id = :id")
    AuthorEntity getAuthorById(int id);

    @Query("SELECT * FROM AUTHOR WHERE username = :username")
    AuthorEntity getAuthorByUserName(String username);

    @Query("SELECT * FROM PLACE")
    List<AuthorEntity> getAllAuthors();


}