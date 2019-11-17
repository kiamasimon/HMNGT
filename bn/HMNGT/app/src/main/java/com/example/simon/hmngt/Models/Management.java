package com.example.simon.hmngt.Models;

/**
 * Created by simon on 9/20/19.
 */

public class Management {
    private int id;
    private String first_name;
    private String email;
    private String phone_number;

    public Management(){

    }

    public Management(int id, String first_name, String email, String phone_number){
        this.id = id;
        this.first_name = first_name;
        this.email = email;
        this.phone_number = phone_number;
    }

    public int getId() {
        return id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return first_name;
    }
}
