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
import com.roshan.hotelmanegment.Model.BestHotelMode;
import com.roshan.hotelmanegment.R;

import java.util.List;

public class BestHotelAdapter extends RecyclerView.Adapter<BestHotelAdapter.BestHotelViewHolder> {
    private Context context;
    private List<BestHotelMode> bestHotelModeList;

    public BestHotelAdapter(Context context, List<BestHotelMode> bestHotelModeList) {
        this.context = context;
        this.bestHotelModeList = bestHotelModeList;
    }

    @NonNull
    @Override
    public BestHotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.best_hotel_row_layout, parent, false);
        return new BestHotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BestHotelViewHolder holder, int position) {
        Glide.with(context)
                .load(bestHotelModeList.get(position).getHotelImage())
                .into(holder.hotelImage);

        holder.hotelName.setText(bestHotelModeList.get(position).getHotelName());
        holder.hotelLocation.setText(bestHotelModeList.get(position).getHotelLocation());
        holder.hotelPrice.setText(bestHotelModeList.get(position).getHotelPrice());
        holder.hotelRating.setRating(bestHotelModeList.get(position).getHotelRating());
    }

    @Override
    public int getItemCount() {
        return bestHotelModeList != null ? bestHotelModeList.size() : 0;

    }

    public class BestHotelViewHolder extends RecyclerView.ViewHolder{

        ImageView hotelImage;
        TextView hotelName, hotelLocation, hotelPrice;
        RatingBar hotelRating;
        public BestHotelViewHolder(@NonNull View itemView) {
            super(itemView);

            hotelImage = itemView.findViewById(R.id.iv_hotel_img);
            hotelName = itemView.findViewById(R.id.tv_hotel_name);
            hotelLocation = itemView.findViewById(R.id.tv_hotel_location);
            hotelPrice = itemView.findViewById(R.id.tv_rent_amount);
            hotelRating = itemView.findViewById(R.id.hotel_rating);
        }
    }
}
