package com.roshan.hotelmanegment.UI.UiFragments.ChatFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roshan.hotelmanegment.Adapters.FriendListAdapter;
import com.roshan.hotelmanegment.Common.CommonFunction;
import com.roshan.hotelmanegment.Interface.ItemClickListener;
import com.roshan.hotelmanegment.Model.User;
import com.roshan.hotelmanegment.R;
import com.roshan.hotelmanegment.UI.MainActivity;
import com.roshan.hotelmanegment.UI.MessagingActivity;

import java.util.ArrayList;
import java.util.List;

public class FriendListFragment extends Fragment implements ItemClickListener {

    private final String TAG = getClass().getSimpleName();
    private RecyclerView friendListRv;
    private List<User> userList = new ArrayList<>();
    private FriendListAdapter friendListAdapter;
    private TextView noFriends;

    public FriendListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_list, container, false);
        // bindId's
        bindID(view);
        getFriendList();
        return view;
    }

    private void getFriendList() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("app_user");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                if (snapshot != null) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User user = dataSnapshot.getValue(User.class);
                        if (!user.getUserID().equals(firebaseUser.getUid())) {
                            userList.add(user);
                        }
                    }
                    addDataInFriendList();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        reference.addValueEventListener(listener);
    }

    private void addDataInFriendList() {
        Log.e(TAG,"userList: "+userList.size());
        if (userList.size() != 0) {
            noFriends.setVisibility(View.GONE);
            friendListRv.setHasFixedSize(true);
            friendListRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            friendListAdapter = new FriendListAdapter(getActivity(), userList);
            friendListAdapter.setOnItemClickListener(this);
            friendListRv.setAdapter(friendListAdapter);
        }else {
            noFriends.setVisibility(View.VISIBLE);
        }
    }

    private void bindID(View view) {
        friendListRv = view.findViewById(R.id.rv_friend);
        noFriends = view.findViewById(R.id.tv_no_friends);
    }

    @Override
    public void onItemClick(int itemPosition) {
        startActivity(new Intent(getActivity(), MessagingActivity.class)
                .putExtra("userID", userList.get(itemPosition).getUserID()));
    }
}