package com.app.marvel.comics.injection.modules;

import com.app.marvel.comics.SchedulersFactory;
import com.app.marvel.comics.domain.ISchedulersFactory;
import com.app.marvel.comics.domain.repository.IComicsRepository;
import com.app.marvel.comics.domain.repository.ISelectedComicRepository;
import com.mobile.app.data.remote.ComicsService;
import com.mobile.app.data.repository.ComicsRepository;
import com.mobile.app.data.repository.SelectedComicRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApiModule {
    @Provides
    @Singleton
    ComicsService providesComicsService(final Retrofit retrofit) {
        return retrofit.create(ComicsService.class);
    }

    @Provides
    @Singleton
    IComicsRepository providesComisRepository(final ComicsRepository comicsRepository) {
        return comicsRepository;
    }



    @Provides
    @Singleton
    ISchedulersFactory providesSchedulersFactory() {
        return new SchedulersFactory();
    }

    @Provides
    @Singleton
    ISelectedComicRepository providesSelectedComicRepository(final SelectedComicRepository selectedComicRepository) {
        return selectedComicRepository;
    }
}
