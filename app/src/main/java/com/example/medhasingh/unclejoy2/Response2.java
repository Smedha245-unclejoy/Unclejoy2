package com.example.medhasingh.unclejoy2;

/**
 * Created by medha singh on 7/14/2016.
 */
public class Response2 {
    private String store,location,type,discount,ied,created;



    public Response2() {
    }

    public Response2(String store,String location,String type,String discount,String ied,String created) {
        this.created=created;
        this.store = store;
        this.location=location;
        this.type=type;
        this.discount=discount;
        this.ied=ied;

    }


    public String getStore() {
        return store;
    }

    public void setStore(String name) {
        this.store = name;
    }

    public String getLocation(){
        return location;
    }
    public void setLocation(String l){
        this.location=l;
    }
    public String getDiscount(){
        return discount;
    }
    public void setDiscount(String l){
        this.discount=l;
    }
    public String getType(){
        return type;
    }
    public void setType(String l){
        this.type=l;
    }

    public String getIed(){
        return ied;
    }
    public void setIed(String l){
        this.ied=l;
    }
    public String getCreated(){
        return created;
    }
    public void setCreated(String k){
        this.created=k;
    }
}
