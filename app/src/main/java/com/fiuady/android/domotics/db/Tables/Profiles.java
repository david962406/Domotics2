package com.fiuady.android.domotics.db.Tables;

public class Profiles {
    public Profiles(int id, int userid, String name) {
        this.id = id;
        this.userid = userid;
        this.name = name;
    }

    private int id;
    private int userid;
    private String name;

    public int getId() {
        return id;
    }

    public int getUserid() {
        return userid;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
