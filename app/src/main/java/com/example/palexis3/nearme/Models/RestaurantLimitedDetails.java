package com.example.palexis3.nearme.Models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 RestaurantLimitedDetails is similar to Restaurant POJO, but holds fewer properties and attributes.
 */

@Parcel
public class RestaurantLimitedDetails {

    @SerializedName("geometry")
    Geometry geometry;

    @SerializedName("name")
    String name;

    @SerializedName("place_id")
    String place_id;

    @SerializedName("photos")
    ArrayList<Photos> photosArrayList;

    @SerializedName("rating")
    String rating;

    // empty constructor needed by the Parceler library
    public RestaurantLimitedDetails(){}

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public ArrayList<Photos> getPhotosArrayList() {
        return photosArrayList;
    }

    public void setPhotosArrayList(ArrayList<Photos> photosArrayList) {
        this.photosArrayList = photosArrayList;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

}
