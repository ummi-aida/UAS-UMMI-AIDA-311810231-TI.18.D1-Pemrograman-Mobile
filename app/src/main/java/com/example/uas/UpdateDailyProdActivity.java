package com.example.uas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.uas.dataBase.DatabaseQuery;
import com.example.uas.models.MaterialModel;
import com.example.uas.models.ProdModel;
import com.example.uas.models.SensorModel;
import com.example.uas.utils.Preferences;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UpdateDailyProdActivity extends AppCompatActivity {

    TextInputEditText cusUpdt_edt, modelUpdt_edt, mTargetUpdt_edt, runningTimeUpdt_edt, partCodeUpdt_edt,
            partNameUpdt_edt, processNameUpdt_edt, planQtyUpdt_edt, dateUpdt_edt;
    AutoCompleteTextView sensor_updt_dropdown, materialid_updt_dropdown;
    Button btn_updt_prod, btnUpdt_to_link_addSensor, btnUpdt_to_link_addMaterial;
    LinearLayout loading_updt_prod;

    String id, customer, model, minTarget, runningTime, partCode, partName, processName, planQty, date, idSensor, idUser, idMaterial;

    private final List<SensorModel> sensorList = new ArrayList<>();
    private final List<MaterialModel> materialList = new ArrayList<>();
    private final DatabaseQuery databaseQuery = new DatabaseQuery(this);
    int idSensorVal, idMaterialVal;
    String id_prod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_daily_prod);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.navigation_bar_color));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }

        ActionBar ab = getSupportActionBar();
        if (ab != null) ab.setTitle("Update Daily Production");

        cusUpdt_edt = findViewById(R.id.cusUpdt_edt);
        modelUpdt_edt = findViewById(R.id.modelUpdt_edt);
        mTargetUpdt_edt = findViewById(R.id.mTargetUpdt_edt);
        runningTimeUpdt_edt = findViewById(R.id.runningTimeUpdt_edt);
        partCodeUpdt_edt = findViewById(R.id.partCodeUpdt_edt);
        partNameUpdt_edt = findViewById(R.id.partNameUpdt_edt);
        processNameUpdt_edt = findViewById(R.id.processNameUpdt_edt);
        planQtyUpdt_edt = findViewById(R.id.planQtyUpdt_edt);
        dateUpdt_edt = findViewById(R.id.dateUpdt_edt);
        btnUpdt_to_link_addSensor = findViewById(R.id.btnUpdt_to_link_addSensor);
        btnUpdt_to_link_addMaterial = findViewById(R.id.btnUpdt_to_link_addMaterial);

        sensor_updt_dropdown = findViewById(R.id.sensor_updt_dropdown);
        materialid_updt_dropdown = findViewById(R.id.materialid_updt_dropdown);

        btn_updt_prod = findViewById(R.id.btn_updt_prod);
        loading_updt_prod = findViewById(R.id.loading_updt_prod);

        sensorList.addAll(databaseQuery.getAllSensorSetting());
        ArrayList<String> type = new ArrayList<>();
        for (int i = 0; i < sensorList.size(); i++){
            type.add(sensorList.get(i).getSettingName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_select_item, type);
        sensor_updt_dropdown.setAdapter(adapter);

        materialList.addAll(databaseQuery.getAllMaterial());
        ArrayList<String> typeMaterial = new ArrayList<>();
        for (int k = 0; k < materialList.size(); k++){
            typeMaterial.add(materialList.get(k).getTagNumber());
        }
        ArrayAdapter<String> adapterListMaterial = new ArrayAdapter<>(this, R.layout.list_select_item, typeMaterial);
        materialid_updt_dropdown.setAdapter(adapterListMaterial);

        getAndSetIntentData();

        materialid_updt_dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        sensor_updt_dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        dateUpdt_edt.setOnClickListener(this::datePicker);
        btnUpdt_to_link_addSensor.setOnClickListener(this::onBtnLinkAddSensor);
        btnUpdt_to_link_addMaterial.setOnClickListener(this::onBtnLinkAddMaterial);
        btn_updt_prod.setOnClickListener(this::handleSubmit);

    }

    private void handleSubmit(View v) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading_updt_prod.setVisibility(View.VISIBLE);
            }
        }, 250);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading_updt_prod.setVisibility(View.GONE);
                String customerVal = cusUpdt_edt.getText().toString();
                String modelVal = modelUpdt_edt.getText().toString();
                int minTargetVal = Integer.parseInt(mTargetUpdt_edt.getText().toString());
                int runningTimeVal = Integer.parseInt(runningTimeUpdt_edt.getText().toString());
                String partCodeVal = partCodeUpdt_edt.getText().toString();
                String partNameVal = partNameUpdt_edt.getText().toString();
                String processNameVal = processNameUpdt_edt.getText().toString();
                int planQtyVal = Integer.parseInt(planQtyUpdt_edt.getText().toString());
                String dateVal = dateUpdt_edt.getText().toString();
                int idUserVal = Integer.parseInt(Preferences.getRegisteredId(getBaseContext()));

                ProdModel prodModel = new ProdModel(Integer.parseInt(id_prod), customerVal, modelVal, minTargetVal, runningTimeVal, partCodeVal,
                        partNameVal, processNameVal, planQtyVal, dateVal, idUserVal, idSensorVal, idMaterialVal);

                long result = databaseQuery.updateDailyProd(prodModel);
                if (result > 0) {
                    SweetAlertDialog alertDialog = new SweetAlertDialog(UpdateDailyProdActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                    alertDialog.setTitleText("Success")
                            .setContentText("Success, update daily production")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent i = new Intent(UpdateDailyProdActivity.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            })
                            .show();
                    Button btn = (Button) alertDialog.findViewById(R.id.confirm_button);
                    btn.setBackgroundTintList(null);
                } else {
                    SweetAlertDialog alertDialog = new SweetAlertDialog(UpdateDailyProdActivity.this, SweetAlertDialog.ERROR_TYPE);
                    alertDialog.setTitleText("Error")
                            .setContentText("Failed, update daily production")
                            .show();
                    Button btn = (Button) alertDialog.findViewById(R.id.confirm_button);
                    btn.setBackgroundTintList(null);
                }
            }
        }, 2000);
    }

    private void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("customer") &&
                getIntent().hasExtra("model") && getIntent().hasExtra("minTarget") &&
                getIntent().hasExtra("runningTime") && getIntent().hasExtra("partCode") &&
                getIntent().hasExtra("partName") && getIntent().hasExtra("processName") &&
                getIntent().hasExtra("planQty") && getIntent().hasExtra("date") &&
                getIntent().hasExtra("idUser") && getIntent().hasExtra("idMaterial") &&
                getIntent().hasExtra("idSensor")
        ) {
            id_prod = getIntent().getStringExtra("id");
            id = getIntent().getStringExtra("id");
            customer = getIntent().getStringExtra("customer");
            model = getIntent().getStringExtra("model");
            minTarget = getIntent().getStringExtra("minTarget");
            runningTime = getIntent().getStringExtra("runningTime");
            partCode = getIntent().getStringExtra("partCode");
            partName = getIntent().getStringExtra("partName");
            processName = getIntent().getStringExtra("processName");
            planQty = getIntent().getStringExtra("planQty");
            date = getIntent().getStringExtra("date");
            idSensor = getIntent().getStringExtra("idSensor");
            idUser = getIntent().getStringExtra("idUser");
            idMaterial = getIntent().getStringExtra("idMaterial");

            cusUpdt_edt.setText(customer);
            modelUpdt_edt.setText(model);
            mTargetUpdt_edt.setText(minTarget);
            runningTimeUpdt_edt.setText(runningTime);
            partCodeUpdt_edt.setText(partCode);
            partNameUpdt_edt.setText(partName);
            processNameUpdt_edt.setText(processName);
            planQtyUpdt_edt.setText(planQty);
            dateUpdt_edt.setText(date);
        } else {
            SweetAlertDialog alertDialog = new SweetAlertDialog(UpdateDailyProdActivity.this, SweetAlertDialog.ERROR_TYPE);
            alertDialog.setTitleText("Error")
                    .setContentText("Failed, data not found")
                    .show();
            Button btn = (Button) alertDialog.findViewById(R.id.confirm_button);
            btn.setBackgroundTintList(null);
        }
    }

    private void datePicker(View v) {
        MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build();

        datePicker.show(getSupportFragmentManager(), "Tag");
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                dateUpdt_edt.setText(datePicker.getHeaderText());
            }
        });
    }

    private void onBtnLinkAddSensor(View v) {
        Intent i = new Intent(UpdateDailyProdActivity.this, AddSensorActivity.class);
        startActivity(i);
    }

    private void onBtnLinkAddMaterial(View v) {
        Intent i = new Intent(UpdateDailyProdActivity.this, AddMaterialActivity.class);
        startActivity(i);
    }
}