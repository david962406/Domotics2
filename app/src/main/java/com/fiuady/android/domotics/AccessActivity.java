package com.fiuady.android.domotics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.fiuady.android.domotics.db.sensors.DeviceList;
import com.fiuady.android.domotics.db.sensors.RGBActivity;

public class AccessActivity extends AppCompatActivity {

    Button btnNewUser;
    Button btnLed;

    Button btnLedRGB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);

        btnNewUser = (Button)findViewById(R.id.btnNewUser);
        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(AccessActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        btnLed = (Button)findViewById(R.id.btnLed);
        btnLed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccessActivity.this, DeviceList.class);
                startActivity(intent);
            }
        });
        btnLedRGB = (Button)findViewById(R.id.btnLedRGB);
        btnLedRGB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccessActivity.this, RGBActivity.class);
                startActivity(intent);
            }
        });
    }

}