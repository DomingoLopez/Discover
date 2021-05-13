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

    @Query("DELETE FROM AUTHOR WHERE username = :author_name")
    void deleteById(String author_name);

    @Query("SELECT * FROM AUTHOR WHERE username = :author_name")
    AuthorEntity getAuthorById(String author_name);

    @Query("SELECT * FROM AUTHOR WHERE username = :username")
    AuthorEntity getAuthorByUserName(String username);

    @Query("SELECT * FROM AUTHOR")
    List<AuthorEntity> getAllAuthors();


}