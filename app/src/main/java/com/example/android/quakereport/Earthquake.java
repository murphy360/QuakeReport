package com.example.android.quakereport;

import java.util.Date;

/**
 * Created by Corey on 9/3/2016.
 */
public class Earthquake extends Object {

    private double magnitude;
    private String location;
    private Date date;


    public Earthquake(double magnitude, String location, Date date) {
        this.magnitude = magnitude;
        this. location = location;
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(long magnitude) {
        this.magnitude = magnitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}