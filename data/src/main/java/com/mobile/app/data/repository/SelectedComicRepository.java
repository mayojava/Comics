package com.mobile.app.data.repository;

import com.app.marvel.comics.domain.entity.Comic;
import com.app.marvel.comics.domain.repository.ISelectedComicRepository;

import javax.inject.Inject;

public class SelectedComicRepository implements ISelectedComicRepository {
    private Comic selectedComic;

    @Inject SelectedComicRepository() { }

    @Override
    public void putComic(Comic comic) {
        selectedComic = comic;
    }

    @Override
    public Comic getSelectedComic() {
        return selectedComic;
    }
}
