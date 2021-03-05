package com.roshan.hotelmanegment.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.roshan.hotelmanegment.R;
import com.roshan.hotelmanegment.UI.UiFragments.HomeFragment;

public class MainActivity extends AppCompatActivity implements ChipNavigationBar.OnItemSelectedListener{

    private ChipNavigationBar navigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setContentView(R.layout.activity_main);

        binID();
        navigationBar.setOnItemSelectedListener(this);
        navigationBar.setItemSelected(R.id.home, true);
        openFragment(new HomeFragment(), "HomeFragmentTag");
    }

    private void binID() {
        navigationBar = findViewById(R.id.bottom_nav);
    }

    @Override
    public void onItemSelected(int i) {
        Fragment fragment = null;
        switch (i){
            case R.id.home:
                fragment = new HomeFragment();
                openFragment(fragment, "HomeFragmentTag");
                break;

            default:
                break;
        }
    }

    private void openFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                //.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.fragment_container, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}