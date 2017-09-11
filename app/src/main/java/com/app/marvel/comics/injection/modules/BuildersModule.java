package com.app.marvel.comics.injection.modules;

import com.app.marvel.comics.comicslist.ComicsListActivity;
import com.app.marvel.comics.comicslist.ComicsListActivityModule;
import com.app.marvel.comics.injection.scope.PerActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {
    @ContributesAndroidInjector(modules = ComicsListActivityModule.class)
    @PerActivity
    public abstract ComicsListActivity bindComicsActivity();
}
