package com.bauerperception.itassetmanager.model;

import java.time.LocalDate;
import java.util.Calendar;

public class AssetEntity implements Entity{
    private int assetID;
    private String assetName;

    private String assetType;
    private String assetModel;
    
    private String assetDescription;
    private int assignedToID;

    private int locationID;
    private LocalDate purchasedDate;
    private float purchasedPrice;

    public AssetEntity(int assetID, String assetName, String assetType, String assetModel, String assetDescription, int assignedToID, int locationID, LocalDate purchasedDate, float purchasedPrice) {
        this.assetID = assetID;
        this.assetName = assetName;
        this.assetType = assetType;
        this.assetModel = assetModel;
        this.assetDescription = assetDescription;
        this.assignedToID = assignedToID;
        this.locationID = locationID;
        this.purchasedDate = purchasedDate;
        this.purchasedPrice = purchasedPrice;
    }

    public AssetEntity(String assetName, String assetType, String assetModel, String assetDescription, int assignedToID, int locationID, LocalDate purchasedDate, float purchasedPrice) {
        this.assetName = assetName;
        this.assetType = assetType;
        this.assetModel = assetModel;
        this.assetDescription = assetDescription;
        this.assignedToID = assignedToID;
        this.locationID = locationID;
        this.purchasedDate = purchasedDate;
        this.purchasedPrice = purchasedPrice;
    }

    public int getAssetID() {
        return assetID;
    }

    public void setAssetID(int assetID) {
        this.assetID = assetID;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getAssetModel() {
        return assetModel;
    }

    public void setAssetModel(String assetModel) {
        this.assetModel = assetModel;
    }

    public String getAssetDescription() {
        return assetDescription;
    }

    public void setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
    }

    public int getAssignedToID() {
        return assignedToID;
    }

    public void setAssignedToID(int assignedToID) {
        this.assignedToID = assignedToID;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public LocalDate getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(LocalDate purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public float getPurchasedPrice() {
        return purchasedPrice;
    }

    public void setPurchasedPrice(float purchasedPrice) {
        this.purchasedPrice = purchasedPrice;
    }

    @Override
    public int getID() {
        return assetID;
    }
}
