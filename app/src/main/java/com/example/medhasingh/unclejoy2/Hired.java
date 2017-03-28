package com.example.medhasingh.unclejoy2;

/**
 * Created by medha singh on 7/16/2016.
 */
public class Hired {
    private String hire_name,hire_time,hire_type,hire_location,hire_date,hire_dtime,hire_no;



    public Hired() {
    }

    public Hired(String hire_name,String hire_time,String hire_type,String hire_location,String hire_date,String hire_dtime,String hire_no) {
        this.hire_name = hire_name;
        this.hire_time=hire_time;
        this.hire_type=hire_type;
        this.hire_location=hire_location;
        this.hire_date=hire_date;
        this.hire_dtime=hire_dtime;
        this.hire_no=hire_no;

    }

    public String getHire_name() {
        return hire_name;
    }

    public void setHire_name(String name) {
        this.hire_name = name;
    }

    public String getHire_time(){
        return hire_time;
    }
    public void setHire_time(String l){
        this.hire_time=l;
    }
    public String getHire_type(){
        return hire_type;
    }
    public void setHire_type(String l){
        this.hire_type=l;
    }
    public String getHire_location(){
        return hire_location;
    }
    public void setHire_location(String l){
        this.hire_location=l;
    }
    public String getHire_date(){
        return hire_date;
    }
    public void setHire_date(String l){
        this.hire_date=l;
    }
    public String getHire_dtime(){
        return hire_dtime;
    }
    public void setHire_dtime(String l){
        this.hire_dtime=l;
    }
    public String getHire_no(){
        return hire_no;
    }
    public void setHire_no(String p){
        this.hire_no=p;
    }

}
