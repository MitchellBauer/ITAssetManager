package com.bauerperception.itassetmanager.model;

import java.util.ArrayList;

public class LocationEntity implements Entity{
    private int locationID;
    private String locationName;
    private int loadOutID;

    public LocationEntity(int locationID, String locationName, int assignedLoadOut) {
        this.locationID = locationID;
        this.locationName = locationName;
        this.loadOutID = assignedLoadOut;
    }

    public int getLoadOutID() {
        return loadOutID;
    }

    public void setLoadOutID(int loadOutID) {
        this.loadOutID = loadOutID;
    }

    public LocationEntity(String workLocationName) {
        this.locationName = workLocationName;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    @Override
    public int getID() {
        return locationID;
    }

    @Override
    public String toString() {
        return locationName;
    }
}
