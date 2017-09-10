package com.mobile.app.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    @SerializedName("id")
    private long id;
    @SerializedName("title")
    private String title;
    @SerializedName("issueNumber")
    private int issueNumber;
    @SerializedName("description")
    private String description;
    @SerializedName("pageCount")
    private int pageCount;
    @SerializedName("resourceURI")
    private String resourceURI;
    @SerializedName("prices")
    private List<Price> prices;
    @SerializedName("thumbnail")
    private Thumbnail thumbnail;
    @SerializedName("creators")
    private CreatorList creator;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
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

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public CreatorList getCreator() {
        return creator;
    }

    public void setCreator(CreatorList creator) {
        this.creator = creator;
    }
}
