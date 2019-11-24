package com.example.simon.hmngt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.simon.hmngt.Activities.StudentDashboardactivity;
import com.example.simon.hmngt.Adapters.InvitedAdapter;
import com.example.simon.hmngt.Adapters.RoomsAdapter;
import com.example.simon.hmngt.Models.Example4;
import com.example.simon.hmngt.Models.Example5;
import com.example.simon.hmngt.Models.Message;
import com.example.simon.hmngt.Models.Room;
import com.example.simon.hmngt.Models.Student;

import java.util.List;

import static com.example.simon.hmngt.Constants.Constants.BASE_URL;

public class MyRoomActivity extends AppCompatActivity {
    TextView tvName, tvRoomMate, tvRoomNumber;
    Button bn;
    RecyclerView recyclerView;
    InvitedAdapter adapter;
    int room_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_room);
        tvName = findViewById(R.id.tvName);
        tvRoomMate = findViewById(R.id.tvRoomMate);
        tvRoomNumber = findViewById(R.id.tvRoomNumber);
        bn = findViewById(R.id.bnVacate);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        final String student_id = String.valueOf(sharedPreferences.getInt("Student", 1));
        my_room(student_id);

        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vacate_room(student_id);
            }
        });

        get_invited_students(student_id);
    }

    public void my_room(String student_id){
        AndroidNetworking.post( BASE_URL + "/student_room/{student_id}")
                .setTag("test")
                .addPathParameter("student_id", student_id)
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(Example5.class, new ParsedRequestListener<Example5>(){
                    @Override
                    public void onResponse(Example5 example5) {
                        if(example5.getResponse().equals("Assigned Residence Details")) {
                            tvName.setText(example5.getRoom().getRoom_number());
                            tvRoomMate.setText(example5.getRoommate().getFirst_name() + " " + example5.getRoommate().getFirst_name());
                            tvRoomNumber.setText(example5.getRoom().getFloor());
                            room_id = example5.getRoom().getId();

                        }else if(example5.getResponse().equals("No roommate assigned to you yet")) {
                            tvName.setText(example5.getRoom().getRoom_number());
                            tvRoomNumber.setText(example5.getRoom().getFloor());
                            tvRoomMate.setText("No roomate assigned to you yet");

                        }else if(example5.getResponse().equals("Your Roommate Vacated")){
                            tvName.setText(example5.getRoom().getRoom_number());
                            tvRoomNumber.setText(example5.getRoom().getFloor());
                            tvRoomMate.setText("Your Roommate Vacated");

                        }else {
                            Toast.makeText(getApplicationContext(), ""+example5.getResponse(), Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), ""+anError, Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void vacate_room(String student_id){
        AndroidNetworking.get( BASE_URL + "/student/vacate/{student_id}")
                .setTag("test")
                .addPathParameter("student_id", student_id)
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(Message.class, new ParsedRequestListener<Message>(){
                    @Override
                    public void onResponse(Message message) {
                        if (message.getMessage().equals("Vacation successfull")){
                            Toast.makeText(getApplicationContext(), "" + message.getMessage(), Toast.LENGTH_LONG).show();
                            Intent in = new Intent(getApplicationContext(), StudentDashboardactivity.class);
                            startActivity(in);

                        }else{
                            Toast.makeText(getApplicationContext(), ""+message.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Could Not Get Room Details", Toast.LENGTH_LONG).show();
                    }
                });
    }


    public void get_invited_students(String student_id){
        AndroidNetworking.get( BASE_URL + "/student_room_invitees/{student_id}")
                .setTag("test")
                .addPathParameter("student_id", student_id)
//                .addPathParameter("room_id", room_id)
                .setPriority(Priority.LOW)
                .build()
                .getAsObjectList(Student.class, new ParsedRequestListener<List<Student>>(){
                    @Override
                    public void onResponse(List<Student> students) {
                        adapter = new InvitedAdapter(getApplicationContext(), students);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Could Not Get Invited Students", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
