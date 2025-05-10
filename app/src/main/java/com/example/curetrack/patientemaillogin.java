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

public class patientemaillogin extends AppCompatActivity {

    TextInputLayout email, pass;
    Button login;
    FirebaseAuth Fauth;
    String emailid, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientemaillogin);

        email = findViewById(R.id.email3);
        pass = findViewById(R.id.pwd3);
        login = findViewById(R.id.loginbtn1);
        Fauth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailid = email.getEditText().getText().toString().trim();
                pwd = pass.getEditText().getText().toString().trim();

                if (isValid()) {
                    final ProgressDialog mDialog = new ProgressDialog(patientemaillogin.this);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setCancelable(false);
                    mDialog.setMessage("Sign In Please Wait.......");
                    mDialog.show();

                    Fauth.signInWithEmailAndPassword(emailid, pwd)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    mDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        Toast.makeText(patientemaillogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(patientemaillogin.this, Patienthome.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(patientemaillogin.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private boolean isValid() {
        email.setErrorEnabled(false);
        email.setError("");
        pass.setErrorEnabled(false);
        pass.setError("");

        boolean validEmail = false, validPassword = false;

        if (TextUtils.isEmpty(emailid)) {
            email.setErrorEnabled(true);
            email.setError("Email is required");
        } else {
            validEmail = true;
        }

        if (TextUtils.isEmpty(pwd)) {
            pass.setErrorEnabled(true);
            pass.setError("Password is required");
        } else {
            validPassword = true;
        }

        return validEmail && validPassword;
    }
}
