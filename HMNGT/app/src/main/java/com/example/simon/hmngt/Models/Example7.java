package com.example.simon.hmngt.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by simon on 9/30/19.
 */

public class Example7 {

    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("student")
    @Expose
    private Student student;

    public String getResponse() {
        return response;
    }

    public Student getStudent() {
        return student;
    }
}
