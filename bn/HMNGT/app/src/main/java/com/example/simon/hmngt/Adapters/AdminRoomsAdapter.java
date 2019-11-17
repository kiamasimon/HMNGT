package com.example.simon.hmngt.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.simon.hmngt.Activities.AdminRoomDetailsActivity;
import com.example.simon.hmngt.Activities.RoomDetailsActivity;
import com.example.simon.hmngt.Models.Room;
import com.example.simon.hmngt.R;

import java.util.List;

/**
 * Created by simon on 9/29/19.
 */


public class AdminRoomsAdapter extends RecyclerView.Adapter<AdminRoomsAdapter.RoomsViewHolder> {

    private Context mCtx;
    private List<Room> roomList;

    public AdminRoomsAdapter(Context mCtx, List<Room> roomList){
        this.mCtx = mCtx;
        this.roomList = roomList;
    }
    @NonNull
    @Override
    public RoomsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.rooms_card_view, parent, false);
        return new RoomsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomsViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.id = room.getId();
        holder.tvRoomNumber.setText(room.getRoom_number());
        holder.tvRoomFloor.setText(room.getFloor());
        holder.tvRoomType.setText(room.getType());
//        holder.textViewTitle.setText(room.getName() + " " + room.getId());
//        holder.tvName.setText(student.getFirst_name());
//        holder.tvAge.setText(student.getLast_name());
//        holder.tvCourse.setText(student.getSchool());
    }
    @Override
    public int getItemCount() {
        return roomList.size();
    }

    class RoomsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private int id;
        TextView tvRoomNumber, tvRoomFloor, tvRoomType;

        public RoomsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
//            textViewTitle = itemView.findViewById(R.id.textViewRegion);
//            tvName = itemView.findViewById(R.id.tvStudentName);
//            tvCourse = itemView.findViewById(R.id.tvStudentCourse);
//            tvAge = itemView.findViewById(R.id.tvStudentAge);
            tvRoomNumber = itemView.findViewById(R.id.tvRoomNumber);
            tvRoomFloor = itemView.findViewById(R.id.tvRoomFloor);
            tvRoomType = itemView.findViewById(R.id.tvType);
        }
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(itemView.getContext(), AdminRoomDetailsActivity.class);
            intent.putExtra("room", id);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            itemView.getContext().startActivity(intent);
        }
    }
}
