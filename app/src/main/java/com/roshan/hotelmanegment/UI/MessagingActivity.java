package com.roshan.hotelmanegment.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.roshan.hotelmanegment.Adapters.MessageAdapter;
import com.roshan.hotelmanegment.Common.CommonFunction;
import com.roshan.hotelmanegment.Model.Chat;
import com.roshan.hotelmanegment.Model.User;
import com.roshan.hotelmanegment.Notification.ApiService;
import com.roshan.hotelmanegment.Notification.NotificationData;
import com.roshan.hotelmanegment.Notification.Response;
import com.roshan.hotelmanegment.Notification.RetrofitClient;
import com.roshan.hotelmanegment.Notification.Sender;
import com.roshan.hotelmanegment.Notification.Token;
import com.roshan.hotelmanegment.R;
import com.roshan.hotelmanegment.SharedPreference.Preference;
import com.roshan.hotelmanegment.Utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MessagingActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private EditText message;
    private RelativeLayout sendMessage;
    private FirebaseUser firebaseUser;
    private String receiverID;
    private DatabaseReference reference;
    private TextView receiverName;
    private List<Chat> chatList = new ArrayList<>();
    private RecyclerView readChatRecyclerview;
    private ValueEventListener seenListener;
    private ImageButton backButton;
    private MessageAdapter messageAdapter;
    private ApiService apiService;
    private boolean notify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setContentView(R.layout.activity_messaging);
        bindID();
        Preference.init(this);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        receiverID = getIntent().getStringExtra("userID");
        apiService = RetrofitClient.getClient(Util.FCM_BASE_URL).create(ApiService.class);
        sendMessage.setOnClickListener(this);
        backButton.setOnClickListener(this);
        getChatMessage();
        seenMessage(receiverID);
    }

    private void seenMessage(final String receiverID) {
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Chat chat = dataSnapshot.getValue(Chat.class);
                        if (chat.getReceiver().equals(firebaseUser.getUid()) &&
                                chat.getSender().equals(receiverID)) {
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("isseen", true);
                            dataSnapshot.getRef().updateChildren(hashMap);
                        }
                    }
                } else {
                    CommonFunction.showToastMessage(MessagingActivity.this,
                            "No data found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getChatMessage() {
        reference = FirebaseDatabase.getInstance()
                .getReference("app_user")
                .child(receiverID);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    User user = snapshot.getValue(User.class);
                    receiverName.setText(user.getUserName());
                    readMessage();
                } else {
                    CommonFunction.showToastMessage(MessagingActivity.this,
                            "No data found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        reference.addValueEventListener(listener);
    }

    private void readMessage() {
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                if (snapshot != null) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Chat chat = dataSnapshot.getValue(Chat.class);
                        if (chat.getReceiver().equals(firebaseUser.getUid()) &&
                                chat.getSender().equals(receiverID) ||
                                chat.getReceiver().equals(receiverID) &&
                                        chat.getSender().equals(firebaseUser.getUid())) {
                            chatList.add(chat);
                        }

                        setChatInRV();
                        Log.e(TAG,"List: "+chat);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        reference.addValueEventListener(listener);
    }

    private void setChatInRV() {
        readChatRecyclerview.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        readChatRecyclerview.setLayoutManager(layoutManager);
        messageAdapter = new MessageAdapter(this, chatList);
        readChatRecyclerview.setAdapter(messageAdapter);
    }

    private void bindID() {
        message = findViewById(R.id.et_message);
        sendMessage = findViewById(R.id.rl_send);
        receiverName = findViewById(R.id.current_username);
        readChatRecyclerview = findViewById(R.id.rv_messages);
        backButton = findViewById(R.id.ib_back_arrow);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_send:
                notify = true;
                sendMsg(view);
                break;

            case R.id.ib_back_arrow:
                finish();
                break;
        }
    }

    public void sendMsg(View view) {
        Log.e(TAG, "Send button click");
        String myMessage = message.getText().toString();

        if (TextUtils.isEmpty(myMessage)) {
            CommonFunction.showToastMessage(MessagingActivity.this, "Please enter a message");
        } else {
            Preference.setIsMessageSend(true);
            DatabaseReference myReference = FirebaseDatabase.getInstance().getReference();
            HashMap<String, Object> hashMap = new HashMap<>();

            hashMap.put("sender", firebaseUser.getUid());
            hashMap.put("receiver", receiverID);
            hashMap.put("message", myMessage);
            hashMap.put("time", Util.getCurrentTime());
            hashMap.put("isseen", false);

            myReference.child("Chats").push().setValue(hashMap);
        }
        message.getText().clear();

        final String msg = myMessage;

        reference = FirebaseDatabase.getInstance()
                .getReference("app_user")
                .child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (notify) {
                    sendNotification(receiverID, user.getUserName(), msg);
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendNotification(String receiverID, String userName, String msg) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiverID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Token token = dataSnapshot.getValue(Token.class);
                    NotificationData data = new NotificationData(firebaseUser.getUid(),
                            userName, msg, R.mipmap.ic_launcher, receiverID);

                    Sender sender = new Sender(data, token.getToken());
                    apiService.sendNotification(sender)
                            .enqueue(new Callback<Response>() {
                                @Override
                                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                    if (response.code() == Util.RESPONSE_OK && response.body().success != 1){
                                        Toast.makeText(MessagingActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Response> call, Throwable t) {
                                    Log.e(TAG, t.getMessage());
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                readMessage();
            }
        }, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        reference.removeEventListener(seenListener);
    }
}