package com.bauerperception.itassetmanager.DAO;

import com.bauerperception.itassetmanager.model.LocationEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class LocationDAOImpl {
    private static Statement stmt;

    public static ObservableList<LocationEntity> getAllLocations() throws Exception{
        ObservableList<LocationEntity> allLocations = FXCollections.observableArrayList();
        DBConn.makeConn();
        String sqlStatement = "SELECT * FROM locations";
        stmt = DBConn.conn.createStatement();
        ResultSet result = stmt.executeQuery(sqlStatement);

        while(result.next()){
            int locationID = result.getInt("idlocations");
            String locationName = result.getString("location_name");

            LocationEntity LocationResult = new LocationEntity(locationID, locationName);
            allLocations.add(LocationResult);
        }
        DBConn.closeConn();
        return allLocations;
    }
}
