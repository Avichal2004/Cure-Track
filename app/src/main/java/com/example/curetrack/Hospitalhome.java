package com.example.curetrack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Hospitalhome extends AppCompatActivity {

    private ImageView hospitalImageView;
    private Uri imageUri;
    private EditText etHospitalName, etReceptionistName, etPhone, etEmail, etBuilding, etStreet, etCity, etLicense;
    private Button submitButton;
    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;
    private StorageReference storageRef;

    private static final int IMAGE_PICK_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hospitalhome);

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("Hospitals");
        storageRef = FirebaseStorage.getInstance().getReference("HospitalImages");

        hospitalImageView = findViewById(R.id.hospitalImageView);
        etHospitalName = findViewById(R.id.etHospitalName);
        etReceptionistName = findViewById(R.id.etReceptionistName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etBuilding = findViewById(R.id.etBuilding);
        etStreet = findViewById(R.id.etStreet);
        etCity = findViewById(R.id.etCity);
        etLicense = findViewById(R.id.etLicense);
        submitButton = findViewById(R.id.submitButton);

        hospitalImageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, IMAGE_PICK_CODE);
        });

        submitButton.setOnClickListener(v -> {
            if (imageUri != null) {
                String uid = mAuth.getCurrentUser().getUid();
                StorageReference imgRef = storageRef.child(uid + "/" + System.currentTimeMillis() + ".jpg");

                Log.d("UploadDebug", "Uploading image: " + imageUri.toString());

                imgRef.putFile(imageUri)
                        .continueWithTask(task -> {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return imgRef.getDownloadUrl();
                        })
                        .addOnSuccessListener(downloadUri -> {
                            String imageUrlFromFirebase = downloadUri.toString();
                            Picasso.get().load(imageUrlFromFirebase).into(hospitalImageView);
                            saveHospitalData(imageUrlFromFirebase, uid);
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(this, "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());

            } else {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            hospitalImageView.setImageURI(imageUri);
        }
    }

    private void saveHospitalData(String imageUrl, String uid) {
        String hospitalName = etHospitalName.getText().toString().trim();
        String receptionist = etReceptionistName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String building = etBuilding.getText().toString().trim();
        String street = etStreet.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String license = etLicense.getText().toString().trim();

        HashMap<String, Object> hospitalData = new HashMap<>();
        hospitalData.put("hospitalName", hospitalName);
        hospitalData.put("receptionist", receptionist);
        hospitalData.put("phone", phone);
        hospitalData.put("email", email);
        hospitalData.put("building", building);
        hospitalData.put("street", street);
        hospitalData.put("city", city);
        hospitalData.put("license", license);
        hospitalData.put("imageUrl", imageUrl);

        // Store directly under UID (not using push)
        dbRef.child(uid).setValue(hospitalData)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Hospital info added", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show());
    }
}
