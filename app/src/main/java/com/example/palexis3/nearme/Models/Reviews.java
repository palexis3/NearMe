package com.example.palexis3.nearme.Models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 Reviews POJO holds all the properties and attributes that well define a review given for
 a specific restaurant.
 */

@Parcel
public class Reviews {

    @SerializedName("author_name")
    String authorName;

    @SerializedName("text")
    String text;

    public Reviews(){}

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
