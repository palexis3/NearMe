package com.example.palexis3.nearme.Networking;


import com.example.palexis3.nearme.Responses.ResultResponse;
import com.example.palexis3.nearme.Responses.ResultsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestaurantClient {

    @GET("/maps/api/place/nearbysearch/json?location=37.787698,-122.404288&radius=200&type=restaurant")
    Call<ResultsResponse> getRestaurants(
            @Query("key") String key
    );

    @GET("/maps/api/place/details/json")
    Call<ResultResponse> getRestaurant(
            @Query("placeid") String place_id,
            @Query("key") String key
    );
}
