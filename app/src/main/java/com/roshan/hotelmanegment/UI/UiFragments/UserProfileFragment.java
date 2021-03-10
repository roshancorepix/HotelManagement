package com.roshan.hotelmanegment.UI.UiFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.roshan.hotelmanegment.R;
import com.roshan.hotelmanegment.UI.LoginSignUpActivity;


public class UserProfileFragment extends Fragment implements View.OnClickListener {

    private TextView logoutButton;
    public UserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        // Inflate the layout for this fragment
        logoutButton = view.findViewById(R.id.btn_logout);
        logoutButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginSignUpActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                getActivity().finish();
                break;
        }
    }
}