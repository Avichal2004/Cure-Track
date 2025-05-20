package com.example.curetrack;

public class Hospital {
    private String hospitalName;
    private String phone;
    private String imageUrl;
    private int availableBeds;
    private String city;
    private String building;
    private int staffCount;
    private String license;
    private int receptionist;
    private String street;
    private int totalBeds;
    private int doctorCount;
    private String email;
    private boolean medicineAvailability;
    private String uid;

    public Hospital() {
        // Needed for Firebase
    }
    public Hospital(String hospitalName, String phone, String imageUrl, int availableBeds,String uid,String email) {
        this.hospitalName = hospitalName;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.availableBeds = availableBeds;
        this.uid = uid;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }
    // Getters
    public String getHospitalName() {
        return hospitalName;
    }

    public String getPhone() {
        return phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getAvailableBeds() {
        return availableBeds;
    }

    public String getCity() {
        return city;
    }

    public String getBuilding() {
        return building;
    }

    public int getStaffCount() {
        return staffCount;
    }

    public String getLicense() {
        return license;
    }

    public int getReceptionist() {
        return receptionist;
    }

    public String getStreet() {
        return street;
    }

    public int getTotalBeds() {
        return totalBeds;
    }

    public int getDoctorCount() {
        return doctorCount;
    }

    public String getEmail() {
        return email;
    }

    public boolean isMedicineAvailability() {
        return medicineAvailability;
    }

    // Optionally, add setters if you plan to update values from app
}
