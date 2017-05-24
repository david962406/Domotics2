package com.fiuady.android.domotics.db.sensors;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
    TextView dataSW1;
    TextView dataSW2;
    TextView dataSW3;
    TextView dataSW4;
    TextView dataSW5;
    Switch GlobalAlarm;
    Switch SWPIR;
    Switch SW1;
    Switch SW2;
    Switch SW3;
    Switch SW4;
    Switch SW5;

    Button Save;
    Button UpdateStatus;

    boolean isGlobalAlarmChecked;
    boolean isSWPIRChecked;
    boolean isSW1Checked;
    boolean isSW2Checked;
    boolean isSW3Checked;
    boolean isSW4Checked;
    boolean isSW5Checked;

    String AlarmSensorsStatus;

    public Alarms() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_alarms, container, false);

        final AccessActivity activity = (AccessActivity)getActivity();


        GlobalAlarm = (Switch)view.findViewById(R.id.sw_alarm);
        SWPIR = (Switch)view.findViewById(R.id.sw_PIR);
        SW1 = (Switch)view.findViewById(R.id.sw_SW1);
        SW2 = (Switch)view.findViewById(R.id.sw_SW2);
        SW3 = (Switch)view.findViewById(R.id.sw_SW3);
        SW4 = (Switch)view.findViewById(R.id.sw_SW4);
        SW5 = (Switch)view.findViewById(R.id.sw_SW5);

        dataPIR = (TextView)view.findViewById(R.id.txt_PIRState);
        dataSW1 = (TextView)view.findViewById(R.id.txt_StateSW1);
        dataSW2 = (TextView)view.findViewById(R.id.txt_StateSW2);
        dataSW3 = (TextView)view.findViewById(R.id.txt_StateSW3);
        dataSW4 = (TextView)view.findViewById(R.id.txt_StateSW4);
        dataSW5 = (TextView)view.findViewById(R.id.txt_StateSW5);


        Save = (Button)view.findViewById(R.id.btn_SaveConf);
        UpdateStatus = (Button)view.findViewById(R.id.btn_UpdateStatus);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Enviar los Strings para los casos al arduino
                activity.AlarmMSG(isSWPIRChecked, isSW1Checked, isSW2Checked, isSW3Checked,
                        isSW4Checked, isSW5Checked);
            }
        });

        UpdateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pir_sw;
                int sw1;
                int sw2;
                int sw3;
                int sw4;
                int sw5;

                AlarmSensorsStatus = activity.getDataAlarmSensors().toString();
                String[] separated1 = AlarmSensorsStatus.split("-");
                pir_sw = Integer.valueOf(separated1[0]);
                sw1 = Integer.valueOf(separated1[1]);
                sw2 = Integer.valueOf(separated1[2]);
                sw3 = Integer.valueOf(separated1[3]);
                sw4 = Integer.valueOf(separated1[4]);
                sw5 = Integer.valueOf(separated1[5]);

                if(pir_sw == 1){
                    dataPIR.setText("Movimiento");
                }else{
                    dataPIR.setText("Sin Movimiento");
                }
                if(sw1 == 1){
                    dataSW1.setText("Abierto");
                }else {
                    dataSW1.setText("Cerrado");
                }
                if(sw2 == 1){
                    dataSW2.setText("Abierto");
                }else {
                    dataSW2.setText("Cerrado");
                }
                if(sw3 == 1){
                    dataSW3.setText("Abierto");
                }else {
                    dataSW3.setText("Cerrado");
                }
                if(sw4 == 1){
                    dataSW4.setText("Abierto");
                }else {
                    dataSW4.setText("Cerrado");
                }
                if(sw5 == 1){
                    dataSW5.setText("Abierto");
                }else {
                    dataSW5.setText("Cerrado");
                }
            }
        });

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
