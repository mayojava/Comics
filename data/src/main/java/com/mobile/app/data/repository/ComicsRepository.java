package com.mobile.app.data.repository;

import com.app.marvel.comics.domain.ISchedulersFactory;
import com.app.marvel.comics.domain.entity.Comic;
import com.app.marvel.comics.domain.repository.IComicsRepository;
import com.mobile.app.data.db.entities.ComicsEntity;
import com.mobile.app.data.mappers.Mapper;
import com.mobile.app.data.remote.ComicsService;
import com.mobile.app.data.store.ReactiveStore;
import com.mobile.app.data.utils.Constants;
import com.mobile.app.data.utils.Md5HashGenerator;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import polanski.option.OptionUnsafe;

public class ComicsRepository implements IComicsRepository {
    private final ReactiveStore<ComicsEntity> reactiveStore;
    private final ComicsService comicsService;
    private final Mapper mapper;
    private final ISchedulersFactory schedulersFactory;

    @Inject
    ComicsRepository(@NonNull final ReactiveStore<ComicsEntity> reactiveStore,
                     @NonNull final ComicsService comicsService,
                     @NonNull final ISchedulersFactory schedulersFactory,
                     @NonNull final Mapper mapper) {
        this.reactiveStore = reactiveStore;
        this.comicsService = comicsService;
        this.mapper = mapper;
        this.schedulersFactory = schedulersFactory;
    }

    @Override
    public Completable fetchComics() {
        final long timestamp = System.currentTimeMillis();
        final String hash = Md5HashGenerator.generateHash(timestamp,
                Constants.PRIVATE_KEY, Constants.PUBLIC_KEY);

        return comicsService.getComics(Constants.PUBLIC_KEY, hash, timestamp)
                .subscribeOn(schedulersFactory.io())
                .observeOn(schedulersFactory.computation())
                .flatMapObservable(response -> Observable.fromIterable(response.getData().getResults()))
                .map(mapper::mapToDbEntity)
                .toList()
                .doOnSuccess(reactiveStore::replaceAll)
                .toCompletable();
    }

    @Override
    public Flowable<List<Comic>> getAllComics() {
        return reactiveStore.getAll()
                .subscribeOn(schedulersFactory.computation())
                .map(optional -> {
                    if (optional.isNone() || OptionUnsafe.getUnsafe(optional).isEmpty()) {
                        return Collections.emptyList();
                    } else {
                        return mapper.mapToDomainComicModel(OptionUnsafe.getUnsafe(optional));
                    }
                });
    }
}
