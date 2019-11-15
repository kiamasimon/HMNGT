package com.example.simon.hmngt.Models;

/**
 * Created by simon on 9/20/19.
 */

public class Student {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private String hobbies;
    private String religion;
    private String school;
    private String sports;
    private String course;
    private String visitor_count;
    private String drugs;
    private String gender;

    public Student(){

    }

    public Student(int id, String first_name, String last_name, String email, String phone_number, String hobbies,
                    String religion, String school, String sports, String course, String visitor_count, String drugs,
                   String gender){
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.hobbies = hobbies;
        this.religion = religion;
        this.school = school;
        this.sports = sports;
        this.course = course;
        this.visitor_count = visitor_count;
        this.drugs = drugs;
        this.gender = gender;

    }

    public String getReligion() {
        return religion;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public int getId() {
        return id;
    }

    public String getSchool() {
        return school;
    }

    public String getHobbies() {
        return hobbies;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getSports() {
        return sports;
    }

    public String getCourse() {
        return course;
    }

    public String getDrugs() {
        return drugs;
    }

    public String getVisitor_count() {
        return visitor_count;
    }

    public String getGender() {
        return gender;
    }
}
