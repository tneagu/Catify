package com.tneagu.catify.net.models;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Photo {
    @SerializedName("id")
    private String id;
    @SerializedName("links")
    private Map<String, String> links;

    public String getId() {
        return id;
    }

    public Map<String, String> getLinks() {
        return links;
    }
}
