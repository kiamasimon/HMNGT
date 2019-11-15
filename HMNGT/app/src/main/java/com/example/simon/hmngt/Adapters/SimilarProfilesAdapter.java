package com.example.simon.hmngt.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.simon.hmngt.Models.Student;
import com.example.simon.hmngt.R;

import java.util.List;

/**
 * Created by simon on 9/23/19.
 */

public class SimilarProfilesAdapter extends RecyclerView.Adapter<SimilarProfilesAdapter.ProfilesViewHolder> {
    private Context mCtx;
    private List<Student> studentList;

    public SimilarProfilesAdapter(Context mCtx, List<Student> studentList){
        this.mCtx = mCtx;
        this.studentList = studentList;
    }
    @NonNull
    @Override
    public ProfilesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.similar_profiles_cardview, parent, false);
        return new ProfilesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfilesViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.id = student.getId();
//        holder.textViewTitle.setText(room.getName() + " " + room.getId());
        holder.tvName.setText(student.getFirst_name());
        holder.tvAge.setText(student.getLast_name());
        holder.tvCourse.setText(student.getSchool());
    }
    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class ProfilesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private int id;
        TextView tvName, tvCourse, tvAge;

        public ProfilesViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
//            textViewTitle = itemView.findViewById(R.id.textViewRegion);
            tvName = itemView.findViewById(R.id.tvStudentName);
            tvCourse = itemView.findViewById(R.id.tvStudentCourse);
            tvAge = itemView.findViewById(R.id.tvStudentAge);
        }
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(itemView.getContext(), RoomDetailsActivity.class);
//            intent.putExtra("room_id", id);
//            itemView.getContext().startActivity(intent);
        }
    }
}
