package com.bauerperception.itassetmanager.model;

public class EquipmentEntity {
    private int equipmentID;
    private int assignedLoadOutID;
    private int loadOutSlotNum;
    private String mfr;
    private String modelNum;
    private double purchasePrice;
    private double lastPurchasedPrice;
    private String whereToPurchaseURL;
    private int quantityNeeded;
    private String equipmentType;
    //These two fields are only used in reports. Not the best implementation but it does work.
    private int purchaseForEmployeeID;
    private int purchaseForLocation;

    public EquipmentEntity(int equipmentID, String mfr, String modelNum, String equipmentType, int loadOutID, int loadOutSlotNum, int quantity, double purchasePrice, double lastPurchasePrice, String purchaseUrl) {
        this.equipmentID = equipmentID;
        this.assignedLoadOutID = loadOutID;
        this.loadOutSlotNum = loadOutSlotNum;
        this.mfr = mfr;
        this.modelNum = modelNum;
        this.purchasePrice = purchasePrice;
        this.lastPurchasedPrice = purchasePrice;
        this.whereToPurchaseURL = purchaseUrl;
        this.quantityNeeded = quantity;
        this.equipmentType = equipmentType;
    }

    public EquipmentEntity(int equipmentID, String mfr, String modelNum, String equipmentType, int loadOutID, int slotNum, int quantity, double purchasePrice, String purchaseUrl) {
        this.equipmentID = equipmentID;
        this.assignedLoadOutID = loadOutID;
        this.loadOutSlotNum = slotNum;
        this.mfr = mfr;
        this.modelNum = modelNum;
        this.purchasePrice = purchasePrice;
        this.whereToPurchaseURL = purchaseUrl;
        this.quantityNeeded = quantity;
        this.equipmentType = equipmentType;
    }

    public EquipmentEntity(String mfr, String modelNum, String equipmentType, int loadOutID, int slotNum, int quantity, double purchasePrice, String purchaseUrl) {
        this.assignedLoadOutID = loadOutID;
        this.loadOutSlotNum = slotNum;
        this.mfr = mfr;
        this.modelNum = modelNum;
        this.purchasePrice = purchasePrice;
        this.whereToPurchaseURL = purchaseUrl;
        this.quantityNeeded = quantity;
        this.equipmentType = equipmentType;
    }

    public int getPurchaseForLocation() {
        return purchaseForLocation;
    }

    public void setPurchaseForLocation(int purchaseForLocation) {
        this.purchaseForLocation = purchaseForLocation;
    }

    public int getPurchaseForEmployeeID() {
        return purchaseForEmployeeID;
    }

    public void setPurchaseForEmployeeID(int purchaseForEmployeeID) {
        this.purchaseForEmployeeID = purchaseForEmployeeID;
    }

    public int getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

    public int getAssignedLoadOutID() {
        return assignedLoadOutID;
    }

    public void setAssignedLoadOutID(int assignedLoadOutID) {
        this.assignedLoadOutID = assignedLoadOutID;
    }

    public int getLoadOutSlotNum() {
        return loadOutSlotNum;
    }

    public String getMfr() {
        return mfr;
    }

    public String getModelNum() {
        return modelNum;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public String getWhereToPurchaseURL() {
        return whereToPurchaseURL;
    }

    public int getQuantityNeeded() {
        return quantityNeeded;
    }

    public void setQuantityNeeded(int quantityNeeded) {
        this.quantityNeeded = quantityNeeded;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    @Override
    public String toString() {
        return equipmentType + " " + mfr + " model=" + modelNum + " $" + purchasePrice;
    }
}
