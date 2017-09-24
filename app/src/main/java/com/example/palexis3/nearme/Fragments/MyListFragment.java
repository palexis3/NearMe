package com.example.palexis3.nearme.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 MyListFragement utilizes RecyclerView to populate individual restaurants.
 */

public class MyListFragment extends Fragment {


    private static final String TAG = "MyListFragment";

    // used for Butterknife
    private Unbinder unbinder;

    private RestaurantClient restaurantService;
    private RestaurantListAdapter restaurantListAdapter;
    RecyclerView.LayoutManager mLayoutManager;


    @BindView(R.id.rvResturantList)
    RecyclerView recyclerView;


    // newInstance constructor for creating fragment with arguments
    public static MyListFragment newInstance(String title) {
        MyListFragment fragmentFirst = new MyListFragment();
        Bundle args = new Bundle();
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    // Inflate the view for the fragment based on layout XML
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        // Initialize dataset, this data is coming from google places API.
        initDataset();

        return view;
    }


    // When binding a fragment in onCreateView, set the views to null in onDestroyView.
    // ButterKnife returns an Unbinder on the initial binding that has an unbind method to do this automatically.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

                        mLayoutManager = new LinearLayoutManager(getActivity());

                        recyclerView.setLayoutManager(mLayoutManager);

                        restaurantListAdapter = new RestaurantListAdapter(restaurantsArrayList, getActivity());

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
