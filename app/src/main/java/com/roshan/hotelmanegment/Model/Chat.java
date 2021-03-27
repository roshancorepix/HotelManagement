package com.roshan.hotelmanegment.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chat {
    @Expose
    @SerializedName("sender")
    private String sender;
    @Expose
    @SerializedName("receiver")
    private String receiver;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("time")
    private String time;
    @Expose
    @SerializedName("isseen")
    private boolean isseen;

    public Chat() {
    }

    public Chat(String sender, String receiver, String message, String time, boolean isseen) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.time = time;
        this.isseen = isseen;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }
}
