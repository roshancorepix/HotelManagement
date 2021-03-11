package com.roshan.hotelmanegment.SharedPreference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
    private static SharedPreferences preferences;
    private static final String PREF_NAME = "LoginPreference";
    private static final String IS_LOGIN = "isLogin";

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
}
