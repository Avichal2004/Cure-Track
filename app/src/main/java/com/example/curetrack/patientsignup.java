package com.example.curetrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class patientsignup extends AppCompatActivity {

    TextInputLayout Fname, Email, Pass, cpass, mobileno;
    Button signup;

    FirebaseAuth FAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    String fname, emailid, password, confpassword, mobile;
    String role = "Patient";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientsignup);

        Fname = findViewById(R.id.fullname);
        Email = findViewById(R.id.email1);
        Pass = findViewById(R.id.Pwd1);
        cpass = findViewById(R.id.confirmpwd1);
        mobileno = findViewById(R.id.Phoneno1);
        signup = findViewById(R.id.submitbtn1);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Patient");
        FAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname = Fname.getEditText().getText().toString().trim();
                emailid = Email.getEditText().getText().toString().trim();
                password = Pass.getEditText().getText().toString().trim();
                confpassword = cpass.getEditText().getText().toString().trim();
                mobile = mobileno.getEditText().getText().toString().trim();

                if (isValid()) {
                    final ProgressDialog mDialog = new ProgressDialog(patientsignup.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("Registration in progress, please wait...");
                    mDialog.show();

                    FAuth.createUserWithEmailAndPassword(emailid, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    mDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        String userId = FAuth.getCurrentUser().getUid();

                                        DatabaseReference userRef = firebaseDatabase.getReference("User").child(userId);
                                        HashMap<String, String> userMap = new HashMap<>();
                                        userMap.put("Role", role);

                                        userRef.setValue(userMap);

                                        HashMap<String, String> patientMap = new HashMap<>();
                                        patientMap.put("Mobile No", mobile);
                                        patientMap.put("First Name", fname);
                                        patientMap.put("EmailId", emailid);
                                        patientMap.put("Password", password);
                                        patientMap.put("Confirm Password", confpassword);

                                        databaseReference.child(userId).setValue(patientMap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(patientsignup.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(patientsignup.this, patientemaillogin.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(patientsignup.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid() {
        Email.setErrorEnabled(false);
        Email.setError("");
        Fname.setErrorEnabled(false);
        Fname.setError("");
        Pass.setErrorEnabled(false);
        Pass.setError("");
        mobileno.setErrorEnabled(false);
        mobileno.setError("");
        cpass.setErrorEnabled(false);
        cpass.setError("");

        boolean isValidname = false, isValidemail = false, isValidpassword = false, isValidconfpassword = false, isValidmobilenum = false;

        if (TextUtils.isEmpty(fname)) {
            Fname.setErrorEnabled(true);
            Fname.setError("Enter First Name");
        } else {
            isValidname = true;
        }

        if (TextUtils.isEmpty(emailid)) {
            Email.setErrorEnabled(true);
            Email.setError("Email is Required");
        } else if (!emailid.matches(emailpattern)) {
            Email.setErrorEnabled(true);
            Email.setError("Enter a Valid Email Id");
        } else {
            isValidemail = true;
        }

        if (TextUtils.isEmpty(password)) {
            Pass.setErrorEnabled(true);
            Pass.setError("Enter Password");
        } else if (password.length() < 8) {
            Pass.setErrorEnabled(true);
            Pass.setError("Password is too short");
        } else {
            isValidpassword = true;
        }

        if (TextUtils.isEmpty(confpassword)) {
            cpass.setErrorEnabled(true);
            cpass.setError("Enter Password Again");
        } else if (!password.equals(confpassword)) {
            cpass.setErrorEnabled(true);
            cpass.setError("Passwords do not match");
        } else {
            isValidconfpassword = true;
        }

        if (TextUtils.isEmpty(mobile)) {
            mobileno.setErrorEnabled(true);
            mobileno.setError("Mobile Number is Required");
        } else if (mobile.length() != 10) {
            mobileno.setErrorEnabled(true);
            mobileno.setError("Invalid Mobile Number");
        } else {
            isValidmobilenum = true;
        }

        return isValidname && isValidemail && isValidpassword && isValidconfpassword && isValidmobilenum;
    }
}
