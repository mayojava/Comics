package com.mobile.app.data.db;

import com.mobile.app.data.db.dao.ComicDao;
import com.mobile.app.data.db.entities.ComicsEntity;
import com.mobile.app.data.store.ReactiveStore;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import polanski.option.Option;

public class DiskReactiveStore implements ReactiveStore<ComicsEntity> {
    private final ComicDao comicDao;

    @Inject
    public DiskReactiveStore(final ComicDao comicDao) {
        this.comicDao = comicDao;
    }

    @Override
    public void storeAll(List<ComicsEntity> entities) {
        comicDao.insertAll(entities);
    }

    @Override
    public void replaceAll(List<ComicsEntity> entities) {
        comicDao.deleteAll();
        storeAll(entities);
    }

    @Override
    public Flowable<Option<List<ComicsEntity>>> getAll() {
        return comicDao.getAllComics()
                .map(list -> list.isEmpty() ? Option.ofObj(Collections.emptyList()) : Option.ofObj(list));
    }
}
