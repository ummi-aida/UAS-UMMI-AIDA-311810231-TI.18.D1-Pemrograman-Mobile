package com.example.uas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uas.dataBase.DatabaseQuery;
import com.example.uas.models.UserModel;
import com.example.uas.utils.Preferences;
import com.example.uas.validation.LoginValidation;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    Button ToSignUp, BtnLogin;
    TextInputLayout Email, Password;
    TextInputEditText passInput, EmailEdt;
    LinearLayout loading_login;

    LoginValidation loginValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.navigation_bar_color));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }

        ToSignUp = findViewById(R.id.btn_link_signup);
        BtnLogin = findViewById(R.id.btn_login);
        Email = findViewById(R.id.email);
        Password =findViewById(R.id.password);
        passInput = findViewById(R.id.pass);
        EmailEdt = findViewById(R.id.email_edt);
        loading_login = findViewById(R.id.loading_login);

        loginValidation = new LoginValidation(Email, Password, passInput, EmailEdt);
        loginValidation.onFieldFocusChangeListener();

        passInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Password.clearFocus();
                    EmailEdt.clearFocus();
                    Email.clearFocus();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return false;
            }
        });

        BtnLogin.setOnClickListener(this::onLogin);

        ToSignUp.setBackgroundResource(0);
        ToSignUp.setOnClickListener(this::toSignUp);
    }

    private void onLogin(View v) {
        loginValidation.onSubmit();
        boolean isValidForm = loginValidation.getValidForm();
        if (isValidForm) {
            String email = EmailEdt.getText().toString();
            String pass = passInput.getText().toString();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loading_login.setVisibility(View.VISIBLE);
                }
            }, 250);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DatabaseQuery databaseQuery = new DatabaseQuery(getApplicationContext());
                    UserModel userModel = databaseQuery.checkUserLogin(email, pass);

                    if (userModel != null) {
                        Preferences.setRegisteredId(getBaseContext(), String.valueOf(userModel.getId()));
                        Preferences.setRegisteredUser(getBaseContext(), String.valueOf(userModel.getEmail()));
                        Preferences.setRegisteredNameUser(getBaseContext(), String.valueOf(userModel.getFName()));
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        SweetAlertDialog alertDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
                        alertDialog.setTitleText("Error")
                                .setContentText("Failed login, user or password is wrong")
                                .show();
                        Button btn = (Button) alertDialog.findViewById(R.id.confirm_button);
                        btn.setBackgroundTintList(null);
                    }
                    loading_login.setVisibility(View.GONE);
                }
            }, 2000);
        }
    }

    private void toSignUp(View v) {
        Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(i);
        finish();
    }
}