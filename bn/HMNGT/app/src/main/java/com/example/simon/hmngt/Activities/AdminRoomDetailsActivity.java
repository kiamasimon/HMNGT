package com.example.simon.hmngt.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.simon.hmngt.Adapters.VersionAdapter;
import com.example.simon.hmngt.Models.Example;
import com.example.simon.hmngt.Models.Example6;
import com.example.simon.hmngt.Models.Room;
import com.example.simon.hmngt.Models.Student;
import com.example.simon.hmngt.R;

import static com.example.simon.hmngt.Constants.Constants.BASE_URL;

public class AdminRoomDetailsActivity extends AppCompatActivity {
    TextView tvRoomNumber, tvRoomFloor, tvRoomDescription, tvRoomType;
    String student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_room_details);
        setTitle("Room Details");
        tvRoomDescription = findViewById(R.id.tvRoomDescription);
        tvRoomFloor = findViewById(R.id.tvRoomFloor);
        tvRoomNumber = findViewById(R.id.tvRoomNumber);
        tvRoomType = findViewById(R.id.tvRoomType);
//        listView = findViewById(R.id.listView);

        final int room_id = getIntent().getExtras().getInt("room");
        get_room_details(String.valueOf(room_id));
    }

    public void get_room_details(final String room_id){
        AndroidNetworking.post( BASE_URL + "/admin_room_details/{room_id}")
                .addPathParameter("room_id", room_id)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(Example6.class, new ParsedRequestListener<Example6>(){
                    @Override
                    public void onResponse(Example6 example6) {
                        if(example6.getResponse().equals("Room Details")){
                            Toast.makeText(getApplicationContext(), ""+example6.getResponse(), Toast.LENGTH_LONG).show();
                            Room room = example6.getRoom();
                            tvRoomDescription.setText(room.getShort_description());
                            tvRoomFloor.setText(room.getFloor());
                            tvRoomType.setText(room.getType());
                            tvRoomNumber.setText(room.getRoom_number());
                        }else {
                            Toast.makeText(getApplicationContext(), ""+example6.getResponse(), Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Could Not Get Room Details", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
