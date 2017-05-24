package com.fiuady.android.domotics.db;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fiuady.android.domotics.db.Tables.Profiles;
import com.fiuady.android.domotics.db.Tables.UserData;
import com.fiuady.android.domotics.db.Tables.Users;
import com.fiuady.android.domotics.db.Tables.deviceConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristian Avalos on 13/05/2017.
 */

public final class Inventory {

    class UserCursor extends CursorWrapper{
        public  UserCursor(Cursor cursor) {
            super(cursor);
        }
        public UserData getUser(){
            Cursor cursor= getWrappedCursor();
            return new UserData(cursor.getInt(cursor.getColumnIndex(DomoticsDbSchema.UsersTable.Columns.ID)),
                    cursor.getString(cursor.getColumnIndex(DomoticsDbSchema.UsersTable.Columns.PASSWORD)),
                    cursor.getInt(cursor.getColumnIndex(DomoticsDbSchema.UsersTable.Columns.ADMIN)),
                    cursor.getString(cursor.getColumnIndex(DomoticsDbSchema.UsersTable.Columns.USERNAME)),
                    cursor.getInt(cursor.getColumnIndex(DomoticsDbSchema.UsersTable.Columns.LAST_CONFIGURATION)),
                    cursor.getString(cursor.getColumnIndex(DomoticsDbSchema.UserDataTable.Columns.FIRST_NAME)),
                    cursor.getString(cursor.getColumnIndex(DomoticsDbSchema.UserDataTable.Columns.LAST_NAME)));
        }
    }

    class ProfileCursor extends CursorWrapper {
        public ProfileCursor (Cursor cursor){super(cursor);}
        public Profiles getProfiles () {
            Cursor cursor = getWrappedCursor();
            return new Profiles(cursor.getInt(0), cursor.getInt(1), cursor.getString(2));
        }
    }

    class DeviceConfCursor extends CursorWrapper {
        public DeviceConfCursor (Cursor cursor) {super(cursor);}
        public deviceConfiguration getProfiles () {
            Cursor cursor = getWrappedCursor();
            return new deviceConfiguration(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                    cursor.getInt(3), cursor.getInt(4));
        }
    }

    private InventoryHelper inventoryHelper;
    private SQLiteDatabase db;
    public Inventory(Context context){
       //inventoryHelper=new InventoryHelper(getApplicationContext());
        inventoryHelper=new InventoryHelper(context);
        db=inventoryHelper.getWritableDatabase();

    }

    public  List<Users> getAllUsers(){
        ArrayList<Users> list=new ArrayList<Users>();
        UserCursor cursor = new UserCursor(db.rawQuery("SELECT \n" +
                "u.id AS id, u.password AS password, u.admin AS admin, u.user_name AS user_name, u.last_configuration AS last_configuration, ud.first_name AS first_name, ud.last_name AS last_name\n" +
                "FROM users u\n" +
                "INNER JOIN user_data ud WHERE (u.id = ud.user_id) ",null));
        while(cursor.moveToNext()){
            list.add(cursor.getUser());
        }
        cursor.close();
        return list;
    }

    public void newUser (int id, String firstName, String lastName, String userName, String password) {
        db.execSQL("INSERT INTO users  (id, password, admin, user_name, last_configuration) VALUES (" + id + ", '" + password +
                "', 0, '" + userName + "', NULL);");
        db.execSQL("INSERT INTO user_data  (user_id, first_name, last_name) " +
                "VALUES (" + id + ", '" + firstName + "', '" + lastName + "');");
        Log.d("prueba", "1");
    }

    public int getLastUserId(){
        Log.d("prueba", "2");
        Cursor cursor = db.rawQuery("SELECT MAX(id) FROM users", null);
        Log.d("prueba", "2.0");
        cursor.moveToFirst();
        Log.d("prueba", "2.2");
        int id = cursor.getInt(0);
        Log.d("prueba", "2.3");
        cursor.close();
        Log.d("prueba", "2.1");
        return id+1;
    }

