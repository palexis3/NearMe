package com.example.palexis3.nearme.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.palexis3.nearme.Models.Result;
import com.example.palexis3.nearme.Networking.RestaurantClient;
import com.example.palexis3.nearme.Networking.ServiceGenerator;
import com.example.palexis3.nearme.R;
import com.example.palexis3.nearme.Responses.ResultsResponse;
import com.example.palexis3.nearme.Utilities.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
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
        Call<ResultsResponse> call = client.getRestaurants(Utils.getGooglePlacesApiKey());
        call.enqueue(new Callback<ResultsResponse>() {
            @Override
            public void onResponse(Call<ResultsResponse> call, Response<ResultsResponse> response) {
                if(response.isSuccessful()) {
                    try {

                        ResultsResponse sourceResponse = response.body();

                        ArrayList<Result> restaurantsArrayList =
                                new ArrayList<>(sourceResponse.getResults());

                        // Add markers of restaurants to map
                        if(restaurantsArrayList != null && restaurantsArrayList.size() > 0) {

                            double minLat = Double.MAX_VALUE, minLong = Double.MAX_VALUE, maxLat = Double.MIN_VALUE, maxLong = Double.MIN_VALUE;

                            for(Result res: restaurantsArrayList) {
                                
                                LatLng latLng = new LatLng(res.getGeometry().getLocation().getLat(),
                                       res.getGeometry().getLocation().getLng());

                                if(latLng.latitude != 0.000000) {
                                    minLat = Math.min(minLat, latLng.latitude);
                                    maxLat = Math.max(maxLat, latLng.latitude);
                                }

                                if(latLng.longitude != 0.000000) {
                                    minLong = Math.min(minLong, latLng.longitude);
                                    maxLong = latLng.longitude;
                                }

                                MarkerOptions markerOptions = new MarkerOptions();
                                // position of restaurant
                                markerOptions.position(latLng);

                                // name of restaurant
                                markerOptions.title(res.getName());

                                // focus on markers in map
                                gmap.animateCamera(CameraUpdateFactory.zoomIn());

                                // Add marker for map
                                gmap.addMarker(markerOptions);
                                gmap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            }

                            // focus on a particular area
                            LatLngBounds AREA = new LatLngBounds(
                                    new LatLng(minLat, minLong), new LatLng(maxLat, maxLong));

                            String area = String.format("MinLong: %f, MinLat: %f, maxLong: %f, maxLat: %f", minLong, minLat,
                                    maxLong, maxLat);

                            Log.d("AREA", area);
                            gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(AREA.getCenter(), 15));

                        }
                    } catch(Exception e) {
                        Log.d(TAG, "There is an error");
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResultsResponse> call, Throwable t) {
                Log.d(TAG, t.getLocalizedMessage());
            }
        });
    }
}
