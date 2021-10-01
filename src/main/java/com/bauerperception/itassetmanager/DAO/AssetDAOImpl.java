package com.bauerperception.itassetmanager.DAO;

import com.bauerperception.itassetmanager.model.AssetEntity;
import com.bauerperception.itassetmanager.util.TimeUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

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
            String assetManufacturer = result.getString("manufacturer");
            String assetType = result.getString("type");
            String assetModel = result.getString("model_num");
            String assetDescription = result.getString("description");
            int assignedToID = result.getInt("assigned_to");
            int location = result.getInt("location");
            String purchasedDateString = result.getString("purchased_date");
            double purchasedPrice = result.getDouble("purchased_price");

            LocalDate purchasedDate = TimeUtil.importMySQLToLocalDate(purchasedDateString);

            AssetEntity assetResult = new AssetEntity(assetID, assetManufacturer, assetType, assetModel, assetDescription, assignedToID, location, purchasedDate, purchasedPrice);
            allAssets.add(assetResult);
        }
        DBConn.closeConn();
        return allAssets;
    }

    public static ObservableList<String> getAssetTypes() throws Exception{
        //Future Can be renamed as types and probably should be pulled into it's own DAO
        ObservableList<String> allAssetTypes = FXCollections.observableArrayList();
        DBConn.makeConn();
        String sqlStatement = "SELECT * FROM assettypes";
        stmt = DBConn.conn.createStatement();
        ResultSet result = stmt.executeQuery(sqlStatement);

        while(result.next()){
            //int assetID = result.getInt("idassettypes");
            String typeName = result.getString("type_name");

            allAssetTypes.add(typeName);
        }
        DBConn.closeConn();
        return allAssetTypes;
    }

    public static void addAsset(AssetEntity assetEntity) throws Exception {
        PreparedStatement ps;
        Connection conn = DBConn.getConn();

        String sqlStatement = "INSERT INTO asset (manufacturer, type, model_num, description, assigned_to, location, purchased_date, purchased_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        ps = getPreparedAssetStatement(assetEntity, conn, sqlStatement);
        ps.executeUpdate();
        DBConn.closeConn();
    }

    private static PreparedStatement getPreparedAssetStatement(AssetEntity assetEntity, Connection conn, String sqlStatement) throws SQLException {
        PreparedStatement ps;
        ps = conn.prepareStatement(sqlStatement);
        ps.setString(1, assetEntity.getAssetManufacturer());
        ps.setString(2, assetEntity.getAssetType());
        ps.setString(3, assetEntity.getAssetModel());
        ps.setString(4, assetEntity.getAssetDescription());
        ps.setInt(5, assetEntity.getAssignedToID());
        ps.setInt(6, assetEntity.getLocationID());
        ps.setString(7, TimeUtil.exportLocalDateToMySQL(assetEntity.getPurchasedDate()));
        ps.setDouble(8, assetEntity.getPurchasedPrice());
        return ps;
    }

    public static void updateAsset(AssetEntity assetEntity) throws Exception {
        PreparedStatement ps;
        Connection conn = DBConn.getConn();

        String sqlStatement = "UPDATE asset SET manufacturer = ?, type = ?, model_num = ?, description = ?, assigned_to = ?, location = ?, purchased_date = ?, purchased_price = ? WHERE idasset = ?;";
        ps = getPreparedAssetStatement(assetEntity, conn, sqlStatement);
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

    public static ObservableList<AssetEntity> getAssetsByEmployeeID(int employeeID) throws Exception {
        ObservableList<AssetEntity> allAssets = FXCollections.observableArrayList();
        DBConn.makeConn();
        String sqlStatement = "SELECT * FROM asset WHERE assigned_to = " + employeeID;
        stmt = DBConn.conn.createStatement();
        ResultSet result = stmt.executeQuery(sqlStatement);

        while(result.next()){
            int assetID = result.getInt("idasset");
            String assetManufacturer = result.getString("manufacturer");
            String assetType = result.getString("type");
            String assetModel = result.getString("model_num");
            String assetDescription = result.getString("description");
            int assignedToID = result.getInt("assigned_to");
            int location = result.getInt("location");
            String purchasedDateString = result.getString("purchased_date");
            double purchasedPrice = result.getDouble("purchased_price");

            LocalDate purchasedDate = TimeUtil.importMySQLToLocalDate(purchasedDateString);

            AssetEntity assetResult = new AssetEntity(assetID, assetManufacturer, assetType, assetModel, assetDescription, assignedToID, location, purchasedDate, purchasedPrice);
            allAssets.add(assetResult);
        }
        DBConn.closeConn();
        return allAssets;
    }

    public static ObservableList<AssetEntity> getAllUnassignedAssets() throws Exception {
        ObservableList<AssetEntity> allAssets = FXCollections.observableArrayList();
        DBConn.makeConn();
        String sqlStatement = "SELECT * FROM asset WHERE assigned_to IS NUll OR assigned_to = -1";
        stmt = DBConn.conn.createStatement();
        ResultSet result = stmt.executeQuery(sqlStatement);

        while(result.next()){
            int assetID = result.getInt("idasset");
            String assetManufacturer = result.getString("manufacturer");
            String assetType = result.getString("type");
            String assetModel = result.getString("model_num");
            String assetDescription = result.getString("description");
            int assignedToID = result.getInt("assigned_to");
            int location = result.getInt("location");
            String purchasedDateString = result.getString("purchased_date");
            double purchasedPrice = result.getDouble("purchased_price");

            LocalDate purchasedDate = TimeUtil.importMySQLToLocalDate(purchasedDateString);

            AssetEntity assetResult = new AssetEntity(assetID, assetManufacturer, assetType, assetModel, assetDescription, assignedToID, location, purchasedDate, purchasedPrice);
            allAssets.add(assetResult);
        }
        DBConn.closeConn();
        return allAssets;
    }

    public static AssetEntity getAssetByID(int selectedAssetID) throws Exception {
        ObservableList<AssetEntity> allAssets = FXCollections.observableArrayList();
        DBConn.makeConn();
        String sqlStatement = "SELECT * FROM asset WHERE idasset = " + selectedAssetID;
        stmt = DBConn.conn.createStatement();
        ResultSet result = stmt.executeQuery(sqlStatement);

        while(result.next()){
            int assetID = result.getInt("idasset");
            String assetManufacturer = result.getString("manufacturer");
            String assetType = result.getString("type");
            String assetModel = result.getString("model_num");
            String assetDescription = result.getString("description");
            int assignedToID = result.getInt("assigned_to");
            int location = result.getInt("location");
            String purchasedDateString = result.getString("purchased_date");
            double purchasedPrice = result.getDouble("purchased_price");

            LocalDate purchasedDate = TimeUtil.importMySQLToLocalDate(purchasedDateString);

            AssetEntity assetResult = new AssetEntity(assetID, assetManufacturer, assetType, assetModel, assetDescription, assignedToID, location, purchasedDate, purchasedPrice);
            allAssets.add(assetResult);
        }
        DBConn.closeConn();
        if (allAssets.size() > 0){
            return allAssets.get(0);
        } else {
            return null;
        }
    }

    public static void addAssetWithCustomID(AssetEntity assetEntity, int customID) throws Exception {
        PreparedStatement ps;
        Connection conn = DBConn.getConn();
        String sqlStatement = "INSERT INTO asset (idasset, manufacturer, type, model_num, description, " +
                "assigned_to, location, purchased_date, purchased_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sqlStatement);
        ps.setInt(1, customID);
        ps.setString(2, assetEntity.getAssetManufacturer());
        ps.setString(3, assetEntity.getAssetType());
        ps.setString(4, assetEntity.getAssetModel());
        ps.setString(5, assetEntity.getAssetDescription());
        ps.setInt(6, assetEntity.getAssignedToID());
        ps.setInt(7, assetEntity.getLocationID());
        ps.setString(8, TimeUtil.exportLocalDateToMySQL(assetEntity.getPurchasedDate()));
        ps.setDouble(9, assetEntity.getPurchasedPrice());
        ps.executeUpdate();
        DBConn.closeConn();
    }
}
