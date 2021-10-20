package com.bauerperception.itassetmanager.model;

import java.time.LocalDate;

public class AssetEntity implements Entity{
    private int assetID;
    private String assetManufacturer;
    private String assetType;
    private String assetModel;
    private String assetDescription;
    private int assignedToID;
    private int locationID;
    private LocalDate purchasedDate;
    private double purchasedPrice;

    private boolean isAccountedFor; //Used for the Need to Purchase Report
    //Future Serial no.?

    public AssetEntity(int assetID, String assetManufacturer, String assetType, String assetModel, String assetDescription, int assignedToID, int locationID, LocalDate purchasedDate, double purchasedPrice) {
        this.assetID = assetID;
        this.assetManufacturer = assetManufacturer;
        this.assetType = assetType;
        this.assetModel = assetModel;
        this.assetDescription = assetDescription;
        this.assignedToID = assignedToID;
        this.locationID = locationID;
        this.purchasedDate = purchasedDate;
        this.purchasedPrice = purchasedPrice;
    }

    public AssetEntity(String assetManufacturer, String assetType, String assetModel, String assetDescription, int assignedToID, int locationID, LocalDate purchasedDate, double purchasedPrice) {
        this.assetManufacturer = assetManufacturer;
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

    public String getAssetManufacturer() {
        return assetManufacturer;
    }

    public void setAssetManufacturer(String assetManufacturer) {
        this.assetManufacturer = assetManufacturer;
    }

    public String getAssetType() {
        return assetType;
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

    public int getAssignedToID() {
        return assignedToID;
    }

    public int getLocationID() {
        return locationID;
    }

    public LocalDate getPurchasedDate() {
        return purchasedDate;
    }

    public double getPurchasedPrice() {
        return purchasedPrice;
    }

    @Override
    public int getID() {
        return assetID;
    }

    public boolean getIsAccounted() {
        return this.isAccountedFor;
    }

    /*
    Sets if the Asset has been counted in the "Need to Purchase" report
     */
    public void setAccountedFor(boolean accountedFor) {
        isAccountedFor = accountedFor;
    }
}
