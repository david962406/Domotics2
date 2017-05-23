package com.fiuady.android.domotics.db.sensors;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fiuady.android.domotics.AccessActivity;
import com.fiuady.android.domotics.R;

/**
 * Created by david on 19/05/2017.
 */

public class ledcontrol2 extends Fragment {
    private TextView dataTemp;
    private ImageButton on;
    private ImageButton off;
    private SeekBar lumcontrol;
    public ledcontrol2() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_led_control, container, false);
       // dataTemp = (TextView)view.findViewById(R.id.tempSensor);
        final AccessActivity activity = (AccessActivity)getActivity();
        //dataTemp.setText(activity.getDataTempSensor());
       on = (ImageButton)view.findViewById(R.id.button2);
        off = (ImageButton)view.findViewById(R.id.button3);
        lumcontrol=(SeekBar)view.findViewById(R.id.seekBar);
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.turnOnLed();
            }
        });
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.turnOffLed();
            }
        });
        lumcontrol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser==true)
                {
                    activity.lumchange(String.valueOf(progress));
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                activity.lumchange(String.valueOf(seekBar.getProgress()));

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                activity.lumchange(String.valueOf(seekBar.getProgress()));

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

