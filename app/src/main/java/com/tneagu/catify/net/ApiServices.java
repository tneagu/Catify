package com.tneagu.catify.net;

import com.tneagu.catify.net.models.PhotoResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {


    @GET("/search/photos")
    Single<PhotoResponse> searchPhoto(@Query("query") String searchTerm);
}
