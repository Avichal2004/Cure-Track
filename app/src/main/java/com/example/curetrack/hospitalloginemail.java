package com.example.curetrack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class hospitalloginemail extends AppCompatActivity {

    TextInputLayout email, pass;
    Button login;
    FirebaseAuth Fauth;
    String emailid, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hospitalloginemail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = findViewById(R.id.email2);
        pass = findViewById(R.id.pwd2);
        login = findViewById(R.id.button);

        Fauth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailid = email.getEditText().getText().toString().trim();
                pwd = pass.getEditText().getText().toString().trim();

                if (isValid()) {
                    final ProgressDialog mDialog = new ProgressDialog(hospitalloginemail.this);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setCancelable(false);
                    mDialog.setMessage("Signing in, please wait...");
                    mDialog.show();

                    Fauth.signInWithEmailAndPassword(emailid, pwd)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    mDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        Toast.makeText(hospitalloginemail.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(hospitalloginemail.this, Hospitalhome.class));
                                        finish();
                                    } else {
                                        ReusableCodeForAll.ShowAlert(hospitalloginemail.this, "Login Failed", task.getException().getMessage());
                                    }
                                }
                            });
                }
            }
        });
    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid() {
        email.setErrorEnabled(false);
        pass.setErrorEnabled(false);

        boolean isValid = true;

        if (TextUtils.isEmpty(emailid)) {
            email.setErrorEnabled(true);
            email.setError("Email is required");
            isValid = false;
        } else if (!emailid.matches(emailpattern)) {
            email.setErrorEnabled(true);
            email.setError("Invalid Email Address");
            isValid = false;
        }

        if (TextUtils.isEmpty(pwd)) {
            pass.setErrorEnabled(true);
            pass.setError("Password is required");
            isValid = false;
        }

        return isValid;
    }
}
