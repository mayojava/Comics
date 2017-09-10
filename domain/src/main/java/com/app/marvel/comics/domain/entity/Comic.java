package com.app.marvel.comics.domain.entity;

import com.google.auto.value.AutoValue;

import io.reactivex.annotations.NonNull;

@AutoValue
public abstract class Comic {
    @NonNull
    public abstract String title();

    @NonNull
    public abstract String thumbnail();

    @NonNull
    public abstract String description();

    public abstract int pageCount();

    public abstract float price();

    @NonNull
    public abstract String priceType();

    @NonNull
    public abstract String authors();

    @NonNull
    public static Builder builder() {
        return new AutoValue_Comic.Builder();
    }

    @AutoValue.Builder
    public interface Builder {
        Builder title(final String title);

        Builder thumbnail(final String thumbnail);

        Builder description(final String description);

        Builder pageCount(final int pageCount);

        Builder price(final float price);

        Builder priceType(final String priceType);

        Builder authors(final String authors);

        Comic build();
    }
}
