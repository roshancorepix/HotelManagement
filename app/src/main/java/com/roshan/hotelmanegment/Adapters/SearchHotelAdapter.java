package com.roshan.hotelmanegment.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.roshan.hotelmanegment.Model.Hotel;
import com.roshan.hotelmanegment.R;

import java.util.List;

public class SearchHotelAdapter extends RecyclerView.Adapter<SearchHotelAdapter.SearchHotelViewHolder> {

    private Context context;
    private List<Hotel> hotelList;

    public SearchHotelAdapter(Context context, List<Hotel> hotelList) {
        this.context = context;
        this.hotelList = hotelList;
    }

    @NonNull
    @Override
    public SearchHotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_hotel_layout, parent, false);
        return new SearchHotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHotelViewHolder holder, int position) {
        Glide.with(context)
                .load(hotelList.get(position).getImageUrl())
                .into(holder.hotelImage);

        holder.hotelName.setText(hotelList.get(position).getHotelName());
        holder.hotelLocation.setText(hotelList.get(position).getHotelLocation());
        holder.hotelPrice.setText(hotelList.get(position).getHotelPrice());
        holder.hotelRating.setText(hotelList.get(position).getHotelRating());
    }

    @Override
    public int getItemCount() {
        return hotelList != null ? hotelList.size() : 0;
    }

    public class SearchHotelViewHolder extends RecyclerView.ViewHolder{

        ImageView hotelImage;
        TextView hotelRating, hotelName, hotelLocation, hotelPrice;
        public SearchHotelViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelImage = itemView.findViewById(R.id.iv_h_image);
            hotelLocation = itemView.findViewById(R.id.tv_hotel_location);
            hotelName = itemView.findViewById(R.id.tv_hotel_name);
            hotelPrice = itemView.findViewById(R.id.tv_hotel_price);
            hotelRating = itemView.findViewById(R.id.hotel_rating);
        }
    }
}
