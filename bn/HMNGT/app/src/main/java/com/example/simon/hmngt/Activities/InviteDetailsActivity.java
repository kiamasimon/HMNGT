package com.example.simon.hmngt.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.simon.hmngt.Adapters.VersionAdapter;
import com.example.simon.hmngt.Models.Example;
import com.example.simon.hmngt.Models.Invite;
import com.example.simon.hmngt.Models.Room;
import com.example.simon.hmngt.Models.Student;
import com.example.simon.hmngt.R;

import static com.example.simon.hmngt.Constants.Constants.BASE_URL;

public class InviteDetailsActivity extends AppCompatActivity {
    String student;
    TextView tvInviteeName, tvInviteeHobbies, tvInviteeReligion, tvInviteeSports, tvInviteeSchool;
    TextView roomNumber, roomFloor, roomDescription, roomType;
    Button accept;
    int inviter_id;
    String r_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_details);
        tvInviteeName = findViewById(R.id.tvInviteeName);
        tvInviteeHobbies = findViewById(R.id.tvInviteeHobbies);
        tvInviteeReligion = findViewById(R.id.tvInviteeReligion);
        tvInviteeSports = findViewById(R.id.tvInviteeSports);
        roomNumber = findViewById(R.id.tvRoomNumber);
        roomFloor = findViewById(R.id.tvRoomFloor);
        roomDescription = findViewById(R.id.tvRoomDescription);
        roomType = findViewById(R.id.tvRoomType);
        accept = findViewById(R.id.accept);
//        tvInviteeSchool = findViewById(R.id.tvIn)

        inviter_id = getIntent().getExtras().getInt("inviter_id");
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("User", MODE_PRIVATE);
        student= String.valueOf(preferences.getInt("Student", 1));
        get_invite_details(student, String.valueOf(inviter_id));

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept_invite(student, String.valueOf(inviter_id), r_id);
            }
        });
    }

    public void get_invite_details(String student_id, String inviter_id){
        Log.i("conn", ""+inviter_id);
        AndroidNetworking.post( BASE_URL + "/invite_details/{student_id}/{inviter_id}")
                .addPathParameter("student_id", student_id)
                .addPathParameter("inviter_id", inviter_id)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(Invite.class, new ParsedRequestListener<Invite>(){
                    @Override
                    public void onResponse(Invite invite) {
                        if (invite.getResponse().equals("Invite Details")){
                            Student student = invite.getStudent();
                            tvInviteeName.setText(student.getFirst_name());
                            tvInviteeHobbies.setText(student.getHobbies());
                            tvInviteeReligion.setText(student.getReligion());
//                        tvInviteeSchool.setText(student.getSchool());
                            tvInviteeSports.setText(student.getSports());
                            Room room = invite.getRoom();
                            roomNumber.setText(room.getRoom_number());
                            roomDescription.setText(room.getShort_description());
                            roomFloor.setText(room.getFloor());
                            roomType.setText(room.getType());
                            r_id = String.valueOf(room.getId());
                        }else {
                            Toast.makeText(getApplicationContext(), ""+invite.getResponse(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Could Not Get Invite Details", Toast.LENGTH_LONG).show();
                    }
                });
    }


    public void accept_invite(final String student_id, final String invitee_id, final String k_id){
        AndroidNetworking.post( BASE_URL + "/accept_invite/{student_id}/{invitee_id}")
                .addPathParameter("student_id", student_id)
                .addPathParameter("invitee_id", invitee_id)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(Invite.class, new ParsedRequestListener<Invite>(){
                    @Override
                    public void onResponse(Invite invite) {
                        if (invite.getResponse().equals("A room is already assigned to you")){
                            Toast.makeText(getApplicationContext(), ""+invite.getResponse(), Toast.LENGTH_LONG).show();
                        }else if (invite.getResponse().equals("Pairing Complete")) {
                            Intent ac = new Intent(InviteDetailsActivity.this, PaymentActivity.class);
                            ac.putExtra("student_id", student_id);
                            ac.putExtra("room_id", k_id);
//                            Toast.makeText(getApplicationContext(), "Invite Accepted, Pairing Complete", Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(InviteDetailsActivity.this, StudentDashboardactivity.class);
                            startActivity(ac);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), ""+invite.getResponse(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Could Not Get Invite Details", Toast.LENGTH_LONG).show();
                    }
                });
    }

}
