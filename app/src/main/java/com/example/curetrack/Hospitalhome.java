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

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Hospitalhome extends AppCompatActivity {

    private ImageView hospitalImageView;
    private Uri imageUri; // Now it's Uri, not String
    private EditText etHospitalName, etReceptionistName, etPhone, etEmail, etBuilding, etStreet, etCity, etLicense;
    private Button submitButton;
    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;

    private static final int PICK_IMAGE_REQUEST = 1;
    private String imageUrlFromCloudinary = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hospitalhome);

        // Cloudinary configuration
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", "dm0owpeim");
        config.put("api_key", "217179454114589");
        config.put("api_secret", "yrXuQ-2Dvu-8IQYZHcxeZF0C4rA");

        MediaManager.init(this, config);

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("Hospitals");

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

        hospitalImageView.setOnClickListener(v -> openImageChooser());

        submitButton.setOnClickListener(v -> {
            if (imageUri != null) {
                try {
                    File file = createTempFileFromUri(imageUri);
                    uploadImageToCloudinary(file);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to process image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                saveUserInfo(imageUrlFromCloudinary);
            }
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            Log.d("image uri", String.valueOf(imageUri));
            hospitalImageView.setImageURI(imageUri);
        }
    }

    private File createTempFileFromUri(Uri uri) throws Exception {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        File tempFile = File.createTempFile("upload", ".jpg", getCacheDir());
        OutputStream outputStream = new FileOutputStream(tempFile);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.close();
        inputStream.close();
        return tempFile;
    }

    private void uploadImageToCloudinary(File file) {
        MediaManager.get().upload(file.getAbsolutePath())
                .option("public_id", "hospital_" + UUID.randomUUID())
                .callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {
                        Toast.makeText(Hospitalhome.this, "Uploading image...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {}

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        imageUrlFromCloudinary = (String) resultData.get("secure_url");
                        Picasso.get().load(imageUrlFromCloudinary).into(hospitalImageView);
                        saveUserInfo(imageUrlFromCloudinary);
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        Toast.makeText(Hospitalhome.this, "Upload failed: " + error.getDescription(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {
                        Log.e("Cloudinary", "Upload rescheduled: " + error.getDescription());
                    }
                })
                .dispatch();
    }

    private void saveUserInfo(String imageUrl) {
        String uid = mAuth.getCurrentUser().getUid();
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

        dbRef.child(uid).setValue(hospitalData)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Hospital info added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, Hospitalhome1.class));
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to save data: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
