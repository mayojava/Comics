package com.mobile.app.data.store;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import polanski.option.Option;

public interface ReactiveStore<Object> {
    void storeAll(@NonNull final List<Object> objects);

    void replaceAll(@NonNull final List<Object> objects);

    Flowable<Option<List<Object>>> getAll();
}
