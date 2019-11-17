package com.example.simon.hmngt.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by simon on 9/24/19.
 */

public class Example {

    @SerializedName("room")
    @Expose
    private Room room;
    @SerializedName("students")
    @Expose
    private List<Student> students = null;
    @SerializedName("response")
    @Expose
    private String response;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getResponse() {
        return response;
    }
}
