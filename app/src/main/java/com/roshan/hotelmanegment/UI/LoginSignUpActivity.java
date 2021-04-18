package com.roshan.hotelmanegment.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.roshan.hotelmanegment.R;
import com.roshan.hotelmanegment.SharedPreference.Preference;
import com.roshan.hotelmanegment.Utils.StatusBarUtils;

public class LoginSignUpActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.changeStatusBarColor(this, getWindow(), R.color.colorAccent);
        setContentView(R.layout.activity_login_sign_up);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        CardView skipButton = findViewById(R.id.card_skip_button);

        Preference.init(this);

        if (Preference.getUserLogin()){
            openHomePage();
        }

        //skipButton.setOnClickListener(v -> openHomePage());

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.log_in)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.sign_up)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final PagerAdapter adapter = new com.roshan.hotelmanegment.Adapters.PagerAdapter(this, getSupportFragmentManager(),
                tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void openHomePage() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}