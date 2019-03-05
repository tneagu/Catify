package com.tneagu.catify.net.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoResponse {
    @SerializedName("results")
    private List<Photo> photos;


    public List<Photo> getPhotos() {
        return photos;
    }
}
