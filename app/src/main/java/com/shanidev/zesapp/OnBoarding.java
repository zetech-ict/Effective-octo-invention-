package com.shanidev.zesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class OnBoarding extends AppCompatActivity {

    TextView nSkipOnBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        nSkipOnBoard = findViewById(R.id.skip_onboard);

        ViewPager2 viewPager2 = findViewById(R.id.onboard_viewpager);

        nSkipOnBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Sign_Up.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.materialButton).setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Sign_Up.class);
            startActivity(intent);
            finish();
        });


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(OnBoarding.this);
        viewPager2.setAdapter(viewPagerAdapter);

    }
    public static class ViewPagerAdapter extends FragmentStateAdapter{

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new FirstFragment();
                case 1:
                    return new SecondIntro();
                case 2:
                    return new ThirdIntro();
            }
            return new FirstFragment();
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}