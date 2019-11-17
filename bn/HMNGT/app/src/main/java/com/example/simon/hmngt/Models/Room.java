package com.example.simon.hmngt.Models;

/**
 * Created by simon on 9/20/19.
 */

public class Room {
    private int id;
    private String room_number;
    private String type;
    private String status_occupied;
    private String short_description;
    private String floor;

    public Room(){

    }

    public Room(int id, String room_number, String type, String status_occupied, String short_description, String floor){
        this.id = id;
        this.room_number = room_number;
        this.type = type;
        this.status_occupied = status_occupied;
        this.floor = floor;
        this.short_description = short_description;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getFloor() {
        return floor;
    }

    public String getRoom_number() {
        return room_number;
    }

    public String getShort_description() {
        return short_description;
    }

    public String getStatus_occupied() {
        return status_occupied;
    }
}
