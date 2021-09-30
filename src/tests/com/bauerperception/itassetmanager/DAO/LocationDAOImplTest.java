package com.bauerperception.itassetmanager.DAO;

import com.bauerperception.itassetmanager.model.LocationEntity;
import javafx.collections.ObservableList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Statement;

import static org.junit.Assert.*;

public class LocationDAOImplTest {

    @BeforeClass
    public static void setUp() throws Exception {
        LocationEntity locationEntity = LocationDAOImpl.getLocationByID(-1);
        if (locationEntity == null){
            locationEntity = new LocationEntity("Setup Location", -2);
            LocationDAOImpl.addLocationWithCustomID(locationEntity, -1);
        } else {
            locationEntity = new LocationEntity("Setup Location", -2);
            locationEntity.setLocationID(-1);
            LocationDAOImpl.updateLocation(locationEntity);
        }
    }

    @AfterClass
    public static void tearDown() throws Exception {
        DBConn.makeConn();
        String sqlStatement = "DELETE FROM locations WHERE id  = '" + -1 + "'";
        Statement stmt = DBConn.conn.createStatement();
        stmt.executeUpdate(sqlStatement);
        DBConn.closeConn();
    }

    @Test
    public void getAllLocations() throws Exception {
        ObservableList<LocationEntity> allLocations = LocationDAOImpl.getAllLocations();
        assertTrue(allLocations.size() > 0);
        assertEquals("Setup Location", allLocations.get(0).getLocationName());
        assertEquals(-2, allLocations.get(0).getLoadOutID());
    }

    @Test
    public void addLocation() throws Exception {
        LocationEntity locationEntity = new LocationEntity("add1", -3);
        LocationDAOImpl.addLocation(locationEntity);
        ObservableList<LocationEntity> allLocations = LocationDAOImpl.getAllLocations();
        locationEntity = allLocations.get(allLocations.size() - 1);
        assertEquals("add1", locationEntity.getLocationName());
        assertEquals(-3, locationEntity.getLoadOutID());

        //Cleanup and test delete location
        LocationDAOImpl.deleteLocation(locationEntity);
        assertNull(LocationDAOImpl.getLocationByID(locationEntity.getLocationID()));
    }

    @Test
    public void updateLocation() throws Exception {
        LocationEntity locationEntity = LocationDAOImpl.getLocationByID(-1);
        locationEntity.setLocationName("update1");
        locationEntity.setLoadOutID(1000);
        LocationDAOImpl.updateLocation(locationEntity);
        locationEntity = LocationDAOImpl.getLocationByID(-1);
        assertEquals("update1", locationEntity.getLocationName());
        assertEquals(1000, locationEntity.getLoadOutID());

        //Clean up
        locationEntity = new LocationEntity("Setup Location", -2);
        locationEntity.setLocationID(-1);
        LocationDAOImpl.updateLocation(locationEntity);
    }
}