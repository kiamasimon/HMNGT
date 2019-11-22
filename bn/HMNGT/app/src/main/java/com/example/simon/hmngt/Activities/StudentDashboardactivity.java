package com.example.simon.hmngt.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.simon.hmngt.Adapters.RoomsAdapter;
import com.example.simon.hmngt.Adapters.SimilarProfilesAdapter;
import com.example.simon.hmngt.InvitesActivity;
import com.example.simon.hmngt.Models.Room;
import com.example.simon.hmngt.Models.Student;
import com.example.simon.hmngt.MyRoomActivity;
import com.example.simon.hmngt.R;

import java.util.List;

import static com.example.simon.hmngt.Constants.Constants.BASE_URL;

public class StudentDashboardactivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RoomsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboardactivity);
        setTitle("Dashboard");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        SharedPreferences preferences = getApplicationContext().getSharedPreferences("User", MODE_PRIVATE);
//        final String student = String.valueOf(preferences.getInt("Student", 1));
        get_available_rooms();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.students_options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.profile:
                startActivity(new Intent(this, StudentProfileActivity.class));
                return true;
            case R.id.invites:
                startActivity(new Intent(this, InvitesActivity.class));
                return true;
            case R.id.my_room:
                startActivity(new Intent(this, MyRoomActivity.class));
                return true;
//            case R.id.booking_status:
//                startActivity(new Intent(this, BookingStatusActivity.class));
//                return true;
            case R.id.logout:
                startActivity(new Intent(this, CommonActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void get_available_rooms(){
        AndroidNetworking.get( BASE_URL + "/students_get_rooms")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsObjectList(Room.class, new ParsedRequestListener<List<Room>>(){
                    @Override
                    public void onResponse(List<Room> rooms) {
                        adapter = new RoomsAdapter(getApplicationContext(), rooms);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Could Not Profiles", Toast.LENGTH_LONG).show();
                    }
                });
    }
//    public void get_similar_profiles(String student_id){
//        AndroidNetworking.post( BASE_URL + "/get_students_with_similar_profile{student_id}")
//                .addPathParameter("student_id", student_id)
////                .addBodyParameter("student_id", student_id)
//                .setTag("test")
//                .setPriority(Priority.LOW)
//                .build()
//                .getAsObjectList(Student.class, new ParsedRequestListener<List<Student>>(){
//                    @Override
//                    public void onResponse(List<Student> students) {
//                        adapter = new SimilarProfilesAdapter(getApplicationContext(), students);
//                        recyclerView.setAdapter(adapter);
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        Toast.makeText(getApplicationContext(), "Could Not Profiles", Toast.LENGTH_LONG).show();
//                    }
//                });
//    }
}
