package com.fiuady.android.domotics.db.Tables;

public class Doors {
    public Doors(int doorid, int password) {
        this.id = id;
        this.password = password;
    }

    private int id;
    private int password;

    public int getId() {
        return id;
    }
    //
    public int getPassword() {
        return password;
    }
}
