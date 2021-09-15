package com.bauerperception.itassetmanager.model;

import java.util.ArrayList;

public class LocationEntity implements Entity{
    private int locationID;
    private String locationName;
    private ArrayList<LoadOutEntity> assignedLoadOuts;

    public LocationEntity(int locationID, String locationName) {
        this.locationID = locationID;
        this.locationName = locationName;
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
