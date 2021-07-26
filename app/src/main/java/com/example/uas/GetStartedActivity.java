package com.example.uas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class GetStartedActivity extends AppCompatActivity {

    ImageView LogoApp;
    TextView TextJargon;
    Button btn_signup, btn_getstarted;
    Animation btt, ttb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(getSupportActionBar()).hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.navigation_bar_color));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }

        LogoApp = findViewById(R.id.logo_getstarted);
        TextJargon = findViewById(R.id.txt_jargon);
        btn_signup = findViewById(R.id.btn_to_signup);
        btn_getstarted = findViewById(R.id.btn_getStarted);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        LogoApp.startAnimation(ttb);
        TextJargon.startAnimation(ttb);
        btn_signup.startAnimation(btt);
        btn_getstarted.startAnimation(btt);

        btn_getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GetStartedActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GetStartedActivity.this, SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}