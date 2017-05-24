package com.fiuady.android.domotics.db;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import com.fiuady.android.domotics.AccessActivity;
import com.fiuady.android.domotics.R;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class NewProfile extends android.app.Fragment{
    EditText textNameProfile;
    ImageButton btnRoom1;
    ImageButton btnRoom2;
    ImageButton btnOutSide;
    ImageButton btnSave;

    boolean room1 = false;
    boolean room2 = false;
    boolean outside = false;

    //Room1
    int room1VActive = 0;
    int room1VSensor = 0;
    boolean room1rgb = false;
    int rgbDataRoom1[] = new int[3];
    int rgbr1 = 0;

    //Room2
    int room2VActive = 0;
    int room2VSensor = 0;
    boolean room2rgb = false;
    int rgbDataRoom2[] = new int[3];
    int rgbr2 = 0;

    //Outsides
    int led1Status = 0;
    int led1Auto = 0;
    int led1brightness = 0;
    int led2Status = 0;
    int led2Auto = 0;
    int led2brightness = 0;
    int alarmStatus = 0;
    int pir = 0;
    int sw1 = 0;
    int sw2 = 0;
    int sw3 = 0;
    int sw4 = 0;
    int sw5 = 0;

    //Auxiliares
    int r;
    int g;
    int b;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.new_profile, container, false);

        for (int i = 0; i < 3; i ++) {
            rgbDataRoom1[i] = 255;
        }

        final AccessActivity activity = (AccessActivity)getActivity();
        textNameProfile = (EditText)view.findViewById(R.id.textNewProfile);
        btnRoom1 = (ImageButton)view.findViewById(R.id.btnEditRoom1);
        btnRoom1.setBackgroundTintList(ColorStateList.valueOf(0xff888888));
        btnRoom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(container.getContext());
                View view1 = inflater.inflate(R.layout.dialog_edit_room_1, container, false);
                builder.setView(view1);
                final AlertDialog alertDialog = builder.create();

                ImageButton btnOK = (ImageButton)view1.findViewById(R.id.btnEditRoom1);
                final ImageButton rgbChangeColor = (ImageButton)view1.findViewById(R.id.colorpicker);
                rgbChangeColor.setEnabled(false);
                final Switch switchVentilatorStatus = (Switch)view1.findViewById(R.id.switchVentilatorStatus);
                final Switch switchVentilatorAuto = (Switch)view1.findViewById(R.id.switchAutomatic);
                final Switch switchRGB = (Switch)view1.findViewById(R.id.switchRGBStatus);

                switchVentilatorAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (switchVentilatorAuto.isChecked()) {
                            switchVentilatorStatus.setChecked(true);
                            switchVentilatorStatus.setEnabled(false);
                        }
                        else {
                            switchVentilatorStatus.setEnabled(true);
                        }
                    }
                });

                switchRGB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!switchRGB.isChecked()) {
                            rgbChangeColor.setEnabled(false);
                        }
                        else {
                            rgbChangeColor.setEnabled(true);
                        }
                    }
                });

                rgbChangeColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ColorPickerDialogBuilder
                                .with(alertDialog.getContext())
                                .setTitle("Choose RGB color")
                                .initialColor(Color.WHITE)
                                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                                .lightnessSliderOnly()
                                .density(12)
                                .setOnColorSelectedListener(new OnColorSelectedListener() {
                                    @Override
                                    public void onColorSelected(int selectedColor) {
                                        // Nothing to do...
                                    }
                                })
                                .setPositiveButton("ok", new ColorPickerClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                        r = (selectedColor >> 16) & 0xFF;
                                        g = (selectedColor >> 8) & 0xFF;
                                        b = (selectedColor >> 0) & 0xFF;
                                        room1rgb = true;
                                        rgbr1 = 1;
                                    }
                                })
                                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .build()
                                .show();
                    }
                });
