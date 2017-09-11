package com.app.marvel.comics.comicdetails;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.app.marvel.comics.domain.entity.Comic;
import com.app.marvel.comics.domain.repository.ISelectedComicRepository;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class ComicDetailsViewModel extends ViewModel {

    private final MutableLiveData<Comic> selectedComicLiveData = new MutableLiveData<>();

    @Inject
    public ComicDetailsViewModel(@Nonnull final ISelectedComicRepository selectedComicRepository) {
        selectedComicLiveData.postValue(selectedComicRepository.getSelectedComic());
    }

    @Nonnull
    LiveData<Comic> getSelectedComic() {
        return selectedComicLiveData;
    }
}
