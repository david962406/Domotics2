package com.fiuady.android.domotics;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fiuady.android.domotics.db.Inventory;
import com.fiuady.android.domotics.db.Tables.deviceConfiguration;
import com.fiuady.android.domotics.db.sensors.Alarms;
import com.fiuady.android.domotics.db.sensors.DoorActivity;
import com.fiuady.android.domotics.db.sensors.ledcontrol2;
import com.fiuady.android.domotics.db.sensors.prueba;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

public class AccessActivity extends FragmentActivity {
//
    ImageButton btnSettings;
    ImageButton btnLed;
    ImageButton btnDataSensor;
    ImageButton btnAlarm;
    ImageButton btnDoor;

    private ProgressDialog progress;
    String address = null;
    public static BluetoothSocket btSocket = null;
    BluetoothAdapter myBluetooth = null;
    private boolean isBtConnected = false;
    //static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    final prueba fragment1 = new prueba();
    final ledcontrol2 fragment2 = new ledcontrol2();
    final Alarms fragment3 = new Alarms();
    final DoorActivity fragment4 = new DoorActivity();
    final InfoActivity fragment5 = new InfoActivity();
    String tempData;
    String AlarmSensorsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);

        //Aqui pondrán la dirección del modulo bluetooth que estan usando,
        //La pueden obtener mediante la clase devicelist
        address = "20:17:01:03:42:80";
        new ConnectBT().execute(); //Call the class to connect

        //btnNewUser = (ImageButton)findViewById(R.id.btnProfiles);
        //btnNewUser.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        android.app.FragmentManager fragmentManager = getFragmentManager();
        //        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //        transaction.replace(R.id.contenedor, fragment5);
        //        transaction.commit();
        //    }
        //});

        btnSettings = (ImageButton) findViewById(R.id.settings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.contenedor, fragment5);
                transaction.commit();
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

        int lc = getIntent().getExtras().getInt("lc");
        updateProfile(lc);
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
            appendMessageText(values[0] );
        }
    }

    private void appendMessageText(String text) {
        tempData = text;
        AlarmSensorsData = text;
    }

    public void updateProfile (int id) {
        /*0, 'Iluminación exterior');
        1, 'Iluminación de la piscina');
        2, 'Ventilador cuarto 1');
        3, 'Ventilador cuarto 2');
        4, 'Sensor de temperatura 1');
        5, 'Sensor de temperatura 2');
        6, 'RGB cuarto 1');
        7, 'RGB cuarto 2');
        8, 'Sensor de iluminación 1');
        9, 'Sensor de movimiento');
        10, 'Puerta 1');
        11, 'Puerta 2');
        12, 'Ventana 1');
        13, 'Ventana 2');
        14, 'Ventana 3');
        15, 'Alarma');*/

        String pir = "";
        String sw1 = "";
        String sw2 = "";
        String sw3 = "";
        String sw4 = "";
        String sw5 = "";

        Inventory inventory = new Inventory(getApplicationContext());
        List<deviceConfiguration> list = inventory.getProfilesById(id);

        for (deviceConfiguration dc : list) {
            switch (dc.getDeviceId()) {
                case 0: {
                    if (dc.getSensorActive() == 1) {turnOnLed1(); break;}
                    else if (dc.getSensorActive() == 0 && dc.getDeviceActive() == 1) {
                        turnOnLed1();
                        lumchange(String.valueOf(dc.getData()));
                        break;
                    }
                    else {
                        turnOffLed1();
                        break;
                    }
                }

                case 1: {
                    if (dc.getSensorActive() == 1) {turnOnLed2(); break;}
                    else if (dc.getSensorActive() == 0 && dc.getDeviceActive() == 1) {
                        turnOnLed2();
                        lumchange2(String.valueOf(dc.getData()));
                        break;
                    }
                    else {
                        turnOffLed1();
                        break;
                    }
                }

                case 2: {
                    if (dc.getSensorActive() == 1) {
                        turnonsmart();
                        break;
                    }
                    else if (dc.getSensorActive() == 0 && dc.getDeviceActive() == 1) {
                        turnonfan1();
                        break;
                    }
                    else {
                        turnofffan1();
                        break;
                    }
                }

                case 3: {
                    if (dc.getSensorActive() == 1) {
                        turnonsmart2();
                        break;
                    }
                    else if (dc.getSensorActive() == 0 && dc.getDeviceActive() == 1) {
                        turnonfan2();
                        break;
                    }
                    else {
                        turnofffan2();
                        break;
                    }
                }

                case 6: {
                    if (dc.getDeviceActive() == 1) {
                        RGB1COLORPARAMETER(dc.getData());
                        break;
                    }
                    else {
                        turnOffrgb();
                        break;
                    }
                }

                case 7: {
                    if (dc.getDeviceActive() == 1) {
                        RGB2COLORPARAMETER(dc.getData());
                        break;
                    }
                    else {
                        turnOffrgb2();
                        break;
                    }
                }

                case 9: {
                    if (dc.getDeviceActive() == 1) {
                        pir = "1";
                        break;
                    }
                    else {
                        pir = "0";
                        break;
                    }
                }
                case 10: {
                    if (dc.getDeviceActive() == 1) {
                        sw1 = "1";
                        break;
                    }
                    else {
                        sw1 = "0";
                        break;
                    }
                }
                case 11: {
                    if (dc.getDeviceActive() == 1) {
                        sw2 = "1";
                        break;
                    }
                    else {
                        sw2 = "0";
                        break;
                    }
                }
                case 12: {
                    if (dc.getDeviceActive() == 1) {
                        sw3 = "1";
                        break;
                    }
                    else {
                        sw3 = "0";
                        break;
                    }
                }
                case 13: {
                    if (dc.getDeviceActive() == 1) {
                        sw4 = "1";
                        break;
                    }
                    else {
                        sw4 = "0";
                        break;
                    }
                }
                case 14: {
                    if (dc.getDeviceActive() == 1) {
                        sw5 = "1";
                        break;
                    }
                    else {
                        sw5 = "0";
                        break;
                    }
                }
            }
        }
        String clave = pir + sw1 + sw2 + sw3 + sw4 + sw5;
        updateAlarms(clave);
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
    public void turnOffLed1()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("TF1".toString().getBytes());
                btSocket.getOutputStream().flush();
                btSocket.getOutputStream().write(":".toString().getBytes());



            }
            catch (IOException e)
            {
               // msg("Error");
            }
        }
    }
    public void turnOffLed2()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("TF2".toString().getBytes());
                btSocket.getOutputStream().flush();
                btSocket.getOutputStream().write(":".toString().getBytes());



            }
            catch (IOException e)
            {
                // msg("Error");
            }
        }
    }

    public void autoLed1 () {
        String code = "ACTIVATEAUTOLED";
        try
        {
            btSocket.getOutputStream().write(code.getBytes());
            btSocket.getOutputStream().flush();
            btSocket.getOutputStream().write(":".toString().getBytes());



        }
        catch (IOException e)
        {
            // msg("Error");
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

    public void turnOnLed1()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("TO1".toString().getBytes());
                Log.d("dd","TO".toString().getBytes().toString());
                btSocket.getOutputStream().flush();
                btSocket.getOutputStream().write(":".toString().getBytes());


            }
            catch (IOException e)
            {
                //msg("Error");
            }
        }


    }
    public void turnOnLed2()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("TO2".toString().getBytes());
                Log.d("dd","TO".toString().getBytes().toString());
                btSocket.getOutputStream().flush();
                btSocket.getOutputStream().write(":".toString().getBytes());


            }
            catch (IOException e)
            {
                //msg("Error");
            }
        }


    }
    public void Opendoor1()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("OPENDOOR1".toString().getBytes());
                Log.d("dd","TO".toString().getBytes().toString());
                btSocket.getOutputStream().flush();
                btSocket.getOutputStream().write(":".toString().getBytes());


            }
            catch (IOException e)
            {
                //msg("Error");
            }
        }


    }
    public void Closedoor1()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("CLOSEDOOR1".toString().getBytes());
                Log.d("dd","TO".toString().getBytes().toString());
                btSocket.getOutputStream().flush();
                btSocket.getOutputStream().write(":".toString().getBytes());


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
                btSocket.getOutputStream().write("OPENDOOR2".toString().getBytes());
                Log.d("dd","TO".toString().getBytes().toString());
                btSocket.getOutputStream().flush();
                btSocket.getOutputStream().write(":".toString().getBytes());


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
                btSocket.getOutputStream().write("CLOSEDOOR2".toString().getBytes());
                Log.d("dd","TO".toString().getBytes().toString());
                btSocket.getOutputStream().flush();
                btSocket.getOutputStream().write(":".toString().getBytes());


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
            Log.d("RGB",value2);
            btSocket.getOutputStream().write(value2.getBytes());
            btSocket.getOutputStream().flush();
            btSocket.getOutputStream().write(":".toString().getBytes());


        }
        catch (IOException e)
        {

        }


    }
    public void lumchange2(String value)
    {

        try
        {
            String value2;
            value2="LAM"+value;
            btSocket.getOutputStream().write(value2.getBytes());
            Log.d("123",value2);
            btSocket.getOutputStream().flush();
            btSocket.getOutputStream().write(":".toString().getBytes());

        }
        catch (IOException e)
        {

        }


    }
    public void AlarmMSG(boolean PIR, boolean SW1, boolean SW2, boolean SW3, boolean SW4, boolean SW5) {
        if (btSocket!=null) {
            try {

                String Clave = "ALARM";
                if(PIR){
                    Clave = Clave + "1";
                }else{
                    Clave = Clave + "0";
                }
                if(SW1){
                    Clave = Clave + "1";
                }else{
                    Clave = Clave + "0";
                }
                if(SW2){
                    Clave = Clave + "1";
                }else{
                    Clave = Clave + "0";
                }
                if(SW3){
                    Clave = Clave + "1";
                }else{
                    Clave = Clave + "0";
                }
                if(SW4){
                    Clave = Clave + "1";
                }else{
                    Clave = Clave + "0";
                }
                if(SW5){
                    Clave = Clave + "1";
                }else{
                    Clave = Clave + "0";
                }

                btSocket.getOutputStream().write(Clave.getBytes());
                btSocket.getOutputStream().flush();
            }
            catch (IOException e)
            {
                //msg("Error");
            }
        }
    }

    public void updateAlarms (String Clave) {
        try {
            btSocket.getOutputStream().write(Clave.getBytes());
            btSocket.getOutputStream().flush();
            btSocket.getOutputStream().write(":".toString().getBytes());
        }

        catch (Exception e) {

        }
    }

    public String getDataAlarmSensors () {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("UPDATEAlarmSensors".toString().getBytes());
                btSocket.getOutputStream().flush();
                btSocket.getOutputStream().write(":".toString().getBytes());

            }
            catch (IOException e)
            {
                // msg("Error");
            }
        }
        return AlarmSensorsData;
    }
    public void turnOnrgb()
    {
        try
        {
            String value2;
            value2="RGB1ON";
            Log.d("RGB","mandando turn onn led");
            btSocket.getOutputStream().write(value2.getBytes());
            btSocket.getOutputStream().flush();
            btSocket.getOutputStream().write(":".toString().getBytes());


        }
        catch (IOException e)
        {

        }
    }
    public void turnOffrgb()
    {
        try
        {
            String value2;
            value2="RGB1OFF";
            Log.d("RGB","mandando turn offled");
            btSocket.getOutputStream().write(value2.getBytes());
            btSocket.getOutputStream().flush();
            btSocket.getOutputStream().write(":".toString().getBytes());


        }
        catch (IOException e)
        {

        }}
    public void turnOnrgb2()
    {
        try
        {
            String value2;
            value2="RGB2ON";
            btSocket.getOutputStream().write(value2.getBytes());
            btSocket.getOutputStream().flush();
            btSocket.getOutputStream().write(":".toString().getBytes());
            Log.d("RGB","mandando turn onled2rgb");

        }
        catch (IOException e)
        {

        }
    }
    public void turnOffrgb2()
    {
        try
        {
            String value2;
            value2="RGB2OFF";
            btSocket.getOutputStream().write(value2.getBytes());
            btSocket.getOutputStream().flush();
            btSocket.getOutputStream().write(":".toString().getBytes());


        }
        catch (IOException e)
        {

        }
    }
    public void RGB1COLOR()
    {           ColorPickerDialogBuilder
            .with(AccessActivity.this)
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
                    String a,c,d;
                    int r = (selectedColor >> 16) & 0xFF;
                    int g = (selectedColor >> 8) & 0xFF;
                    int b = (selectedColor >> 0) & 0xFF;
                    if(r==0)
                    {
                        a="000";
                    }
                    else
                    {
                        if(r<10)
                        {
                            a="00"+Integer.toString(r);
                        }
                        else if (r<100)
                        {
                            a="0"+Integer.toString(r);
                        }
                        else
                        {a=Integer.toString(r);}

                    }
                    if(g==0)
                    {
                        c="000";
                    }
                    else
                    {
                        if(g<10)
                        {
                            c="00"+Integer.toString(g);
                        }
                        else if (g<100)
                        {
                            c="0"+Integer.toString(g);
                        }
                        else
                        {c=Integer.toString(g);}

                    }
                    if(b==0)
                    {
                        d="000";
                    }
                    else
                    {
                        if(b<10)
                        {
                            d="00"+Integer.toString(b);
                        }
                        else if (b<100)
                        {
                            d="0"+Integer.toString(b);
                        }
                        else
                        {d=Integer.toString(b);}

                    }
                    try
                    {
                        String value2;
                        value2="RGB1D"+a+c+d;
                        btSocket.getOutputStream().write(value2.getBytes());
                        btSocket.getOutputStream().flush();
                        btSocket.getOutputStream().write(":".toString().getBytes());


                    }
                    catch (IOException e)
                    {

                    }
                    Log.d("RGB", "R [" + a + "] - G [" + c + "] - B [" + d + "]");

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
    public void RGB1COLORPARAMETER(String rgb)
    {
                    try
                    {
                        String value2;
                        value2="RGB1D"+rgb;
                        btSocket.getOutputStream().write(value2.getBytes());
                        btSocket.getOutputStream().flush();
                        btSocket.getOutputStream().write(":".toString().getBytes());


                    }
                    catch (IOException e)
                    {

                    }





    }
    public void RGB2COLORPARAMETER(String rgb)
    {
        try
        {
            String value2;
            value2="RGB2D"+rgb;
            btSocket.getOutputStream().write(value2.getBytes());
            btSocket.getOutputStream().flush();
            btSocket.getOutputStream().write(":".toString().getBytes());


        }
        catch (IOException e)
        {

        }





    }
    public void RGB2COLOR()
    {
        ColorPickerDialogBuilder
                .with(AccessActivity.this)
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
                        String a,c,d;
                        int r = (selectedColor >> 16) & 0xFF;
                        int g = (selectedColor >> 8) & 0xFF;
                        int b = (selectedColor >> 0) & 0xFF;
                        if(r==0)
                        {
                            a="000";
                        }
                        else
                        {
                            if(r<10)
                            {
                                a="00"+Integer.toString(r);
                            }
                            else if (r<100)
                            {
                                a="0"+Integer.toString(r);
                            }
                            else
                            {a=Integer.toString(r);}

                        }
                        if(g==0)
                        {
                            c="000";
                        }
                        else
                        {
                            if(g<10)
                            {
                                c="00"+Integer.toString(g);
                            }
                            else if (g<100)
                            {
                                c="0"+Integer.toString(g);
                            }
                            else
                            {c=Integer.toString(g);}

                        }
                        if(b==0)
                        {
                            d="000";
                        }
                        else
                        {
                            if(b<10)
                            {
                                d="00"+Integer.toString(b);
                            }
                            else if (b<100)
                            {
                                d="0"+Integer.toString(b);
                            }
                            else
                            {d=Integer.toString(b);}

                        }
                        try
                        {
                            String value2;
                            value2="RGB2D"+a+c+d;
                            btSocket.getOutputStream().write(value2.getBytes());
                            btSocket.getOutputStream().flush();
                            btSocket.getOutputStream().write(":".toString().getBytes());


                        }
                        catch (IOException e)
                        {

                        }
                        Log.d("RGB", "R [" + a + "] - G [" + c + "] - B [" + d + "]");

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
    public void turnonfan1()
    {
        try
        {
            String value2;
            value2="FAN1ON";
            btSocket.getOutputStream().write(value2.getBytes());
            btSocket.getOutputStream().flush();
            btSocket.getOutputStream().write(":".toString().getBytes());


        }
        catch (IOException e)
        {

        }
    }
    public void turnofffan1()
    {
        try
        {
            String value2;
            value2="FAN1OFF";
            btSocket.getOutputStream().write(value2.getBytes());
            btSocket.getOutputStream().flush();
            btSocket.getOutputStream().write(":".toString().getBytes());


        }
        catch (IOException e)
        {

        }
    }
    public void turnonfan2()
    {
        try
        {
            String value2;
            value2="FAN2ON";
            btSocket.getOutputStream().write(value2.getBytes());
            btSocket.getOutputStream().flush();
            btSocket.getOutputStream().write(":".toString().getBytes());


        }
        catch (IOException e)
        {

        }
    }
    public void turnofffan2()
    {
        try
        {
            String value2;
            value2="FAN2OFF";
            btSocket.getOutputStream().write(value2.getBytes());
            btSocket.getOutputStream().flush();
            btSocket.getOutputStream().write(":".toString().getBytes());


        }
        catch (IOException e)
        {

        }
    }
    public void turnonsmart()
    {
        try
        {
            String value2;
            value2="SMARTON";
            btSocket.getOutputStream().write(value2.getBytes());
            btSocket.getOutputStream().flush();
            btSocket.getOutputStream().write(":".toString().getBytes());

        }
        catch (IOException e)
        {

        }
    }
    public void turnoffsmart()
    {
        try
        {
            String value2;
            value2="SMARTOFF";
            btSocket.getOutputStream().write(value2.getBytes());
            btSocket.getOutputStream().flush();
            btSocket.getOutputStream().write(":".toString().getBytes());


        }
        catch (IOException e)
        {

        }
    }
    public void turnonsmart2()
    {
        try
        {
            String value2;
            value2="SMARTON2";
            btSocket.getOutputStream().write(value2.getBytes());
            btSocket.getOutputStream().flush();
            btSocket.getOutputStream().write(":".toString().getBytes());


        }
        catch (IOException e)
        {

        }
    }
    public void turnoffsmart2()
    {
        try
        {
            String value2;
            value2="SMARTOFF2";
            btSocket.getOutputStream().write(value2.getBytes());
            btSocket.getOutputStream().flush();
            btSocket.getOutputStream().write(":".toString().getBytes());


        }
        catch (IOException e)
        {

        }
    }
}