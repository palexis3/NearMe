package com.example.palexis3.nearme.Responses;

import com.example.palexis3.nearme.Models.RestaurantLimitedDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 RestaurantsResponse gets a list of RestaurantLimitedDetails objects.
 */

public class RestaurantsResponse {

    @SerializedName("results")
    List<RestaurantLimitedDetails> restaurantLimitedDetailsList;

    public RestaurantsResponse() {
        restaurantLimitedDetailsList = new ArrayList<>();
    }

    public List<RestaurantLimitedDetails> getRestaurantLimitedDetailsList() {
        return restaurantLimitedDetailsList;
    }

    public static RestaurantsResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        RestaurantsResponse restaurantsResponse = gson.fromJson(response, RestaurantsResponse.class);
        return restaurantsResponse;
    }
}
