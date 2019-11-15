package com.example.simon.hmngt.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.simon.hmngt.Models.Example7;
import com.example.simon.hmngt.Models.Student;
import com.example.simon.hmngt.R;

import static com.example.simon.hmngt.Constants.Constants.BASE_URL;

public class StudentLoginActivity extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    public TextView textView;
    private Spinner spinner;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        Email = findViewById(R.id.etEmail);
        progressDialog= new ProgressDialog(this);
        Password = findViewById(R.id.etUserPassward);
        Button login = findViewById(R.id.Btn_login);
        TextView register = findViewById(R.id.tvRegister);
        textView = findViewById(R.id.tvSignUp);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudentRegisrationActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Email.getText().toString();
                final String password = Password.getText().toString();
                SignIn(email, password);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(StudentLoginActivity.this,RegistrationActivity.class));
            }
        });
    }

    public void SignIn(String username, String password){
        AndroidNetworking.post(BASE_URL + "/signin")
                .addBodyParameter("username", username)
                .addBodyParameter("password", password)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(Example7.class, new ParsedRequestListener<Example7>(){
                    @Override
                    public void onResponse(Example7 example7) {
                        if (example7.getResponse().equals("Login Successful")){
                            Student student = example7.getStudent();
                            Log.i("conn", ""+student.getId());
                            Intent intent = new Intent(getApplicationContext(), StudentDashboardactivity.class);
                            SharedPreferences preferences = getApplicationContext().getSharedPreferences("User", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("Student", student.getId());
                            editor.apply();
                            intent.putExtra("name", student.getFirst_name());
                            Toast.makeText(StudentLoginActivity.this, "Welcome", Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }else {
                            Toast.makeText(StudentLoginActivity.this, ""+example7.getResponse(), Toast.LENGTH_LONG).show();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        Log.i("conn", ""+ error);
//                        spinner.setEnabled(false);
                        if(error.getErrorCode() == 0){
                            Toast.makeText(StudentLoginActivity.this, "Network Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
