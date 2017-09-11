package com.app.marvel.comics.domain.interactor;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import polanski.option.Option;

/**
 * Base interface for all use cases
 */

public interface ReactiveInteractor {

    /**
     * Returns a Flowable that listens to changes in the data and emits. This flowable never completes
     *
     * @param <Params> optional parameter for the request
     * @param <Result> type of returned response
     */
    interface RetrieveInteractor<Params, Result> extends ReactiveInteractor {
        @NonNull
        Flowable<Result> getResponseStream(@NonNull final Option<Params> params);
    }

    /**
     * Used to request a single data and return it as a single. It will complete or error
     *
     * @param <Params> optional parameter needed to complete the call
     * @param <Result> Type of returned result
     */
    interface RequestInteractor<Params, Result> extends ReactiveInteractor {
        @NonNull
        Single<Result> makeRequest(@NonNull final Option<Params> params);
    }

    /**
     * Used to refresh data stored in persistence
     */
    interface RefreshInteractor extends ReactiveInteractor {
        Completable refreshData();
    }
}
