package com.mobile.app.data.remote;

import com.mobile.app.data.models.ApiResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ComicsService {
    String API_KEY = "apikey";
    String HASH = "hash";
    String TIMESTAMP = "ts";

    @GET("v1/public/comics?limit=100")
    Single<ApiResponse> getComics(
            @Query(API_KEY) String publicKey,
            @Query(HASH) String hash,
            @Query(TIMESTAMP) long timestamp
    );
}
