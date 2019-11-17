package com.example.simon.hmngt.Models;

/**
 * Created by simon on 9/21/19.
 */

public class Booking {
    private String student_id;
    private String room_id;
    private String id;
    private String status;
    private String floor;
    private String name;
    private String description;
    private String type;

    public Booking(){

    }

    public Booking(String student_id, String room_id,String id, String status, String name, String floor, String description, String type){
        this.student_id = student_id;
        this.status = status;
        this.id = id;
        this.floor = floor;
        this.name = name;
        this.type = type;
        this.description = description;

    }

    public String getId() {
        return id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public String getStatus() {
        return status;
    }

    public String getFloor() {
        return floor;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }
}