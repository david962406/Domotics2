package com.fiuady.android.domotics.db.sensors;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fiuady.android.domotics.AccessActivity;
import com.fiuady.android.domotics.R;

public class prueba extends Fragment{
    private TextView dataTemp;
    private ImageButton btnUpDate;
    public prueba() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_data_sensors, container, false);
        dataTemp = (TextView)view.findViewById(R.id.tempSensor);
        final AccessActivity activity = (AccessActivity)getActivity();
        dataTemp.setText(activity.getDataTempSensor());
        btnUpDate = (ImageButton) view.findViewById(R.id.btnConect);
        btnUpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataTemp.setText(activity.getDataTempSensor());
            }
        });
        return view;
    }
}
