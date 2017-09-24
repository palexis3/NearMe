package com.example.palexis3.nearme.Activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.example.palexis3.nearme.Models.Restaurant;
import com.example.palexis3.nearme.R;

import org.parceler.Parcels;

public class MyMapActivity extends FragmentActivity implements OnMapReadyCallback {  
    private static final String TAG = "MyMapActivity"; 
    private SupportMapFragment mapFragment; 
    private Restaurant res;  
    @Override 

    protected void onCreate(@Nullable Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        // retrieve the content view that renders the map 
        setContentView(R.layout.frag_map); 
    }   

    @Override 

    public void onMapReady(GoogleMap googleMap) { 
        /**         Manipulates the map when it's available. 
         *        The API invokes this callback when the map is ready to be used. 
         */  

        res = (Restaurant) Parcels.unwrap(getIntent().getParcelableExtra("res"));  

        // Get the SupportMapFragment and request notification 
        // when the map is ready to be used. 

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager() 
         .findFragmentById(R.id.map); 

        mapFragment.getMapAsync(this);  

        //Add a marker for each of our items in restaurant details list 
        // if(res != null) { 
        LatLng obj = new LatLng(res.getGeometry().getLocation().getLatitude(), 
                res.getGeometry().getLocation().getLongitude()); 
        googleMap.addMarker(new MarkerOptions().position(obj) 
                .title(res.getName())); 
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(obj)); 
    } 
}
