package com.roshan.hotelmanegment.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.roshan.hotelmanegment.Model.ExploreWord;
import com.roshan.hotelmanegment.R;

import java.util.List;

public class ExploreWorldAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<ExploreWord> exploreWordList;

    public ExploreWorldAdapter(Context context, List<ExploreWord> exploreWordList) {
        this.context = context;
        this.exploreWordList = exploreWordList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_explore_world_row, container, false);
        ImageView countryImage = view.findViewById(R.id.city_image);
        TextView countryName = view.findViewById(R.id.country_name);
        TextView countryStay = view.findViewById(R.id.tv_stay);
        TextView amount = view.findViewById(R.id.package_amount);

        Glide.with(context)
                .load(exploreWordList.get(position).getCountryImage())
                .into(countryImage);

        countryName.setText(exploreWordList.get(position).getCountryName());
        countryStay.setText(exploreWordList.get(position).getSayDays());
        amount.setText(exploreWordList.get(position).getPackageAmount());
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return exploreWordList != null ? exploreWordList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
