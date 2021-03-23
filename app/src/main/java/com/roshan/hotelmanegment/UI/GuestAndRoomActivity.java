package com.roshan.hotelmanegment.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.roshan.hotelmanegment.R;
import com.roshan.hotelmanegment.Utils.Util;

public class GuestAndRoomActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton roomAddButton, roomRemoveButton, adultAddButton,adultRemoveButton,
            childAddButton, childRemoveButton, closeButton;
    private TextView totalRoom, totalAdult, totalChild;
    private Button confirmButton;
    private int room = 1;
    private int adult = 1;
    private int child = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setContentView(R.layout.activity_guest_and_room);
        bindID();

        roomAddButton.setOnClickListener(this);
        roomRemoveButton.setOnClickListener(this);
        adultAddButton.setOnClickListener(this);
        adultRemoveButton.setOnClickListener(this);
        childAddButton.setOnClickListener(this);
        childRemoveButton.setOnClickListener(this);
        confirmButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);

        Intent i = getIntent();
        totalRoom.setText(i.getStringExtra(Util.ROOM));
        totalAdult.setText(i.getStringExtra(Util.ADULT));
        totalChild.setText(i.getStringExtra(Util.CHILD));
        room = Integer.parseInt(totalRoom.getText().toString());
        adult = Integer.parseInt(totalAdult.getText().toString());
        child = Integer.parseInt(totalChild.getText().toString());
        Log.e("TAG","CHILD: "+i.getStringExtra(Util.CHILD));
    }

    private void bindID() {
        roomAddButton = findViewById(R.id.btn_room_add);
        roomRemoveButton = findViewById(R.id.btn_room_remove);
        adultAddButton = findViewById(R.id.btn_adult_add);
        adultRemoveButton = findViewById(R.id.btn_adult_remove);
        childAddButton = findViewById(R.id.btn_child_add);
        childRemoveButton = findViewById(R.id.btn_child_remove);
        totalRoom = findViewById(R.id.tv_total_room);
        totalAdult = findViewById(R.id.tv_total_adult);
        totalChild = findViewById(R.id.tv_total_child);
        confirmButton = findViewById(R.id.btn_confirm);
        closeButton = findViewById(R.id.ic_close);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_room_add:
                Log.e("TAG", "Add button click");
                if (room < 8) {
                    addRoom();
                    addAdult();
                }
                break;

            case R.id.btn_room_remove:
                if (room != 1){
                    removeRoom();
                }
                break;
            case R.id.btn_adult_add:
                if (adult < 10) {
                    addAdult();
                }
                break;
            case R.id.btn_adult_remove:
                if (adult != 1){
                    removeAdult();
                }
                break;

            case R.id.btn_child_add:
                if (child < 8){
                    addChild();
                }
                break;
            case R.id.btn_child_remove:
                if (child != 0){
                    removeChild();
                }
                break;

            case R.id.btn_confirm:
                confirmRoomAndGuest();
                break;

            case R.id.ic_close:
                finish();
                break;
        }
    }

    private void confirmRoomAndGuest() {
        Intent intent = new Intent();
        intent.putExtra(Util.ROOM, totalRoom.getText().toString());
        intent.putExtra(Util.ADULT, totalAdult.getText().toString());
        intent.putExtra(Util.CHILD, totalChild.getText().toString());
        Log.e("TAG,","CHILD2: "+totalChild.getText().toString());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void removeChild() {
        child = child - 1;
        totalChild.setText(String.valueOf(child));
    }

    private void addChild() {
        child = child + 1;
        totalChild.setText(String.valueOf(child));
    }

    private void removeRoom() {
        room = room - 1;
        totalRoom.setText(String.valueOf(room));
    }

    private void addRoom() {
        room = room + 1;
        totalRoom.setText(String.valueOf(room));
    }

    private void addAdult(){
        adult = adult + 1;
        totalAdult.setText(String.valueOf(adult));
    }

    private void removeAdult(){
        adult = adult - 1;
        totalAdult.setText(String.valueOf(adult));
    }
}