package com.roshan.hotelmanegment.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roshan.hotelmanegment.Adapters.SearchHotelAdapter;
import com.roshan.hotelmanegment.Common.CommonFunction;
import com.roshan.hotelmanegment.Model.Hotel;
import com.roshan.hotelmanegment.R;

import java.util.ArrayList;
import java.util.List;

public class HotelListActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private String cityName;
    private TextView titleLocation;
    private List<Hotel> hotelList = new ArrayList<>();
    private SearchHotelAdapter searchHotelAdapter;
    private RecyclerView searchHotelRv;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setContentView(R.layout.activity_hotel_list);
        bindID();
        getIntentData();
        getHotelData(cityName);

        backButton.setOnClickListener(v -> finish());
    }

    private void getHotelData(String location) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(location);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Hotel hotel = dataSnapshot.getValue(Hotel.class);
                        hotelList.add(hotel);
                    }
                    setDataInRecyclerview();
                } else {
                    CommonFunction.showToastMessage(HotelListActivity.this, "Please try again later");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        myRef.addValueEventListener(listener);
    }

    private void setDataInRecyclerview() {
        searchHotelRv.setHasFixedSize(true);
        searchHotelRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        searchHotelAdapter = new SearchHotelAdapter(this, hotelList);
        searchHotelRv.setAdapter(searchHotelAdapter);
    }


    private void getIntentData() {
        cityName = getIntent().getStringExtra("city");
        titleLocation.setText(cityName);
    }

    private void bindID() {
        titleLocation = findViewById(R.id.tv_location);
        searchHotelRv = findViewById(R.id.rv_hotel);
        backButton = findViewById(R.id.back_button);
    }
}