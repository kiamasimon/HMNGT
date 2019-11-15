package com.example.simon.hmngt.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.simon.hmngt.ManagementDashboardActivity;
import com.example.simon.hmngt.Models.Management;
import com.example.simon.hmngt.Models.Student;
import com.example.simon.hmngt.R;

import static com.example.simon.hmngt.Constants.Constants.BASE_URL;

public class ManagementLogInActivity extends AppCompatActivity {
    private EditText Email;
    private EditText Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_log_in);
        Email = findViewById(R.id.etEmail);
        Password = findViewById(R.id.etUserPassward);
        Button login = findViewById(R.id.Btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Email.getText().toString();
                final String password = Password.getText().toString();
                SignIn(email, password);
            }
        });
    }

    public void SignIn(String username, String password){
        AndroidNetworking.post(BASE_URL + "/admin_login")
                .addBodyParameter("username", username)
                .addBodyParameter("password", password)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(Management.class, new ParsedRequestListener<Management>(){
                    @Override
                    public void onResponse(Management management) {
                        Intent intent = new Intent(getApplicationContext(), ManagementDashboardActivity.class);
                        SharedPreferences preferences = getApplicationContext().getSharedPreferences("User", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("Admin", management.getId());
                        editor.apply();
                        intent.putExtra("name", management.getFirstName());
                        Toast.makeText(ManagementLogInActivity.this, "Welcome", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }
                    @Override
                    public void onError(ANError error) {
                        Log.i("conn", ""+ error);
//                        spinner.setEnabled(false);
                        if(error.getErrorCode() == 0){
                            Toast.makeText(ManagementLogInActivity.this, "Network Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
