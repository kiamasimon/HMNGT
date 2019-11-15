package com.example.simon.hmngt.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by simon on 9/29/19.
 */

public class Example2 {
    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("invites")
    @Expose
    private List<Student> students;

    public String getResponse() {
        return response;
    }

    public List<Student> getStudents() {
        return students;
    }
}
