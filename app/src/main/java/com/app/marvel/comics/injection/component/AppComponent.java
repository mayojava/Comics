package com.app.marvel.comics.injection.component;

import android.app.Application;

import com.app.marvel.comics.ComicsApplication;
import com.app.marvel.comics.injection.modules.ApiModule;
import com.app.marvel.comics.injection.modules.AppModule;
import com.app.marvel.comics.injection.modules.BuildersModule;
import com.app.marvel.comics.injection.modules.DatabaseModule;
import com.app.marvel.comics.injection.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        BuildersModule.class,
        DatabaseModule.class,
        NetworkModule.class,
        ApiModule.class
})
@Singleton
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance Builder application (Application application);
        AppComponent build();
    }

    void inject(final ComicsApplication application);
}
