package com.example.palexis3.nearme.Networking;


import com.example.palexis3.nearme.Responses.RestaurantDetailResponse;
import com.example.palexis3.nearme.Responses.RestaurantsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestaurantClient {

    @GET("/maps/api/place/nearbysearch/json?location=37.787698,-122.404288&radius=200&type=restaurant")
    Call<RestaurantsResponse> getRestaurants(
            @Query("key") String key
    );

    @GET("/maps/api/place/details/json")
    Call<RestaurantDetailResponse> getRestaurant(
            @Query("placeid") String place_id,
            @Query("key") String key
    );
}
