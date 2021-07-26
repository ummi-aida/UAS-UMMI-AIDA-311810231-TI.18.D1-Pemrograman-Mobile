package com.example.uas.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uas.LoginActivity;
import com.example.uas.R;
import com.example.uas.utils.Preferences;

public class ProfileFragment extends Fragment {

    View RootView;
    TextView profile_name, profile_email;
    LinearLayout logout;

    public static ProfileFragment newInstance(String param1, String param2) {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RootView = inflater.inflate(R.layout.fragment_profile, container, false);
        profile_name = RootView.findViewById(R.id.profile_name);
        profile_email = RootView.findViewById(R.id.profile_email);
        logout = RootView.findViewById(R.id.logout);

        String profileName = Preferences.getRegisteredUserName(getContext());
        String profileEmail = Preferences.getRegisteredUser(getContext());

        profile_name.setText(profileName);
        profile_email.setText(profileEmail);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.clearLoggedInUser(getContext());
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        return RootView;
    }
}