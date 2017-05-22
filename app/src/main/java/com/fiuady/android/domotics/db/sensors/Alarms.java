package com.fiuady.android.domotics.db.sensors;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.fiuady.android.domotics.AccessActivity;
import com.fiuady.android.domotics.R;

public class Alarms extends android.app.Fragment {
    TextView dataPIR;
    Switch GlobalAlarm;
    Switch SWPIR;
    Switch SW1;
    Switch SW2;
    Switch SW3;
    Switch SW4;
    Switch SW5;

    boolean isGlobalAlarmChecked;
    boolean isSWPIRChecked;
    boolean isSW1Checked;
    boolean isSW2Checked;
    boolean isSW3Checked;
    boolean isSW4Checked;
    boolean isSW5Checked;

    public Alarms() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_alarms, container, false);

        final AccessActivity activity = (AccessActivity)getActivity();

        dataPIR = (TextView)view.findViewById(R.id.sw_PIR);
        GlobalAlarm = (Switch)view.findViewById(R.id.sw_alarm);
        SWPIR = (Switch)view.findViewById(R.id.sw_PIR);
        SW1 = (Switch)view.findViewById(R.id.sw_SW1);
        SW2 = (Switch)view.findViewById(R.id.sw_SW2);
        SW3 = (Switch)view.findViewById(R.id.sw_SW3);
        SW4 = (Switch)view.findViewById(R.id.sw_SW4);
        SW5 = (Switch)view.findViewById(R.id.sw_SW5);

        GlobalAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Switch que activa o desactiva la alarma de manera global
                isGlobalAlarmChecked = isChecked;
                if(!isSWPIRChecked && isGlobalAlarmChecked && !isSW1Checked && !isSW2Checked &&
                        !isSW3Checked && !isSW4Checked && !isSW5Checked){
                    isSWPIRChecked = true;
                    isSW1Checked = true;
                    isSW2Checked = true;
                    isSW3Checked = true;
                    isSW4Checked = true;
                    isSW5Checked = true;

                }
                if(!isGlobalAlarmChecked){
                    isSWPIRChecked = false;
                    isSW1Checked = false;
                    isSW2Checked = false;
                    isSW3Checked = false;
                    isSW4Checked = false;
                    isSW5Checked = false;
                }

                Log.d("KKKK", "isChecked: " + isChecked);
                SWPIR.setChecked(isSWPIRChecked);
                SW1.setChecked(isSW1Checked);
                SW2.setChecked(isSW2Checked);
                SW3.setChecked(isSW3Checked);
                SW4.setChecked(isSW4Checked);
                SW5.setChecked(isSW5Checked);
            }
        });

        SWPIR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Switch que activa o desactiva la alarma Para el sensor PIR
                //individualmente o en conjunto con los reed Switch, dependiendo cual este activado en la app
                isSWPIRChecked =isChecked;
                if(!isSWPIRChecked && isGlobalAlarmChecked && !isSW1Checked && !isSW2Checked &&
                        !isSW3Checked && !isSW4Checked && !isSW5Checked){
                    GlobalAlarm.setChecked(false);
                }
                if(isSWPIRChecked || isSW1Checked || isSW2Checked ||
                        isSW3Checked || isSW4Checked || isSW5Checked){
                    GlobalAlarm.setChecked(true);
                }
            }
        });
        SW1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            //Switch que activa o desactiva la alarma Para el reed Switch 1
            //individualmente o en conjunto con los otros reed Switch y/o con el PIR, dependiendo cual este activado en la app
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSW1Checked = isChecked;
                if(!isSWPIRChecked && isGlobalAlarmChecked && !isSW1Checked && !isSW2Checked &&
                        !isSW3Checked && !isSW4Checked && !isSW5Checked){
                    GlobalAlarm.setChecked(false);
                }
                if(isSWPIRChecked || isSW1Checked || isSW2Checked ||
                        isSW3Checked || isSW4Checked || isSW5Checked){
                    GlobalAlarm.setChecked(true);
                }
            }
        });
        SW2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSW2Checked = isChecked;
                if(!isSWPIRChecked && isGlobalAlarmChecked && !isSW1Checked && !isSW2Checked &&
                        !isSW3Checked && !isSW4Checked && !isSW5Checked){
                    GlobalAlarm.setChecked(false);
                }
                if(isSWPIRChecked || isSW1Checked || isSW2Checked ||
                        isSW3Checked || isSW4Checked || isSW5Checked){
                    GlobalAlarm.setChecked(true);
                }
            }
        });
        SW3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSW3Checked = isChecked;
                if(!isSWPIRChecked && isGlobalAlarmChecked && !isSW1Checked && !isSW2Checked &&
                        !isSW3Checked && !isSW4Checked && !isSW5Checked){
                    GlobalAlarm.setChecked(false);
                }
                if(isSWPIRChecked || isSW1Checked || isSW2Checked ||
                        isSW3Checked || isSW4Checked || isSW5Checked){
                    GlobalAlarm.setChecked(true);
                }
            }
        });
        SW4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSW4Checked = isChecked;
                if(!isSWPIRChecked && isGlobalAlarmChecked && !isSW1Checked && !isSW2Checked &&
                        !isSW3Checked && !isSW4Checked && !isSW5Checked){
                    GlobalAlarm.setChecked(false);
                }
                if(isSWPIRChecked || isSW1Checked || isSW2Checked ||
                        isSW3Checked || isSW4Checked || isSW5Checked){
                    GlobalAlarm.setChecked(true);
                }
            }
        });
        SW5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSW5Checked = isChecked;
                if(!isSWPIRChecked && isGlobalAlarmChecked && !isSW1Checked && !isSW2Checked &&
                        !isSW3Checked && !isSW4Checked && !isSW5Checked){
                    GlobalAlarm.setChecked(false);
                }
                if(isSWPIRChecked || isSW1Checked || isSW2Checked ||
                        isSW3Checked || isSW4Checked || isSW5Checked){
                    GlobalAlarm.setChecked(true);
                }
            }
        });

        return view;
    }
}
