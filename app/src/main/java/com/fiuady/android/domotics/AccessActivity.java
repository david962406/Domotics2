package com.fiuady.android.domotics;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fiuady.android.domotics.db.sensors.DataSensorsActivity;
import com.fiuady.android.domotics.db.sensors.DeviceList;
import com.fiuady.android.domotics.db.sensors.ledcontrol2;
import com.fiuady.android.domotics.db.sensors.prueba;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class AccessActivity extends FragmentActivity {

    Button btnNewUser;
    Button btnLed;
    Button btnBluetoothConection;
    Button btnDataSensor;

    private ProgressDialog progress;
    String address = null;
    public static BluetoothSocket btSocket = null;
    BluetoothAdapter myBluetooth = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    final prueba fragment1 = new prueba();
    final ledcontrol2 fragment2 = new ledcontrol2();
    String tempData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);


        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //recivimos la mac address obtenida en la actividad anterior
        new ConnectBT().execute(); //Call the class to connect

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
                //Intent intent = new Intent(AccessActivity.this, DeviceList.class);
                //startActivity(intent);
                android.app.FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.add(R.id.contenedor, fragment2);
                transaction.commit();
            }
        });

        btnBluetoothConection = (Button)findViewById(R.id.btnBtConection);
        btnBluetoothConection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccessActivity.this, DataSensorsActivity.class);
                startActivity(intent);
            }
        });

        btnDataSensor = (Button)findViewById(R.id.dataSensors);
        btnDataSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.add(R.id.contenedor, fragment1);
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
                btSocket.getOutputStream().write(":".toString().getBytes());

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
                btSocket.getOutputStream().write(":".toString().getBytes());


            }
            catch (IOException e)
            {
               // msg("Error");
            }
        }
    }

    public void turnOnLed()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("TO".toString().getBytes());
                btSocket.getOutputStream().flush();
                btSocket.getOutputStream().write(":".toString().getBytes());


            }
            catch (IOException e)
            {
                //msg("Error");
            }
        }
    }

}