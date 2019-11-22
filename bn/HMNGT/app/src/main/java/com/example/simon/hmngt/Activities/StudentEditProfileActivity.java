package com.example.simon.hmngt.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.simon.hmngt.Models.Student;
import com.example.simon.hmngt.R;

import static com.example.simon.hmngt.Constants.Constants.BASE_URL;

public class StudentEditProfileActivity extends AppCompatActivity {
    EditText edFirstName, edLastName, edReligion, edHobbies, edPhoneNumber, edSports, edEmail;
    EditText edDrugs, edVisitorCount, edCourse;
    Button bnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_edit_profile);
        setTitle("Profile Details");

        edFirstName = findViewById(R.id.edFirstName);
        edLastName = findViewById(R.id.edLastName);
        edReligion = findViewById(R.id.edReligion);
        edHobbies = findViewById(R.id.edHobbies);
        edPhoneNumber = findViewById(R.id.edPhone);
        edSports = findViewById(R.id.edSports);
        edEmail = findViewById(R.id.edEmail);
        bnUpdate = findViewById(R.id.bnUpdate);
        edDrugs = findViewById(R.id.edDrugs);
        edVisitorCount = findViewById(R.id.edVisitorCount);
        edCourse = findViewById(R.id.edCourse);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("User", MODE_PRIVATE);
        final String student = String.valueOf(preferences.getInt("Student", 1));
        get_profile_details(student);

        bnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString();
                String first_name = edFirstName.getText().toString();
                String last_name = edLastName.getText().toString();
                String phone_number = edPhoneNumber.getText().toString();
                String hobbies = edHobbies.getText().toString();
                String religion = edReligion.getText().toString();
                String sports = edSports.getText().toString();
                String drugs = edDrugs.getText().toString();
                String visitor_count = edVisitorCount.getText().toString();
                String course = edCourse.getText().toString();
                update_profile_details(student, first_name, last_name, hobbies, email, phone_number, sports, religion, drugs, course, visitor_count);
            }
        });
    }

    public void get_profile_details(final String student_id){
        AndroidNetworking.post( BASE_URL + "/get_student_profile/{student_id}")
                .addPathParameter("student_id", student_id)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(Student.class, new ParsedRequestListener<Student>(){
                    @Override
                    public void onResponse(Student student) {
                        edEmail.setText(student.getEmail());
                        edFirstName.setText(student.getFirst_name());
                        edLastName.setText(student.getLast_name());
                        edPhoneNumber.setText(student.getPhone_number());
                        edHobbies.setText(student.getHobbies());
                        edReligion.setText(student.getReligion());
                        edSports.setText(student.getSports());
                        edEmail.setText(student.getEmail());
                        edDrugs.setText(student.getDrugs());
                        edCourse.setText(student.getCourse());
                        edVisitorCount.setText(student.getVisitor_count());
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Could Not Profiles", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public  void update_profile_details(String student_id, String first_name, String last_name, String hobbies, String email, String phone_number, String sports,
                                        String religion, String drugs, String course, String visitor_count){
        AndroidNetworking.post( BASE_URL + "/update_student_profile/{student_id}")
                .addPathParameter("student_id", student_id)
                .addBodyParameter("first_name", first_name)
                .addBodyParameter("last_name", last_name)
                .addBodyParameter("hobbies", hobbies)
                .addBodyParameter("email", email)
                .addBodyParameter("phone_number", phone_number)
                .addBodyParameter("sports", sports)
                .addBodyParameter("religion", religion)
                .addBodyParameter("drugs", drugs)
                .addBodyParameter("course", course)
                .addBodyParameter("visitor_count", visitor_count)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(Student.class, new ParsedRequestListener<Student>(){
                    @Override
                    public void onResponse(Student student) {
                        Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), StudentProfileActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Could Not Uodate Profile", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
