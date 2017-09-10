package com.app.marvel.comics.domain.repository;

import com.app.marvel.comics.domain.entity.Comic;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface IComicsRepository {
    Completable fetchComics();

    Flowable<List<Comic>> getAllComics();
}
