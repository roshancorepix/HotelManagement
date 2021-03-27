package com.roshan.hotelmanegment.Adapters;

import android.content.Context;
import android.text.TextUtils;
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
import com.roshan.hotelmanegment.Model.Chat;
import com.roshan.hotelmanegment.R;
import com.roshan.hotelmanegment.Utils.Util;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private Context context;
    private List<Chat> chatList;
    FirebaseUser firebaseUser;

    public MessageAdapter(Context context, List<Chat> chatList){
        this.context = context;
        this.chatList = chatList;
    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == Util.MESSAGE_TYPE_RIGHT) {
            view = LayoutInflater.from(context).inflate(R.layout.row_chat_right, parent, false);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.row_chat_left, parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.message.setText(chatList.get(position).getMessage());
        holder.messageTime.setText(chatList.get(position).getTime());
        Glide.with(context)
                .load(R.drawable.ic_chat_user)
                .into(holder.currentImage);

        if (position == chatList.size() - 1){
            if (chatList.get(position).isIsseen()){
                Glide.with(context)
                        .load(R.drawable.ic_seen)
                        .into(holder.messageIndicator);
            }else {
                Glide.with(context)
                        .load(R.drawable.ic_delivered)
                        .into(holder.messageIndicator);
            }
        }else {
            holder.messageIndicator.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return chatList != null ? chatList.size() : 0;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        ImageView currentImage, messageIndicator;
        TextView message, messageTime;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            currentImage = itemView.findViewById(R.id.current_user_profile);
            messageIndicator = itemView.findViewById(R.id.iv_msg_status);
            message = itemView.findViewById(R.id.tv_show_message);
            messageTime = itemView.findViewById(R.id.msg_time);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if (chatList.get(position).getSender().equals(firebaseUser.getUid())){
            return Util.MESSAGE_TYPE_RIGHT;
        }else {
            return Util.MESSAGE_TYPE_LEFT;
        }
    }
}
