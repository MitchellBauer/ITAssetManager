package com.bauerperception.itassetmanager.model;

public class EquipmentEntity {
    private int equipmentID;
    private int assignedLoadOutID;
    private int loadOutSlotNum;
    private String name;
    private String modelNum;
    private float purchasePrice;
    private float lastPurchasedPrice;
    private String whereToPurchaseURL;
    private int quantityNeeded;
    //TODO Probably need to convert type to an actual OO?
    private String equipmentType;

    public EquipmentEntity(int equipmentID, int assignedLoadOutID, int loadOutSlotNum, String name, String modelNum, float purchasePrice, float lastPurchasedPrice, String whereToPurchaseURL, int quantityNeeded, String equipmentType) {
        this.equipmentID = equipmentID;
        this.assignedLoadOutID = assignedLoadOutID;
        this.loadOutSlotNum = loadOutSlotNum;
        this.name = name;
        this.modelNum = modelNum;
        this.purchasePrice = purchasePrice;
        this.lastPurchasedPrice = lastPurchasedPrice;
        this.whereToPurchaseURL = whereToPurchaseURL;
        this.quantityNeeded = quantityNeeded;
        this.equipmentType = equipmentType;
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

    public void setLoadOutSlotNum(int loadOutSlotNum) {
        this.loadOutSlotNum = loadOutSlotNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelNum() {
        return modelNum;
    }

    public void setModelNum(String modelNum) {
        this.modelNum = modelNum;
    }

    public float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public float getLastPurchasedPrice() {
        return lastPurchasedPrice;
    }

    public void setLastPurchasedPrice(float lastPurchasedPrice) {
        this.lastPurchasedPrice = lastPurchasedPrice;
    }

    public String getWhereToPurchaseURL() {
        return whereToPurchaseURL;
    }

    public void setWhereToPurchaseURL(String whereToPurchaseURL) {
        this.whereToPurchaseURL = whereToPurchaseURL;
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

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }
}
