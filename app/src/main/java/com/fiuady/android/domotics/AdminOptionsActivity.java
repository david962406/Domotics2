package com.fiuady.android.domotics;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class AdminOptionsActivity extends android.app.Fragment {
    private ImageButton btnNewUser;
    private ImageButton btnChangeDoorPassword;
    public AdminOptionsActivity(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sign_in, container, false);

        btnNewUser = (ImageButton) view.findViewById(R.id.newuser);
        btnChangeDoorPassword = (ImageButton) view.findViewById(R.id.changedoorpasswordbtn);

        return view;
    }
}