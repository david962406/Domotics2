package com.fiuady.android.domotics.db;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.fiuady.android.domotics.db.Tables.Doors;
import com.fiuady.android.domotics.db.Tables.UserData;
import com.fiuady.android.domotics.db.Tables.Users;

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

    class DoorCursor extends CursorWrapper{
        public  DoorCursor(Cursor cursor) {
            super(cursor);
        }
        public Doors getDoor(){
            Cursor cursor= getWrappedCursor();
            return new Doors(cursor.getInt(cursor.getColumnIndex(DomoticsDbSchema.DoorsTable.Columns.ID)),
                    cursor.getInt(cursor.getColumnIndex(DomoticsDbSchema.DoorsTable.Columns.PASSWORD)));
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
        db.execSQL("INSERT INTO users  (id, password, admin, user_name, last_configuration) VALUES (" + id + ", '" + password + "', 0, '" + userName + "', NULL);");
        db.execSQL("INSERT INTO user_data  (user_id, first_name, last_name) VALUES (" + id + ", '" + firstName + "', '" + lastName + "');");
    }

    public int getDoorPassword () {
        ArrayList<Doors> list=new ArrayList<Doors>();
        Cursor cursor = db.rawQuery("SELECT d.id AS id, d.password AS password FROM doors d", null);

        cursor.moveToPosition(0);
        int doorpassword = cursor.getInt(1);
        cursor.close();
        return doorpassword;
    }

    public int getLastUserId(){
        Cursor cursor = db.rawQuery("SELECT MAX(u.id)+1 FROM users u;", null);
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        cursor.close();
        return id;
    }

    public void modifyMainDoorPw (int password) {
        String stringNewPw = Integer.toString(password);
        db.execSQL("UPDATE doors\n" +
                "  SET password = " + stringNewPw + "\n" +
                "  WHERE id = 0");
    }

}

