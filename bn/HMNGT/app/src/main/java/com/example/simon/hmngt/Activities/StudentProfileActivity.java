package com.example.simon.hmngt.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.simon.hmngt.Adapters.RoomsAdapter;
import com.example.simon.hmngt.Models.Room;
import com.example.simon.hmngt.Models.Student;
import com.example.simon.hmngt.R;

import java.util.List;

import static com.example.simon.hmngt.Constants.Constants.BASE_URL;

public class StudentProfileActivity extends AppCompatActivity {
    TextView tvFirstName, tvLastName, tvEmail, tvReligion, tvFloor, tvPhoneNumber, tvHobbies;
    TextView tvDrugs, tvVisitorCount, tvCourse;
    Button bnEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        setTitle("Profile Details");

        tvFirstName = findViewById(R.id.tvFirstName);
        tvLastName = findViewById(R.id.tvLastName);
        tvEmail = findViewById(R.id.tvEmail);
        tvReligion = findViewById(R.id.tvReligion);
//        tvFloor = findViewById(R.id.tvRoomFloor);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvHobbies = findViewById(R.id.tvHobbies);
        bnEdit = findViewById(R.id.btnEdit);
        tvDrugs = findViewById(R.id.tvDrugUse);
        tvCourse = findViewById(R.id.tvCourse);
        tvVisitorCount = findViewById(R.id.tvVisitorCount);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("User", MODE_PRIVATE);
        final String student = String.valueOf(preferences.getInt("Student", 1));
        get_available_rooms(student);

        bnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudentEditProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    public void get_available_rooms(final String student_id){
        AndroidNetworking.post( BASE_URL + "/get_student_profile/{student_id}")
                .addPathParameter("student_id", student_id)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(Student.class, new ParsedRequestListener<Student>(){
                    @Override
                    public void onResponse(Student student) {
                        tvEmail.setText(student.getEmail());
                        tvFirstName.setText(student.getFirst_name());
                        tvLastName.setText(student.getLast_name());
                        tvPhoneNumber.setText(student.getPhone_number());
                        tvHobbies.setText(student.getHobbies());
                        tvReligion.setText(student.getReligion());
                        tvDrugs.setText(student.getDrugs());
                        tvCourse.setText(student.getCourse());
                        tvVisitorCount.setText(student.getVisitor_count());
//                        edEmail.setText(student.getEmail());
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Could Not Profiles", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
