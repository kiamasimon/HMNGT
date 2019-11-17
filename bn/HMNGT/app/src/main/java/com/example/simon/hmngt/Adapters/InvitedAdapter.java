package com.example.simon.hmngt.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.simon.hmngt.Activities.RoomDetailsActivity;
import com.example.simon.hmngt.Activities.StudentDashboardactivity;
import com.example.simon.hmngt.Models.Message;
import com.example.simon.hmngt.Models.Room;
import com.example.simon.hmngt.Models.Student;
import com.example.simon.hmngt.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.simon.hmngt.Constants.Constants.BASE_URL;

/**
 * Created by Developer on 11/15/2019.
 */


public class InvitedAdapter extends RecyclerView.Adapter<InvitedAdapter.InvitedViewHolder> {

    private Context mCtx;
    private List<Student> studentList;

    public InvitedAdapter(Context mCtx, List<Student> studentList){
        this.mCtx = mCtx;
        this.studentList = studentList;
    }
    @NonNull
    @Override
    public InvitedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.invited_card, parent, false);
        return new InvitedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvitedViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.id = student.getId();
        holder.tvStudentName.setText(' ' + student.getFirst_name() + ' ');
        holder.room_mate_id = String.valueOf(student.getId());
    }
    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class InvitedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private int id;
        TextView tvStudentName;
        ImageView im;
        String room_mate_id;

        public InvitedViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            im = itemView.findViewById(R.id.img);

            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = mCtx.getSharedPreferences("User", MODE_PRIVATE);
                    final String student_id = String.valueOf(sharedPreferences.getInt("Student", 1));
                    AndroidNetworking.get( BASE_URL + "/uninvite_student/{student_id}/{room_mate_id}")
                            .setTag("test")
                            .addPathParameter("student_id", student_id)
                            .addPathParameter("room_mate_id", room_mate_id)
                            .setPriority(Priority.LOW)
                            .build()
                            .getAsObject(Message.class, new ParsedRequestListener<Message>(){
                                @Override
                                public void onResponse(Message message) {
                                    if (message.getMessage().equals("Invite Revoked")){
                                        Toast.makeText(mCtx.getApplicationContext(), "" + message.getMessage(), Toast.LENGTH_LONG).show();
                                        Intent in = new Intent(mCtx.getApplicationContext(), StudentDashboardactivity.class);
                                        mCtx.startActivity(in);

                                    }else{
                                        Toast.makeText(mCtx.getApplicationContext(), ""+message.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                }

                                @Override
                                public void onError(ANError anError) {
                                    Toast.makeText(mCtx.getApplicationContext(), "Could Not UnInvite", Toast.LENGTH_LONG).show();
                                }
                            });
                }
            });
        }
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(itemView.getContext(), RoomDetailsActivity.class);
//            intent.putExtra("room", id);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            itemView.getContext().startActivity(intent);
        }
    }
}
