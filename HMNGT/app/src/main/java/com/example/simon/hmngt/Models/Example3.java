package com.example.simon.hmngt.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by simon on 9/29/19.
 */

public class Example3 {
    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("rooms")
    @Expose
    private List<Room> rooms;

    public String getResponse() {
        return response;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
