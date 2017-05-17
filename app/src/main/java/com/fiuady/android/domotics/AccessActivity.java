package com.fiuady.android.domotics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AccessActivity extends AppCompatActivity {

    Button btnNewUser;
    Button btnLed;

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
    }

}