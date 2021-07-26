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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uas.dataBase.DatabaseQuery;
import com.example.uas.models.MaterialModel;
import com.example.uas.models.ProdModel;
import com.example.uas.models.SensorModel;
import com.example.uas.utils.Preferences;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddDailyProductionActivity extends AppCompatActivity {

    TextInputLayout dateContainer;
    TextInputEditText dateEdt, planQtyEdt, customerEdt, modelEdt, mTargetEdt, runningTimeEdt, partCodeEdt, partNameEdt,
        processNameEdt;
    Button btnAddProd, btn_to_link_addSensor, btn_to_link_addMaterial;
    LinearLayout loadingProd;
    private List<SensorModel> sensorList = new ArrayList<>();
    private List<MaterialModel> materialList = new ArrayList<>();
    private DatabaseQuery databaseQuery = new DatabaseQuery(this);
    int idSensorVal, idMaterialVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_daily_production);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.navigation_bar_color));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }

        ActionBar ab = getSupportActionBar();
        if (ab != null) ab.setTitle("Add Daily Production");

        planQtyEdt = findViewById(R.id.planQty_edt);
        planQtyEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    planQtyEdt.clearFocus();
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return false;
            }
        });

        sensorList.addAll(databaseQuery.getAllSensorSetting());
        ArrayList<String> type = new ArrayList<>();
        for (int i = 0; i < sensorList.size(); i++){
            type.add(sensorList.get(i).getSettingName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_select_item, type);
        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.filled_exposed_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter);

        materialList.addAll(databaseQuery.getAllMaterial());
        ArrayList<String> typeMaterial = new ArrayList<>();
        for (int k = 0; k < materialList.size(); k++) {
            typeMaterial.add(materialList.get(k).getTagNumber());
        }
        ArrayAdapter<String> adapterListMaterial = new ArrayAdapter<>(this, R.layout.list_select_item, typeMaterial);
        AutoCompleteTextView materialId = findViewById(R.id.materialid_dropdown);
        materialId.setAdapter(adapterListMaterial);

        materialId.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = parent.getAdapter().getItem(position).toString();
                for (int j = 0; j < materialList.size(); j++) {
                    if (selectedValue.equals(materialList.get(j).getTagNumber())) {
                        idMaterialVal = (int) materialList.get(j).getId();
                    }
                }
            }
        });

        editTextFilledExposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedValue = parent.getAdapter().getItem(position).toString();
                for (int j = 0; j < sensorList.size(); j++) {
                    if (selectedValue.equals(sensorList.get(j).getSettingName())) {
                        idSensorVal = (int) sensorList.get(j).getId();
                    }
                }
            }
        });

        dateContainer = findViewById(R.id.date_container);
        dateEdt = findViewById(R.id.date_edt);
        customerEdt = findViewById(R.id.customer_edt);
        modelEdt = findViewById(R.id.model_edt);
        mTargetEdt = findViewById(R.id.mTarget_edt);
        runningTimeEdt = findViewById(R.id.runningTime_edt);
        partCodeEdt = findViewById(R.id.partCode_edt);
        partNameEdt = findViewById(R.id.partName_edt);
        processNameEdt = findViewById(R.id.processName_edt);
        dateEdt = findViewById(R.id.date_edt);
        btnAddProd = findViewById(R.id.btn_add_prod);
        loadingProd = findViewById(R.id.loading_prod);
        btn_to_link_addSensor = findViewById(R.id.btn_to_link_addSensor);
        btn_to_link_addMaterial = findViewById(R.id.btn_to_link_addMaterial);

        dateEdt.setOnClickListener(this::datePicker);
        btnAddProd.setOnClickListener(this::handleSubmit);
        btn_to_link_addSensor.setOnClickListener(this::onBtnLinkAddSensor);
        btn_to_link_addMaterial.setOnClickListener(this::onBtnLinkAddMaterial);
    }

    private void datePicker(View v) {
        MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build();

        datePicker.show(getSupportFragmentManager(), "Tag");
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                dateEdt.setText(datePicker.getHeaderText());
            }
        });
    }

    private void handleSubmit(View v) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingProd.setVisibility(View.VISIBLE);
            }
        }, 250);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingProd.setVisibility(View.GONE);
                String customerVal = customerEdt.getText().toString();
                String modelVal = modelEdt.getText().toString();
                int minTargetVal = Integer.parseInt(mTargetEdt.getText().toString());
                int runningTimeVal = Integer.parseInt(runningTimeEdt.getText().toString());
                String partCodeVal = partCodeEdt.getText().toString();
                String partNameVal = partNameEdt.getText().toString();
                String processNameVal = processNameEdt.getText().toString();
                int planQtyVal = Integer.parseInt(planQtyEdt.getText().toString());
                String dateVal = dateEdt.getText().toString();
                int idUserVal = Integer.parseInt(Preferences.getRegisteredId(getBaseContext()));

                ProdModel prodModel = new ProdModel(-1, customerVal, modelVal, minTargetVal, runningTimeVal, partCodeVal,
                        partNameVal, processNameVal, planQtyVal, dateVal, idUserVal, idSensorVal, idMaterialVal);

                long result = databaseQuery.insertDailyProd(prodModel);
                if (result > 0) {
                    prodModel.setId(result);
                    SweetAlertDialog alertDialog = new SweetAlertDialog(AddDailyProductionActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                    alertDialog.setTitleText("Success")
                            .setContentText("Success, add daily production")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent i = new Intent(AddDailyProductionActivity.this, MainActivity.class);
                                    startActivity(i);
                                    alertDialog.dismissWithAnimation();
                                    finish();
                                }
                            })
                            .show();
                    Button btn = (Button) alertDialog.findViewById(R.id.confirm_button);
                    btn.setBackgroundTintList(null);
                } else {
                    SweetAlertDialog alertDialog = new SweetAlertDialog(AddDailyProductionActivity.this, SweetAlertDialog.ERROR_TYPE);
                    alertDialog.setTitleText("Error")
                            .setContentText("Failed, add daily production")
                            .show();
                    Button btn = (Button) alertDialog.findViewById(R.id.confirm_button);
                    btn.setBackgroundTintList(null);
                }
            }
        }, 2000);
    }

    private void onBtnLinkAddSensor(View v) {
        Intent i = new Intent(AddDailyProductionActivity.this, AddSensorActivity.class);
        startActivity(i);
    }

    private void onBtnLinkAddMaterial(View v) {
        Intent i = new Intent(AddDailyProductionActivity.this, AddMaterialActivity.class);
        startActivity(i);
    }

}