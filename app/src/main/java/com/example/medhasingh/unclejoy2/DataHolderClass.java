package com.example.medhasingh.unclejoy2;

import android.net.Uri;

/**
 * Created by medha singh on 7/22/2016.
 */
public class DataHolderClass {
    private static DataHolderClass ourInstance = null;

    public static DataHolderClass getInstance() {
        if (ourInstance == null)
            ourInstance = new DataHolderClass();
        return ourInstance;
    }

    private DataHolderClass() {
    }
    private String distributor_id;;

    public String getDistributor_id() {
        return distributor_id;
    }

    public void setDistributor_id(String distributor_id) {
        this.distributor_id = distributor_id;
    }
    private Uri[] urms;
    public Uri[] getDistributor_uri(){
        return urms;
    }
    public void setDistributor_uri(Uri[] urmsn){
        this.urms=urmsn;
    }
    private Uri[] urmd;
    public Uri[] getPdf_uri(){
        return urmd;
    }
    public void setPdf_uri(Uri[] urmsn){
        this.urms=urmsn;
    }
}
