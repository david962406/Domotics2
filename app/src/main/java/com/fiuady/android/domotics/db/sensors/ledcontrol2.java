package com.fiuady.android.domotics.db.sensors;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fiuady.android.domotics.AccessActivity;
import com.fiuady.android.domotics.R;

/**
 * Created by david on 19/05/2017.
 */

public class ledcontrol2 extends Fragment {
    private TextView dataTemp;
    private Button on;
    private Button off;
    public ledcontrol2() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_led_control, container, false);
       // dataTemp = (TextView)view.findViewById(R.id.tempSensor);
        final AccessActivity activity = (AccessActivity)getActivity();
        //dataTemp.setText(activity.getDataTempSensor());
       on = (Button)view.findViewById(R.id.button2);
        off = (Button)view.findViewById(R.id.button3);
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
       // btnUpDate.setOnClickListener(new View.OnClickListener() {
       //     @Override
       //     public void onClick(View v) {
       //         dataTemp.setText(activity.getDataTempSensor());
       //     }
       // });
        return view;
    }
}
