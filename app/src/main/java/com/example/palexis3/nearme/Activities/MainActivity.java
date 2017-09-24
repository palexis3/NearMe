package com.example.palexis3.nearme.Activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.palexis3.nearme.Adapter.RestaurantListAdapter;
import com.example.palexis3.nearme.Models.RestaurantLimitedDetails;
import com.example.palexis3.nearme.Networking.RestaurantClient;
import com.example.palexis3.nearme.Networking.ServiceGenerator;
import com.example.palexis3.nearme.R;
import com.example.palexis3.nearme.Responses.RestaurantsResponse;
import com.example.palexis3.nearme.Utilities.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 MyListFragement utilizes RecyclerView to populate individual restaurants.
 */

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    private RestaurantListAdapter restaurantListAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.rvResturantList)
    RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Initialize dataset, this data is coming from google places API.
        initDataset();
    }


    private void initDataset() {

        // create an instance of our restaurant client
        RestaurantClient client = ServiceGenerator.createService(RestaurantClient.class);
        Call<RestaurantsResponse>  call = client.getRestaurants(Utils.getGooglePlacesApiKey());
        call.enqueue(new Callback<RestaurantsResponse>() {
            @Override
            public void onResponse(Call<RestaurantsResponse> call, Response<RestaurantsResponse> response) {
                if(response.isSuccessful()) {

                    RestaurantsResponse sourceResponse = response.body();
                    ArrayList<RestaurantLimitedDetails> restaurantsArrayList =
                            new ArrayList<>(sourceResponse.getRestaurantLimitedDetailsList());

                    if(restaurantsArrayList != null && restaurantsArrayList.size() > 0) {

                        mLayoutManager = new LinearLayoutManager(getApplicationContext());

                        recyclerView.setLayoutManager(mLayoutManager);

                        restaurantListAdapter = new RestaurantListAdapter(restaurantsArrayList, getApplicationContext());

                        // Set RestaurantListAdapter as the adapter for RecyclerView.
                        recyclerView.setAdapter(restaurantListAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<RestaurantsResponse> call, Throwable t) {
                Log.d(TAG, t.getLocalizedMessage());
            }
        });
    }
}
