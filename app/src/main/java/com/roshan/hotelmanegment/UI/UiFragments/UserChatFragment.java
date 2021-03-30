package com.roshan.hotelmanegment.UI.UiFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;
import com.roshan.hotelmanegment.Adapters.ChatTabAdapter;
import com.roshan.hotelmanegment.R;
import com.roshan.hotelmanegment.UI.SearchUserActivity;

public class UserChatFragment extends Fragment {

    private ViewPager chatViewPager;
    private TabLayout chatTabLayout;
    private ImageButton searchButton;

    public UserChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_chat, container, false);
        chatTabLayout = view.findViewById(R.id.chat_tab_layout);
        chatViewPager = view.findViewById(R.id.chat_view_pager);
        searchButton = view.findViewById(R.id.ib_search);

        chatTabLayout.addTab(chatTabLayout.newTab().setText("Chats"));
        chatTabLayout.addTab(chatTabLayout.newTab().setText("Friends"));
        chatTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ChatTabAdapter adapter = new ChatTabAdapter(getActivity(), getChildFragmentManager(), chatTabLayout.getTabCount());

        chatViewPager.setAdapter(adapter);
        chatViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(chatTabLayout));

        chatTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                chatViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        searchButton.setOnClickListener(v -> startActivity(new Intent(getActivity(),
                SearchUserActivity.class)));
        return view;
    }
}