package com.roshan.hotelmanegment.Common;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.roshan.hotelmanegment.R;

public class CommonFunction {
    private static final String PASSWORD_PATTERN = "((?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})";
    public static boolean isNetworkConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static void showToastMessage(Activity activity, String message){
        View layout = activity.getLayoutInflater().inflate(R.layout.toast_layout,
                (ViewGroup) activity.findViewById(R.id.custom_toast));

        TextView textView = layout.findViewById(R.id.tv_message);
        textView.setText(message);

        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean validate(final String password){
        return PASSWORD_PATTERN.matches(password);
    }
}
