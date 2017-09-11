package com.app.marvel.comics.domain.repository;

import com.app.marvel.comics.domain.entity.Comic;

public interface ISelectedComicRepository {
    void putComic(Comic comic);
    Comic getSelectedComic();
}
