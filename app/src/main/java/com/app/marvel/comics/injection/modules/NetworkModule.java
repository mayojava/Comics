package com.app.marvel.comics.injection.modules;

import android.content.Context;

import com.app.marvel.comics.domain.exception.NoNetworkException;
import com.app.marvel.comics.utils.NetworkMonitor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobile.app.data.BuildConfig;
import com.mobile.app.data.utils.Constants;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public Retrofit providesRetrofit(final Gson gson,
                                     final OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    @Provides
    @Singleton
    public OkHttpClient providesOkHttpClient(final HttpLoggingInterceptor httpLoggingInterceptor,
                                             @Named("InternetConnectionInterceptor")
                                             final Interceptor connectionInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(connectionInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    @Named("InternetConnectionInterceptor")
    public Interceptor providesNetworkInterceptor(final NetworkMonitor networkMonitor) throws NoNetworkException {
        return chain -> {
            if (networkMonitor.isConnected()) {
                return chain.proceed(chain.request());
            } else {
                throw new NoNetworkException();
            }
        };
    }

    @Singleton
    @Provides
    public HttpLoggingInterceptor providesLoggingInterceptor(@Named("isDebug") final boolean isDebug) {
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        return httpLoggingInterceptor.setLevel(
                isDebug ?
                        HttpLoggingInterceptor.Level.BODY :
                        HttpLoggingInterceptor.Level.NONE);
    }

    @Singleton
    @Provides
    public Gson providesGson() {
        return new GsonBuilder()
                .setLenient()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Singleton
    @Provides
    @Named("isDebug")
    public boolean providesIsDebug() {
        return BuildConfig.DEBUG;
    }

    @Provides
    @Singleton
    public Cache providesOktthpCache(final File file) {
        return new Cache(file, 1024*1024*10);
    }

    @Provides
    @Singleton
    public File providesFile(final Context context) {
        return context.getFilesDir();
    }
}
