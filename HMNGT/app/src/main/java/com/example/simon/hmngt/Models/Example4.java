package com.example.simon.hmngt.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by simon on 9/29/19.
 */

public class Example4 {
    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("bookings")
    @Expose
    private List<Student> students;

    @SerializedName("room")
    @Expose
    private Room room;

    public String getResponse() {
        return response;
    }

    public List<Student> getStudents() {
        return students;
    }

    public Room getRoom() {
        return room;
    }
}
