package com.example.palexis3.nearme.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.palexis3.nearme.Models.RestaurantLimitedDetails;
import com.example.palexis3.nearme.Networking.RestaurantClient;
import com.example.palexis3.nearme.Networking.ServiceGenerator;
import com.example.palexis3.nearme.R;
import com.example.palexis3.nearme.Responses.RestaurantsResponse;
import com.example.palexis3.nearme.Utilities.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private final static String TAG = "MapActivity";
    private GoogleMap gmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // retrieve the content view that renders the map
        setContentView(R.layout.activity_maps);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gmap = googleMap;

        // create an instance of our restaurant client
        RestaurantClient client = ServiceGenerator.createService(RestaurantClient.class);
        Call<RestaurantsResponse> call = client.getRestaurants(Utils.getGooglePlacesApiKey());
        call.enqueue(new Callback<RestaurantsResponse>() {
            @Override
            public void onResponse(Call<RestaurantsResponse> call, Response<RestaurantsResponse> response) {
                if(response.isSuccessful()) {
                    try {
                        RestaurantsResponse sourceResponse = response.body();
                        ArrayList<RestaurantLimitedDetails> restaurantsArrayList =
                                new ArrayList<>(sourceResponse.getRestaurantLimitedDetailsList());

                        // Add markers of restaurants to map
                        if(restaurantsArrayList != null && restaurantsArrayList.size() > 0) {
                            for(RestaurantLimitedDetails res: restaurantsArrayList) {
                                
                                LatLng latLng = new LatLng(res.getGeometry().getLocation().getLatitude(),
                                       res.getGeometry().getLocation().getLongitude());
                                MarkerOptions markerOptions = new MarkerOptions();
                                // position of restaurant
                                markerOptions.position(latLng);
                                // name of restaurant
                                markerOptions.title(res.getName());
                                // Add marker for map
                                gmap.addMarker(markerOptions);
                                gmap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            }
                        }
                    } catch(Exception e) {
                        Log.d(TAG, "There is an error");
                        e.printStackTrace();
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
