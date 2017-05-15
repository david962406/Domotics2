package com.fiuady.android.domotics.db.Tables;

public class UserData extends Users{
    public UserData(int id, String password, int isAdmin, String userName, int lastConfiguration, String firstName, String lastName) {
        super(id, password, isAdmin, userName, lastConfiguration);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