//00BF9A
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        room1 = true;
                        btnRoom1.setBackgroundTintList(ContextCompat.getColorStateList(alertDialog.getContext(), R.color.colorAccent));

                        if (rgbr1 == 1) {
                            rgbDataRoom1[0] = r;
                            rgbDataRoom1[1] = g;
                            rgbDataRoom1[2] = b;
                        }
                        if (switchVentilatorStatus.isChecked()){room1VActive = 1;}
                        if (switchVentilatorAuto.isChecked()){room1VSensor = 1;}
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });

        btnRoom2 = (ImageButton)view.findViewById(R.id.btnEditRoom2);
        btnRoom2.setBackgroundTintList(ColorStateList.valueOf(0xff888888));
        btnRoom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(container.getContext());
                View view1 = inflater.inflate(R.layout.dialog_edit_room_1, container, false);
                builder.setView(view1);
                final AlertDialog alertDialog = builder.create();

                ImageButton btnOK = (ImageButton)view1.findViewById(R.id.btnEditRoom1);
                final ImageButton rgbChangeColor = (ImageButton)view1.findViewById(R.id.colorpicker);
                rgbChangeColor.setEnabled(false);
                final Switch switchVentilatorStatus = (Switch)view1.findViewById(R.id.switchVentilatorStatus);
                final Switch switchVentilatorAuto = (Switch)view1.findViewById(R.id.switchAutomatic);
                final Switch switchRGB = (Switch)view1.findViewById(R.id.switchRGBStatus);

                switchVentilatorAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (switchVentilatorAuto.isChecked()) {
                            switchVentilatorStatus.setChecked(true);
                            switchVentilatorStatus.setEnabled(false);
                        }
                        else {
                            switchVentilatorStatus.setEnabled(true);
                        }
                    }
                });

                switchRGB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!switchRGB.isChecked()) {
                            rgbChangeColor.setEnabled(false);
                        }
                        else {
                            rgbChangeColor.setEnabled(true);
                        }
                    }
                });

                rgbChangeColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ColorPickerDialogBuilder
                                .with(alertDialog.getContext())
                                .setTitle("Choose RGB color")
                                .initialColor(Color.WHITE)
                                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                                .lightnessSliderOnly()
                                .density(12)
                                .setOnColorSelectedListener(new OnColorSelectedListener() {
                                    @Override
                                    public void onColorSelected(int selectedColor) {
                                        // Nothing to do...
                                    }
                                })
                                .setPositiveButton("ok", new ColorPickerClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                        r = (selectedColor >> 16) & 0xFF;
                                        g = (selectedColor >> 8) & 0xFF;
                                        b = (selectedColor >> 0) & 0xFF;
                                        room2rgb = true;
                                        rgbr2 = 1;
                                    }
                                })
                                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .build()
                                .show();
                    }
                });
