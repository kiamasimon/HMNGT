package com.example.simon.hmngt.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.simon.hmngt.AdminRoomsListActivity;
import com.example.simon.hmngt.Models.Room;
import com.example.simon.hmngt.Models.Student;
import com.example.simon.hmngt.R;

import static com.example.simon.hmngt.Constants.Constants.BASE_URL;

public class AddRoomActivity extends AppCompatActivity {
    EditText edRoomNumber, edRoomFloor, edRoomType, edRoomDescription;
    Button bnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        setTitle("Add Room");

        bnAdd = findViewById(R.id.bnAdd);
        edRoomNumber = findViewById(R.id.edRoomNumber);
        edRoomFloor = findViewById(R.id.edRoomFloor);
        edRoomType = findViewById(R.id.edRoomType);
        edRoomDescription = findViewById(R.id.edRoomDescription);

        bnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String room_number, room_floor, room_type, room_description;
                room_number = edRoomNumber.getText().toString();
                room_floor = edRoomFloor.getText().toString();
                room_type = edRoomType.getText().toString();
                room_description = edRoomDescription.getText().toString();
                if(room_number.equals("") || room_floor.equals("") || room_type.equals("") || room_description.equals("")){
                    Toast.makeText(getApplicationContext(), "Enter All Details Please", Toast.LENGTH_LONG).show();
                }else {
                    add_room(room_number, room_floor, room_type, room_description);
                }
            }
        });
    }

    public  void add_room(String room_number, String room_floor, String room_type, String room_description ){
        AndroidNetworking.post( BASE_URL + "/admin_add_room")
                .addBodyParameter("room_number", room_number)
                .addBodyParameter("room_floor", room_floor)
                .addBodyParameter("room_type", room_type)
                .addBodyParameter("room_description", room_description)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(Room.class, new ParsedRequestListener<Room>(){
                    @Override
                    public void onResponse(Room room) {
                        Toast.makeText(getApplicationContext(), "Room Added Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), AdminRoomsListActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Could Not Uodate Profile", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
