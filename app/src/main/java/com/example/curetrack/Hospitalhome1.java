package com.example.curetrack;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Hospitalhome1 extends AppCompatActivity {

    EditText availableBeds, totalBeds, doctorCount, staffCount, medicineAvailability;
    EditText editDeptAvailableBeds, editDeptTotalBeds;
    Spinner spinnerDepartments;
    Button updateButton, deptUpdateButton;
    ImageView hospitalImage;
    TextView name11;

    DatabaseReference userRef;
    FirebaseAuth auth;

    String[] departments = {"General", "Cardiology", "Neurology", "Pediatrics"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospitalhome1);

        availableBeds = findViewById(R.id.editDeptAvailableBeds);
        totalBeds = findViewById(R.id.editDeptTotalBeds);
        doctorCount = findViewById(R.id.doctorCount);
        staffCount = findViewById(R.id.staffCount);
        medicineAvailability = findViewById(R.id.medicineAvailability);

        editDeptAvailableBeds = findViewById(R.id.editDeptAvailableBeds);
        editDeptTotalBeds = findViewById(R.id.editDeptTotalBeds);
        spinnerDepartments = findViewById(R.id.spinnerDepartments);

        updateButton = findViewById(R.id.updateButton);
        deptUpdateButton = findViewById(R.id.update); // Use correct ID in XML for dept update

        hospitalImage = findViewById(R.id.hospitalImage);
        name11 = findViewById(R.id.name);

        auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference("Hospitals").child(userId);

        // Load hospital image
        userRef.child("imageUrl").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot snapshot) {
                String imageUrl = snapshot.getValue(String.class);
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Picasso.get().load(imageUrl).into(hospitalImage);
                }
            }
            @Override public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Hospitalhome1.this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        });

        // Load hospital name
        userRef.child("hospitalName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name1 = snapshot.getValue(String.class);
                name11.setText(name1);
            }
            @Override public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Hospitalhome1.this, "Failed to load", Toast.LENGTH_SHORT).show();
            }
        });

        // Populate department spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, departments);
        spinnerDepartments.setAdapter(adapter);

        // Update hospital basic data
        updateButton.setOnClickListener(v -> {
            Map<String, Object> updates = new HashMap<>();

            if (!availableBeds.getText().toString().trim().isEmpty())
                updates.put("availableBeds", Integer.parseInt(availableBeds.getText().toString().trim()));
            if (!totalBeds.getText().toString().trim().isEmpty())
                updates.put("totalBeds", Integer.parseInt(totalBeds.getText().toString().trim()));
            if (!doctorCount.getText().toString().trim().isEmpty())
                updates.put("doctorCount", Integer.parseInt(doctorCount.getText().toString().trim()));
            if (!staffCount.getText().toString().trim().isEmpty())
                updates.put("staffCount", Integer.parseInt(staffCount.getText().toString().trim()));
            if (!medicineAvailability.getText().toString().trim().isEmpty())
                updates.put("medicineAvailability", medicineAvailability.getText().toString().trim());

            userRef.updateChildren(updates).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Information Updated Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Update department-specific bed info
        deptUpdateButton.setOnClickListener(v -> {
            String dept = spinnerDepartments.getSelectedItem().toString();
            String avail = editDeptAvailableBeds.getText().toString().trim();
            String total = editDeptTotalBeds.getText().toString().trim();

            if (avail.isEmpty() || total.isEmpty()) {
                Toast.makeText(this, "Please fill all bed fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> deptData = new HashMap<>();
            deptData.put("availableBeds", Integer.parseInt(avail));
            deptData.put("totalBeds", Integer.parseInt(total));

            userRef.child("Departments").child(dept).updateChildren(deptData)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, dept + " Department Beds Updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Department Update Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
