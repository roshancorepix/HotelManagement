package com.roshan.hotelmanegment.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.roshan.hotelmanegment.Common.CommonFunction;
import com.roshan.hotelmanegment.R;
import com.roshan.hotelmanegment.Utils.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HotelBookingActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout calenderButton;
    private TextView bookingDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_hotel_booking);

        bindID();
        calenderButton.setOnClickListener(this);
        bookingDateText.setText(currentDate()+" - "+nextDate(currentDate()));
    }

    private void bindID() {
        calenderButton = findViewById(R.id.view_calender);
        bookingDateText = findViewById(R.id.booking_date);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_calender:
                openCalenderActivity();
                break;
        }
    }

    private void openCalenderActivity() {
        startActivityForResult(new Intent(HotelBookingActivity.this,
                CalenderActivity.class), Util.DATE_REQUEST);
    }

    public String currentDate(){
        String currentDate;
        Date todayDate = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        currentDate = formatter.format(todayDate);
        return currentDate;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Util.DATE_REQUEST) {

            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("date");
                bookingDateText.setText(result + " - "+ nextDate(result));
                CommonFunction.showToastMessage(HotelBookingActivity.this, result);
            }
        }
    }

    private String nextDate(String date) {
        String nextDate = null;
        Date mDate = convertDate(date);
        Date next = new Date(mDate.getYear(), mDate.getMonth(), mDate.getDate() + 1);
        DateFormat df = SimpleDateFormat.getDateInstance();
        nextDate = df.format(next);
        return nextDate;
    }

    private Date convertDate(String date){
        Date mDate = null;
        DateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        try {
            mDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
         return mDate;
    }
}