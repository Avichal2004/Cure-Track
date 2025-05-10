package com.example.curetrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class chooseone extends AppCompatActivity {

    Button aspatient,ashospital;
    String type;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chooseone);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.back3), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        intent=getIntent();
        type=intent.getStringExtra("Home").toString().trim();
        ashospital=(Button)findViewById(R.id.hospital);
        aspatient=(Button) findViewById(R.id.patient);
        ashospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("Email")){
                    Intent loginemail=new Intent(chooseone.this,hospitalloginemail.class);
                    startActivity(loginemail);
                    finish();
                }
                if(type.equals("Phone")){
                    Intent loginphone=new Intent(chooseone.this,hospitalloginphone.class);
                    startActivity(loginphone);
                    finish();
                }
                if(type.equals("signup")){
                    Intent signup=new Intent(chooseone.this,hospitalsigup.class);
                    startActivity(signup);
                }
            }
        });
        aspatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("Email")){
                    Intent loginemailpatient=new Intent(chooseone.this,patientemaillogin.class);
                    startActivity(loginemailpatient);
                    finish();
                }
                if(type.equals("Phone")){
                    Intent loginphonepatient=new Intent(chooseone.this,patientphonelogin.class);
                    startActivity(loginphonepatient);
                    finish();
                }
                if(type.equals("signup")){
                    Intent signuppatient=new Intent(chooseone.this,patientsignup.class);
                    startActivity(signuppatient);
                }
            }
        });
    }

}