package com.example.simon.hmngt.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.simon.hmngt.Activities.InviteDetailsActivity;
import com.example.simon.hmngt.Activities.RoomDetailsActivity;
import com.example.simon.hmngt.Models.Room;
import com.example.simon.hmngt.Models.Student;
import com.example.simon.hmngt.R;

import java.util.List;

/**
 * Created by simon on 9/24/19.
 */

public class StudentInvitesAdapter extends RecyclerView.Adapter<StudentInvitesAdapter.InvitesViewHolder> {

    private Context mCtx;
    private List<Student> studentList;

    public StudentInvitesAdapter(Context mCtx, List<Student> studentList ){
        this.mCtx = mCtx;
        this.studentList = studentList;
    }
    @NonNull
    @Override
    public InvitesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.invites_card_view, parent, false);
        return new InvitesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvitesViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.id = student.getId();
        holder.tvStudentName.setText(student.getFirst_name()+ " " + student.getLast_name());
    }
    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class InvitesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private int id;
        TextView tvStudentName;

        public InvitesViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
        }
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(itemView.getContext(), InviteDetailsActivity.class);
            intent.putExtra("inviter_id", id);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            itemView.getContext().startActivity(intent);
        }
    }
}
