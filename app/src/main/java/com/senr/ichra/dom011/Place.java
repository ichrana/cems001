package com.senr.ichra.dom011;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.net.URL;
import java.util.Comparator;

public class Place implements Serializable {

    private String name;
    private String phone;
    private String rating;
    private double lat;
    private double lng;
    private double distance;
    private String url;

    public Place(String name, String phone, String rating, double lat, double lng, String url) {
        this.name = name;
        this.phone = phone;
        this.rating = rating;
        this.lat = lat;
        this.lng = lng;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
