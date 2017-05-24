package com.fiuady.android.domotics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class InfoActivity extends android.app.Fragment {
    private ImageButton btnChangeProfile;
    private ImageButton btnNewProfile;
    private ImageButton btnChangeUserPassword;
    private ImageButton btnChangeDoorPassword;
    public InfoActivity(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sign_in, container, false);
        super.onCreate(savedInstanceState);
        final AccessActivity activity = (AccessActivity)getActivity();
        btnChangeProfile = (ImageButton) view.findViewById(R.id.changeprofilebtn);
        btnNewProfile = (ImageButton) view.findViewById(R.id.newprofilebtn);
        btnChangeUserPassword = (ImageButton) view.findViewById(R.id.changeuserpasswordbtn);
        btnChangeDoorPassword = (ImageButton) view.findViewById(R.id.changedoorpasswordbtn);

        return view;
    }
}