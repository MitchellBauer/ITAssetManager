package com.bauerperception.itassetmanager.DAO;

import com.bauerperception.itassetmanager.model.AssetEntity;
import com.bauerperception.itassetmanager.util.TimeUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            String assignedToName = result.getString("assigned_name");
            String location = result.getString("location");
            String purchasedDateString = result.getString("purchased_date");
            float purchasedPrice = result.getFloat("purchased_price");

            Calendar purchasedDate = TimeUtil.importUTCMySQLToCal(purchasedDateString);

            AssetEntity assetResult = new AssetEntity(assetID, assetName, assetType, assetModel, assetDescription, assignedToID, assignedToName, location, purchasedDate, purchasedPrice);
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
}
