package com.example.simon.hmngt.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by simon on 9/24/19.
 */

public class Invite {
    @SerializedName("student")
    @Expose
    private Student student;
    @SerializedName("room")
    @Expose
    private Room room;
    @SerializedName("response")
    @Expose
    private String response;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getResponse() {
        return response;
    }
}
