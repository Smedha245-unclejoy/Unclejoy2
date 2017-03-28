package com.example.medhasingh.unclejoy2;

import android.graphics.Bitmap;

/**
 * Created by medha singh on 6/23/2016.
 */
public class Search {
    private String  thumbnailUrl;
    private String  title;
    private String address;


    public Search() {
    }

    public Search( String thumbnailUrl,String title,String address) {
        this.thumbnailUrl = thumbnailUrl;
        this.title=title;
        this.address=address;

    }



    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title =title;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address =address;
    }

}