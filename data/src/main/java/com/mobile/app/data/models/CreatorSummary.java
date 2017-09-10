package com.mobile.app.data.models;

import com.google.gson.annotations.SerializedName;

public class CreatorSummary {
    @SerializedName("role")
    private String role;
    @SerializedName("name")
    private String name;
    @SerializedName("resourceURI")
    private String resourceUri;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }
}
