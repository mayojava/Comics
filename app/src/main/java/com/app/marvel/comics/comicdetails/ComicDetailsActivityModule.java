package com.app.marvel.comics.comicdetails;

import android.arch.lifecycle.ViewModelProvider;

import com.app.marvel.comics.injection.scope.PerActivity;
import com.app.marvel.comics.utils.ViewModelUtil;

import dagger.Module;
import dagger.Provides;

@Module
public class ComicDetailsActivityModule {
    @Provides
    @PerActivity
    ViewModelProvider.Factory providesComicDetailsViewModel(final ViewModelUtil viewModelUtil,
                                                            final ComicDetailsViewModel comicDetailsViewModel) {
        return viewModelUtil.createViewModelFor(comicDetailsViewModel);
    }

}
