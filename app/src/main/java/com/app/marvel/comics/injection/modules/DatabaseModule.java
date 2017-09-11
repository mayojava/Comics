package com.app.marvel.comics.injection.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.mobile.app.data.db.ComicsDatabase;
import com.mobile.app.data.db.DiskReactiveStore;
import com.mobile.app.data.db.dao.ComicDao;
import com.mobile.app.data.db.entities.ComicsEntity;
import com.mobile.app.data.store.ReactiveStore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    @Provides
    @Singleton
    public ComicsDatabase providesComicDatabase(final Context context) {
        return Room.databaseBuilder(context, ComicsDatabase.class, "comics-database").build();
    }

    @Provides
    @Singleton
    public ComicDao proividesComicDao(final ComicsDatabase comicsDatabase) {
        return comicsDatabase.comicsDao();
    }

    @Singleton
    @Provides
    public ReactiveStore<ComicsEntity> DiskReactiveStore(final DiskReactiveStore diskReactiveStore) {
        return diskReactiveStore;
    }
}
