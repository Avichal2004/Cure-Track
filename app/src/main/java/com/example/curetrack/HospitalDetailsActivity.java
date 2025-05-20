package com.example.curetrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HospitalDetailsActivity extends AppCompatActivity {

    TextView hospitalName, phoneNumber,hospitalemail;
    Spinner deptSpinner, deptSpinner1;
    EditText deptBeds, deptWaitTime;
    ImageView hospitalImage;
    FirebaseUser currentUser ;
    ImageView backButton;


    String[] departments = {"General", "Cardiology", "Neurology", "Pediatrics"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);

        hospitalName = findViewById(R.id.hospitalName);
        hospitalemail=findViewById(R.id.hospitalemail);
        phoneNumber = findViewById(R.id.textphoneno);
        deptSpinner = findViewById(R.id.spinnerBedDept);
        deptSpinner1 = findViewById(R.id.spinnerBedDept1);
        deptBeds = findViewById(R.id.editAvailableBeds);
        deptWaitTime = findViewById(R.id.editAvgWaitTime);
        hospitalImage = findViewById(R.id.imageHospital);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String name = getIntent().getStringExtra("hospitalName");
        String phone = getIntent().getStringExtra("phone");
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String email = getIntent().getStringExtra("email");
        String uid=getIntent().getStringExtra("uid");

        hospitalName.setText(name+" Hospital");
        hospitalemail.setText(email);
        phoneNumber.setText(phone);
        Picasso.get().load(imageUrl).into(hospitalImage);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, departments);
        deptSpinner.setAdapter(adapter);
        deptSpinner1.setAdapter(adapter);

        deptSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String dept = departments[position];
                fetchBeds(name, dept);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        deptSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String dept = departments[position];
                fetchWaitTime(name, dept);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        findViewById(R.id.appointmentbtn).setOnClickListener(v -> {
            Intent intent = new Intent(this, AppointmentActivity.class);
            intent.putExtra("hospital_uid", uid);
            Log.d("uid2",uid);
            startActivity(intent);
        });


        findViewById(R.id.medicine).setOnClickListener(v -> {
            startActivity(new Intent(this, MedicineActivity.class));
        });

        findViewById(R.id.info).setOnClickListener(v -> {
            startActivity(new Intent(this, AboutHospitalActivity.class));
        });

        findViewById(R.id.btnChat).setOnClickListener(v -> {
            startActivity(new Intent(this, ChatActivity.class));
        });
    }

    private void fetchBeds(String hospitalName, String department) {
        FirebaseDatabase.getInstance().getReference("Hospitals")
                .child(currentUser.getUid())
                .child("Departments")
                .child(department)
                .child("availableBeds")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Integer beds = snapshot.getValue(Integer.class);
                        if (beds != null) {
                            deptBeds.setText(String.valueOf(beds));
                        } else {
                            deptBeds.setText("0");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
    }

    private void fetchWaitTime(String hospitalName, String department) {
        FirebaseDatabase.getInstance().getReference("Hospitals")
                .child(hospitalName)
                .child("Departments")
                .child(department)
                .child("waitingTime")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String wait = snapshot.getValue(String.class);
                        if (wait != null) {
                            deptWaitTime.setText(wait);
                        } else {
                            deptWaitTime.setText("N/A");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
    }
}
