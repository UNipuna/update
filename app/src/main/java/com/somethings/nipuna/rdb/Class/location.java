package com.somethings.nipuna.rdb.Class;

public class location {

    public String location_name;
    public double latitude;
    public double longitude;

    public location(String location_name, double latitude, double longitude) {
        this.location_name = location_name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
