package com.example.curetrack;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AppointmentActivity extends AppCompatActivity {

    EditText fullName, age, phone;
    Spinner generalSpinner, specialSpinner;
    ImageView backButton;
    Button submitBtn, downloadBtn;
    DatabaseReference databaseReference;

    String hospitalUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_activity);

        fullName = findViewById(R.id.fullName);
        age = findViewById(R.id.age);
        phone = findViewById(R.id.phone);
        generalSpinner = findViewById(R.id.generalSpinner);
        specialSpinner = findViewById(R.id.specialSpinner);
        submitBtn = findViewById(R.id.submitBtn);
        downloadBtn = findViewById(R.id.downloadBtn);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        hospitalUid = getIntent().getStringExtra("hospital_uid");
        ArrayAdapter<CharSequence> generalAdapter = ArrayAdapter.createFromResource(this,
                R.array.general_departments, android.R.layout.simple_spinner_item);
        generalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        generalSpinner.setAdapter(generalAdapter);

        ArrayAdapter<CharSequence> specialAdapter = ArrayAdapter.createFromResource(this,
                R.array.special_departments, android.R.layout.simple_spinner_item);
        specialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialSpinner.setAdapter(specialAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Hospitals")
                .child(hospitalUid).child("Patients");

        submitBtn.setOnClickListener(v -> submitAppointment());
        downloadBtn.setOnClickListener(v -> {
            // Implement download logic
        });
    }

    private void submitAppointment() {
        String name = fullName.getText().toString();
        String ageVal = age.getText().toString();
        String phoneNo = phone.getText().toString();
        String general = generalSpinner.getSelectedItem().toString();
        String special = specialSpinner.getSelectedItem().toString();

        if (name.isEmpty() || ageVal.isEmpty() || phoneNo.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = databaseReference.push().getKey();
        HashMap<String, String> patientData = new HashMap<>();
        patientData.put("name", name);
        patientData.put("age", ageVal);
        patientData.put("phone", phoneNo);
        patientData.put("general", general);
        patientData.put("special", special);

        Log.d("uid",id);
        assert id != null;
        databaseReference.child(id).setValue(patientData)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Appointment Submitted", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Submission Failed", Toast.LENGTH_SHORT).show());
    }
}

