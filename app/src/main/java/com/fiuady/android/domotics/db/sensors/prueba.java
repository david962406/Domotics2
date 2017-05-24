package com.fiuady.android.domotics.db.sensors;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.fiuady.android.domotics.AccessActivity;
import com.fiuady.android.domotics.R;

public class prueba extends Fragment{
    private TextView dataTemp;
    private ImageButton btnUpDate;
    private Switch fan1;
    private Switch fan2;
    private Switch smartfan;
    private EditText smartfantemp;
    public prueba() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_data_sensors, container, false);
        dataTemp = (TextView)view.findViewById(R.id.tempSensor);
        fan1 = (Switch) view.findViewById(R.id.sw_FAN1);
        fan2 = (Switch) view.findViewById(R.id.sw_FAN2);
        smartfan = (Switch) view.findViewById(R.id.sw_smartFAN);
        smartfantemp=(EditText)view.findViewById(R.id.editsmart);
        final AccessActivity activity = (AccessActivity)getActivity();
        dataTemp.setText(activity.getDataTempSensor());
        btnUpDate = (ImageButton) view.findViewById(R.id.btnConect);
        final String[] temperature = new String[1];
                temperature[0]="";
        btnUpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataTemp.setText(activity.getDataTempSensor());
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
        smartfantemp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.toString().isEmpty())
                    {
                        temperature[0] =s.toString();

                    }
                    else
                    {
                       smartfan.setEnabled(true);
                        temperature[0] =s.toString();
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        smartfan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)

                {
                    if(temperature[0].isEmpty())
                    {
                        smartfan.setChecked(false);
                    }
                    else
                    { activity.turnonsmart(temperature[0]);}
                }
                else
                {
                    activity.turnoffsmart(temperature[0]);
                }
            }
        });

        return view;


    }
}
