package com.example.palexis3.nearme.Models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 Photo POJO that holds necessary properties to define a photo.
 */

@Parcel
public class Photos {

    @SerializedName("photo_reference")
    String photoReference;

    public Photos(){}

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }
}
