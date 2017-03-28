package com.example.medhasingh.unclejoy2;

/**
 * Created by medha singh on 7/5/2016.
 */
public class Response {
    private String storename,timeelapsed,store_category,map,date,time,url,booking_id,count;



    public Response() {
    }

    public Response(String storename,String timeelapsed,String store_category,String map,String date,String time,String url,String booking_id,String count) {
        this.storename = storename;
        this.timeelapsed=timeelapsed;
        this.store_category=store_category;
        this.map=map;
        this.date=date;
        this.time=time;
        this.url=url;
        this.booking_id=booking_id;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String name) {
        this.storename = name;
    }

    public String getTimelapsed(){
        return timeelapsed;
    }
    public void setTimeelapsed(String l){
        this.timeelapsed=l;
    }
    public String getStore_category(){
        return store_category;
    }
    public void setStore_category(String l){
        this.store_category=l;
    }
    public String getMap(){
        return map;
    }
    public void setMap(String l){
        this.map=l;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String l){
        this.date=l;
    }
    public String getTime(){
        return time;
    }
    public void setTime(String l){
        this.time=l;
    }
    public String getUrl(){
        return url;
    }
    public void setUrl(String l){
        this.url=l;
    }
    public String getBooking_id(){
        return booking_id;
    }
    public void setBooking_id(String l){
        this.booking_id=l;
    }
    public String getCount(){
        return count;
    }
    public void setCount(String count){
        this.count=count;
    }
}
