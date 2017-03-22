package com.sahajarora.photoapp.rest;

import com.sahajarora.photoapp.model.Album;
import com.sahajarora.photoapp.model.Photo;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by sahajarora1286 on 17-02-2017.
 */

public interface JsonPlaceHolderApi {
    @GET("/photos")
    void getPhotos(@Query("albumId") String albumId, Callback<ArrayList<Photo>> photos);

    @GET("/albums")
    void getAlbums(Callback<ArrayList<Album>> albums);
}
