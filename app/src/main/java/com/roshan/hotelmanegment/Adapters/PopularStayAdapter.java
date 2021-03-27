package com.roshan.hotelmanegment.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.roshan.hotelmanegment.Model.PopularHotel;
import com.roshan.hotelmanegment.R;

import java.util.List;

public class PopularStayAdapter extends RecyclerView.Adapter<PopularStayAdapter.PopularStayViewHolder> {
    private Context context;
    private List<PopularHotel> popularHotelList;

    public PopularStayAdapter(Context context, List<PopularHotel> popularHotelList) {
        this.context = context;
        this.popularHotelList = popularHotelList;
    }

    @NonNull
    @Override
    public PopularStayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_popular_stays, parent, false);
        return new PopularStayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularStayViewHolder holder, int position) {
        Glide.with(context)
                .load(popularHotelList.get(position).getImageUrl())
                .into(holder.hotelImage);

        holder.hotelRating.setText(popularHotelList.get(position).getHotelRating());
        holder.hotelLocation.setText(popularHotelList.get(position).getHotelLocation());
        holder.hotelStar.setText(popularHotelList.get(position).getHotelStar());
        holder.hotelName.setText(popularHotelList.get(position).getHotelName());
        holder.hotelPrice.setText(popularHotelList.get(position).getHotelPrice());
    }

    @Override
    public int getItemCount() {
        return popularHotelList != null ? popularHotelList.size() : 0;
    }

    public class PopularStayViewHolder extends RecyclerView.ViewHolder{

        ImageView hotelImage;
        TextView hotelName, hotelStar, hotelLocation, hotelPrice, hotelRating;
        public PopularStayViewHolder(@NonNull View itemView) {
            super(itemView);

            hotelImage = itemView.findViewById(R.id.p_hotel_image);
            hotelRating = itemView.findViewById(R.id.hotel_rating);
            hotelLocation = itemView.findViewById(R.id.tv_h_location);
            hotelStar = itemView.findViewById(R.id.tv_h_star);
            hotelName = itemView.findViewById(R.id.tv_h_name);
            hotelPrice = itemView.findViewById(R.id.tv_h_price);
        }
    }
}
