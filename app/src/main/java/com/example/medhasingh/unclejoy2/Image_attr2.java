package com.example.medhasingh.unclejoy2;

/**
 * Created by medha singh on 7/27/2016.
 */
public class Image_attr2 {
    private String  thumbnailUrl;
    private String title;

    public Image_attr2() {
    }

    public Image_attr2( String thumbnailUrl,String title) {
        this.thumbnailUrl = thumbnailUrl;
        this.title=title;

    }
    public String getTitle2(){
        return title;
    }
public void setTitle2(String title){
    this.title=title;
}


    public String getThumbnailUrl2() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl2(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

}
