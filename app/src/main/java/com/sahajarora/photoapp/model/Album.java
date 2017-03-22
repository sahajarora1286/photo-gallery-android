package com.sahajarora.photoapp.model;

/**
 * Created by sahajarora1286 on 17-02-2017.
 */

public class Album {

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String userId;

    private String id;

    private String title;
}