//00BF9A
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        room2 = true;
                        btnRoom2.setBackgroundTintList(ContextCompat.getColorStateList(alertDialog.getContext(), R.color.colorAccent));

                        if (rgbr2 == 1) {
                            rgbDataRoom2[0] = r;
                            rgbDataRoom2[1] = g;
                            rgbDataRoom2[2] = b;
                        }
                        if (switchVentilatorStatus.isChecked()){room2VActive = 1;}
                        if (switchVentilatorAuto.isChecked()){room2VSensor = 1;}

                        if (room1 && room2 && outside) {
                            btnSave.setEnabled(true);
                        }

                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });

        btnOutSide = (ImageButton)view.findViewById(R.id.btnOutSide);
        btnOutSide.setBackgroundTintList(ColorStateList.valueOf(0xff888888));
        btnOutSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(container.getContext());
                View view1 = inflater.inflate(R.layout.dialog_edit_outdoors, container, false);
                builder.setView(view1);
                final AlertDialog alertDialog = builder.create();

                final Switch switchLedStatus1 = (Switch)view1.findViewById(R.id.switchStatus1) ;
                final Switch switchLedAuto1 = (Switch)view1.findViewById(R.id.switchAutomatic1) ;
                switchLedAuto1.setChecked(false);
                final Switch switchLedStatus2 = (Switch)view1.findViewById(R.id.switchStatus2) ;
                final Switch switchLedAuto2 = (Switch)view1.findViewById(R.id.switchAutomatic2) ;
                switchLedAuto2.setChecked(false);

                final Switch switchAlarmStatus = (Switch)view1.findViewById(R.id.sw_alarm) ;
                final Switch switchPir = (Switch)view1.findViewById(R.id.sw_PIR) ;
                final Switch switchSW1 = (Switch)view1.findViewById(R.id.sw_SW1) ;
                final Switch switchSW2 = (Switch)view1.findViewById(R.id.sw_SW2) ;
                final Switch switchSW3 = (Switch)view1.findViewById(R.id.sw_SW3) ;
                final Switch switchSW4 = (Switch)view1.findViewById(R.id.sw_SW4) ;
                final Switch switchSW5 = (Switch)view1.findViewById(R.id.sw_SW5) ;
                final SeekBar brightness1 = (SeekBar)view1.findViewById(R.id.seekBar1) ;
                final SeekBar brightness2 = (SeekBar)view1.findViewById(R.id.seekBar2) ;


                switchLedAuto1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (switchLedAuto1.isChecked()) {
                            switchLedStatus1.setChecked(true);
                            switchLedStatus1.setEnabled(false);
                            brightness1.setEnabled(false);
                        }
                        else {
                            switchLedStatus1.setEnabled(true);
                            brightness1.setEnabled(true);
                        }
                    }
                });

                switchLedAuto2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (switchLedAuto2.isChecked()) {
                            switchLedStatus2.setChecked(true);
                            switchLedStatus2.setEnabled(false);
                            brightness2.setEnabled(false);
                        }
                        else {
                            switchLedStatus2.setEnabled(true);
                            brightness2.setEnabled(true);
                        }
                    }
                });

                switchPir.setEnabled(false);
                switchSW1.setEnabled(false);
                switchSW2.setEnabled(false);
                switchSW3.setEnabled(false);
                switchSW4.setEnabled(false);
                switchSW5.setEnabled(false);

                switchAlarmStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(switchAlarmStatus.isChecked()) {
                            switchPir.setEnabled(true);
                            switchSW1.setEnabled(true);
                            switchSW2.setEnabled(true);
                            switchSW3.setEnabled(true);
                            switchSW4.setEnabled(true);
                            switchSW5.setEnabled(true);
                        }
                    }
                });

                final ImageButton btnOk = (ImageButton)view1.findViewById(R.id.btnSave);
                btnOk.setBackgroundTintList(ColorStateList.valueOf(0xff888888));
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        outside = true;
                        btnOutSide.setBackgroundTintList(ContextCompat.getColorStateList(alertDialog.getContext(), R.color.colorAccent));
                        if (!switchAlarmStatus.isChecked()) {
                            alarmStatus = 0;
                            pir = 0;
                            sw1 = 0;
                            sw2 = 0;
                            sw3 = 0;
                            sw4 = 0;
                            sw5 = 0;
                        }
                        else {
                            alarmStatus = 1;
                            if (switchPir.isChecked()) {
                                pir = 1;
                            }
                            if (switchSW1.isChecked()) {
                                sw1 = 1;
                            }
                            if (switchSW2.isChecked()) {
                                sw2 = 1;
                            }
                            if (switchSW3.isChecked()) {
                                sw3 = 1;
                            }
                            if (switchSW4.isChecked()) {
                                sw4 = 1;
                            }
                            if (switchSW5.isChecked()) {
                                sw5 = 1;
                            }
                        }

                        if (switchLedAuto1.isChecked()) {
                            led1Status = 1;
                            led1brightness = 255;
                            led1Auto = 1;
                        }
                        else if (!switchLedStatus1.isChecked()) {
                            led1Status = 0;
                            led1brightness = 0;
                            led1Auto = 0;
                        }
                        else if (!switchLedAuto1.isChecked() && switchLedStatus1.isChecked()){
                            led1Auto = 0;
                            led1brightness = brightness1.getProgress();
                            led1Status = 1;
                        }

                        if (switchLedAuto2.isChecked()) {
                            led2Status = 1;
                            led2brightness = 255;
                            led2Auto = 1;
                        }
                        else if (!switchLedStatus2.isChecked()) {
                            led2Status = 0;
                            led2brightness = 0;
                            led2Auto = 0;
                        }
                        else if (!switchLedAuto2.isChecked() && switchLedStatus2.isChecked()){
                            led2Auto = 0;
                            led2brightness = brightness1.getProgress();
                            led2Status = 1;
                        }

                        if (room1 && room2 && outside) {
                            btnSave.setEnabled(true);
                            btnSave.setBackgroundTintList(ContextCompat.getColorStateList(alertDialog.getContext(), R.color.colorAccent));
                        }
                        alertDialog.dismiss();
                    }

                });
                alertDialog.show();
            }
        });


        btnSave = (ImageButton)view.findViewById(R.id.btnSave);
        btnSave.setBackgroundTintList(ColorStateList.valueOf(0xff888888));
        btnSave.setEnabled(false);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (textNameProfile.getText().toString().trim().equals("")) {
                Toast.makeText(activity.getApplicationContext(), "Inserta nombre del perfil", Toast.LENGTH_SHORT).show();
            }
            else {

            }
                int data[] = new int[18];
                data[0] = room1VActive;
                data[1] = room1VSensor;
                data[2] = rgbr1;
                data[3] = room2VActive;
                data[4] = room2VSensor;
                data[5] = rgbr2;
                data[6] = led1Status;
                data[7] = led1Auto;
                data[8] = led1brightness;
                data[9] = led2Status;
                data[10] = led2Auto;
                data[11] = led2brightness;
                data[12] = pir;
                data[13] = sw1;
                data[14] = sw2;
                data[15] = sw3;
                data[16] = sw4;
                data[17] = sw5;

                Inventory inventory = new Inventory(activity.getApplicationContext());
                inventory.newProfile(data, rgbDataRoom1, rgbDataRoom2, textNameProfile.getText().toString(), 0);

                Toast.makeText(activity.getApplicationContext(), "Perfil agregado exitosamente!", Toast.LENGTH_SHORT).show();

                getActivity().getFragmentManager().beginTransaction().remove(NewProfile.this).commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
