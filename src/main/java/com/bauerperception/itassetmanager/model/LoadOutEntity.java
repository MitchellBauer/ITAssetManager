package com.bauerperception.itassetmanager.model;

public class LoadOutEntity implements Entity{
    private int loadOutID;

    private String loadOutName;

    public LoadOutEntity(int loadOutID, String name) {
        this.loadOutID = loadOutID;
        this.loadOutName = name;
    }

    public LoadOutEntity(String text) {
        this.loadOutName = text;
    }

    public String getLoadOutName() {
        return loadOutName;
    }

    public int getLoadOutID() {
        return loadOutID;
    }

    public void setLoadOutID(int loadOutID) {
        this.loadOutID = loadOutID;
    }

    @Override
    public int getID() {
        return loadOutID;
    }

    @Override
    public String toString() {
        return "ID: " + loadOutID + " Name: " + loadOutName;
    }
}
