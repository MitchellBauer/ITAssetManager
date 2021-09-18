package com.bauerperception.itassetmanager.model;

import java.util.ArrayList;

public class LoadOutEntity implements Entity{
    private int loadOutID;

    private String loadOutName;

    private ArrayList<EquipmentEntity> equipmentList;

    public LoadOutEntity(int loadOutID, String loadOutName, ArrayList<EquipmentEntity> equipmentList) {
        this.loadOutID = loadOutID;
        this.loadOutName = loadOutName;
        this.equipmentList = equipmentList;
    }

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

    public void setLoadOutName(String loadOutName) {
        this.loadOutName = loadOutName;
    }

    public int getLoadOutID() {
        return loadOutID;
    }

    public void setLoadOutID(int loadOutID) {
        this.loadOutID = loadOutID;
    }

    public ArrayList<EquipmentEntity> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(ArrayList<EquipmentEntity> equipmentList) {
        this.equipmentList = equipmentList;
    }

    @Override
    public int getID() {
        return loadOutID;
    }
}
