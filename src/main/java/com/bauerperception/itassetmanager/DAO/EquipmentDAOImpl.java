package com.bauerperception.itassetmanager.DAO;

import com.bauerperception.itassetmanager.model.EquipmentEntity;
import com.bauerperception.itassetmanager.model.LoadOutEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class EquipmentDAOImpl {
    private static Statement stmt;

    public static ObservableList<EquipmentEntity> equipmentByLoadOutID(LoadOutEntity loadOutEntity) throws Exception{
        ObservableList<EquipmentEntity> allEquipment = FXCollections.observableArrayList();
        DBConn.makeConn();
        String sqlStatement = "SELECT * FROM equipment WHERE load_out_id = " + loadOutEntity.getLoadOutID();
        stmt = DBConn.conn.createStatement();
        ResultSet result = stmt.executeQuery(sqlStatement);

        while(result.next()){
            int equipmentID = result.getInt("id");
            String name = result.getString("name");
            String modelNum = result.getString("model_num");
            String equipmentType = result.getString("equipment_type");
            int loadOutID = result.getInt("load_out_id");
            int loadOutSlotNum = result.getInt("load_out_slot_num");
            int quantity = result.getInt("quantity");
            float purchasePrice = result.getFloat("purchase_price");
            float lastPurchasePrice = result.getFloat("last_purchase_price");
            String purchaseUrl = result.getString("purchase_url");

            EquipmentEntity equipmentResult = new EquipmentEntity(equipmentID, name, modelNum, equipmentType, loadOutID,
                    loadOutSlotNum, quantity, purchasePrice, lastPurchasePrice, purchaseUrl);
            allEquipment.add(equipmentResult);
        }
        DBConn.closeConn();
        return allEquipment;
    }

    public static void updateEquipment(EquipmentEntity equipmentEntity) throws Exception {
        PreparedStatement ps;
        Connection conn = DBConn.getConn();
        String sqlStatement = "UPDATE equipment SET name = ?, model_num = ?, equipment_type = ?, load_out_id = ?," +
                "load_out_slot_num = ?, quantity = ?, purchase_price = ?, last_purchase_price = ?, purchase_url = ? WHERE id = ?;";
        ps = conn.prepareStatement(sqlStatement);
        ps.setString(1, equipmentEntity.getName());
        ps.setString(2, equipmentEntity.getModelNum());
        ps.setString(3, equipmentEntity.getEquipmentType());
        ps.setInt(4, equipmentEntity.getAssignedLoadOutID());
        ps.setInt(5, equipmentEntity.getLoadOutSlotNum());
        ps.setInt(6, equipmentEntity.getQuantityNeeded());
        ps.setFloat(7, equipmentEntity.getPurchasePrice());
        ps.setFloat(8, equipmentEntity.getPurchasePrice());
        ps.setString(9, equipmentEntity.getWhereToPurchaseURL());
        ps.setInt(10, equipmentEntity.getEquipmentID());
        ps.executeUpdate();
        DBConn.closeConn();
    }

    public static void addEquipment(EquipmentEntity equipmentEntity) throws Exception {
        PreparedStatement ps;
        Connection conn = DBConn.getConn();
        String sqlStatement = "INSERT INTO equipment (name, model_num, equipment_type, load_out_id, load_out_slot_num, quantity, " +
                "purchase_price, last_purchase_price, purchase_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sqlStatement);
        ps.setString(1, equipmentEntity.getName());
        ps.setString(2, equipmentEntity.getEquipmentType());
        ps.setInt(3, equipmentEntity.getAssignedLoadOutID());
        ps.setInt(4, equipmentEntity.getLoadOutSlotNum());
        ps.setInt(5, equipmentEntity.getQuantityNeeded());
        ps.setFloat(6, equipmentEntity.getPurchasePrice());
        ps.setFloat(7, equipmentEntity.getPurchasePrice());
        ps.setString(8, equipmentEntity.getWhereToPurchaseURL());
        ps.executeUpdate();
        DBConn.closeConn();
    }

    public static void deleteEquipment(EquipmentEntity equipmentEntity) throws Exception {
        DBConn.makeConn();
        String sqlStatement = "DELETE FROM equipment WHERE id  = '" + equipmentEntity.getEquipmentID() + "'";
        stmt = DBConn.conn.createStatement();
        stmt.executeUpdate(sqlStatement);
        DBConn.closeConn();
    }

    public static int newEquipmentID() throws Exception {
        DBConn.makeConn();
        String sqlStatement = "SELECT MAX(id) AS highestID FROM equipment";
        stmt = DBConn.conn.createStatement();
        ResultSet result = stmt.executeQuery(sqlStatement);
        int highestID = 0;

        while(result.next()){
            highestID = result.getInt("highestID");
        }
        DBConn.closeConn();
        highestID++;
        return highestID;
    }

    public static ObservableList<EquipmentEntity> getAllEquipment() throws Exception {
        ObservableList<EquipmentEntity> allEquipment = FXCollections.observableArrayList();
        DBConn.makeConn();
        String sqlStatement = "SELECT * FROM equipment";
        stmt = DBConn.conn.createStatement();
        ResultSet result = stmt.executeQuery(sqlStatement);

        while(result.next()){
            int equipmentID = result.getInt("id");
            String name = result.getString("name");
            String modelNum = result.getString("model_num");
            String equipmentType = result.getString("equipment_type");
            int loadOutID = result.getInt("load_out_id");
            int loadOutSlotNum = result.getInt("load_out_slot_num");
            int quantity = result.getInt("quantity");
            float purchasePrice = result.getFloat("purchase_price");
            float lastPurchasePrice = result.getFloat("last_purchase_price");
            String purchaseUrl = result.getString("purchase_url");

            EquipmentEntity equipmentResult = new EquipmentEntity(equipmentID, name, modelNum, equipmentType, loadOutID,
                    loadOutSlotNum, quantity, purchasePrice, lastPurchasePrice, purchaseUrl);
            allEquipment.add(equipmentResult);
        }
        DBConn.closeConn();
        return allEquipment;
    }

    public static ObservableList<String> getTypes() throws Exception {
        ObservableList<String> allTypes = FXCollections.observableArrayList();
        DBConn.makeConn();
        String sqlStatement = "SELECT * FROM assettypes";
        stmt = DBConn.conn.createStatement();
        ResultSet result = stmt.executeQuery(sqlStatement);

        while(result.next()){
            //TODO May need to use this asset ID and then this will need class
            int assetID = result.getInt("idassettypes");
            String assetName = result.getString("type_name");

            allTypes.add(assetName);
        }
        DBConn.closeConn();
        return allTypes;
    }
}
