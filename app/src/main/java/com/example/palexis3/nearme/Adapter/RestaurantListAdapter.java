package com.example.palexis3.nearme.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.palexis3.nearme.Activities.DetailActivity;
import com.example.palexis3.nearme.Models.Result;
import com.example.palexis3.nearme.R;
import com.example.palexis3.nearme.Utilities.Utils;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {

    private ArrayList<Result> limitedDetailsList;
    private Context context;

    public RestaurantListAdapter(ArrayList<Result> limitedDetailsList, Context context) {
        this.limitedDetailsList = limitedDetailsList;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }


    // creating view holder class as required by recyclerview for performance reasons
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.ivRestaurantImage)
        ImageView image;

        @BindView(R.id.tvRestaurantName)
        TextView name;

        @BindView(R.id.tvRestaurantRating)
        TextView rating;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        // Handles the item being clicked
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                Result restaurant = limitedDetailsList.get(position);
                // We can access the data within the views
                Intent intent = new Intent(context, DetailActivity.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("restaurant", Parcels.wrap(restaurant));
                context.startActivity(intent);
            }
        }
    }

    // inflating restaurant item layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_restaurant, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // get the specific restaurant
        Result restaurant = limitedDetailsList.get(position);

        TextView name = holder.name;
        name.setText(restaurant.getName());

        TextView rating = holder.rating;
        if(restaurant.getRating() != null) {
            rating.setText(String.valueOf(restaurant.getRating()));
        } else {
            rating.setText("N/A");
        }


        String photo_reference = restaurant.getPhotosArrayList() != null && restaurant.getPhotosArrayList().size() > 0 ?
                restaurant.getPhotosArrayList().get(0).getPhotoReference() : null;

        String photo = String.format(
                "https://maps.googleapis.com/maps/api/place/photo?maxwidth=200&photoreference=%s&key=%s",
                photo_reference,
                Utils.getGooglePlacesApiKey());

        ImageView image = holder.image;
        Glide.with(context)
                .load(photo)
                .error(R.drawable.local_dining)
                .into(image);
    }

    @Override
    public int getItemCount() {
        return limitedDetailsList != null ? limitedDetailsList.size() : 0;
    }
}
