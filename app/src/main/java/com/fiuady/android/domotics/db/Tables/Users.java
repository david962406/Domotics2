package com.fiuady.android.domotics.db.Tables;

public class Users {
    public Users(int id, String password, int isAdmin, String userName, int lastConfiguration) {
        this.id = id;
        this.password = password;
        this.isAdmin = isAdmin;
        this.userName = userName;
        this.lastConfiguration = lastConfiguration;
    }

    private int id;
    private String password;
    private int isAdmin;
    private String userName;
    private int lastConfiguration;

    public int getId() {
        return id;
    }
//
    public String getPassword() {
        return password;
    }

    public int isAdmin() {
        return isAdmin;
    }

    public String getUserName() {
        return userName;
    }

    public int getLastConfiguration() {
        return lastConfiguration;
    }
}
