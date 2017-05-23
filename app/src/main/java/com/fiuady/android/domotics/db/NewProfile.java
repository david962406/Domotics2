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
import android.widget.Switch;

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
    ImageButton btnSwimPool;
    ImageButton btnMainRoom;
    ImageButton btnSave;

    boolean room1 = false;
    boolean room2 = false;
    boolean outside = false;

    //Room1
    int room1VActive = 0;
    int room1VSensor = 0;
    boolean room1rgb = false;
    int rgbDataRoom1[] = new int[3];

    //Room2
    int room2VActive = 0;
    int room2VSensor = 0;
    boolean room2rgb = false;
    int rgbDataRoom2[] = new int[3];


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

                        if (room1rgb) {
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

                        if (room2rgb) {
                            rgbDataRoom2[0] = r;
                            rgbDataRoom2[1] = g;
                            rgbDataRoom2[2] = b;
                        }
                        if (switchVentilatorStatus.isChecked()){room2VActive = 1;}
                        if (switchVentilatorAuto.isChecked()){room2VSensor = 1;}
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });

        btnSwimPool = (ImageButton)view.findViewById(R.id.btnEditSwimPool);
        btnMainRoom = (ImageButton)view.findViewById(R.id.btnEditMainRoom);
        btnSave = (ImageButton)view.findViewById(R.id.btnSave);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
