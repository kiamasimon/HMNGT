package com.example.simon.hmngt.Activities;

import android.content.Intent;
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
import com.example.simon.hmngt.Models.Example;
import com.example.simon.hmngt.Models.Message;
import com.example.simon.hmngt.R;

import static com.example.simon.hmngt.Constants.Constants.BASE_URL;

public class PaymentActivity extends AppCompatActivity {
    Button bnPay;
    TextView edPayment;
    String student_id, room_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setTitle("Payment");

        bnPay = findViewById(R.id.bnPay);
        edPayment = findViewById(R.id.edPayment);
        student_id = getIntent().getExtras().getString("student_id");
        room_id = getIntent().getExtras().getString("room_id");

        edPayment.setText("Pay a deposit of 1500Ksh to complete your booking");
        bnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpesa_payment(student_id, room_id);
            }
        });
    }

    public void mpesa_payment(String student_id, String room_id){
        AndroidNetworking.post( BASE_URL + "/initiate_stk_push/{student_id}/{room_id}")
                .addPathParameter("student_id", student_id)
                .addPathParameter("room_id", room_id)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(Message.class, new ParsedRequestListener<Message>(){
                    @Override
                    public void onResponse(Message message) {
                        if(message.getMessage().equals("Payment Successful")){
                            Toast.makeText(getApplicationContext(), "Payment Successful", Toast.LENGTH_LONG).show();
                            Intent in = new Intent(getApplicationContext(), StudentDashboardactivity.class);
                            startActivity(in);
                        }else {
                            Toast.makeText(getApplicationContext(), ""+message.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Could Not Complete Booking", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
