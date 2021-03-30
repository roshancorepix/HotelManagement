package com.roshan.hotelmanegment.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roshan.hotelmanegment.Model.Chat;
import com.roshan.hotelmanegment.R;
import com.roshan.hotelmanegment.UI.MessagingActivity;
import com.roshan.hotelmanegment.Utils.Util;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private final String TAG = getClass().getSimpleName();
    private Context context;
    private List<Chat> chatList;
    FirebaseUser firebaseUser;
    private int msgType;

    public MessageAdapter(Context context, List<Chat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        msgType = viewType;
        if (viewType == Util.MESSAGE_TYPE_RIGHT) {
           View view = LayoutInflater.from(context).inflate(R.layout.row_chat_right, parent, false);
           return new MessageViewHolder(view);
        } else {
           View view = LayoutInflater.from(context).inflate(R.layout.row_chat_left, parent, false);
            return new MessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        holder.message.setText(chatList.get(position).getMessage());
        holder.messageTime.setText(chatList.get(position).getTime());
        if (msgType == Util.MESSAGE_TYPE_LEFT) {
            Glide.with(context)
                    .load(R.drawable.ic_chat_user)
                    .into(holder.currentImage);
        }
        if (msgType == Util.MESSAGE_TYPE_RIGHT) {
            if (position == chatList.size() - 1) {
                Log.e(TAG, "MSG: "+chatList.get(position).isIsseen());
                if (chatList.get(position).isIsseen()) {
                    Glide.with(context)
                            .load(R.drawable.ic_seen)
                            .into(holder.messageSeen);
                } else {
                    Glide.with(context)
                            .load(R.drawable.ic_delivered)
                            .into(holder.messageSeen);
                }
            } else {
                holder.messageSeen.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return chatList != null ? chatList.size() : 0;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        ImageView currentImage, messageSeen;
        TextView message, messageTime;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            currentImage = itemView.findViewById(R.id.profile_image);
            messageSeen = itemView.findViewById(R.id.iv_show_status);
            message = itemView.findViewById(R.id.tv_show_message);
            messageTime = itemView.findViewById(R.id.msg_time);

        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (chatList.get(position).getSender().equals(firebaseUser.getUid())) {
            return Util.MESSAGE_TYPE_RIGHT;
        } else {
            return Util.MESSAGE_TYPE_LEFT;
        }
    }
}
