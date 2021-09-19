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
            int locationID = result.getInt("id");
            String locationName = result.getString("name");
            int loadOutID = result.getInt("loadout_id");

            LocationEntity LocationResult = new LocationEntity(locationID, locationName, loadOutID);
            allLocations.add(LocationResult);
        }
        DBConn.closeConn();
        return allLocations;
    }

    public static void addLocation(LocationEntity newLocation) throws Exception{
        PreparedStatement ps;
        Connection conn = DBConn.getConn();

        String sqlStatement = "INSERT INTO locations (name, loadout_id) VALUES (?, ?)";
        ps = conn.prepareStatement(sqlStatement);
        ps.setString(1, newLocation.getLocationName());
        ps.setInt(2, newLocation.getLoadOutID());
        ps.executeUpdate();
        DBConn.closeConn();
    }

    public static void deleteLocation(LocationEntity selectedLocation) throws Exception{
        DBConn.makeConn();
        String sqlStatement = "DELETE FROM locations WHERE id  = '" + selectedLocation.getLocationID() + "'";
        stmt = DBConn.conn.createStatement();
        stmt.executeUpdate(sqlStatement);
        DBConn.closeConn();
    }

    public static void updateLocation(LocationEntity selectedLocation) throws Exception{
        PreparedStatement ps;
        Connection conn = DBConn.getConn();

        String sqlStatement = "UPDATE locations SET name = ?, loadout_id = ? WHERE id = ?";
        ps = conn.prepareStatement(sqlStatement);
        ps.setString(1, selectedLocation.getLocationName());
        ps.setInt(2, selectedLocation.getLoadOutID());
        ps.setInt(3, selectedLocation.getLocationID());
        ps.executeUpdate();
        DBConn.closeConn();
    }
}
