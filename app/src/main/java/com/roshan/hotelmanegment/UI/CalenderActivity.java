package com.roshan.hotelmanegment.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.roshan.hotelmanegment.Common.CommonFunction;
import com.roshan.hotelmanegment.CustomClass.CalendarView;
import com.roshan.hotelmanegment.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class CalenderActivity extends AppCompatActivity {

    CalendarView calendarView;
    ImageButton closeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setContentView(R.layout.activity_calender);

        HashSet<Date> events = new HashSet<>();
        events.add(new Date());

        calendarView = findViewById(R.id.c_view);
        closeButton = findViewById(R.id.ic_close);
        calendarView.updateCalendar(events);

        calendarView.setEventHandler(date -> {
            DateFormat df = SimpleDateFormat.getDateInstance();
            Intent intent = new Intent();
            intent.putExtra("date", df.format(date));
            setResult(Activity.RESULT_OK, intent);
            finish();
        });

        closeButton.setOnClickListener(v-> finish());
    }
}