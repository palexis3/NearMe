package com.example.palexis3.nearme.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.palexis3.nearme.Models.RestaurantLimitedDetails;
import com.example.palexis3.nearme.R;
import com.example.palexis3.nearme.Utilities.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {

    private List<RestaurantLimitedDetails> limitedDetailsList;
    private Context context;

    public RestaurantListAdapter(List<RestaurantLimitedDetails> limitedDetailsList, Context context) {
        this.limitedDetailsList = limitedDetailsList;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivRestaurantImage)
        ImageView image;

        @BindView(R.id.tvRestaurantName)
        TextView name;

        @BindView(R.id.tvRestaurantRating)
        TextView rating;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
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
        RestaurantLimitedDetails restaurant = limitedDetailsList.get(position);

        TextView name = holder.name;
        name.setText(restaurant.getName());

        TextView rating = holder.rating;
        rating.setText(restaurant.getRating());

        String photo_reference = restaurant.getPhotosArrayList().size() > 0 ? restaurant.getPhotosArrayList().get(0).getPhotoReference() : null;

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
