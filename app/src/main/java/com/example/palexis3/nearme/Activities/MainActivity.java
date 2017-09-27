package com.example.palexis3.nearme.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.palexis3.nearme.Adapter.RestaurantListAdapter;
import com.example.palexis3.nearme.Models.Result;
import com.example.palexis3.nearme.Networking.RestaurantClient;
import com.example.palexis3.nearme.Networking.ServiceGenerator;
import com.example.palexis3.nearme.R;
import com.example.palexis3.nearme.Responses.ResultsResponse;
import com.example.palexis3.nearme.Utilities.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 MainActivity utilizes RecyclerView to populate individual restaurants.
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
        Call<ResultsResponse>  call = client.getRestaurants(Utils.getGooglePlacesApiKey());
        call.enqueue(new Callback<ResultsResponse>() {
            @Override
            public void onResponse(Call<ResultsResponse> call, Response<ResultsResponse> response) {
                if(response.isSuccessful()) {

                    ResultsResponse sourceResponse = response.body();
                    ArrayList<Result> restaurantsArrayList =
                            new ArrayList<>(sourceResponse.getResults());

                    if(restaurantsArrayList != null && restaurantsArrayList.size() > 0) {

                        mLayoutManager = new LinearLayoutManager(getApplicationContext());

                        recyclerView.setLayoutManager(mLayoutManager);

                        restaurantListAdapter = new RestaurantListAdapter(restaurantsArrayList, getApplicationContext());

                        // Set restaurantListAdapter as the adapter for RecyclerView.
                        recyclerView.setAdapter(restaurantListAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultsResponse> call, Throwable t) {
                Log.d(TAG, t.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map_view:
                // get our map view
                Intent intent = new Intent(this, MyMapActivity.class);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
