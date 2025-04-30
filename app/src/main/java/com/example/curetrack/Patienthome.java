package com.example.curetrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Patienthome extends AppCompatActivity {

    ImageButton aiims,apollo,bansal;
    Button btn,btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patienthome);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        aiims = (ImageButton)findViewById(R.id.aiims);
        apollo = (ImageButton)findViewById(R.id.apollo);
        bansal = (ImageButton)findViewById(R.id.bansal);
        aiims.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent Z = new Intent(Patienthome.this,aiimsnext.class);
             startActivity(Z);
             finish();
            }
        });
        btn = (Button)findViewById(R.id.btn);
        btn1 = (Button)findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Z = new Intent(Patienthome.this,Patientrecord.class);
                startActivity(Z);
                finish();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Z = new Intent(Patienthome.this,Patientnotification.class);
                startActivity(Z);
                finish();
            }
        });
        SearchView searchView = findViewById(R.id.searchview);
        searchView.setQueryHint("Search hospital");
    }
}