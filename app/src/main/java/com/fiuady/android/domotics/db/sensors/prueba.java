package com.fiuady.android.domotics.db.sensors;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.fiuady.android.domotics.AccessActivity;
import com.fiuady.android.domotics.R;

public class prueba extends Fragment{
    private TextView dataTemp;
    private TextView dataTemp2;
    private ImageButton btnUpDate;
    private ImageButton btnUpDate2;
    private Switch fan1;
    private Switch fan2;
    private Switch smartfan;
    private Switch smartfan2;

    public prueba() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_data_sensors, container, false);
        dataTemp = (TextView)view.findViewById(R.id.tempSensor);
        dataTemp2 = (TextView)view.findViewById(R.id.tempSensor2);
        fan1 = (Switch) view.findViewById(R.id.sw_FAN1);
        fan2 = (Switch) view.findViewById(R.id.sw_FAN2);
        smartfan = (Switch) view.findViewById(R.id.sw_smartFAN);
        smartfan2 = (Switch) view.findViewById(R.id.sw_smartFAN2);

        final AccessActivity activity = (AccessActivity)getActivity();
        dataTemp.setText(activity.getDataTempSensor());
        btnUpDate = (ImageButton) view.findViewById(R.id.btnConect);
        btnUpDate2 = (ImageButton) view.findViewById(R.id.btnConect2);
        final String[] temperature = new String[1];
                temperature[0]="";
        btnUpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataTemp.setText(activity.getDataTempSensor());
            }
        });
        btnUpDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataTemp2.setText(activity.getDataTempSensor());
            }
        });
        fan1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)

                {
                    activity.turnonfan1();
                }
                else
                {
                    activity.turnofffan1();
                }
            }
        });

        fan2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)

                {
                    activity.turnonfan2();
                }
                else
                {
                    activity.turnofffan2();
                }
            }
        });



        smartfan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)

                {
                    activity.turnonsmart();

                }
                else
                {
                   activity.turnoffsmart();
                }
            }
        });

        return view;


    }
}
