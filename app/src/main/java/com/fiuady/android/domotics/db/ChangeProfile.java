package com.fiuady.android.domotics.db;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.fiuady.android.domotics.AccessActivity;
import com.fiuady.android.domotics.R;
import com.fiuady.android.domotics.db.Tables.Profiles;

import java.util.ArrayList;
import java.util.List;

public class ChangeProfile extends Fragment{
    Spinner spinner;
    ImageButton btnOK;
    ArrayAdapter<Profiles> adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_change_profile, container, false);
        final AccessActivity activity = (AccessActivity)getActivity();

        spinner = (Spinner)view.findViewById(R.id.spinner);

        Inventory inventory = new Inventory(activity.getApplicationContext());
        ArrayList<Profiles> list = new ArrayList<>();

        List<Profiles> auxlist = inventory.getProfiles();
        for (Profiles c : auxlist) {
            list.add(c);
        }

        adapter = new ArrayAdapter<Profiles>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        btnOK = (ImageButton)view.findViewById(R.id.btnOk);

        return view;
    }
}
