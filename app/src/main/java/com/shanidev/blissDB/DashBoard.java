package com.shanidev.blissDB;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.shanidev.zesapp.R;

public class DashBoard extends AppCompatActivity {

    BottomNavigationView nBottomNavigationView;
    FrameLayout nFramelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        nBottomNavigationView = findViewById(R.id.bottomNavigationView);
        nFramelayout = findViewById(R.id.frameLayout);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment()).commit();


        nBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                SearchFragment fragment = null;
                switch (item.getItemId()){
                    case R.id.home:
                    case R.id.post:
                        fragment = new SearchFragment();
                        break;
                    case R.id.messages:
                        fragment = new SearchFragment();
                        break;
                    case R.id.search:
                        fragment = new SearchFragment();
                        break;
                    case R.id.account:
                        fragment = (SearchFragment) new Fragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
                return true;
            }
        });



    }
}