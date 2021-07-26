package com.example.uas.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uas.AddDailyProductionActivity;
import com.example.uas.QrcodeScannActivity;
import com.example.uas.R;
import com.example.uas.adapter.HomeAdapter;
import com.example.uas.dataBase.DatabaseQuery;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    View RootView;
    RecyclerView recyclerView;
    FloatingActionButton add_button, fab1, fab2;
    ImageView empty_imageview;
    TextView no_data;
    HomeAdapter homeAdapter;
    boolean flag = true;

    DatabaseQuery databaseQuery;
    private ArrayList<String> prod_id, customer, model, min_target, running_time, part_code, part_name, process_name,
            plan_qty, date, id_user, id_material, id_sensor;

    public static HomeFragment newInstance(String param1, String param2) {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RootView = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Daily Production");

        recyclerView = RootView.findViewById(R.id.rvHome);
        empty_imageview = RootView.findViewById(R.id.empty_imageview);
        no_data = RootView.findViewById(R.id.no_data);
        add_button = RootView.findViewById(R.id.add_production);
        fab1 = RootView.findViewById(R.id.fab1);
        fab2 = RootView.findViewById(R.id.fab2);

        databaseQuery = new DatabaseQuery(getContext());
        prod_id = new ArrayList<>();
        customer = new ArrayList<>();
        model = new ArrayList<>();
        min_target = new ArrayList<>();
        running_time = new ArrayList<>();
        part_code = new ArrayList<>();
        part_name = new ArrayList<>();
        process_name = new ArrayList<>();
        plan_qty = new ArrayList<>();
        date = new ArrayList<>();
        id_user = new ArrayList<>();
        id_material = new ArrayList<>();
        id_sensor = new ArrayList<>();

        storeDataInArrays();

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    fab1.show();
                    fab2.show();
                    fab1.animate().translationY(-(fab2.getCustomSize() + add_button.getCustomSize() + 30));
                    fab2.animate().translationY(-(add_button.getCustomSize() + 20));

                    add_button.setImageResource(R.drawable.ic_close);
                    flag = false;
                } else {
                    fab1.hide();
                    fab2.hide();
                    fab1.animate().translationY(0);
                    fab2.animate().translationY(0);

                    add_button.setImageResource(R.drawable.ic_add);
                    flag = true;
                }
            }
        });

        fab1.setOnClickListener(this::readBarcode);
        fab2.setOnClickListener(this::toAddDailyProd);

        homeAdapter = new HomeAdapter(getActivity(), getContext(), prod_id, customer, model, min_target, running_time, part_code,
                part_name, process_name, plan_qty, date, id_user, id_material, id_sensor);
        recyclerView.setAdapter(homeAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return RootView;
    }

    private void storeDataInArrays() {
        Cursor cursor = databaseQuery.readAllDataProd();
        if (cursor.getCount() == 0) {
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                prod_id.add(cursor.getString(0));
                customer.add(cursor.getString(1));
                model.add(cursor.getString(2));
                min_target.add(cursor.getString(3));
                running_time.add(cursor.getString(4));
                part_code.add(cursor.getString(5));
                part_name.add(cursor.getString(6));
                process_name.add(cursor.getString(7));
                plan_qty.add(cursor.getString(8));
                date.add(cursor.getString(9));
                id_user.add(cursor.getString(10));
                id_material.add(cursor.getString(11));
                id_sensor.add(cursor.getString(12));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    private void readBarcode(View v) {
        Intent i = new Intent(getActivity(), QrcodeScannActivity.class);
        startActivity(i);
    }

    private void toAddDailyProd (View v) {
        Intent i = new Intent(getActivity(), AddDailyProductionActivity.class);
        startActivity(i);
    }
}