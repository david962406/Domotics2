package com.fiuady.android.domotics.db.Tables;

public class deviceConfiguration {
    public deviceConfiguration(int deviceId, int sensorActive, String data, int deviceActive, int idConfiguration) {
        this.deviceId = deviceId;
        this.sensorActive = sensorActive;
        this.data = data;
        this.deviceActive = deviceActive;
        this.idConfiguration = idConfiguration;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public int getSensorActive() {
        return sensorActive;
    }

    public String getData() {
        return data;
    }

    public int getDeviceActive() {
        return deviceActive;
    }

    public int getIdConfiguration() {
        return idConfiguration;
    }

    int deviceId;
    int sensorActive;
    String data;
    int deviceActive;
    int idConfiguration;
}
