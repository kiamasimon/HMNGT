package com.example.simon.hmngt.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.simon.hmngt.Activities.RoomDetailsActivity;
import com.example.simon.hmngt.Models.Student;
import com.example.simon.hmngt.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**< sparseBooleanArray.size()
 * Created by simon on 9/24/19.
 */

public class VersionAdapter extends ArrayAdapter<Student> {

    Context context;
    List<Student> studentList = new ArrayList<>();
    private SparseBooleanArray mSelectedItemIds;
    public VersionAdapter(@NonNull Context context, int resource, List<Student> studentList) {
        super(context, resource, studentList);
        this.context = context;
        this.studentList = studentList;
        mSelectedItemIds = new SparseBooleanArray();
        studentList.addAll(studentList);
    }

    @Override
    public int getCount(){
        return studentList.size();
    }

    @Override
    public long getItemId(int position){
        return position;
    }



    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        VersionHolder holder = new VersionHolder();

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_list,null);
            holder.checkBox = convertView.findViewById(R.id.check_box);
            holder.textView = convertView.findViewById(R.id.tv_name);
            holder.tvHobbies = convertView.findViewById(R.id.tvHobbies);
            holder.tvReligion = convertView.findViewById(R.id.tvReligion);
            holder.tvSports = convertView.findViewById(R.id.tvSports);
            convertView.setTag(holder);

            holder = (VersionHolder) convertView.getTag();
            holder.checkBox.setChecked(mSelectedItemIds.get(position));
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkCheckBox(position, !mSelectedItemIds.get(position));
                }
            });

        }else{
            holder = (VersionHolder) convertView.getTag();
            holder.checkBox.setChecked(mSelectedItemIds.get(position));
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkCheckBox(position, !mSelectedItemIds.get(position));
                }
            });
        }

        Student student  = studentList.get(position);
        holder.textView.setText(student.getFirst_name());
        holder.tvSports.setText(student.getSports());
        holder.tvHobbies.setText(student.getHobbies());
        holder.tvReligion.setText(student.getReligion());
        holder.checkBox.setTag(studentList);

        return convertView;
    }

    public static class VersionHolder{
        public CheckBox checkBox;
        public TextView textView;
        public TextView tvSports, tvHobbies, tvReligion;
    }

    public void checkCheckBox(int position, boolean value){
        if(value){
            mSelectedItemIds.put(position, true);
        }else {
            mSelectedItemIds.delete(position);
        }
        notifyDataSetChanged();
    }

    public SparseBooleanArray getSelectedIds(){
        return mSelectedItemIds;
    }
}