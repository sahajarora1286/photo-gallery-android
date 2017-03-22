package com.sahajarora.photoapp.rest;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by sahajarora1286 on 17-02-2017.
 */

public class RestClient {
    private static JsonPlaceHolderApi REST_CLIENT;
    private static String ROOT = "http://jsonplaceholder.typicode.com";

    static {
        setupRestClient();
    }

    private RestClient() {}

    public static JsonPlaceHolderApi get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(ROOT)
                .setClient(new OkClient(new OkHttpClient()))
                .setLogLevel(RestAdapter.LogLevel.FULL);

        RestAdapter restAdapter = builder.build();
        REST_CLIENT = restAdapter.create(JsonPlaceHolderApi.class);
    }
}
