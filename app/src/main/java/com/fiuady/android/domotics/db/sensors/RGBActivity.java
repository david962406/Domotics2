package com.fiuady.android.domotics.db.sensors;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;

import com.fiuady.android.domotics.AccessActivity;
import com.fiuady.android.domotics.R;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class RGBActivity extends Fragment {
    ImageButton lampbutton1;
    ImageButton lampbutton2;
    Switch SWrgb1;
    Switch SWrgb2;

    public void SendColor1 (int r, int g, int b)
    {
        ColorStateList cs1 = new ColorStateList(new int[][]{{}}, new int[]{Color.rgb(r,g,b)});
        lampbutton1.setBackgroundTintList(cs1);
    }

    public void SendColor2 (int r, int g, int b)
    {
        ColorStateList cs1 = new ColorStateList(new int[][]{{}}, new int[]{Color.rgb(r,g,b)});
        lampbutton2.setBackgroundTintList(cs1);
    }


    public RGBActivity() {
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {




        View view = inflater.inflate(R.layout.activity_rgb, container, false);
        final AccessActivity activity = (AccessActivity)getActivity();
        //dataTemp.setText(activity.getDataTempSensor());
        SWrgb1 = (Switch)view.findViewById(R.id.sw_RGB1);
        SWrgb2 = (Switch)view.findViewById(R.id.sw_RGB2);
        lampbutton1=(ImageButton) view.findViewById(R.id.lampbutton1);
        lampbutton2=(ImageButton)view.findViewById(R.id.lampbutton2);

        lampbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.colorchange(1);
            }
        });
        lampbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               activity.colorchange(2);
            }
        });

        SWrgb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (SWrgb1.isChecked()) {
                    activity.turnRGBon(1);
                }
                else
                {
                    activity.turnRGBoff(1);
                }
            }
        });

        SWrgb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SWrgb2.isActivated()) {
                    activity.turnRGBon(2);
                }
                else
                {
                    activity.turnRGBoff(2);
                }
            }
        });

        // btnUpDate.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         dataTemp.setText(activity.getDataTempSensor());
        //     }
        // });
        return view;

    }
}