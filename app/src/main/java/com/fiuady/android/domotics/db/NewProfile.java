package com.fiuady.android.domotics.db;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fiuady.android.domotics.AccessActivity;
import com.fiuady.android.domotics.R;

public class NewProfile extends android.app.Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_led_control, container, false);
        final AccessActivity activity = (AccessActivity)getActivity();

        return view;
    }
}
