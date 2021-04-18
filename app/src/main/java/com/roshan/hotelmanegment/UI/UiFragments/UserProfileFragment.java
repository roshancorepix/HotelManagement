package com.roshan.hotelmanegment.UI.UiFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.roshan.hotelmanegment.R;
import com.roshan.hotelmanegment.SharedPreference.Preference;
import com.roshan.hotelmanegment.UI.LoginSignUpActivity;


public class UserProfileFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout logoutButton;
    private TextView userName, userEmail, userMobileNo, countryCode;

    public UserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        Preference.init(getActivity());
        logoutButton = view.findViewById(R.id.btn_logout);
        userName = view.findViewById(R.id.username);
        userEmail = view.findViewById(R.id.user_email);
        userMobileNo = view.findViewById(R.id.user_mobile_no);
        countryCode = view.findViewById(R.id.user_code_country);
        logoutButton.setOnClickListener(this);

        setUserProfile();

        return view;
    }

    private void setUserProfile() {
        userName.setText(Preference.getUsername());
        userEmail.setText(Preference.getUserEmail());
        if (Preference.getUserMobileNo() != null) {
            userMobileNo.setText(Preference.getUserMobileNo());
        }else {
            countryCode.setVisibility(View.GONE);
            userMobileNo.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                Preference.setIsLogin(false);
                Preference.clear();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginSignUpActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                getActivity().finish();
                break;
        }
    }
}