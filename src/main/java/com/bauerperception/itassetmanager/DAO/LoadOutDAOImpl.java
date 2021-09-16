package com.bauerperception.itassetmanager.DAO;

import com.bauerperception.itassetmanager.model.LoadOutEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class LoadOutDAOImpl {
    private static Statement stmt;

    public static ObservableList<LoadOutEntity> getAllLoadOuts() throws Exception{
        ObservableList<LoadOutEntity> allLoadOuts = FXCollections.observableArrayList();
        DBConn.makeConn();
        String sqlStatement = "SELECT * FROM loadouts";
        stmt = DBConn.conn.createStatement();
        ResultSet result = stmt.executeQuery(sqlStatement);

        while(result.next()){
            int loadOutID = result.getInt("id");
            String name = result.getString("name");

            LoadOutEntity loadOutResult = new LoadOutEntity(loadOutID, name);
            allLoadOuts.add(loadOutResult);
        }
        DBConn.closeConn();
        return allLoadOuts;
    }

    public static void updateLoadOut(LoadOutEntity loadOutEntity) throws Exception {
        PreparedStatement ps;
        Connection conn = DBConn.getConn();
        String sqlStatement = "UPDATE loadouts SET name = ? WHERE id = ?;";
        ps = conn.prepareStatement(sqlStatement);
        ps.setString(1, loadOutEntity.getLoadOutName());
        ps.setInt(2, loadOutEntity.getLoadOutID());
        ps.executeUpdate();
        DBConn.closeConn();
    }

    public static void addLoadOut(LoadOutEntity loadOutEntity) throws Exception {
        PreparedStatement ps;
        Connection conn = DBConn.getConn();
        String sqlStatement = "INSERT INTO loadouts (name) " +
                "VALUES (?)";
        ps = conn.prepareStatement(sqlStatement);
        ps.setString(1, loadOutEntity.getLoadOutName());
        ps.executeUpdate();
        DBConn.closeConn();
    }

    public static void deleteLoadOut(LoadOutEntity loadOutEntity) throws Exception {
        //TODO Validation for deletion to protect equipment from being orphans
        DBConn.makeConn();
        String sqlStatement = "DELETE FROM loadouts WHERE id  = '" + loadOutEntity.getLoadOutID() + "'";
        stmt = DBConn.conn.createStatement();
        stmt.executeUpdate(sqlStatement);
        DBConn.closeConn();
    }
}
