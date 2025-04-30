package com.example.curetrack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageView imageview;
    TextView textView;
    ImageView imageview1;
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.back3), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imageview=(ImageView) findViewById(R.id.screenshot_);
        textView=(TextView)findViewById(R.id.cure_track_);
        imageview1=(ImageView) findViewById(R.id.imageView);
        textView1=(TextView)findViewById(R.id.cure_track1_);
        imageview.animate().alpha(0f).setDuration(0);
        imageview1.animate().alpha(0f).setDuration(0);
        textView.animate().alpha(0f).setDuration(0);
        textView1.animate().alpha(0f).setDuration(0);

        imageview.animate().alpha(1f).setDuration(1000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                textView.animate().alpha(1f).setDuration(800);
                imageview1.animate().alpha(1f).setDuration(1000);
                textView1.animate().alpha(1f).setDuration(800);
            }
        });
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent intent=new Intent(MainActivity.this,Mianmenu.class);
               startActivity(intent);
               finish();
           }
       },3000);
    }
}