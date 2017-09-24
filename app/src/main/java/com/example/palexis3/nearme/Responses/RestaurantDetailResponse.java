package com.example.palexis3.nearme.Responses;


import com.example.palexis3.nearme.Models.Restaurant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 RestaurantDetailResponse gets a single Restaurant object.
 */

public class RestaurantDetailResponse {

    @SerializedName("result")
    Restaurant restaurant;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public static RestaurantDetailResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        RestaurantDetailResponse restaurantsResponse = gson.fromJson(response, RestaurantDetailResponse.class);
        return restaurantsResponse;
    }
}
