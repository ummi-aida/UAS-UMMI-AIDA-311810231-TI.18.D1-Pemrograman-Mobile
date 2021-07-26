package com.example.uas.validation;

import android.view.View;

import com.example.uas.utils.EmailValidation;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginValidation {
    TextInputLayout Email, Password;
    TextInputEditText passInput, EmailEdt;
    boolean validForm = false;
    boolean emailFieldTouched = false;
    boolean passFieldTouched = false;
    boolean anyTouched = false;
    EmailValidation emailValidation = new EmailValidation();

    public LoginValidation( TextInputLayout Email, TextInputLayout Password, TextInputEditText passInput,
                            TextInputEditText EmailEdt) {
        this.Email = Email;
        this.Password = Password;
        this.passInput = passInput;
        this.EmailEdt = EmailEdt;
    }

    private View.OnFocusChangeListener onBlurValidateListener(String touch) {
        return new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) switch (touch) {
                    case "emailFieldTouched":
                        emailFieldTouched = true;
                        break;
                    case "passFieldTouched":
                        passFieldTouched = true;
                        break;
                    default:
                        break;
                }
                if (!hasFocus) validate();
            }
        };
    };

    public void onFieldFocusChangeListener() {
        EmailEdt.setOnFocusChangeListener(onBlurValidateListener("emailFieldTouched"));
        passInput.setOnFocusChangeListener(onBlurValidateListener("passFieldTouched"));
    }

    public void onSubmit() {
        validate(1);
    }

    private void validate (int... submitted) {
        if (submitted != null && submitted.length >= 1) anyTouched = true;
        if (emailFieldTouched || anyTouched) {
            if (Email.getEditText().length() < 5 ) {
                Email.setErrorEnabled(true);
                Email.setError("Email harus 5 karakter atau lebih");
                validForm = false;
            } else if (!emailValidation.isValidEmail(Email.getEditText().getText())) {
                Email.setErrorEnabled(true);
                Email.setError("Email tidak valid");
                validForm = false;
            } else {
                Email.setErrorEnabled(false);
                Email.setError(null);
                validForm = true;
            }
        }

        if (passFieldTouched || anyTouched) {
            if (Password.getEditText().length() < 6) {
                Password.setErrorEnabled(true);
                Password.setError("Email minimal 6 karakter atau lebih");
                validForm = false;
            } else {
                Password.setErrorEnabled(false);
                Password.setError(null);
                validForm = true;
            }
        }
    }

    public boolean getValidForm() {
        return validForm;
    }
}