    public List<Profiles> getProfiles () {
        ArrayList<Profiles> list = new ArrayList();
        ProfileCursor cursor = new ProfileCursor(db.rawQuery("SELECT * \n" +
                "FROM configuration", null));
        while (cursor.moveToNext()) {

            list.add(cursor.getProfiles());
        }
        cursor.close();

        return list;
    }

    public List<deviceConfiguration> getProfilesById (int id) {
        DeviceConfCursor cursor = new DeviceConfCursor(db.rawQuery("SELECT * \n" +
                "FROM device_configuration\n" +
                "WHERE id_configuration = " + id + ";", null));

        ArrayList<deviceConfiguration> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(cursor.getProfiles());
        }

        cursor.close();

        return list;
    }

    public void newProfile (int data[], int rgb1[], int rgb2[],  String profile, int userid) {
        //Insertando datos en sql

        //Recuperando id..
        Cursor cursor = db.rawQuery("SELECT MAX (id) + 1\n" +
                "FROM configuration", null);
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        cursor.close();

        //configuration
        db.execSQL("INSERT INTO configuration (id, user_id, description) VALUES (" + id  + ", " + userid + ", '" + profile + "');");
        //device_configuration
        //--Cuartos--

        //Ventilador cuarto 1
        db.execSQL("INSERT INTO device_configuration (device_id, sensor_active, data, device_active, id_configuration) VALUES (2, " + data[1] + ", NULL, " + data[0] + ", " + id + ");");
        //Rgb cuarto 1
        String rgb = Integer.toString(rgb1[0]) + "-" + Integer.toString(rgb1[1]) + "-" + Integer.toString(rgb1[2]);
        db.execSQL("INSERT INTO device_configuration (device_id, sensor_active, data, device_active, id_configuration) VALUES (6, NULL, "   + rgb + ", " + data[2] + ", " + id + ");");
        //Ventilador cuarto 2
        db.execSQL("INSERT INTO device_configuration (device_id, sensor_active, data, device_active, id_configuration) VALUES (3, " + data[4] + ", NULL, " + data[3] + ", " + id + ");");
        //Rgb cuarto 2
        rgb = Integer.toString(rgb2[0]) + "-" + Integer.toString(rgb2[1]) + "-" + Integer.toString(rgb2[2]);
        db.execSQL("INSERT INTO device_configuration (device_id, sensor_active, data, device_active, id_configuration) VALUES (7, NULL, "   + rgb + ", " + data[5] + ", " + id + ");");

        //--Exteriores--
        //Led externo
        db.execSQL("INSERT INTO device_configuration (device_id, sensor_active, data, device_active, id_configuration) VALUES (0, " + data[7] + ", " + data[8] + ", " + data[6] + ", " + id + ");");
        //Led piscina
        db.execSQL("INSERT INTO device_configuration (device_id, sensor_active, data, device_active, id_configuration) VALUES (1, " + data[10] + ", " + data[11] + ", " + data[9] + ", " + id + ");");

        //--Alarmas--
        //Movimiento
        db.execSQL("INSERT INTO device_configuration (device_id, sensor_active, data, device_active, id_configuration) VALUES (9, NULL, NULL , " + data[12] + ", " + id + ");");
        //Puerta 1
        db.execSQL("INSERT INTO device_configuration (device_id, sensor_active, data, device_active, id_configuration) VALUES (10, " + data[13] + ", NULL , 1, " + id + ");");
        //Puerta 2
        db.execSQL("INSERT INTO device_configuration (device_id, sensor_active, data, device_active, id_configuration) VALUES (11, " + data[14] + ", NULL , 1, " + id + ");");
        //Ventana 1
        db.execSQL("INSERT INTO device_configuration (device_id, sensor_active, data, device_active, id_configuration) VALUES (12, " + data[15] + ", NULL , 1, " + id + ");");
        //Ventana 2
        db.execSQL("INSERT INTO device_configuration (device_id, sensor_active, data, device_active, id_configuration) VALUES (13, " + data[16] + ", NULL , 1, " + id + ");");
        //Ventana 3
        db.execSQL("INSERT INTO device_configuration (device_id, sensor_active, data, device_active, id_configuration) VALUES (14, " + data[17] + ", NULL , 1, " + id + ");");
    }

}

