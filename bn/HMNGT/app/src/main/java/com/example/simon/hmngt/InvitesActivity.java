package com.example.simon.hmngt;

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
import com.example.simon.hmngt.Adapters.RoomsAdapter;
import com.example.simon.hmngt.Adapters.StudentInvitesAdapter;
import com.example.simon.hmngt.Models.Example;
import com.example.simon.hmngt.Models.Example2;
import com.example.simon.hmngt.Models.Invite;
import com.example.simon.hmngt.Models.Room;
import com.example.simon.hmngt.Models.Student;

import java.util.List;

import static com.example.simon.hmngt.Constants.Constants.BASE_URL;

public class InvitesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    StudentInvitesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invites);
        setTitle("Invites");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("User", MODE_PRIVATE);
        final String student = String.valueOf(preferences.getInt("Student", 1));
        get_invites(student);
    }

    public void get_invites(String student_id){
        Log.i("conn", ""+student_id);
        AndroidNetworking.post( BASE_URL + "/get_invites/{student_id}")
                .addPathParameter("student_id", student_id )
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(Example2.class, new ParsedRequestListener<Example2>(){
                    @Override
                    public void onResponse(Example2 example2) {
                        String message = example2.getResponse();
                        if (message.equals("Invites")){
                            List<Student> students = example2.getStudents();
                            if(students.size() > 0){
                                adapter = new StudentInvitesAdapter(getApplicationContext(), students);
                                recyclerView.setAdapter(adapter);
                            }else {
                                Toast.makeText(getApplicationContext(), "No invites", Toast.LENGTH_LONG).show();
                            }
                        }else if (message.equals("A room is already assigned to you")) {
                            Toast.makeText(getApplicationContext(), ""+message, Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "No invites", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Could Not Get Invites", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
