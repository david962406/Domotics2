package com.fiuady.android.domotics.db;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

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

    /*public void insertUser(int Id, String FN, String LN, String UN, String Password, String email) {
        int newID = Id + 1;
        String query = "INSERT INTO accounts (id, first_name, last_name, username, password, e_mail) " +
                "VALUES("+String.valueOf(newID)+", '"+FN+"', '"+LN+"','"+UN+"','"+Password+"', '"+ email+"');";
        db.execSQL(query);
    }*/

    public int getLastUserId(){
        int id = -1;
        UserCursor cursor = new UserCursor(db.rawQuery("SELECT MAX(u.id) From users u;", null));
        while (cursor.moveToNext()){
            id = cursor.getInt(0);
        }
        return id;
    }

}

