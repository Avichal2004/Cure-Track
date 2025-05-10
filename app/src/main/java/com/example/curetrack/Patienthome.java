package com.example.curetrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Patienthome extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Hospital> hospitalList;
    private HospitalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patienthome);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hospitalList = new ArrayList<>();
        adapter = new HospitalAdapter(this, hospitalList);
        recyclerView.setAdapter(adapter);

        // Load data from Firebase
        loadHospitalsFromFirebase();

        // Setup Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;

                if (item.getItemId() == R.id.nav_home) {
                    return true; // Stay on current activity
                } else if (item.getItemId() == R.id.nav_record) {
                    intent = new Intent(Patienthome.this, RecordActivity.class);
                } else if (item.getItemId() == R.id.nav_notification) {
                    intent = new Intent(Patienthome.this, NotificationActivity.class);
                }

                if (intent != null) {
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });
    }

    private void loadHospitalsFromFirebase() {
        FirebaseDatabase.getInstance().getReference("Hospitals")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        hospitalList.clear();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            Hospital hospital = data.getValue(Hospital.class);
                            if (hospital != null) {
                                hospitalList.add(hospital);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Patienthome.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
