package com.mobile.app.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mobile.app.data.db.dao.ComicDao;
import com.mobile.app.data.db.entities.ComicsEntity;

@Database(entities = {ComicsEntity.class}, version = 1)
public abstract class ComicsDatabase extends RoomDatabase {
    public abstract ComicDao comicsDao();
}
