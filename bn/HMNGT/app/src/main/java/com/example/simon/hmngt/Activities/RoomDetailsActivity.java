package com.example.simon.hmngt.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.simon.hmngt.Adapters.VersionAdapter;
import com.example.simon.hmngt.Models.Example;
import com.example.simon.hmngt.Models.Room;
import com.example.simon.hmngt.Models.Student;
import com.example.simon.hmngt.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static com.example.simon.hmngt.Constants.Constants.BASE_URL;

public class RoomDetailsActivity extends AppCompatActivity {
    TextView tvRoomNumber, tvRoomFloor, tvRoomDescription, tvRoomType;
    ListView listView;
    Button bnSubmit;
    List<Student> students;

    VersionAdapter versionAdapter;
//    List<Student> students = new ArrayList<>();
//    LinearLayout linearLayout;
//    CheckBox checkBox;
    String student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);
        setTitle("Room Details");
        tvRoomDescription = findViewById(R.id.tvRoomDescription);
        tvRoomFloor = findViewById(R.id.tvRoomFloor);
        tvRoomNumber = findViewById(R.id.tvRoomNumber);
        tvRoomType = findViewById(R.id.tvRoomType);
        listView = findViewById(R.id.listView);
        bnSubmit = findViewById(R.id.bnSubmit);
//        linearLayout = findViewById(R.id.checkboxes);
//        listView.setAdapter(new CustomListView(this, listViewItems));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Position " + position, Toast.LENGTH_LONG).show();
            }
        });

        final int room_id = getIntent().getExtras().getInt("room");
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("User", MODE_PRIVATE);
         student= String.valueOf(preferences.getInt("Student", 1));

        get_room_details(String.valueOf(room_id), student);

        bnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray sparseBooleanArray = versionAdapter.getSelectedIds();
                JSONArray arrayList = new JSONArray();
                for(int i=0; i < sparseBooleanArray.size(); i++){
                    Log.i("before_append", ""+ sparseBooleanArray.get(i));
                    arrayList.put(students.get(sparseBooleanArray.keyAt(i)).getId());
                }
                String ids = String.valueOf(arrayList);
                Log.i("student_name", " " + students.get(sparseBooleanArray.keyAt(0)).getId());
                make_booking(String.valueOf(room_id), ids, student);
            }
        });
    }

    public void get_room_details(final String room_id, String student_id){
        AndroidNetworking.post( BASE_URL + "/get_room_details/{room_id}/{student_id}")
                .addPathParameter("room_id", room_id)
                .addPathParameter("student_id", student_id)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(Example.class, new ParsedRequestListener<Example>(){
                    @Override
                    public void onResponse(Example example) {
                        Room room = example.getRoom();
                        tvRoomDescription.setText(room.getShort_description());
                        tvRoomFloor.setText(room.getFloor());
                        tvRoomType.setText(room.getType());
                        tvRoomNumber.setText(room.getRoom_number());

                        students = example.getStudents();
                        versionAdapter = new VersionAdapter(RoomDetailsActivity.this,R.layout.custom_list,students);
                        listView.setAdapter(versionAdapter);
                        for (Student student : students){

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Could Not Get Room Details", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void make_booking(final String room_id, String ids, final String student_id){
        Log.i("ids", ""+ids);
        AndroidNetworking.post( BASE_URL + "/student_room_booking/{student_id}/{room_id}")
                .addPathParameter("student_id", student_id)
                .addPathParameter("room_id", room_id)
                .addBodyParameter("roommate_ids", ids)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(Example.class, new ParsedRequestListener<Example>(){
                    @Override
                    public void onResponse(Example example) {
                        if (example.getResponse().equals("You are already assigned a room, please file a complaint if you have one")){
                            Toast.makeText(getApplicationContext(), ""+example.getResponse(), Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "Room booking successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                            intent.putExtra("student_id", student_id);
                            intent.putExtra("room_id", room_id);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Could Not Complete Booking", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
