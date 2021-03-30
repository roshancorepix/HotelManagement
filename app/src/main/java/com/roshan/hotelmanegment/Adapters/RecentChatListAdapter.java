package com.roshan.hotelmanegment.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roshan.hotelmanegment.Interface.ItemClickListener;
import com.roshan.hotelmanegment.Model.Chat;
import com.roshan.hotelmanegment.Model.User;
import com.roshan.hotelmanegment.R;

import java.util.List;

public class RecentChatListAdapter extends RecyclerView.Adapter<RecentChatListAdapter.RecentChatListViewHolder> {

    private Context context;
    private List<User> userList;
    private String lstMsg;
    private ItemClickListener itemClickListener;

    public RecentChatListAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecentChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_recent_chat_list, parent, false);
        return new RecentChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentChatListViewHolder holder, int position) {

        Glide.with(context)
                .load(R.drawable.ic_chat_user)
                .into(holder.userImage);

        holder.username.setText(userList.get(position).getUserName());

        getLastMessage(userList.get(position).getUserID(), holder.lastMessage);

        holder.itemView.setOnClickListener(v -> itemClickListener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return userList != null ? userList.size() : 0;
    }

    public class RecentChatListViewHolder extends RecyclerView.ViewHolder{

        ImageView userImage;
        TextView username, lastMessage;
        public RecentChatListViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.user_image);
            username = itemView.findViewById(R.id.tv_chat_username);
            lastMessage = itemView.findViewById(R.id.tv_recent_msg);
        }
    }

    private void getLastMessage(String userID, TextView lastMessage){
        lstMsg = "default";

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userID) ||
                            chat.getReceiver().equals(userID) && chat.getSender().equals(firebaseUser.getUid())){
                        lstMsg = chat.getMessage();
                    }
                }

                switch (lstMsg){
                    case "default":
                        lastMessage.setText("No Message");
                        break;

                    default:
                        lastMessage.setText(lstMsg);
                        break;
                }

                lstMsg = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
