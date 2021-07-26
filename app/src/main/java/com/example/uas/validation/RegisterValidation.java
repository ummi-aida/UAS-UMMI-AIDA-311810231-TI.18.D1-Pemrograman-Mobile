package com.example.uas.validation;

import android.view.View;

import com.example.uas.utils.EmailValidation;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterValidation {
    TextInputEditText passET, fullNameET, emailET;
    TextInputLayout FnameInput, EmailInput, PassInput;
    private boolean passEdtTouched = false, fNameEdtTouched = false, emailEdtTouched = false;
    private boolean validForm = false;
    private boolean anyTouched = false;
    EmailValidation emailValidation = new EmailValidation();

    public RegisterValidation(TextInputEditText[] editTexts, TextInputLayout[] inputLayouts) {
        this.fullNameET = editTexts[0];
        this.emailET = editTexts[1];
        this.passET = editTexts[2];
        this.FnameInput = inputLayouts[0];
        this.EmailInput = inputLayouts[1];
        this.PassInput = inputLayouts[2];
    }

    private View.OnFocusChangeListener onBlurValidateListener(String touch) {
        return new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) switch (touch) {
                    case "passEdtTouched":
                        passEdtTouched = true;
                        break;
                    case "fNameEdtTouched":
                        fNameEdtTouched = true;
                        break;
                    case "emailEdtTouched":
                        emailEdtTouched = true;
                        break;
                    default:
                        break;
                }
                if (!hasFocus) validate();
            }
        };
    };

    public void onFieldFocusChangeListener() {
        fullNameET.setOnFocusChangeListener(onBlurValidateListener("fNameEdtTouched"));
        emailET.setOnFocusChangeListener(onBlurValidateListener("emailEdtTouched"));
        passET.setOnFocusChangeListener(onBlurValidateListener("passEdtTouched"));
    }

    public void onSubmit() {
        validate(1);
    }

    private void validate(int... submitted) {
        if (submitted != null && submitted.length >= 1) anyTouched = true;
        if (fNameEdtTouched || anyTouched) {
            if (FnameInput.getEditText().length() < 5) {
                FnameInput.setErrorEnabled(true);
                FnameInput.setError("Nama minimal 5 karakter");
                validForm = false;
            } else {
                FnameInput.setErrorEnabled(false);
                FnameInput.setError(null);
                validForm = true;
            }
        }

        if (emailEdtTouched || anyTouched) {
            if (EmailInput.getEditText().length() < 5 ) {
                EmailInput.setErrorEnabled(true);
                EmailInput.setError("Email harus 5 karakter atau lebih");
                validForm = false;
            } else if (!emailValidation.isValidEmail(EmailInput.getEditText().getText())) {
                EmailInput.setErrorEnabled(true);
                EmailInput.setError("Email tidak valid");
                validForm = false;
            } else {
                EmailInput.setErrorEnabled(false);
                EmailInput.setError(null);
                validForm = true;
            }
        }

        if (passEdtTouched || anyTouched) {
            if (PassInput.getEditText().length() < 6) {
                PassInput.setErrorEnabled(true);
                PassInput.setError("Email minimal 6 karakter atau lebih");
                validForm = false;
            } else {
                PassInput.setErrorEnabled(false);
                PassInput.setError(null);
                validForm = true;
            }
        }
    }

    public boolean getValidForm() {
        return validForm;
    }

}
