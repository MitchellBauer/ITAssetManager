package com.bauerperception.itassetmanager.model;

public class MissingData {
    private int ID;
    private String missingDataType;

    public MissingData(int ID, String missingDataType) {
        this.ID = ID;
        this.missingDataType = missingDataType;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMissingDataType() {
        return missingDataType;
    }

    public void setMissingDataType(String missingDataType) {
        this.missingDataType = missingDataType;
    }
}
