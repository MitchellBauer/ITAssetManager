package com.bauerperception.itassetmanager.DAO;

import com.bauerperception.itassetmanager.model.LoadOutEntity;
import javafx.collections.ObservableList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class LoadOutDAOImplTest {

    @BeforeClass
    public static void setUp() throws Exception {
        LoadOutEntity loadOutEntity = LoadOutDAOImpl.getLoadOutByID(-1);
        if (loadOutEntity == null){
            loadOutEntity = new LoadOutEntity("Setup Loadout");
            LoadOutDAOImpl.addLoadOutWithCustomID(loadOutEntity, -1);
        } else {
            loadOutEntity = new LoadOutEntity("Setup Loadout");
            loadOutEntity.setLoadOutID(-1);
            LoadOutDAOImpl.updateLoadOut(loadOutEntity);
        }
    }

    @AfterClass
    public static void tearDown() throws Exception {
        DBConn.makeConn();
        String sqlStatement = "DELETE FROM loadouts WHERE id  = '" + -1 + "'";
        Statement stmt = DBConn.conn.createStatement();
        stmt.executeUpdate(sqlStatement);
        DBConn.closeConn();
    }

    @Test
    public void getAllLoadOuts() throws Exception {
        ObservableList<LoadOutEntity> allLoadOuts = LoadOutDAOImpl.getAllLoadOuts();
        assertTrue(allLoadOuts.size() > 0);
        assertEquals("Setup Loadout", allLoadOuts.get(0).getLoadOutName());
    }

    @Test
    public void updateLoadOut() throws Exception {
        LoadOutEntity loadOutEntity = new LoadOutEntity(-1, "Update Setup");
        LoadOutDAOImpl.updateLoadOut(loadOutEntity);
        ObservableList<LoadOutEntity> allLoadOuts = LoadOutDAOImpl.getAllLoadOuts();
        assertEquals("Update Setup", allLoadOuts.get(0).getLoadOutName());

        //Clean up
        loadOutEntity = new LoadOutEntity(-1, "Setup Loadout");
        LoadOutDAOImpl.updateLoadOut(loadOutEntity);
    }

    @Test
    public void addLoadOut() throws Exception {
        LoadOutEntity loadOutEntity = new LoadOutEntity( "add1");
        LoadOutDAOImpl.addLoadOut(loadOutEntity);
        loadOutEntity = LoadOutDAOImpl.getAllLoadOuts().get(LoadOutDAOImpl.getAllLoadOuts().size()-1);
        assertEquals("add1", loadOutEntity.getLoadOutName());

        //Clean up and test delete
        LoadOutDAOImpl.deleteLoadOut(loadOutEntity);
        loadOutEntity = LoadOutDAOImpl.getLoadOutByID(loadOutEntity.getLoadOutID());
        assertNull(loadOutEntity);
    }
//
//    @Test
//    public void deleteLoadOut() {
//    }

    @Test
    public void getNewLoadOutID() throws Exception {
        ObservableList<LoadOutEntity> allLoadOuts = LoadOutDAOImpl.getAllLoadOuts();
        int highestID = allLoadOuts.get(allLoadOuts.size() - 1).getLoadOutID() + 1;
        assertEquals(highestID, LoadOutDAOImpl.getNewLoadOutID());

        //Going to try another way of verifying highest ID to get code coverage on getLoadOutByID()
        assertNotNull(LoadOutDAOImpl.getLoadOutByID(LoadOutDAOImpl.getNewLoadOutID()-1));
    }
}