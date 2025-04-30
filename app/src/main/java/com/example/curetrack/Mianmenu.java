package com.example.curetrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Mianmenu extends AppCompatActivity {

    Button signwithmail,signwithphone ,signup;
    ImageView back2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mianmenu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.back3), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        signwithmail=(Button) findViewById(R.id.SignwithEmail);
        signwithphone=(Button) findViewById(R.id.Signwithphone);
        signup=(Button) findViewById(R.id.Signup);
        signwithmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signemail=new Intent(Mianmenu.this,chooseone.class);
                signemail.putExtra("Home","Email");
                startActivity(signemail);
                finish();
            }
        });
        signwithphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signphone=new Intent(Mianmenu.this,chooseone.class);
                signphone.putExtra("Home","Phone");
                startActivity(signphone);
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup=new Intent(Mianmenu.this,chooseone.class);
                signup.putExtra("Home","signup");
                startActivity(signup);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}