package com.roshan.hotelmanegment.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.roshan.hotelmanegment.R;
import com.roshan.hotelmanegment.UI.UiFragments.HolidaysFragment;
import com.roshan.hotelmanegment.UI.UiFragments.HomeFragment;
import com.roshan.hotelmanegment.UI.UiFragments.UserChatFragment;
import com.roshan.hotelmanegment.UI.UiFragments.UserProfileFragment;

public class MainActivity extends AppCompatActivity implements ChipNavigationBar.OnItemSelectedListener {

    private ChipNavigationBar navigationBar;
    private int backCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setContentView(R.layout.activity_main);

        binID();
        navigationBar.setOnItemSelectedListener(this);
        navigationBar.setItemSelected(R.id.home, true);
        openHomeFragment();
    }

    private void binID() {
        navigationBar = findViewById(R.id.bottom_nav);
    }

    @Override
    public void onItemSelected(int i) {
        switch (i) {
            case R.id.home:
                openHomeFragment();
                break;

            case R.id.profile:
                openProfileFragment();
                break;

            case R.id.buddy:
                openChatFragment();
                break;

            case R.id.holidays:
                openHolidayFragment();
                break;
            default:
                break;
        }
    }

    private void openHolidayFragment() {
        HolidaysFragment holidaysFragment = new HolidaysFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction
                .replace(R.id.fragment_container, holidaysFragment, "HolidayFragmentTag")
                .commit();
    }

    private void openHomeFragment() {
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction
                .replace(R.id.fragment_container, homeFragment, "HomeFragmentTag")
                .commit();
    }

    private void openProfileFragment() {
        UserProfileFragment userProfileFragment = new UserProfileFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction
                .replace(R.id.fragment_container, userProfileFragment, "UserProfileFragmentTag")
                .commit();
    }

    private void openChatFragment() {
        UserChatFragment userChatFragment = new UserChatFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction
                .replace(R.id.fragment_container, userChatFragment, "UserChatFFragmentTag")
                .commit();
    }

    @Override
    public void onBackPressed() {
        backCount++;
        if (backCount > 1) {
            backCount = 0;
            finishAffinity();
            super.onBackPressed();
        } else {
            navigationBar.setItemSelected(R.id.home, true);
            openHomeFragment();
        }
    }
}