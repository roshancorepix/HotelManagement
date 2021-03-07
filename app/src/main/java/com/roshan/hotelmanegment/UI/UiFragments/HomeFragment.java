package com.roshan.hotelmanegment.UI.UiFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roshan.hotelmanegment.Adapters.BestHotelAdapter;
import com.roshan.hotelmanegment.Adapters.ExploreWorldAdapter;
import com.roshan.hotelmanegment.Model.BestHotelMode;
import com.roshan.hotelmanegment.Model.ExploreWord;
import com.roshan.hotelmanegment.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private ViewPager exploreWorldViewPager;
    private LinearLayout dotIndicatorLayout;
    private List<ExploreWord> exploreWordList = new ArrayList<>();
    private ExploreWorldAdapter exploreWorldAdapter;
    private TextView[] dots;
    private Handler handler = new Handler();
    int delay = 5000;
    Runnable runnable;
    private int[] pagerIndex = {-1};
    private RecyclerView bestHotelRecyclerview;
    private BestHotelAdapter bestHotelAdapter;
    private List<BestHotelMode> bestHotelModeList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pagerIndex[0]++;
                if (pagerIndex[0] >= exploreWorldAdapter.getCount()){
                    pagerIndex[0] = 0;
                }
                exploreWorldViewPager.setCurrentItem(pagerIndex[0]);
                runnable=this;
                handler.postDelayed(runnable, delay);
            }
        },delay);
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        bindID(view);

        exploreWordList.add(new ExploreWord(R.drawable.dubai, "Dubai", "6 Days / 5 Nights", "$500"));
        exploreWordList.add(new ExploreWord(R.drawable.australia, "Australia", "8 Days / 7 Nights", "$729"));
        exploreWordList.add(new ExploreWord(R.drawable.new_york, "New York", "7 Days / 6 Nights", "$1,100"));
        exploreWordList.add(new ExploreWord(R.drawable.london, "London", "5 Days / 4 Nights", "$1,000"));
        exploreWordList.add(new ExploreWord(R.drawable.canada, "Canada", "7 Days / 6 Nights", "$1,200"));

        exploreWorldAdapter = new ExploreWorldAdapter(getActivity(), exploreWordList);
        exploreWorldViewPager.setAdapter(exploreWorldAdapter);
        addBottomDots(0);
        exploreWorldViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Best Hotel
        getBestHotel();

        return view;
    }

    private void getBestHotel() {
        bestHotelRecyclerview.setHasFixedSize(true);
        bestHotelRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));

        bestHotelModeList.add(new BestHotelMode(R.drawable.burj_al_arab_jumeirah, "BURJ AL ARAB JUMEIRAH", "Dubai", "200", 4.5f));
        bestHotelModeList.add(new BestHotelMode(R.drawable.the_shelbourne_hotel, "The Shelbourne Hotel", "Dubai", "600", 5.0f));
        bestHotelModeList.add(new BestHotelMode(R.drawable.tajmahel, "Tajmahal", "India", "150", 4.5f));
        bestHotelModeList.add(new BestHotelMode(R.drawable.theplaza, "The Plaza", "New York", "400", 5.0f));

        bestHotelAdapter = new BestHotelAdapter(getActivity(), bestHotelModeList);
        bestHotelRecyclerview.setAdapter(bestHotelAdapter);
    }

    private void addBottomDots(int position) {
        dots = new TextView[exploreWordList.size()];
        dotIndicatorLayout.removeAllViews();

        for (int i=0; i < dots.length; i++){
            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226\t"));
            dots[i].setTextSize(18f);
            dots[i].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            dotIndicatorLayout.addView(dots[i]);
        }
        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.colorAccent));
        }

    }

    private void bindID(View view) {
        exploreWorldViewPager = view.findViewById(R.id.explore_view_pager);
        dotIndicatorLayout = view.findViewById(R.id.dot_container);
        bestHotelRecyclerview = view.findViewById(R.id.rv_best_hotel);
    }


}