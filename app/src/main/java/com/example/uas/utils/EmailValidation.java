package com.example.uas.utils;

import android.text.Editable;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class EmailValidation {
    public boolean isValidEmail(Editable email) {
        String expression = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email.toString());
        return matcher.matches();
    }
}
