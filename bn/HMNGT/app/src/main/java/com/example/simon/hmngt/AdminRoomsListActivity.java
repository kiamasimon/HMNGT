package com.example.simon.hmngt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.simon.hmngt.Activities.AddRoomActivity;
import com.example.simon.hmngt.Activities.CommonActivity;
import com.example.simon.hmngt.Adapters.AdminRoomsAdapter;
import com.example.simon.hmngt.Adapters.RoomsAdapter;
import com.example.simon.hmngt.Models.Room;

import java.util.List;

import static com.example.simon.hmngt.Constants.Constants.BASE_URL;

public class AdminRoomsListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AdminRoomsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_rooms_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        get_rooms();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_room, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.add_room:
                startActivity(new Intent(this, AddRoomActivity.class));
                return true;
            case R.id.logout:
                startActivity(new Intent(this, CommonActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void get_rooms(){
        AndroidNetworking.get( BASE_URL + "/admin_get_rooms")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsObjectList(Room.class, new ParsedRequestListener<List<Room>>(){
                    @Override
                    public void onResponse(List<Room> rooms) {
                        adapter = new AdminRoomsAdapter(getApplicationContext(), rooms);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Could Not Profiles", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
