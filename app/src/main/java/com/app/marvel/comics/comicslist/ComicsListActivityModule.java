package com.app.marvel.comics.comicslist;

import android.arch.lifecycle.ViewModelProvider;

import com.app.marvel.comics.injection.scope.PerActivity;
import com.app.marvel.comics.utils.ViewModelUtil;

import dagger.Module;
import dagger.Provides;

@Module
public class ComicsListActivityModule {
    @Provides
    @PerActivity
    ComicsRecyclerAdapter providesRecyclerAdapter(final ComicsListActivity comicsListActivity) {
        return new ComicsRecyclerAdapter(comicsListActivity);
    }

    @Provides
    @PerActivity
    ViewModelProvider.Factory providesComicsViewModel(final ViewModelUtil viewModelUtil,
                                                      final ComicsListViewModel comicsListViewModel) {
        return viewModelUtil.createViewModelFor(comicsListViewModel);
    }
}
