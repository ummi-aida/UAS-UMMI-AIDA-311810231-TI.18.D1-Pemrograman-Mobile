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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uas.dataBase.DatabaseQuery;
import com.example.uas.models.UserModel;
import com.example.uas.validation.RegisterValidation;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignUpActivity extends AppCompatActivity {

    Button ToLogin, SignUp;
    TextInputEditText passET, fullNameET, emailET;
    TextInputLayout FnameInput, EmailInput, PassInput;
    LinearLayout loading_sign_up;
    RegisterValidation registerValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(getSupportActionBar()).hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.navigation_bar_color));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }

        ToLogin = findViewById(R.id.btn_link_login);
        SignUp = findViewById(R.id.btn_signup);
        passET = findViewById(R.id.pass_input);
        fullNameET = findViewById(R.id.fullname_edt);
        emailET = findViewById(R.id.email_input_edt);
        FnameInput = findViewById(R.id.fullname_input);
        EmailInput = findViewById(R.id.email_input);
        PassInput = findViewById(R.id.password_input);
        loading_sign_up = findViewById(R.id.loading_sign_up);

        registerValidation = new RegisterValidation(
                new TextInputEditText[]{fullNameET, emailET, passET},
                new TextInputLayout[]{FnameInput, EmailInput, PassInput});
        registerValidation.onFieldFocusChangeListener();

        passET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_DONE) {
                    passET.clearFocus();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return false;
            }
        });

        SignUp.setOnClickListener(this::handleSubmit);

        ToLogin.setBackgroundResource(0);
        ToLogin.setOnClickListener(this::toLogin);
    }

    private void handleSubmit(View v) {
        registerValidation.onSubmit();
        if (registerValidation.getValidForm()) {
            String fNameString = fullNameET.getText().toString();
            String emailString = emailET.getText().toString();
            String passwordString = passET.getText().toString();
            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loading_sign_up.setVisibility(View.VISIBLE);
                }
            }, 250);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int role = 0;
                    UserModel userModel = new UserModel(-1, fNameString, emailString, passwordString, role);

                    DatabaseQuery databaseQuery = new DatabaseQuery(getApplicationContext());
                    long result = databaseQuery.insertUser(userModel);
                    if (result > 0) {
                        userModel.setId(result);
                        SweetAlertDialog alertDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                        alertDialog.setTitleText("Success")
                                .setContentText("Success register, please login again")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                                        startActivity(i);
                                        alertDialog.dismissWithAnimation();
                                        finish();
                                    }
                                })
                                .show();
                        Button btn = (Button) alertDialog.findViewById(R.id.confirm_button);
                        btn.setBackgroundTintList(null);
                    } else {
                        SweetAlertDialog alertDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.ERROR_TYPE);
                        alertDialog.setTitleText("Error")
                                .setContentText("Failed, user is already registered")
                                .show();
                        Button btn = (Button) alertDialog.findViewById(R.id.confirm_button);
                        btn.setBackgroundTintList(null);
                    }
                    loading_sign_up.setVisibility(View.GONE);
                }
            }, 2000);
        }
    }

    private void toLogin(View v) {
        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}