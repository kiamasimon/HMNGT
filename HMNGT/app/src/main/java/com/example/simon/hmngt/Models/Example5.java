package com.example.simon.hmngt.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by simon on 9/29/19.
 */

public class Example5 {
    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("roommate")
    @Expose
    private Student roommate;

    @SerializedName("room")
    @Expose
    private Room room;

    public Room getRoom() {
        return room;
    }

    public String getResponse() {
        return response;
    }

    public Student getRoommate() {
        return roommate;
    }
}
