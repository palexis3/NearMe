package com.example.palexis3.nearme.Activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.palexis3.nearme.Models.RestaurantLimitedDetails;
import com.example.palexis3.nearme.Models.Result;
import com.example.palexis3.nearme.Models.Reviews;
import com.example.palexis3.nearme.Networking.RestaurantClient;
import com.example.palexis3.nearme.Networking.ServiceGenerator;
import com.example.palexis3.nearme.R;
import com.example.palexis3.nearme.Responses.ResultResponse;
import com.example.palexis3.nearme.Utilities.Utils;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";


    private RestaurantLimitedDetails restaurantLimited;

    @BindView(R.id.ivRestaurantDetailImage)
    ImageView image;
    @BindView(R.id.tvRestaurantDetailName)
    TextView name;
    @BindView(R.id.tvRestaurantDetailRating)
    TextView rating;
    @BindView(R.id.tvRestaurantDetailContact)
    TextView contact;
    @BindView(R.id.tvRestaurantDetailAddress)
    TextView address;
    @BindView(R.id.tvRestaurantDetailReview)
    TextView reviews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_restaurant_detail);
        ButterKnife.bind(this);
        restaurantLimited = Parcels.unwrap(getIntent().getParcelableExtra("restaurant"));

        // first get the details for this restaurant
        getRestaurantDetails();
    }

    private void getRestaurantDetails() {

        if(restaurantLimited != null) {
            // create an instance of our restaurant client
            RestaurantClient client = ServiceGenerator.createService(RestaurantClient.class);
            Call<ResultResponse> call = client.getRestaurant(restaurantLimited.getPlace_id(), Utils.getGooglePlacesApiKey());
            call.enqueue(new Callback<ResultResponse>() {
                @Override
                public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                    if(response.isSuccessful()) {
                        ResultResponse resultResponse = response.body();
                        if(resultResponse != null) {
                            updateView(resultResponse.getResult());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResultResponse> call, Throwable t) {
                    Log.d(TAG, t.getLocalizedMessage());
                    Toast.makeText(getApplicationContext(), "Couldn't get restaurant details", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Couldn't get restaurant details", Toast.LENGTH_LONG).show();
        }

    }

    private void updateView(Result restaurant) {

        // check if we have a valid restaurant
        if(restaurant != null) {
            name.setText(restaurant.getName());

            String con = restaurant.getFormatted_phone_number() != null
                    ? restaurant.getFormatted_phone_number() : "N/A";
            contact.setText(con);

            String rate = restaurant.getRating() != null ? String.valueOf(restaurant.getRating()) : "N/A";
            rating.setText(rate);

            String addr = restaurant.getFormatted_address() != null ? restaurant.getFormatted_address() : "N/A";
            address.setText(addr);

            String photo_reference = restaurant.getPhotosArrayList() != null && restaurant.getPhotosArrayList().size() > 0 ?
                    restaurant.getPhotosArrayList().get(0).getPhotoReference() : null;


            // setting up the text
            reviews.setMovementMethod(new ScrollingMovementMethod());
            ArrayList<Reviews> reviewsArrayList = restaurant.getReviewsArrayList();

            if(reviewsArrayList != null && reviewsArrayList.size() > 0) {
                StringBuilder sb = new StringBuilder();
                String reviewer, text;

                for(Reviews review: reviewsArrayList) {
                    reviewer = String.format("Reviewer: %-20s\n", review.getAuthorName());
                    text = String.format("%-50s\n", review.getText());
                    sb.append(reviewer + "\n");
                    sb.append(text + "\n\n");
                }
                reviews.setText(sb.toString());
            } else {
                reviews.setText("N/A");
            }

            String photo = String.format(
                    "https://maps.googleapis.com/maps/api/place/photo?maxwidth=200&photoreference=%s&key=%s",
                    photo_reference,
                    Utils.getGooglePlacesApiKey());

            Glide.with(this)
                    .load(photo)
                    .error(R.drawable.local_dining)
                    .into(image);
        } else {
            Toast.makeText(this, "Could not get the details for this restaurant!", Toast.LENGTH_LONG).show();
        }
    }
}
