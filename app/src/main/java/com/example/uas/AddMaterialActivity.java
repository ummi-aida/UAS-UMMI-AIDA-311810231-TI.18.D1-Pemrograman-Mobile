package com.example.uas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uas.dataBase.DatabaseQuery;
import com.example.uas.models.MaterialModel;
import com.example.uas.utils.Preferences;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddMaterialActivity extends AppCompatActivity {

    TextInputEditText weight_edt, tagNumb_edt, start_edt, finish_edt, totalTime_edt, result_edt;
    Button btn_add_material;
    LinearLayout loading_material;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.navigation_bar_color));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }

        ActionBar ab = getSupportActionBar();
        if (ab != null) ab.setTitle("Add Material");

        btn_add_material = findViewById(R.id.btn_add_material);
        loading_material = findViewById(R.id.loading_material);
        weight_edt = findViewById(R.id.weight_edt);
        tagNumb_edt = findViewById(R.id.tagNumb_edt);
        start_edt = findViewById(R.id.start_edt);
        finish_edt = findViewById(R.id.finish_edt);
        totalTime_edt = findViewById(R.id.totalTime_edt);
        result_edt = findViewById(R.id.result_edt);

        tagNumb_edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    tagNumb_edt.clearFocus();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return false;
            }
        });

        result_edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    result_edt.clearFocus();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return false;
            }
        });

        start_edt.setOnClickListener(this::getStartTime);
        finish_edt.setOnClickListener(this::getFinishTime);
        btn_add_material.setOnClickListener(this::handleSubmit);
    }

    private void getStartTime(View v) {
        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build();
        timePicker.show(getSupportFragmentManager(), "tag");
        timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hour = String.valueOf(timePicker.getHour()), minute = String.valueOf(timePicker.getMinute());
                if (timePicker.getHour() < 10) hour = "0" + timePicker.getHour();
                if (timePicker.getMinute() < 10) minute = "0" + timePicker.getMinute();
                String time = hour + " : " + minute;
                start_edt.setText(time);
            }
        });
    }

    private void getFinishTime(View v) {
        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build();
        timePicker.show(getSupportFragmentManager(), "tag");
        timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hour = String.valueOf(timePicker.getHour()), minute = String.valueOf(timePicker.getMinute());
                if (timePicker.getHour() < 10) hour = "0" + timePicker.getHour();
                if (timePicker.getMinute() < 10) minute = "0" + timePicker.getMinute();
                String time = hour + " : " + minute;
                finish_edt.setText(time);
            }
        });
    }

    private void handleSubmit(View v) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading_material.setVisibility(View.VISIBLE);
            }
        }, 250);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int weightVal = Integer.parseInt(weight_edt.getText().toString());
                String tagNumbVal = tagNumb_edt.getText().toString();
                String startVal = start_edt.getText().toString();
                String finishVal = finish_edt.getText().toString();
                int totalTimeVal = Integer.parseInt(totalTime_edt.getText().toString());
                int resultVal = Integer.parseInt(result_edt.getText().toString());

                MaterialModel materialModel = new MaterialModel(1, weightVal, tagNumbVal, startVal, finishVal, totalTimeVal, resultVal);
                DatabaseQuery databaseQuery = new DatabaseQuery(AddMaterialActivity.this);
                long result = databaseQuery.insertMaterial(materialModel);

                if (result > 0) {
                    SweetAlertDialog alertDialog = new SweetAlertDialog(AddMaterialActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                    alertDialog.setTitleText("Success")
                            .setContentText("Success, add material")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent i = new Intent(AddMaterialActivity.this, MainActivity.class);
                                    startActivity(i);
                                    alertDialog.dismissWithAnimation();
                                    finish();
                                }
                            })
                            .show();
                    Button btn = (Button) alertDialog.findViewById(R.id.confirm_button);
                    btn.setBackgroundTintList(null);
                } else {
                    SweetAlertDialog alertDialog = new SweetAlertDialog(AddMaterialActivity.this, SweetAlertDialog.ERROR_TYPE);
                    alertDialog.setTitleText("Error")
                            .setContentText("Failed, add material")
                            .show();
                    Button btn = (Button) alertDialog.findViewById(R.id.confirm_button);
                    btn.setBackgroundTintList(null);
                }

                loading_material.setVisibility(View.GONE);
            }
        }, 2000);
    }

}