package com.fiuady.android.domotics.db.sensors;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
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
    private Switch onled1;
    private Switch onled2;
    private Switch onled3;
    private Switch onled4;
    private SeekBar lumcontrol;
    private SeekBar lumcontrol2;
    private ImageButton rgb1;
    private ImageButton rgb2;

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
        onled1 = (Switch) view.findViewById(R.id.sw_led1);
        onled2 = (Switch) view.findViewById(R.id.sw_led2);
        onled3 = (Switch) view.findViewById(R.id.sw_led3);
        onled4 = (Switch) view.findViewById(R.id.sw_led4);
        rgb1=(ImageButton) view.findViewById(R.id.button7);
        rgb2=(ImageButton) view.findViewById(R.id.button8);

        lumcontrol=(SeekBar)view.findViewById(R.id.seekBar);
        lumcontrol2=(SeekBar)view.findViewById(R.id.seekBar2);
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.turnOnLed1();
            }
        });
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.turnOffLed1();
            }
        });
        onled1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)

                {
                    activity.turnOnLed1();
                }
                else
                {
                    activity.turnOffLed1();
                }
            }
        });
        onled2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)

                {
                    activity.turnOnLed2();
                }
                else
                {
                    activity.turnOffLed2();
                }
            }
        });
        onled3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)

                {

                    activity.turnOnrgb();
                }
                else
                {
                    activity.turnOffrgb();
                }
            }
        });
        onled4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)

                {
                    activity.turnOnrgb2();
                }
                else
                {
                    activity.turnOffrgb2();
                }
            }
        });
        lumcontrol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser==true)
                {


                        activity.lumchange(String.valueOf(Integer.toString(progress)));


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
        lumcontrol2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                activity.lumchange2(String.valueOf(progress));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // activity.lumchange(String.valueOf(seekBar.getProgress()));

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // activity.lumchange(String.valueOf(seekBar.getProgress()));

            }
        });
        rgb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.RGB1COLOR();

            }
        });
        rgb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
activity.RGB2COLOR();
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

