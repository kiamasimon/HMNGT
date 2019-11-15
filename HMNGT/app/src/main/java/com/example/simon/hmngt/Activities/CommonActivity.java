package com.example.simon.hmngt.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.simon.hmngt.R;

public class CommonActivity extends AppCompatActivity {
    Button imgAdmin, imgStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        imgAdmin = findViewById(R.id.Admin);
        imgStudent = findViewById(R.id.Student);

        imgAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ManagementLogInActivity.class);
                startActivity(intent);
            }
        });

        imgStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudentLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}

