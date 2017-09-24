package com.example.palexis3.nearme.Networking;


import com.example.palexis3.nearme.Responses.RestaurantDetailResponse;
import com.example.palexis3.nearme.Responses.RestaurantsResponse;

import rx.Observable;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestaurantService {

    @GET("/nearbysearch/json?location=37.787698,-122.404288&radius=200&type=restaurant")
    Observable<RestaurantsResponse> getRestaurants(
            @Query("key") String key
    );

    @GET("/details/json")
    Observable<RestaurantDetailResponse> getRestaurant(
            @Query("placeid") String place_id,
            @Query("key") String key
    );

}
