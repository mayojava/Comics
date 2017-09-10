package com.mobile.app.data.models;

import com.google.gson.annotations.SerializedName;

public class Price {
    @SerializedName("type")
    private String type;
    @SerializedName("price")
    private float price;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
