package com.example.uas.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.uas.AddDailyProductionActivity;
import com.example.uas.AddMaterialActivity;
import com.example.uas.AddSensorActivity;
import com.example.uas.R;

public class AddFragment extends Fragment {

    View RootView;
    LinearLayout toAddSensor, toAddMaterial;

    public static AddFragment newInstance(String param1, String param2) {
        return new AddFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RootView = inflater.inflate(R.layout.fragment_add, container, false);
        toAddSensor = RootView.findViewById(R.id.to_add_sensor);
        toAddMaterial = RootView.findViewById(R.id.to_add_material);

        toAddSensor.setOnClickListener(this::toAddSensorAct);
        toAddMaterial.setOnClickListener(this::toAddMaterial);

        return RootView;
    }

    private void toAddSensorAct(View v) {
        Intent i = new Intent(getActivity(), AddSensorActivity.class);
        startActivity(i);
    }

    private void toAddMaterial(View v) {
        Intent i = new Intent(getActivity(), AddMaterialActivity.class);
        startActivity(i);
    }
}