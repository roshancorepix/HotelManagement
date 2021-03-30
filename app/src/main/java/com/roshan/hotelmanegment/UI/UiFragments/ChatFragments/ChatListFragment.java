package com.roshan.hotelmanegment.UI.UiFragments.ChatFragments;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roshan.hotelmanegment.Adapters.RecentChatListAdapter;
import com.roshan.hotelmanegment.Interface.ItemClickListener;
import com.roshan.hotelmanegment.Model.Chat;
import com.roshan.hotelmanegment.Model.User;
import com.roshan.hotelmanegment.R;
import com.roshan.hotelmanegment.SharedPreference.Preference;
import com.roshan.hotelmanegment.UI.MessagingActivity;

import java.util.ArrayList;
import java.util.List;

public class ChatListFragment extends Fragment implements ItemClickListener {

    private final String TAG = getClass().getSimpleName();
    private RecyclerView recentChatListRv;
    private RecentChatListAdapter adapter;
    private List<User> mUsersList = new ArrayList<>();
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    private List<String> userList;
    int pos = 0;
    public ChatListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        // Inflate the layout for this fragment
        recentChatListRv = view.findViewById(R.id.rv_recent_chatList);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        Preference.init(getActivity());

        userList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getSender().equals(firebaseUser.getUid())){
                        userList.add(chat.getReceiver());
                    }if (chat.getReceiver().equals(firebaseUser.getUid())){
                        userList.add(chat.getSender());
                    }
                }
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    private void readChats() {
        reference=FirebaseDatabase.getInstance().getReference("app_user");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsersList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);

                    for (String id : userList){
                        if (user.getUserID().equals(id)){
                            if (mUsersList.size() != 0){
                                for (User user1 : mUsersList){
                                    if (!user.getUserID().equals(user1.getUserID())){
                                        mUsersList.add(user);
                                    }
                                }
                            }else {
                                mUsersList.add(user);
                            }
                        }
                    }
                }
                setRecentChatDataInRv();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setRecentChatDataInRv(){
        recentChatListRv.setHasFixedSize(true);
        recentChatListRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new RecentChatListAdapter(getContext(), mUsersList);
        adapter.setOnItemClickListener(this);
        recentChatListRv.setAdapter(adapter);

    }

    @Override
    public void onItemClick(int itemPosition) {
        Preference.setIsMessageSend(false);
        pos = itemPosition;

        startActivity(new Intent(getActivity(), MessagingActivity.class)
                .putExtra("userID", mUsersList.get(itemPosition).getUserID()));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null && Preference.getIsMessageSend()){
            adapter.notifyItemMoved(pos, 0);
        }
    }
}