package com.shanidev.zesapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shanidev.blissDB.DashBoard;

public class MainActivity<savedInstanceState> extends AppCompatActivity {

    private static final String TAG = null;
    private FirebaseAuth mAuth;
    private Object savedInstanceState;

    @Override
     MainActivity onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        //Handler
        int SPLASH_SCREEN = 3000;
        new Handler().postDelayed(() -> {

            FirebaseUser currentUser = mAuth.getCurrentUser();
            Intent intent;
            if(currentUser != null) {
                intent = new Intent(MainActivity.this, DashBoard.class);
            }else {
                intent = new Intent(MainActivity.this, OnBoarding.class);

            }
            startActivity(intent);
            finish();
        }, SPLASH_SCREEN);
    }
}