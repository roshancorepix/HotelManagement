package com.roshan.hotelmanegment.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.roshan.hotelmanegment.UI.UiFragments.ChatFragments.ChatListFragment;
import com.roshan.hotelmanegment.UI.UiFragments.ChatFragments.FriendListFragment;


public class ChatTabAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;

    public ChatTabAdapter(Context context, @NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ChatListFragment();

            case 1:
                return new FriendListFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
