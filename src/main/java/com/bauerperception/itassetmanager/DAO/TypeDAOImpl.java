package com.bauerperception.itassetmanager.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class TypeDAOImpl {
    private static Statement stmt;

    public static ObservableList<String> getAllTypes() throws Exception{
        ObservableList<String> allTypes = FXCollections.observableArrayList();
        DBConn.makeConn();
        String sqlStatement = "SELECT name FROM types";
        stmt = DBConn.conn.createStatement();
        ResultSet result = stmt.executeQuery(sqlStatement);
        while(result.next()){
            String typeName = result.getString("name");
            allTypes.add(typeName);
        }
        DBConn.closeConn();
        return allTypes;
    }
}
