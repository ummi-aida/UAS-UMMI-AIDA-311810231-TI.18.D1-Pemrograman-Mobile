package com.example.uas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uas.dataBase.DatabaseQuery;
import com.example.uas.models.SensorModel;
import com.google.android.material.textfield.TextInputEditText;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddSensorActivity extends AppCompatActivity {

    CheckBox dieHeight, Buckling, passSensor, missFeed, bodySensor;
    TextInputEditText nameSetEdt;
    Button addSensor;
    LinearLayout Loading;
    int dieHeightValue = 0, bucklingValue = 0, passValue = 0, missFeedValue = 0, bodySensorValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sensor);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.navigation_bar_color));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }
        setTitle("Add Implemented Sensor");

        dieHeight = findViewById(R.id.die_height);
        Buckling = findViewById(R.id.buckling);
        passSensor = findViewById(R.id.pass_sensor);
        missFeed = findViewById(R.id.miss_feed);
        bodySensor = findViewById(R.id.body_sensor);
        nameSetEdt = findViewById(R.id.nameSet_edt);
        addSensor = findViewById(R.id.btn_add_sensor);
        Loading = findViewById(R.id.loading);

        nameSetEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    nameSetEdt.clearFocus();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return false;
            }
        });

        dieHeight.setOnCheckedChangeListener(onCheckedListener("d"));
        Buckling.setOnCheckedChangeListener(onCheckedListener("buckling"));
        passSensor.setOnCheckedChangeListener(onCheckedListener("pass"));
        missFeed.setOnCheckedChangeListener(onCheckedListener("m"));
        bodySensor.setOnCheckedChangeListener(onCheckedListener("body"));

        addSensor.setOnClickListener(this::onSubmit);
    }

    private void onSubmit(View v) {
        SensorModel sensorModel = new SensorModel(-1, nameSetEdt.getText().toString(), dieHeightValue, bucklingValue,
                passValue, missFeedValue, bodySensorValue);

        DatabaseQuery databaseQuery = new DatabaseQuery(getApplicationContext());
        long result = databaseQuery.insertSensor(sensorModel);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Loading.setVisibility(View.VISIBLE);
            }
        }, 250);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Loading.setVisibility(View.GONE);
                if (result > 0) {
                    SweetAlertDialog alertDialog = new SweetAlertDialog(AddSensorActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                    alertDialog.setTitleText("Success")
                            .setContentText("Success, add sensor")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent i = new Intent(AddSensorActivity.this, MainActivity.class);
                                    startActivity(i);
                                    alertDialog.dismissWithAnimation();
                                    finish();
                                }
                            })
                            .show();
                    Button btn = (Button) alertDialog.findViewById(R.id.confirm_button);
                    btn.setBackgroundTintList(null);
                } else {
                    SweetAlertDialog alertDialog = new SweetAlertDialog(AddSensorActivity.this, SweetAlertDialog.ERROR_TYPE);
                    alertDialog.setTitleText("Error")
                            .setContentText("Failed, add sensor")
                            .show();
                    Button btn = (Button) alertDialog.findViewById(R.id.confirm_button);
                    btn.setBackgroundTintList(null);
                }
            }
        }, 2000);
    }

    private CompoundButton.OnCheckedChangeListener onCheckedListener(String action) {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (action) {
                    case "d":
                        if (isChecked) dieHeightValue = 1;
                        else dieHeightValue = 0;
                        break;
                    case "buckling":
                        if (isChecked) bucklingValue = 1;
                        else bucklingValue = 0;
                        break;
                    case "pass":
                        if (isChecked) passValue = 1;
                        else passValue = 0;
                        break;
                    case "m":
                        if (isChecked) missFeedValue = 1;
                        else missFeedValue = 0;
                        break;
                    case "body":
                        if (isChecked) bodySensorValue = 1;
                        else bodySensorValue = 0;
                        break;
                    default:
                        break;
                }
            }
        };
    }
}