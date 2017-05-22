package com.fiuady.android.domotics.db.sensors;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.fiuady.android.domotics.R;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class RGBActivity extends AppCompatActivity {
    ImageButton lampara1;
    private void sendColor(int r, int g, int b) {
      //aqui se envian datos al bluetooth


        Log.d("RGB", "color en hexadecimal"+ lampara1.getColorFilter()+"el que no es filtro");

        ColorStateList csl = new ColorStateList(new int[][]{{}}, new int[]{Color.rgb(r,g,b)});
        lampara1.setBackgroundTintList(csl);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgb);


         lampara1=(ImageButton)findViewById(R.id.lampbutton) ;

        //lampara1.setBackgroundColor(Color.BLACK);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorPickerDialogBuilder
                        .with(RGBActivity.this)
                        .setTitle("Choose RGB color")
                        .initialColor(Color.WHITE)
                        .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
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
                                int r = (selectedColor >> 16) & 0xFF;
                                int g = (selectedColor >> 8) & 0xFF;
                                int b = (selectedColor >> 0) & 0xFF;
                                Log.d("RGB", "R [" + r + "] - G [" + g + "] - B [" + b + "]");
                                sendColor(r,g,b);
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
    }
}
