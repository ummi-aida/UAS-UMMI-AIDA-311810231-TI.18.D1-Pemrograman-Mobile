package com.example.uas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.uas.fragment.AddFragment;
import com.example.uas.fragment.HomeFragment;
import com.example.uas.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private Animation slide_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.navigation_bar_color));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }

        slide_up = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.main_container, new HomeFragment()).commit();

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                View icHome = findViewById(R.id.home_menu);
                View icAdd = findViewById(R.id.add_menu);
                View icProfile = findViewById(R.id.profile_menu);
                View[] icMenus = new View[] { icHome, icAdd, icProfile };
                switch (id) {
                    case R.id.home_menu:
                        animatedItemMenu(icHome, icMenus);
                        fragment = new HomeFragment();
                        break;
                    case R.id.add_menu:
                        animatedItemMenu(icAdd, icMenus);
                        fragment = new AddFragment();
                        break;
                    case R.id.profile_menu:
                        animatedItemMenu(icProfile, icMenus);
                        fragment = new ProfileFragment();
                        break;
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                return true;
            }
        });
    }

    public void animatedItemMenu(View v, View[] icMenus) {
        v.startAnimation(slide_up);
        for (View icMenu : icMenus) {
            if (icMenu != v) {
                icMenu.clearAnimation();
            }
        }
    }

}