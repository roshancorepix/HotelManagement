package com.roshan.hotelmanegment.Model;

public class User {
    private String userID;
    private String userName;
    private String emailID;
    private String mobileNumber;

    public User(String userID, String userName, String emailID, String mobileNumber) {
        this.userID = userID;
        this.userName = userName;
        this.emailID = emailID;
        this.mobileNumber = mobileNumber;
    }

    public User(String userID, String userName, String emailID) {
        this.userID = userID;
        this.userName = userName;
        this.emailID = emailID;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmailID() {
        return emailID;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }
}
