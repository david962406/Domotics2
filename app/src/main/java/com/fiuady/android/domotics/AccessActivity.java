package com.fiuady.android.domotics;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fiuady.android.domotics.db.Inventory;
import com.fiuady.android.domotics.db.sensors.Alarms;
import com.fiuady.android.domotics.db.sensors.DoorActivity;
import com.fiuady.android.domotics.db.sensors.RGBActivity;
import com.fiuady.android.domotics.db.sensors.ledcontrol2;
import com.fiuady.android.domotics.db.sensors.prueba;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class AccessActivity extends FragmentActivity {
//
    ImageButton btnNewUser;
    ImageButton btnLed;
    ImageButton btnDataSensor;
    ImageButton btnAlarm;
    ImageButton btnDoor;

    private ProgressDialog progress;
    String address = null;
    public static BluetoothSocket btSocket = null;
    public static int userid = 0;
    BluetoothAdapter myBluetooth = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    final prueba fragment1 = new prueba();
    final ledcontrol2 fragment2 = new ledcontrol2();
    final Alarms fragment3 = new Alarms();
    final DoorActivity fragment4 = new DoorActivity();
    String tempData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);

        //Aqui pondrán la dirección del modulo bluetooth que estan usando,
        //La pueden obtener mediante la clase devicelist
        address = "20:17:01:03:42:80";

        new ConnectBT().execute(); //Call the class to connect

        btnNewUser = (ImageButton)findViewById(R.id.btnNewUser);
        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(AccessActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        btnLed = (ImageButton) findViewById(R.id.btnLed);
        btnLed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(AccessActivity.this, DeviceList.class);
                //startActivity(intent);
                android.app.FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.replace(R.id.contenedor, fragment2);
                transaction.commit();
            }
        });

        btnDataSensor = (ImageButton) findViewById(R.id.dataSensors);
        btnDataSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.replace(R.id.contenedor, fragment1);
                transaction.commit();
            }
        });

        btnAlarm = (ImageButton) findViewById(R.id.btnAlarms);
        btnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.contenedor, fragment3);
                transaction.commit();
            }
        });
        btnDoor=(ImageButton) findViewById(R.id.btnDoor);
        btnDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.contenedor, fragment4);
                transaction.commit();

            }
        });
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(AccessActivity.this, "Connecting...", "Please wait!!!");
        }

        @Override
        protected Void doInBackground(Void... devices)
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//conectamos al dispositivo y chequeamos si esta disponible
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Conexión Fallida");
                finish();
            }
            else
            {
                msg("Conectado");
                isBtConnected = true;
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(btSocket.getInputStream()));
                    new BtBackgroundTask().execute(br);
                }
                catch (Exception e) {

                }
            }
            progress.dismiss();
        }

        private void msg(String s)
        {
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
        }
    }

    private class BtBackgroundTask extends AsyncTask<BufferedReader, String, Void> {
        @Override
        protected Void doInBackground(BufferedReader... params) {
            try {
                while (!isCancelled()) {
                    publishProgress(params[0].readLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            appendMessageText(values[0] + "°C");
        }
    }

    private void appendMessageText(String text) {
        tempData = text;
    }

    public String getDataTempSensor () {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("UPDATEtemperature".toString().getBytes());
                btSocket.getOutputStream().flush();

            }
            catch (IOException e)
            {
                // msg("Error");
            }
        }
        return tempData;
    }
    public void turnOffLed()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("TF".toString().getBytes());
                btSocket.getOutputStream().flush();
            }
            catch (IOException e)
            {
               // msg("Error");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            btSocket.close();
        }
        catch (Exception e) {

        }
    }

    public void turnOnLed()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("TO".toString().getBytes());
                Log.d("dd","TO".toString().getBytes().toString());
                btSocket.getOutputStream().flush();


            }
            catch (IOException e)
            {
                //msg("Error");
            }
        }


    }
    public void Opendoor1()
    {
        final Inventory inventory = new Inventory(getApplicationContext());
        int maindoorpassword = inventory.getDoorPassword();

        if (userid == 0) {
            LayoutInflater li = LayoutInflater.from(AccessActivity.this);
            final View promptsView = li.inflate(R.layout.fragment_maindoorpw, null);
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AccessActivity.this);
            alertDialogBuilder.setView(promptsView);
            final EditText actualPIN = (EditText) promptsView.findViewById(R.id.editTextActualPIN);
            final EditText newPIN = (EditText) promptsView.findViewById(R.id.editTextInsertNewPIN);
            final EditText confirmPIN = (EditText) promptsView.findViewById(R.id.editTextConfirmNewPIN);
            final Button btnChangePIN = (Button) promptsView.findViewById(R.id.btn_ChangePin);

            btnChangePIN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater li2 = LayoutInflater.from(AccessActivity.this);
                    View promptsView = li2.inflate(R.layout.fragment_changemaindoorpw, null);
                    final EditText actualPIN = (EditText) promptsView.findViewById(R.id.editTextActualPIN);
                    final EditText newPIN = (EditText) promptsView.findViewById(R.id.editTextInsertNewPIN);
                    final EditText confirmPIN = (EditText) promptsView.findViewById(R.id.editTextConfirmNewPIN);
                    alertDialogBuilder.setView(promptsView);
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("Guardar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                        if (Integer.valueOf(actualPIN.getText().toString().length()) != null &&
                                                Integer.valueOf(newPIN.getText().toString().trim().length()) != null &&
                                                        Integer.valueOf(confirmPIN.getText().toString().trim().length()) != null)
                                        {
                                            if (inventory.getDoorPassword() == Integer.valueOf(actualPIN.getText().toString()))
                                            {
                                                if (newPIN.getText().toString().trim().equals(confirmPIN.getText().toString().trim())) {
                                                    inventory.modifyMainDoorPw(Integer.valueOf(newPIN.getText().toString()));
                                                    Toast.makeText(AccessActivity.this, "PIN cambiado exitosamente!", Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                {
                                                    Toast.makeText(AccessActivity.this, "La nueva contraseña no coincide!", Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                }
                                            } else
                                            {
                                                Toast.makeText(AccessActivity.this, "PIN incorrecto!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        }
                                        else
                                        {
                                            Toast.makeText(AccessActivity.this, "Revise los campos!", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                        }
                                    })
                            .setNegativeButton("Cancelar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.dismiss();
                                        }
                                    }
                            );

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                    Toast.makeText(AccessActivity.this, "Hola!", Toast.LENGTH_SHORT).show();
                }
            });

            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.editTextDialogUserInput);

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Go",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    int user_text = Integer.valueOf(userInput.getText().toString());
                                    if (user_text == inventory.getDoorPassword())
                                    {
                                        if (btSocket != null) {
                                            try {
                                                btSocket.getOutputStream().write("OPENDOOR2".toString().getBytes());
                                                Log.d("dd", "TO".toString().getBytes().toString());
                                                btSocket.getOutputStream().flush();
                                                Toast.makeText(AccessActivity.this, "Puerta principal abierta", Toast.LENGTH_SHORT).show();

                                            } catch (IOException e) {
                                                //msg("Error");
                                            }
                                        }
                                    }
                                    else{
                                        Toast.makeText(AccessActivity.this, "Contraseña incorrecta!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.dismiss();
                                }
                            }
                    );

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
        else
        {
            Toast.makeText(AccessActivity.this, "Se requieren privilegios de administrador.", Toast.LENGTH_SHORT).show();
        }

    }

    public void Closedoor1()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("CLOSEDOOR2".toString().getBytes());
                Log.d("dd","TO".toString().getBytes().toString());
                btSocket.getOutputStream().flush();


            }
            catch (IOException e)
            {
                //msg("Error");
            }
        }

    }

    public void Opendoor2()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("OPENDOOR1".toString().getBytes());
                Log.d("dd","TO".toString().getBytes().toString());
                btSocket.getOutputStream().flush();


            }
            catch (IOException e)
            {
                //msg("Error");
            }
        }


    }
    public void Closedoor2()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("CLOSEDOOR1".toString().getBytes());
                Log.d("dd","TO".toString().getBytes().toString());
                btSocket.getOutputStream().flush();


            }
            catch (IOException e)
            {
                //msg("Error");
            }
        }


    }
    public void lumchange(String value)
    {
        try
        {
            String value2;
            value2="LUM"+value;
            btSocket.getOutputStream().write(value2.getBytes());
        }
        catch (IOException e)
        {
        }

    }


    public void colorchange(final int selectedRGB)
    {
        ColorPickerDialogBuilder
                .with(AccessActivity.this)
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
                        int r = (selectedColor >> 16) & 0xFF;
                        int g = (selectedColor >> 8) & 0xFF;
                        int b = (selectedColor >> 0) & 0xFF;
                        Log.d("RGB", "R [" + r + "] - G [" + g + "] - B [" + b + "]");

                        switch(selectedRGB){
                            case 1:
                             //   fragment4.SendColor1(r,g,b);
                                try
                                {
                                    String value1;
                                    String h,i,j;


                                    if(r<10)
                                    {
                                        h = "00" + Integer.toString(r);
                                    }
                                    else if(r<100)
                                    {
                                        h = "0" + Integer.toString(r);
                                    }
                                    else
                                    {
                                        h = Integer.toString(r);
                                    }

                                    if(g<10)
                                    {
                                        i = "00" + Integer.toString(b);
                                    }
                                    else if(g<100)
                                    {
                                        i = "0" + Integer.toString(g);
                                    }
                                    else
                                    {
                                        i = Integer.toString(g);
                                    }

                                    if(b<10)
                                    {
                                        j = "00" + Integer.toString(b);
                                    }
                                    else if(b<100)
                                    {
                                        j = "0" + Integer.toString(b);
                                    }
                                    else
                                    {
                                        j = Integer.toString(b);
                                    }

                                    value1="RGB1"+h+i+j;
                                    btSocket.getOutputStream().write(value1.getBytes());
                                    btSocket.getOutputStream().flush();
                                }
                                catch (IOException e)
                                {

                                }
                                break;

                            case 2:
                               // fragment4.SendColor2(r,g,b);
                                try
                                {
                                    String value1, value2, value3;
                                    value1="RGB2"+r+g+b;
                                    btSocket.getOutputStream().write(value1.getBytes());
                                    btSocket.getOutputStream().flush();
                                }
                                catch (IOException e)
                                {

                                }
                                break;
                        }
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

    public void turnRGBon(int selectedRGB)
    {
        if (btSocket!=null)
        {
            try
            {
                switch (selectedRGB) {
                    case 1:
                        btSocket.getOutputStream().write("RGB1ON".getBytes());
                        btSocket.getOutputStream().flush();
                        break;
                    case 2:
                        btSocket.getOutputStream().write("RGB2ON".getBytes());
                        btSocket.getOutputStream().flush();
                        break;
                }
            }
            catch (IOException e)
            {
                //msg("Error");
            }
        }


    }

    public void turnRGBoff(int selectedRGB)
    {
        if (btSocket!=null)
        {
            try
            {
                switch (selectedRGB) {
                    case 1:
                        btSocket.getOutputStream().write("RGB1OFF".getBytes());
                        btSocket.getOutputStream().flush();
                        break;
                    case 2:
                        btSocket.getOutputStream().write("RGB2OFF".getBytes());
                        btSocket.getOutputStream().flush();
                        break;
                }
            }
            catch (IOException e)
            {
                //msg("Error");
            }
        }


    }


}