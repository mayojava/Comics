package com.app.marvel.comics.injection.modules;

import com.app.marvel.comics.SchedulersFactory;
import com.app.marvel.comics.domain.ISchedulersFactory;
import com.app.marvel.comics.domain.repository.IComicsRepository;
import com.mobile.app.data.remote.ComicsService;
import com.mobile.app.data.repository.ComicsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApiModule {
    @Provides
    @Singleton
    public ComicsService providesComicsService(final Retrofit retrofit) {
        return retrofit.create(ComicsService.class);
    }

    @Provides
    @Singleton
    public IComicsRepository providesComisRepository(final ComicsRepository comicsRepository) {
        return comicsRepository;
    }

    @Provides
    @Singleton
    public ISchedulersFactory providesSchedulersFactory() {
        return new SchedulersFactory();
    }
}
