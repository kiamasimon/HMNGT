package com.example.simon.hmngt.Models;

/**
 * Created by simon on 9/20/19.
 */

public class Message {
    private int value;
    private String message;
    private int id;

    public Message(){

    }

    public Message(int value, String message, int id){
        this.value = value;
        this.message = message;
        this.id = id;

    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public int getStudent() {
        return id;
    }
}
