package com.mobile.app.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "comic")
public class ComicsEntity {
    @PrimaryKey
    private long id;
    private String thumbnail;
    private String title;
    private String description;
    private int pageCount;
    private float price;
    private String priceType;
    private String authors;

    public ComicsEntity(final long id,
                        @NonNull final String thumbnail,
                        @NonNull final String title,
                        @NonNull final String description,
                        final int pageCount,
                        final float price,
                        final String priceType,
                        @NonNull final String authors) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.title = title;
        this.description = description;
        this.pageCount = pageCount;
        this.price = price;
        this.authors = authors;
        this.priceType = priceType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }
}
