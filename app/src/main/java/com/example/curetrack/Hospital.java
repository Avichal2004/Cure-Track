package com.example.curetrack;


import java.io.Serializable;
public class Hospital {
    private String name;
    private String phone;
    private String imageUrl;
    private int beds;

    public Hospital() {
        // Default constructor required for calls to DataSnapshot.getValue(Hospital.class)
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getBeds() {
        return beds;
    }
}
