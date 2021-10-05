package com.bauerperception.itassetmanager.DAO;

import com.bauerperception.itassetmanager.model.EquipmentEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class EquipmentDAOImpl {
    private static Statement stmt;

    public static ObservableList<EquipmentEntity> equipmentByLoadOutID(int loadOutID) throws Exception{
        ObservableList<EquipmentEntity> allEquipment = FXCollections.observableArrayList();
        DBConn.makeConn();
        String sqlStatement = "SELECT * FROM equipment WHERE load_out_id = " + loadOutID;
        stmt = DBConn.conn.createStatement();
        ResultSet result = stmt.executeQuery(sqlStatement);

        while(result.next()){
            int equipmentID = result.getInt("id");
            String name = result.getString("mfr");
            String modelNum = result.getString("model_num");
            String equipmentType = result.getString("equipment_type");
            int resultLoadOutID = result.getInt("load_out_id");
            int loadOutSlotNum = result.getInt("load_out_slot_num");
            int quantity = result.getInt("quantity");
            double purchasePrice = result.getDouble("purchase_price");
            double lastPurchasePrice = result.getDouble("last_purchase_price");
            String purchaseUrl = result.getString("purchase_url");

            EquipmentEntity equipmentResult = new EquipmentEntity(equipmentID, name, modelNum, equipmentType, resultLoadOutID,
                    loadOutSlotNum, quantity, purchasePrice, lastPurchasePrice, purchaseUrl);
            allEquipment.add(equipmentResult);
        }
        DBConn.closeConn();
        return allEquipment;
    }

    public static void updateEquipment(EquipmentEntity equipmentEntity) throws Exception {
        PreparedStatement ps;
        Connection conn = DBConn.getConn();
        String sqlStatement = "UPDATE equipment SET mfr = ?, model_num = ?, equipment_type = ?, load_out_id = ?," +
                "load_out_slot_num = ?, quantity = ?, purchase_price = ?, last_purchase_price = ?, purchase_url = ? WHERE id = ?;";
        ps = conn.prepareStatement(sqlStatement);
        ps.setString(1, equipmentEntity.getMfr());
        ps.setString(2, equipmentEntity.getModelNum());
        ps.setString(3, equipmentEntity.getEquipmentType());
        ps.setInt(4, equipmentEntity.getAssignedLoadOutID());
        ps.setInt(5, equipmentEntity.getLoadOutSlotNum());
        ps.setInt(6, equipmentEntity.getQuantityNeeded());
        ps.setDouble(7, equipmentEntity.getPurchasePrice());
        ps.setDouble(8, equipmentEntity.getPurchasePrice());
        ps.setString(9, equipmentEntity.getWhereToPurchaseURL());
        ps.setInt(10, equipmentEntity.getEquipmentID());
        ps.executeUpdate();
        DBConn.closeConn();
    }

    public static void addEquipment(EquipmentEntity equipmentEntity) throws Exception {
        PreparedStatement ps;
        Connection conn = DBConn.getConn();
        String sqlStatement = "INSERT INTO equipment (mfr, model_num, equipment_type, load_out_id, load_out_slot_num, quantity, " +
                "purchase_price, last_purchase_price, purchase_url, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sqlStatement);
        ps.setString(1, equipmentEntity.getMfr());
        ps.setString(2, equipmentEntity.getModelNum());
        ps.setString(3, equipmentEntity.getEquipmentType());
        ps.setInt(4, equipmentEntity.getAssignedLoadOutID());
        ps.setInt(5, equipmentEntity.getLoadOutSlotNum());
        ps.setInt(6, equipmentEntity.getQuantityNeeded());
        ps.setDouble(7, equipmentEntity.getPurchasePrice());
        ps.setDouble(8, equipmentEntity.getPurchasePrice());
        ps.setString(9, equipmentEntity.getWhereToPurchaseURL());
        ps.setInt(10, getNewEquipmentID());
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

    public static int getNewEquipmentID() throws Exception {
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
            String name = result.getString("mfr");
            String modelNum = result.getString("model_num");
            String equipmentType = result.getString("equipment_type");
            int loadOutID = result.getInt("load_out_id");
            int loadOutSlotNum = result.getInt("load_out_slot_num");
            int quantity = result.getInt("quantity");
            double purchasePrice = result.getDouble("purchase_price");
            double lastPurchasePrice = result.getDouble("last_purchase_price");
            String purchaseUrl = result.getString("purchase_url");

            EquipmentEntity equipmentResult = new EquipmentEntity(equipmentID, name, modelNum, equipmentType, loadOutID,
                    loadOutSlotNum, quantity, purchasePrice, lastPurchasePrice, purchaseUrl);
            allEquipment.add(equipmentResult);
        }
        DBConn.closeConn();
        return allEquipment;
    }

    public static void saveListOfEquipment(ObservableList<EquipmentEntity> equipmentList) throws Exception {
        for (EquipmentEntity i : equipmentList){
            addEquipment(i);
        }
    }

    public static EquipmentEntity getEquipmentByID(int i) throws Exception {
        ObservableList<EquipmentEntity> allEquipment = FXCollections.observableArrayList();
        DBConn.makeConn();
        String sqlStatement = "SELECT * FROM equipment WHERE id = " + i;
        stmt = DBConn.conn.createStatement();
        ResultSet result = stmt.executeQuery(sqlStatement);

        while(result.next()){
            int equipmentID = result.getInt("id");
            String name = result.getString("mfr");
            String modelNum = result.getString("model_num");
            String equipmentType = result.getString("equipment_type");
            int resultLoadOutID = result.getInt("load_out_id");
            int loadOutSlotNum = result.getInt("load_out_slot_num");
            int quantity = result.getInt("quantity");
            double purchasePrice = result.getDouble("purchase_price");
            double lastPurchasePrice = result.getDouble("last_purchase_price");
            String purchaseUrl = result.getString("purchase_url");

            EquipmentEntity equipmentResult = new EquipmentEntity(equipmentID, name, modelNum, equipmentType, resultLoadOutID,
                    loadOutSlotNum, quantity, purchasePrice, lastPurchasePrice, purchaseUrl);
            allEquipment.add(equipmentResult);
        }
        DBConn.closeConn();
        if (allEquipment.size() > 0){
            return allEquipment.get(0);
        } else {
            return null;
        }
    }

    public static void addEquipmentWithCustomID(EquipmentEntity equipmentEntity, int i) throws Exception {
        PreparedStatement ps;
        Connection conn = DBConn.getConn();
        String sqlStatement = "INSERT INTO equipment (id, mfr, model_num, equipment_type, load_out_id, load_out_slot_num, quantity, " +
                "purchase_price, last_purchase_price, purchase_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sqlStatement);
        ps.setInt(1, i);
        ps.setString(2, equipmentEntity.getMfr());
        ps.setString(3, equipmentEntity.getModelNum());
        ps.setString(4, equipmentEntity.getEquipmentType());
        ps.setInt(5, equipmentEntity.getAssignedLoadOutID());
        ps.setInt(6, equipmentEntity.getLoadOutSlotNum());
        ps.setInt(7, equipmentEntity.getQuantityNeeded());
        ps.setDouble(8, equipmentEntity.getPurchasePrice());
        ps.setDouble(9, equipmentEntity.getPurchasePrice());
        ps.setString(10, equipmentEntity.getWhereToPurchaseURL());
        ps.executeUpdate();
        DBConn.closeConn();
    }

    public static void deleteOrphanedEquipment(int loadOutID) throws Exception {
        DBConn.makeConn();
        String sqlStatement = "DELETE FROM equipment WHERE load_out_id  = '" + loadOutID + "'";
        stmt = DBConn.conn.createStatement();
        stmt.executeUpdate(sqlStatement);
        DBConn.closeConn();
    }
}
