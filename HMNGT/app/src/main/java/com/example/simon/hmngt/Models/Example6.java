package com.example.simon.hmngt.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by simon on 9/30/19.
 */

public class Example6 {
    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("room")
    @Expose
    private Room room;

    public String getResponse() {
        return response;
    }

    public Room getRoom() {
        return room;
    }
}
