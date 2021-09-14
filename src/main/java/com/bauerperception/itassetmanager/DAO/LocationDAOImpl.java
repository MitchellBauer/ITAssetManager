package com.bauerperception.itassetmanager.DAO;

import com.bauerperception.itassetmanager.model.AssetEntity;
import com.bauerperception.itassetmanager.model.LocationEntity;
import com.bauerperception.itassetmanager.util.TimeUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

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

    public static void addLocation(LocationEntity newLocation) throws Exception{
        PreparedStatement ps;
        Connection conn = DBConn.getConn();

        String sqlStatement = "INSERT INTO locations (name) VALUES (?)";
        ps = conn.prepareStatement(sqlStatement);
        ps.setString(1, newLocation.getLocationName());
        ps.executeUpdate();
        DBConn.closeConn();
    }

    public static void deleteLocation(LocationEntity selectedLocation) throws Exception{
        DBConn.makeConn();
        String sqlStatement = "DELETE FROM locations WHERE idlocations  = '" + selectedLocation.getLocationID() + "'";
        stmt = DBConn.conn.createStatement();
        stmt.executeUpdate(sqlStatement);
        DBConn.closeConn();
    }

    public static void updateLocation(LocationEntity selectedLocation) throws Exception{
        PreparedStatement ps;
        Connection conn = DBConn.getConn();

        String sqlStatement = "UPDATE locations SET name = ? WHERE idlocations = ?;";
        ps = conn.prepareStatement(sqlStatement);
        ps.setString(1, selectedLocation.getLocationName());
        ps.setInt(2, selectedLocation.getLocationID());
        ps.executeUpdate();
        DBConn.closeConn();
    }
}
