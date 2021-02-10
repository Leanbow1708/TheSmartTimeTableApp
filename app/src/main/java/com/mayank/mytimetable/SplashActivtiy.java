package com.mayank.mytimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivtiy extends AppCompatActivity {

    MotionLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activtiy);

        layout = findViewById(R.id.motion_splash);
        layout.transitionToEnd();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}