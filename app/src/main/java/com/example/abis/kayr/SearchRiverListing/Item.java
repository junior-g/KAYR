package com.example.abis.kayr.SearchRiverListing;

/**
 * Created by abis.
 */

public class Item {
    String RiverName;
    double latitude, longitude;

    public Item(String riverName) {
        RiverName = riverName;
    }

    public void setRiverName(String riverName) {
        RiverName = riverName;
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

    public String getRiverName() {
        return RiverName.toUpperCase();
    }
}
