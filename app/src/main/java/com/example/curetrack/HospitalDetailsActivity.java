package com.example.curetrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HospitalDetailsActivity extends AppCompatActivity {

    TextView hospitalName, phoneNumber, totalBeds, avgWaitTime;
    Spinner deptSpinner;
    TextView deptBeds, deptWaitTime;
    ImageView hospitalImage;

    String[] departments = {"General", "Cardiology", "Neurology", "Pediatrics"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);

        hospitalName = findViewById(R.id.hospitalName);
        phoneNumber = findViewById(R.id.phoneNumber);
        totalBeds = findViewById(R.id.totalBeds);
        avgWaitTime = findViewById(R.id.avgWaitTime);
        deptSpinner = findViewById(R.id.deptSpinner);
        deptBeds = findViewById(R.id.deptBeds);
        deptWaitTime = findViewById(R.id.deptWaitTime);
        hospitalImage = findViewById(R.id.hospitalImage);

        // Get data from Intent
        String name = getIntent().getStringExtra("hospitalName");
        String phone = getIntent().getStringExtra("phone");
        String imageUrl = getIntent().getStringExtra("imageUrl");
        int totalAvailableBeds = getIntent().getIntExtra("totalBeds", 0);

        hospitalName.setText(name);
        phoneNumber.setText(phone);
        totalBeds.setText(String.valueOf(totalAvailableBeds));
        avgWaitTime.setText("10 min"); // Default or fetched value

//        Glide.with(this).load(imageUrl).into(hospitalImage); // Load image

        // Populate spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, departments);
        deptSpinner.setAdapter(adapter);

        deptSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String dept = departments[position];
                fetchDepartmentData(name, dept);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        findViewById(R.id.appointmentBtn).setOnClickListener(v -> {
            startActivity(new Intent(this, AppointmentActivity.class));
        });

        findViewById(R.id.medicineBtn).setOnClickListener(v -> {
            startActivity(new Intent(this, MedicineActivity.class));
        });

        findViewById(R.id.aboutBtn).setOnClickListener(v -> {
            startActivity(new Intent(this, AboutHospitalActivity.class));
        });

        findViewById(R.id.chatBtn).setOnClickListener(v -> {
            startActivity(new Intent(this, ChatActivity.class));
        });
    }

    private void fetchDepartmentData(String hospitalName, String department) {
//        FirebaseDatabase.getInstance().getReference("Hospitals")
//                .child(hospitalName)
//                .child("Departments")
//                .child(department)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        int beds = snapshot.child("availableBeds").getValue(Integer.class);
//                        String wait = snapshot.child("waitingTime").getValue(String.class);
//                        deptBeds.setText(String.valueOf(beds));
//                        deptWaitTime.setText(wait);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {}
//                });
    }
}
