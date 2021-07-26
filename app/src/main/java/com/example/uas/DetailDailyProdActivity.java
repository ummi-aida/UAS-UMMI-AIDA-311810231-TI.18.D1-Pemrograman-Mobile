package com.example.uas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.uas.dataBase.DatabaseQuery;
import com.example.uas.models.ProdDetailModel;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DetailDailyProdActivity extends AppCompatActivity {

    TextView date_detail, id_prod_detail, cus_detail, model_detail, mTarget_detail, runnTime_detail, part_code_detail, part_name_detail, process_name_detail, plan_qty_detail,
            weight_detail, tag_number_detail, start_detail, finish_detail, total_time_detail, result_detail,
            operator_detail, id_operator, email_operator, setting_sensor_detail;
    CheckBox check_die, check_buckling, check_pass_sensor, check_miss_feed, check_body_sensor;
    Button btn_print, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_daily_prod);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.navigation_bar_color));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }

        ActionBar ab = getSupportActionBar();
        if (ab != null) ab.setTitle("Detail Production");

        date_detail = findViewById(R.id.date_detail);
        id_prod_detail = findViewById(R.id.id_prod_detail);
        cus_detail = findViewById(R.id.cus_detail);
        model_detail = findViewById(R.id.model_detail);
        mTarget_detail = findViewById(R.id.mTarget_detail);
        runnTime_detail = findViewById(R.id.runnTime_detail);
        part_code_detail = findViewById(R.id.part_code_detail);
        part_name_detail = findViewById(R.id.part_name_detail);
        process_name_detail = findViewById(R.id.process_name_detail);
        plan_qty_detail = findViewById(R.id.plan_qty_detail);
        weight_detail = findViewById(R.id.weight_detail);
        tag_number_detail = findViewById(R.id.tag_number_detail);
        start_detail = findViewById(R.id.start_detail);
        finish_detail = findViewById(R.id.finish_detail);
        total_time_detail = findViewById(R.id.total_time_detail);
        result_detail = findViewById(R.id.result_detail);
        operator_detail = findViewById(R.id.operator_detail);
        id_operator = findViewById(R.id.id_operator);
        email_operator = findViewById(R.id.email_operator);
        setting_sensor_detail = findViewById(R.id.setting_sensor_detail);

        check_die = findViewById(R.id.check_die);
        check_buckling = findViewById(R.id.check_buckling);
        check_pass_sensor = findViewById(R.id.check_pass_sensor);
        check_miss_feed = findViewById(R.id.check_miss_feed);
        check_body_sensor = findViewById(R.id.check_body_sensor);

        btn_print = findViewById(R.id.btn_print);
        btn_back = findViewById(R.id.btn_back);

        getAndSetDataProduction();

        btn_print.setOnClickListener(this::onBtnPrintClick);
        btn_back.setOnClickListener(this::onBackBtnClick);
    }

    private void getAndSetDataProduction() {
        if (getIntent().hasExtra("id_prod")) {
            DatabaseQuery databaseQuery = new DatabaseQuery(DetailDailyProdActivity.this);
            String id_prod = getIntent().getStringExtra("id_prod");
            ProdDetailModel prodDetailModel = databaseQuery.getProdById(Long.parseLong(id_prod));

            if (prodDetailModel != null) {
                String minTarget = prodDetailModel.getMinTarget() + " PCS";
                String runningTime = prodDetailModel.getRunningTime() + " Hours";
                String planQTY = prodDetailModel.getPlanQty() + " PCS";
                String TotalTime = prodDetailModel.getTotalTime() + " Hours";
                String Result = prodDetailModel.getResult() + " PCS";
                String Weight = prodDetailModel.getWeight() + " KG";

                date_detail.setText(prodDetailModel.getDate());
                id_prod_detail.setText(getIntent().getStringExtra("id_prod"));
                cus_detail.setText(prodDetailModel.getCustomer());
                model_detail.setText(prodDetailModel.getModel());
                mTarget_detail.setText(minTarget);
                runnTime_detail.setText(runningTime);
                part_code_detail.setText(prodDetailModel.getPartCode());
                part_name_detail.setText(prodDetailModel.getPartName());
                process_name_detail.setText(prodDetailModel.getProcessName());
                plan_qty_detail.setText(planQTY);
                weight_detail.setText(Weight);
                tag_number_detail.setText(prodDetailModel.getTagNumber());
                start_detail.setText(prodDetailModel.getStart());
                finish_detail.setText(prodDetailModel.getFinish());
                total_time_detail.setText(TotalTime);
                result_detail.setText(Result);
                operator_detail.setText(prodDetailModel.getFName());
                id_operator.setText(prodDetailModel.getIdUser());
                email_operator.setText(prodDetailModel.getEmail());
                setting_sensor_detail.setText(prodDetailModel.getSettingName());

                if (prodDetailModel.getDieHight() == 1) check_die.setChecked(true);
                if (prodDetailModel.getBuckling() == 1) check_buckling.setChecked(true);
                if (prodDetailModel.getPass() == 1) check_pass_sensor.setChecked(true);
                if (prodDetailModel.getMissFeed() == 1) check_miss_feed.setChecked(true);
                if (prodDetailModel.getBodySensor() == 1) check_body_sensor.setChecked(true);
            } else {
                SweetAlertDialog alertDialog = new SweetAlertDialog(DetailDailyProdActivity.this, SweetAlertDialog.ERROR_TYPE);
                alertDialog.setTitleText("Error")
                        .setContentText("Data is not found")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent i = new Intent(DetailDailyProdActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        })
                        .show();
                Button btn = (Button) alertDialog.findViewById(R.id.confirm_button);
                btn.setBackgroundTintList(null);
            }
        } else {
            SweetAlertDialog alertDialog = new SweetAlertDialog(DetailDailyProdActivity.this, SweetAlertDialog.ERROR_TYPE);
            alertDialog.setTitleText("Error")
                    .setContentText("Data is not found")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent i = new Intent(DetailDailyProdActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    })
                    .show();
            Button btn = (Button) alertDialog.findViewById(R.id.confirm_button);
            btn.setBackgroundTintList(null);
        }
    }

    private void onBtnPrintClick(View v) {
        SweetAlertDialog alertDialog = new SweetAlertDialog(DetailDailyProdActivity.this, SweetAlertDialog.WARNING_TYPE);
        alertDialog.setTitleText("Warning")
                .setContentText("Sorry, this feature is still under development")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        alertDialog.dismissWithAnimation();
                    }
                })
                .show();
        Button btn = (Button) alertDialog.findViewById(R.id.confirm_button);
        btn.setBackgroundTintList(null);
    }

    private void onBackBtnClick(View v) {
        finish();
    }

}