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
import com.fiuady.android.domotics.db.Inventory;

public class DoorActivity extends Fragment{
    private TextView dataTemp;
    private ImageButton open1;
    private ImageButton close1;
    private ImageButton open2;
    private ImageButton close2;
    private SeekBar lumcontrol;
    public DoorActivity() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_door, container, false);
        // dataTemp = (TextView)view.findViewById(R.id.tempSensor);
        final AccessActivity activity = (AccessActivity)getActivity();
        //dataTemp.setText(activity.getDataTempSensor());
        open1 = (ImageButton)view.findViewById(R.id.opendoor1);
        close1 = (ImageButton)view.findViewById(R.id.closedoor1);

        open1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.Opendoor1();
            }
        });
        close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.Closedoor1();
            }
        });
        open2 = (ImageButton)view.findViewById(R.id.opendoor2);
        close2= (ImageButton)view.findViewById(R.id.closedoor2);

        open2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.Opendoor2();
            }
        });
        close2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.Closedoor2();
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
