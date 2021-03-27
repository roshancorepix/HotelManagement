package com.roshan.hotelmanegment.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.roshan.hotelmanegment.Interface.ItemClickListener;
import com.roshan.hotelmanegment.Model.User;
import com.roshan.hotelmanegment.R;

import java.util.List;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder> {
    private Context context;
    private List<User> userList;
    private ItemClickListener itemClickListener;

    public FriendListAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    @NonNull
    @Override
    public FriendListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_friend_list_chat, parent, false);
        return new FriendListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendListViewHolder holder, int position) {
        Glide.with(context)
                .load(R.drawable.ic_chat_user)
                .into(holder.userProfile);

        holder.userName.setText(userList.get(position).getUserName());
        holder.userEmail.setText(userList.get(position).getEmailID());

        holder.itemView.setOnClickListener(v -> itemClickListener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return userList != null ? userList.size() : 0;
    }

    public class FriendListViewHolder extends RecyclerView.ViewHolder{

        ImageView userProfile;
        TextView userName, userEmail;
        public FriendListViewHolder(@NonNull View itemView) {
            super(itemView);
            userProfile = itemView.findViewById(R.id.user_image);
            userName = itemView.findViewById(R.id.tv_chat_username);
            userEmail = itemView.findViewById(R.id.tv_chat_user_email);
        }
    }
}
