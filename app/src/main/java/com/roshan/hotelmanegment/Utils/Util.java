package com.roshan.hotelmanegment.Utils;

import java.util.Calendar;

public class Util {
    public static final String NO_INTERNET = "Please check your internet connection";
    public static final int USERNAME_LENGTH = 6;
    public static final int MOBILE_NUM_LENGTH = 10;

    public static final int RC_SIGN_IN = 2;

    public static final int DATE_REQUEST = 100;
    public static final int GUEST_AND_ROOM_REQUEST = 101;

    public static final String CHILD = "child";
    public static final String ADULT = "adult";
    public static final String ROOM = "room";

    public static final int MESSAGE_TYPE_LEFT = 0;
    public static final int MESSAGE_TYPE_RIGHT = 1;
    public static final String FCM_BASE_URL = "https://fcm.googleapis.com/";
    public static final int RESPONSE_OK = 200;

    public static String getCurrentTime(){
        Calendar now = Calendar.getInstance();
        if(now.get(Calendar.AM_PM) == Calendar.AM){
            // AM
            return now.get(Calendar.HOUR)+":"+now.get(Calendar.MINUTE)+" am";
        }else{
            // PM
            return now.get(Calendar.HOUR)+":"+now.get(Calendar.MINUTE)+" pm";
        }
    }
}
