package com.example.uas.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uas.DetailDailyProdActivity;
import com.example.uas.R;
import com.example.uas.UpdateDailyProdActivity;
import com.example.uas.dataBase.DatabaseQuery;
import com.example.uas.models.ProdDetailModel;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList prod_id, customer, model, min_target, running_time, part_code, part_name, process_name,
        plan_qty, date, id_user, id_material, id_sensor;

    public HomeAdapter(Activity activity, Context context,ArrayList prod_id, ArrayList customer, ArrayList model, ArrayList min_target, ArrayList running_time, ArrayList part_code, ArrayList part_name, ArrayList process_name,
                ArrayList plan_qty, ArrayList date, ArrayList id_user, ArrayList id_material, ArrayList id_sensor) {
        this.activity = activity;
        this.context = context;
        this.prod_id = prod_id;
        this.customer = customer;
        this.model = model;
        this.min_target = min_target;
        this.running_time = running_time;
        this.part_code = part_code;
        this.part_name = part_name;
        this.process_name = process_name;
        this.plan_qty = plan_qty;
        this.date = date;
        this.id_user = id_user;
        this.id_material = id_material;
        this.id_sensor = id_sensor;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.prodIdTxt.setText(String.valueOf(prod_id.get(position)));
        holder.modelTxt.setText(String.valueOf(model.get(position)));
        holder.customerTxt.setText(String.valueOf(customer.get(position)));
        holder.partCodeTxt.setText(String.valueOf(date.get(position)));

        holder.see_detail_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailDailyProdActivity.class);
                intent.putExtra("id_prod", String.valueOf(prod_id.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });

        holder.btn_updt_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateDailyProdActivity.class);
                intent.putExtra("id", String.valueOf(prod_id.get(position)));
                intent.putExtra("customer", String.valueOf(customer.get(position)));
                intent.putExtra("model", String.valueOf(model.get(position)));
                intent.putExtra("minTarget", String.valueOf(min_target.get(position)));
                intent.putExtra("runningTime", String.valueOf(running_time.get(position)));
                intent.putExtra("partCode", String.valueOf(part_code.get(position)));
                intent.putExtra("partName", String.valueOf(part_name.get(position)));
                intent.putExtra("processName", String.valueOf(process_name.get(position)));
                intent.putExtra("planQty", String.valueOf(plan_qty.get(position)));
                intent.putExtra("date", String.valueOf(date.get(position)));
                intent.putExtra("idUser", String.valueOf(id_user.get(position)));
                intent.putExtra("idMaterial", String.valueOf(id_material.get(position)));
                intent.putExtra("idSensor", String.valueOf(id_sensor.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });

        holder.btn_del_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog confirmDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                confirmDialog.setTitleText("Are you sure?")
                        .setContentText("You won't be able to recover this data!")
                        .setConfirmText("Delete!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                DatabaseQuery databaseQuery = new DatabaseQuery(context);
                                long result = databaseQuery.deleteDailyProdById(Long.parseLong(String.valueOf(prod_id.get(position))));
                                if (result > 0) {
                                    sDialog
                                            .setTitleText("Deleted!")
                                            .setContentText("Daily production file has been deleted!")
                                            .setConfirmText("OK")
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    Button btn = (Button) sDialog.findViewById(R.id.confirm_button);
                                    btn.setBackgroundTintList(null);
                                } else {
                                    sDialog
                                            .setTitleText("Error")
                                            .setContentText("Error, delete daily production")
                                            .setConfirmText("OK")
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    Button btn = (Button) sDialog.findViewById(R.id.confirm_button);
                                    btn.setBackgroundTintList(null);
                                }
                            }
                        })
                        .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
                Button btnConfirm = (Button) confirmDialog.findViewById(R.id.confirm_button);
                Button cancelBtnConfitm = (Button) confirmDialog.findViewById(R.id.cancel_button);
                cancelBtnConfitm.setBackgroundTintList(null);
                btnConfirm.setBackgroundTintList(null);
            }
        });

    }

    @Override
    public int getItemCount() {
        return prod_id.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder  {

        TextView prodIdTxt, modelTxt, customerTxt, partCodeTxt;
        Button btn_updt_link, btn_del_link, see_detail_link;
        LinearLayout homeMainLayout;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            prodIdTxt = itemView.findViewById(R.id.prod_id_txt);
            modelTxt = itemView.findViewById(R.id.model_txt);
            customerTxt = itemView.findViewById(R.id.customer_txt);
            partCodeTxt = itemView.findViewById(R.id.part_code_txt);
            homeMainLayout = itemView.findViewById(R.id.home_main_layout);
            see_detail_link = itemView.findViewById(R.id.see_detail_link);
            btn_updt_link = itemView.findViewById(R.id.btn_updt_link);
            btn_del_link = itemView.findViewById(R.id.btn_del_link);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            homeMainLayout.setAnimation(translate_anim);
        }
    }
}
