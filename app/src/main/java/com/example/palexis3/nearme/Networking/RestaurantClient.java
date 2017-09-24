package com.example.palexis3.nearme.Networking;


import android.support.annotation.NonNull;

import com.example.palexis3.nearme.Responses.RestaurantDetailResponse;
import com.example.palexis3.nearme.Responses.RestaurantsResponse;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class RestaurantClient {

    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/";
    private static final String GOOGLE_PLACES_API_KEY = "AIzaSyB-bpw0ollWA5AKpT11Y2CL2qPFs4kC_dk";

    private static RestaurantClient instance;
    private RestaurantService restaurantService;


    public RestaurantClient() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restaurantService = retrofit.create(RestaurantService.class);
    }

    public static RestaurantClient getInstance() {
        if(instance == null) {
            instance = new RestaurantClient();
        }
        return instance;
    }

    public Observable<RestaurantsResponse> getRestaurants(String Key) {
        return restaurantService.getRestaurants(GOOGLE_PLACES_API_KEY);
    }

    public Observable<RestaurantDetailResponse> getRestaurant(@NonNull String place_id, String key) {
        return restaurantService.getRestaurant(place_id, GOOGLE_PLACES_API_KEY);
    }
}
