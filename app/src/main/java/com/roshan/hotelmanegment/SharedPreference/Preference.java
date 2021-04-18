package com.roshan.hotelmanegment.SharedPreference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
    private static SharedPreferences preferences;
    private static final String PREF_NAME = "LoginPreference";
    private static final String IS_LOGIN = "isLogin";
    private static final String IS_MESSAGE_SEND = "isMessageSend";
    private static final String USER_NAME = "username";
    private static final String USER_EMAIL = "email";
    private static final String USER_MOBILE_NO = "mobileNumber";

    public Preference() {
    }

    public static void init(Context context){
        if (preferences == null){
            preferences = context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        }
    }

    public static void setIsLogin(Boolean isLogin){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_LOGIN, isLogin);
        editor.commit();
    }

    public static Boolean getUserLogin(){
        return preferences.getBoolean(IS_LOGIN, false);
    }

    public static void setIsMessageSend(Boolean isMessageSend){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_MESSAGE_SEND, isMessageSend);
        editor.commit();
    }

    public static Boolean getIsMessageSend(){
        return preferences.getBoolean(IS_MESSAGE_SEND, false);
    }

    public static void setUserName(String userName){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_NAME, userName);
        editor.commit();
    }

    public static String getUsername(){
        return preferences.getString(USER_NAME, null);
    }

    public static void setUserEmail(String email){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_EMAIL, email);
        editor.commit();
    }

    public static String getUserEmail(){
        return preferences.getString(USER_EMAIL, null);
    }

    public static void setUserMobileNo(String mobileNo){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_MOBILE_NO, mobileNo);
        editor.commit();
    }

    public static String getUserMobileNo(){
        return preferences.getString(USER_MOBILE_NO, null);
    }

    public static void clear(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
