package com.example.simon.hmngt.Activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.simon.hmngt.Adapters.BookingStudentsAdapter;
import com.example.simon.hmngt.Adapters.StudentInvitesAdapter;
import com.example.simon.hmngt.Models.Example2;
import com.example.simon.hmngt.Models.Example4;
import com.example.simon.hmngt.Models.Student;
import com.example.simon.hmngt.R;

import java.util.List;

import static com.example.simon.hmngt.Constants.Constants.BASE_URL;

public class BookingDetailsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    BookingStudentsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        setTitle("Booking Details");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String room_id = String.valueOf(getIntent().getExtras().getInt("room_id", 1));
        get_booking_details(room_id);
    }

    public void get_booking_details(String room_id){
        AndroidNetworking.post( BASE_URL + "/get_booking_details/{room_id}")
                .addPathParameter("room_id", room_id )
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(Example4.class, new ParsedRequestListener<Example4>(){
                    @Override
                    public void onResponse(Example4 example4) {
                        String message = example4.getResponse();
                        if (message.equals("Booking Details")) {
                            List<Student> students = example4.getStudents();
                            if (students.size() > 0) {
                                adapter = new BookingStudentsAdapter(getApplicationContext(), students);
                                recyclerView.setAdapter(adapter);
                            } else {
                                Toast.makeText(getApplicationContext(), "No Booking Details", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "No Booking Details", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Could Not Booking Details", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
