package com.example.palexis3.nearme.Models;

import android.location.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 Geometry is a POJO found in the Restaurant Model. It holds the
 the Location object.
 */

@Parcel
public class Geometry {

    @SerializedName("location")
    @Expose
    Location location;

    public Geometry(){};

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
