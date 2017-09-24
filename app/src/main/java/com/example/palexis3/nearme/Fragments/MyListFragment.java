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
import com.example.palexis3.nearme.R;
import com.example.palexis3.nearme.Responses.RestaurantsResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 MyListFragement utilizes RecyclerView to populate individual restaurants.
 */

public class MyListFragment extends Fragment {


    private static final String TAG = "MyListFragment";

    // used for RxJava
    private Subscription subscription;

    // used for Butterknife
    private Unbinder unbinder;

    private RestaurantListAdapter restaurantListAdapter;
    private List<RestaurantLimitedDetails> restaurantLimitedDetailsList;
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

        mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(mLayoutManager);

        // Set CustomAdapter as the adapter for RecyclerView.
        recyclerView.setAdapter(restaurantListAdapter);

        return view;
    }

    @Override
    public void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }

    // When binding a fragment in onCreateView, set the views to null in onDestroyView.
    // ButterKnife returns an Unbinder on the initial binding that has an unbind method to do this automatically.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initDataset() {

        subscription = RestaurantClient
                        .getInstance()
                        .getRestaurants()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<RestaurantsResponse>() {
                            @Override
                            public void onCompleted() {
                                Log.d(TAG, "In onCompleted()");
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                Log.d(TAG, "In onError()");
                            }

                            @Override
                            public void onNext(RestaurantsResponse restaurantsResponse) {
                                Log.d(TAG, "In onNext()");
                                restaurantLimitedDetailsList = restaurantsResponse.getRestaurantLimitedDetailsList();
                                restaurantListAdapter = new RestaurantListAdapter(getActivity());
                                restaurantListAdapter.setRestaurantList(restaurantLimitedDetailsList);
                            }
                        });


    }
}
