package com.app.marvel.comics.domain.interactor;

import com.app.marvel.comics.domain.entity.Comic;
import com.app.marvel.comics.domain.repository.IComicsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import polanski.option.Option;

import static io.reactivex.Single.just;

public class FetchComicsInteractor implements
        ReactiveInteractor.RetrieveInteractor<Option<Void>,List<Comic>> {

    @NonNull private final IComicsRepository comicsRepository;

    @Inject
    public FetchComicsInteractor(@NonNull final IComicsRepository comicsRepository) {
        this.comicsRepository = comicsRepository;
    }

    @Override
    public Flowable<List<Comic>> getResponseStream(Option<Option<Void>> params) {
        return comicsRepository.getAllComics()
                //.flatMapSingle(list -> Single.just(list).doOnSuccess(this::fetchIfEmpty));
                .flatMapSingle(list -> fetchIfEmpty(list).andThen(just(list)));
    }

    @NonNull
    private Completable fetchIfEmpty(@NonNull final List<Comic> comics) {
        return comics.isEmpty()
                ? comicsRepository.fetchComics()
                : Completable.complete();
        //return comicsRepository.fetchComics();
    }

}
