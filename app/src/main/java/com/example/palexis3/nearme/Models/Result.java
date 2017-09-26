package com.example.palexis3.nearme.Models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by pa380 on 9/25/17.
 */

@Parcel
public class Result {

    @SerializedName("reviews")
    ArrayList<Reviews> reviewsArrayList;

    @SerializedName("photos")
    ArrayList<Photos> photosArrayList;

    @SerializedName("geometry")
    Geometry geometry;

    String formatted_address;
    String formatted_phone_number;
    String place_id;
    String name;
    Double rating;


    // empty constructor needed by the Parceler library
    public Result() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getFormatted_phone_number() {
        return formatted_phone_number;
    }

    public void setFormatted_phone_number(String formatted_phone_number) {
        this.formatted_phone_number = formatted_phone_number;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public ArrayList<Reviews> getReviewsArrayList() {
        return reviewsArrayList;
    }

    public void setReviewsArrayList(ArrayList<Reviews> reviewsArrayList) {
        this.reviewsArrayList = reviewsArrayList;
    }

    public ArrayList<Photos> getPhotosArrayList() {
        return photosArrayList;
    }

    public void setPhotosArrayList(ArrayList<Photos> photosArrayList) {
        this.photosArrayList = photosArrayList;
    }



}
