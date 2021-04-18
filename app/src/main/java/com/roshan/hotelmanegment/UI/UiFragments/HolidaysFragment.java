package com.roshan.hotelmanegment.UI.UiFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roshan.hotelmanegment.R;


public class HolidaysFragment extends Fragment {


    public HolidaysFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_holidays, container, false);
        // Inflate the layout for this fragment
        return rootView;
    }
}