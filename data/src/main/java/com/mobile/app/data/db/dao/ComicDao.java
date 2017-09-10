package com.mobile.app.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mobile.app.data.db.entities.ComicsEntity;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ComicDao {
    @Query("SELECT * FROM comic")
    Flowable<List<ComicsEntity>> getAllComics();

    @Query("DELETE FROM comic")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ComicsEntity> entities);
}
