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
        ReactiveInteractor.RetrieveInteractor<Option<Void>,List<Comic>>,
        ReactiveInteractor.RefreshInteractor {

    @NonNull private final IComicsRepository comicsRepository;

    @Inject
    FetchComicsInteractor(@NonNull final IComicsRepository comicsRepository) {
        this.comicsRepository = comicsRepository;
    }

    @Override
    public Flowable<List<Comic>> getResponseStream(Option<Option<Void>> params) {
        return comicsRepository.getAllComics();

    }

    @Override
    public Completable refreshData() {
        return comicsRepository.fetchComics();
    }
}
