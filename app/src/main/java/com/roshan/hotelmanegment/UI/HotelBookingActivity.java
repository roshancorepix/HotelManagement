package com.roshan.hotelmanegment.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
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

    private final String TAG = getClass().getSimpleName();
    private RelativeLayout calenderButton, totalPersonButton;
    private TextView bookingDateText, totalAdult, totalChild, totalRoom, dot, textChild;
    private AutoCompleteTextView locationSearch;
    private String[] city = {"Ahmedabad", "Surat", "Rajkot"};
    private Button searchHotelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_hotel_booking);

        bindID();
        calenderButton.setOnClickListener(this);
        totalPersonButton.setOnClickListener(this);
        searchHotelButton.setOnClickListener(this);
        bookingDateText.setText(currentDate() + " - " + nextDate(currentDate()));
        totalChild.setText(String.valueOf(0));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, city);

        locationSearch.setThreshold(1);
        locationSearch.setAdapter(adapter);

    }

    private void bindID() {
        calenderButton = findViewById(R.id.view_calender);
        bookingDateText = findViewById(R.id.booking_date);
        totalPersonButton = findViewById(R.id.rl_total_person);
        totalAdult = findViewById(R.id.total_adult);
        totalChild = findViewById(R.id.total_child);
        totalRoom = findViewById(R.id.total_rooms);
        dot = findViewById(R.id.tv_dot);
        textChild = findViewById(R.id.tv_child);
        locationSearch = findViewById(R.id.autoComplete_location);
        searchHotelButton = findViewById(R.id.btn_search_hotel);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_calender:
                openCalenderActivity();
                break;

            case R.id.rl_total_person:
                openGuestAndRoom();
                break;

            case R.id.btn_search_hotel:
                openHotelList();
                break;
        }
    }

    private void openHotelList() {
        if (TextUtils.isEmpty(locationSearch.getText())) {
            CommonFunction.showToastMessage(this, "Please enter location");
        } else {
            startActivity(new Intent(HotelBookingActivity.this, HotelListActivity.class)
                    .putExtra("city", locationSearch.getText().toString()));
        }
    }

    private void openGuestAndRoom() {
        Log.e("TAG", "Child: " + totalChild.getText());
        startActivityForResult(new Intent(HotelBookingActivity.this,
                        GuestAndRoomActivity.class)
                        .putExtra(Util.ROOM, totalRoom.getText().toString())
                        .putExtra(Util.ADULT, totalAdult.getText().toString())
                        .putExtra(Util.CHILD, totalChild.getText().toString()),
                Util.GUEST_AND_ROOM_REQUEST);
    }

    private void openCalenderActivity() {
        startActivityForResult(new Intent(HotelBookingActivity.this,
                CalenderActivity.class), Util.DATE_REQUEST);
    }

    public String currentDate() {
        String currentDate;
        Date todayDate = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        currentDate = formatter.format(todayDate);
        return currentDate;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Util.DATE_REQUEST) {

            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("date");
                bookingDateText.setText(result + " - " + nextDate(result));
                CommonFunction.showToastMessage(HotelBookingActivity.this, result);
            }
        } else if (requestCode == Util.GUEST_AND_ROOM_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getStringExtra(Util.CHILD).equals("0")) {
                    Log.e("TAG", "Data: " + data.getStringExtra(Util.CHILD));
                    dot.setVisibility(View.GONE);
                    totalChild.setVisibility(View.GONE);
                    textChild.setVisibility(View.GONE);
                    totalChild.setText("0");
                } else {
                    dot.setVisibility(View.VISIBLE);
                    totalChild.setVisibility(View.VISIBLE);
                    textChild.setVisibility(View.VISIBLE);
                    totalChild.setText(data.getStringExtra(Util.CHILD));
                }
                totalAdult.setText(data.getStringExtra(Util.ADULT));
                totalRoom.setText(data.getStringExtra(Util.ROOM));
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

    private Date convertDate(String date) {
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