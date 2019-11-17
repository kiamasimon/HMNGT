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
import com.example.simon.hmngt.Activities.CommonActivity;
import com.example.simon.hmngt.Adapters.BookingsAdapter;
import com.example.simon.hmngt.Adapters.RoomsAdapter;
import com.example.simon.hmngt.Models.Example3;
import com.example.simon.hmngt.Models.Room;
import com.example.simon.hmngt.Models.Student;

import java.util.List;

import static com.example.simon.hmngt.Constants.Constants.BASE_URL;

public class ManagementDashboardActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    BookingsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_dashboard);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        get_bookings();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_options_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.rooms:
                startActivity(new Intent(this, AdminRoomsListActivity.class));
                return true;
//            case R.id.booking_list:
//                startActivity(new Intent(this, AdminBookingListActivity.class));
//                return true;
            case R.id.logout:
                startActivity(new Intent(this, CommonActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void get_bookings(){
        AndroidNetworking.get( BASE_URL + "/admin_get_bookings")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(Example3.class, new ParsedRequestListener<Example3>(){
                    @Override
                    public void onResponse(Example3 example3) {
                        if(example3.getResponse().equals("Bookings")){
                            List<Room> rooms = example3.getRooms();
                            if(rooms.size() > 0){
//                                Toast.makeText(getApplicationContext(), ""+example3., Toast.LENGTH_LONG).show();
                                adapter = new BookingsAdapter(getApplicationContext(), rooms);
                                recyclerView.setAdapter(adapter);
                            }else {
                                Toast.makeText(getApplicationContext(), "There are currently no bookings", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), ""+example3.getResponse(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Could Not Profiles", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
