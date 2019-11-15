package com.example.simon.hmngt.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.simon.hmngt.Models.Student;
import com.example.simon.hmngt.R;

import static com.example.simon.hmngt.Constants.Constants.BASE_URL;

public class StudentRegisrationActivity extends AppCompatActivity {
    EditText edUserName, edFirstName, edLastName, edPassword, edConfirmPassword, edEmail;
    Button bnRegister;
    RadioGroup radioGroup;
    RadioButton sexButton, femaleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_regisration);

        edEmail = findViewById(R.id.edEmail);
        edUserName = findViewById(R.id.edUserName);
        edFirstName = findViewById(R.id.edFirstName);
        edLastName  = findViewById(R.id.edLastName);
        edPassword = findViewById(R.id.edPassword);
        edConfirmPassword = findViewById(R.id.edConfirmPassword);
        bnRegister = findViewById(R.id.bnRegister);
        radioGroup = findViewById(R.id.radioGroup);
        femaleButton  = findViewById(R.id.rbFemale);

        bnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(! edPassword.getText().toString().equals(edConfirmPassword.getText().toString())){
                    Toast.makeText(StudentRegisrationActivity.this, "Password Does Not Match", Toast.LENGTH_LONG).show();
                }else {
                    String email, username, password, first_name, last_name, gender;
                    email = edEmail.getText().toString();
                    username = edUserName.getText().toString();
                    password = edPassword.getText().toString();
                    first_name = edFirstName.getText().toString();
                    last_name = edLastName.getText().toString();
                    int selected_id = radioGroup.getCheckedRadioButtonId();
                    sexButton = (RadioButton) findViewById(selected_id);
                    if(sexButton.getText().equals("Male")){
                        gender = "M";
                    }else if(femaleButton.getText().equals("Female")) {
                        gender = "F";
                    }else{
                        gender = "T";
                    }
                    SignUp(username, email, password, first_name, last_name, gender);
                }
            }
        });
    }

    public void SignUp(String username, String email, String password, String first_name, String last_name, String gender){
        AndroidNetworking.post(BASE_URL + "/signup")
                .addBodyParameter("username", username)
                .addBodyParameter("password", password)
                .addBodyParameter("email", email)
                .addBodyParameter("first_name", first_name)
                .addBodyParameter("last_name", last_name)
                .addBodyParameter("gender", gender)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(Student.class, new ParsedRequestListener<Student>(){
                    @Override
                    public void onResponse(Student student) {
                        Intent intent = new Intent(getApplicationContext(), StudentLoginActivity.class);
                        SharedPreferences preferences = getApplicationContext().getSharedPreferences("User", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("Student", student.getId());
                        editor.apply();
                        intent.putExtra("name", student.getFirst_name());
                        Toast.makeText(StudentRegisrationActivity.this, "User created successfully", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }
                    @Override
                    public void onError(ANError error) {
                        Log.i("conn", ""+ error);
//                        spinner.setEnabled(false);
                        if(error.getErrorCode() == 0){
                            Toast.makeText(StudentRegisrationActivity.this, "Network Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
