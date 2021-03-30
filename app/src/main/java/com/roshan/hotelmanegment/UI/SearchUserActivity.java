package com.roshan.hotelmanegment.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.roshan.hotelmanegment.Adapters.FriendListAdapter;
import com.roshan.hotelmanegment.Interface.ItemClickListener;
import com.roshan.hotelmanegment.Model.User;
import com.roshan.hotelmanegment.R;

import java.util.ArrayList;
import java.util.List;

public class SearchUserActivity extends AppCompatActivity implements ItemClickListener {
    private RecyclerView searchUserRv;
    private EditText searchText;
    private List<User> userList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setContentView(R.layout.activity_search_user);
        bindID();

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchResult(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void searchResult(String s) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("app_user").orderByChild("userName")
                .startAt(s)
                .endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    if (!user.getUserID().equals(firebaseUser.getUid())){
                        userList.add(user);
                    }
                }
                setDataInList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setDataInList() {
        searchUserRv.setHasFixedSize(true);
        searchUserRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        FriendListAdapter friendListAdapter = new FriendListAdapter(this, userList);
        friendListAdapter.setOnItemClickListener(this);
        searchUserRv.setAdapter(friendListAdapter);
    }

    private void bindID() {
        searchUserRv = findViewById(R.id.rv_search_user);
        searchText = findViewById(R.id.et_search);
    }

    @Override
    public void onItemClick(int itemPosition) {
        startActivity(new Intent(SearchUserActivity.this, MessagingActivity.class)
                .putExtra("userID", userList.get(itemPosition).getUserID()));

        finish();
    }
}