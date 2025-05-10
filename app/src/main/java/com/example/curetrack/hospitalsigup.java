package com.example.curetrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class hospitalsigup extends AppCompatActivity {

    TextInputLayout Fname, Email, Pass, cpass, mobileno;
    Button signup;
    FirebaseAuth FAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String fname, emailid, password, confpassword, mobile;
    String role = "Hospital";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitalsignup);

        Fname = findViewById(R.id.HospitalName);
        Email = findViewById(R.id.email);
        Pass = findViewById(R.id.Pwd);
        cpass = findViewById(R.id.confirmpwd);
        mobileno = findViewById(R.id.Phoneno);
        signup = findViewById(R.id.signupbtn);

        databaseReference = FirebaseDatabase.getInstance().getReference("Hospital");
        FAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname = Fname.getEditText().getText().toString().trim();
                emailid = Email.getEditText().getText().toString().trim();
                mobile = mobileno.getEditText().getText().toString().trim();
                password = Pass.getEditText().getText().toString().trim();
                confpassword = cpass.getEditText().getText().toString().trim();

                if (isValid()) {
                    final ProgressDialog mDialog = new ProgressDialog(hospitalsigup.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("Registering...");
                    mDialog.show();

                    FAuth.createUserWithEmailAndPassword(emailid, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                        FirebaseDatabase.getInstance().getReference("User")
                                                .child(userId)
                                                .child("Role")
                                                .setValue(role);

                                        HashMap<String, String> hospitalData = new HashMap<>();
                                        hospitalData.put("phone", mobile);
                                        hospitalData.put("name", fname);
                                        hospitalData.put("EmailId", emailid);
                                        hospitalData.put("Password", password);
                                        hospitalData.put("Confirm Password", confpassword);

                                        FirebaseDatabase.getInstance().getReference("Hospitals")
                                                .child(userId)
                                                .setValue(hospitalData)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        mDialog.dismiss();
                                                        Intent intent = new Intent(hospitalsigup.this, hospitalloginemail.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                    } else {
                                        mDialog.dismiss();
                                        ReusableCodeForAll.ShowAlert(hospitalsigup.this, "Registration Failed", task.getException().getMessage());
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
        Fname.setErrorEnabled(false);
        Pass.setErrorEnabled(false);
        mobileno.setErrorEnabled(false);
        cpass.setErrorEnabled(false);

        boolean isValid = true;

        if (TextUtils.isEmpty(fname)) {
            Fname.setError("Enter Hospital Name");
            isValid = false;
        }
        if (TextUtils.isEmpty(emailid) || !emailid.matches(emailpattern)) {
            Email.setError("Enter valid Email");
            isValid = false;
        }
        if (TextUtils.isEmpty(password) || password.length() < 8) {
            Pass.setError("Password must be at least 8 characters");
            isValid = false;
        }
        if (TextUtils.isEmpty(confpassword) || !confpassword.equals(password)) {
            cpass.setError("Passwords do not match");
            isValid = false;
        }
        if (TextUtils.isEmpty(mobile) || mobile.length() < 10) {
            mobileno.setError("Enter valid mobile number");
            isValid = false;
        }

        return isValid;
    }
}
