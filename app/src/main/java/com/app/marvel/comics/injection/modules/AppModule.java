package com.app.marvel.comics.injection.modules;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Provides
    @Singleton
    public Context providesContext(final Application application) {
        return application;
    }

    @Provides
    @Singleton
    public ConnectivityManager providesConnectivityManager(final Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}
