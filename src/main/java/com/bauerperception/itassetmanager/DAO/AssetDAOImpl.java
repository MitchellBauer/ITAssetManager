package com.bauerperception.itassetmanager.DAO;

import com.bauerperception.itassetmanager.model.AssetEntity;
import com.bauerperception.itassetmanager.util.TimeUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class AssetDAOImpl {
    private static Statement stmt;

    public static ObservableList<AssetEntity> getAllAssets() throws Exception {
        ObservableList<AssetEntity> allAssets = FXCollections.observableArrayList();
        DBConn.makeConn();
        String sqlStatement = "SELECT * FROM asset";
        stmt = DBConn.conn.createStatement();
        ResultSet result = stmt.executeQuery(sqlStatement);

        while(result.next()){
            int assetID = result.getInt("idasset");
            String assetName = result.getString("name");
            String assetType = result.getString("type");
            String assetModel = result.getString("model_num");
            String assetDescription = result.getString("description");
            int assignedToID = result.getInt("assigned_to");
            int location = result.getInt("location");
            String purchasedDateString = result.getString("purchased_date");
            float purchasedPrice = result.getFloat("purchased_price");

            LocalDate purchasedDate = TimeUtil.importMySQLToLocalDate(purchasedDateString);

            AssetEntity assetResult = new AssetEntity(assetID, assetName, assetType, assetModel, assetDescription, assignedToID, location, purchasedDate, purchasedPrice);
            allAssets.add(assetResult);
        }
        DBConn.closeConn();
        return allAssets;
    }

    public static ObservableList<String> getAssetTypes() throws Exception{
        ObservableList<String> allAssetTypes = FXCollections.observableArrayList();
        DBConn.makeConn();
        String sqlStatement = "SELECT * FROM assettypes";
        stmt = DBConn.conn.createStatement();
        ResultSet result = stmt.executeQuery(sqlStatement);

        while(result.next()){
            //TODO May need to use this asset ID and then this will need class
            int assetID = result.getInt("idassettypes");
            String assetName = result.getString("type_name");

            allAssetTypes.add(assetName);
        }
        DBConn.closeConn();
        return allAssetTypes;
    }

    public static void addAsset(AssetEntity assetEntity) throws Exception {
        PreparedStatement ps;
        Connection conn = DBConn.getConn();

        String sqlStatement = "INSERT INTO asset (name, type, model_num, description, assigned_to, location, purchased_date, purchased_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sqlStatement);
        ps.setString(1, assetEntity.getAssetName());
        ps.setString(2, assetEntity.getAssetType());
        ps.setString(3, assetEntity.getAssetModel());
        ps.setString(4, assetEntity.getAssetDescription());
        ps.setInt(5, assetEntity.getAssignedToID());
        ps.setInt(6, assetEntity.getLocationID());
        ps.setString(7, TimeUtil.exportLocalDateToMySQL(assetEntity.getPurchasedDate()));
        ps.setFloat(8, assetEntity.getPurchasedPrice());
        ps.executeUpdate();
        DBConn.closeConn();
    }

    public static void updateAsset(AssetEntity assetEntity) throws Exception {
        PreparedStatement ps;
        Connection conn = DBConn.getConn();

        String sqlStatement = "UPDATE asset SET name = ?, type = ?, model_num = ?, description = ?, assigned_to = ?, location = ?, purchased_date = ?, purchased_price = ? WHERE idasset = ?;";
        ps = conn.prepareStatement(sqlStatement);
        ps.setString(1, assetEntity.getAssetName());
        ps.setString(2, assetEntity.getAssetType());
        ps.setString(3, assetEntity.getAssetModel());
        ps.setString(4, assetEntity.getAssetDescription());
        ps.setInt(5, assetEntity.getAssignedToID());
        ps.setInt(6, assetEntity.getLocationID());
        ps.setString(7, TimeUtil.exportLocalDateToMySQL(assetEntity.getPurchasedDate()));
        ps.setFloat(8, assetEntity.getPurchasedPrice());
        ps.setInt(9, assetEntity.getAssetID());
        ps.executeUpdate();
        DBConn.closeConn();
    }

    public static void deleteAsset(AssetEntity selectedAsset) throws Exception {
        DBConn.makeConn();
        String sqlStatement = "DELETE FROM asset WHERE idasset  = '" + selectedAsset.getAssetID() + "'";
        stmt = DBConn.conn.createStatement();
        stmt.executeUpdate(sqlStatement);
        DBConn.closeConn();
    }
}
