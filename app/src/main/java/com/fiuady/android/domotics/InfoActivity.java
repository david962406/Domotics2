package com.fiuady.android.domotics;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.fiuady.android.domotics.db.ChangeProfile;
import com.fiuady.android.domotics.db.NewProfile;

public class InfoActivity extends android.app.Fragment {
    private ImageButton btnChangeProfile;
    private ImageButton btnNewProfile;
    private ImageButton btnChangeUserPassword;
    private ImageButton btnChangeDoorPassword;

    final NewProfile fragment6 = new NewProfile();
    final ChangeProfile fragment7 = new ChangeProfile();

    public InfoActivity(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_info, container, false);
        super.onCreate(savedInstanceState);
        final AccessActivity activity = (AccessActivity)getActivity();
        btnChangeProfile = (ImageButton) view.findViewById(R.id.changeprofilebtn);
        btnNewProfile = (ImageButton) view.findViewById(R.id.newprofilebtn);
        btnChangeUserPassword = (ImageButton) view.findViewById(R.id.changeuserpasswordbtn);
        btnChangeDoorPassword = (ImageButton) view.findViewById(R.id.changedoorpasswordbtn);

        btnNewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.contenedor, fragment6);
                transaction.commit();
            }
        });

        btnChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.contenedor, fragment7);
                transaction.commit();
            }
        });

        return view;
    }
}