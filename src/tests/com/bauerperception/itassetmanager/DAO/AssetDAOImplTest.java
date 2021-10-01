package com.bauerperception.itassetmanager.DAO;

import com.bauerperception.itassetmanager.model.AssetEntity;
import com.bauerperception.itassetmanager.util.FXUtil;
import com.bauerperception.itassetmanager.util.TimeUtil;
import javafx.collections.ObservableList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class AssetDAOImplTest {
    private static Statement stmt;

    @BeforeClass
    public static void setUp() throws Exception {
        AssetEntity assetEntity = new AssetEntity("Lenovo", "Laptop", "82JW0012US",
                "Gaming Laptop, AMD 5800H CPU, 16GB DDR4 RAM", 9999, 2,
                TimeUtil.importMySQLToLocalDate("2021-09-27"), 1049.99);
        AssetDAOImpl.addAssetWithCustomID(assetEntity, -1);
    }

    @AfterClass
    public static void tearDown() throws Exception{
        DBConn.makeConn();
        String sqlStatement = "DELETE FROM asset WHERE idasset  = '" + -1 + "'";
        stmt = DBConn.conn.createStatement();
        stmt.executeUpdate(sqlStatement);
        DBConn.closeConn();

        //Future Take a snap shot at the beginning of test. Then delete anything extra if needed.
        //Future auto clean auto-increment in database
    }

    @Test
    public void getAssetByID() throws Exception {
        AssetEntity asset = AssetDAOImpl.getAssetByID(-1);
        assertEquals(-1, asset.getAssetID());
        assertEquals("Lenovo", asset.getAssetManufacturer());
        assertEquals("Laptop", asset.getAssetType());
        assertEquals("82JW0012US", asset.getAssetModel());
        assertEquals("Gaming Laptop, AMD 5800H CPU, 16GB DDR4 RAM", asset.getAssetDescription());
        assertEquals(9999, asset.getAssignedToID());
        assertEquals(2, asset.getLocationID());
        assertEquals(TimeUtil.importMySQLToLocalDate("2021-09-27"), asset.getPurchasedDate());
        assertEquals(1049.99, asset.getPurchasedPrice(), .001);
    }

    @Test
    public void getAllAssets() throws Exception {
        ObservableList<AssetEntity> allAssets = AssetDAOImpl.getAllAssets();
        assertTrue(allAssets.size() > 0);
        //Test setup component
        AssetEntity asset = AssetDAOImpl.getAssetByID(-1);
        assertEquals("Lenovo", asset.getAssetManufacturer());
        assertEquals("Laptop", asset.getAssetType());
        assertEquals("82JW0012US", asset.getAssetModel());
        assertEquals("Gaming Laptop, AMD 5800H CPU, 16GB DDR4 RAM", asset.getAssetDescription());
        assertEquals(9999, asset.getAssignedToID());
        assertEquals(2, asset.getLocationID());
        assertEquals(TimeUtil.importMySQLToLocalDate("2021-09-27"), asset.getPurchasedDate());
        assertEquals(1049.99, asset.getPurchasedPrice(), .001);
    }

    @Test
    public void getAssetTypes() throws Exception {
        ObservableList<String> allTypes = AssetDAOImpl.getAssetTypes();
        assertEquals(17, allTypes.size());
        //A few chosen at random
        assertEquals("Mobile Phone", allTypes.stream().filter(a -> Objects.equals(a, "Mobile Phone")).collect(Collectors.toList()).get(0));
        assertEquals("Keyboard", allTypes.stream().filter(a -> Objects.equals(a, "Keyboard")).collect(Collectors.toList()).get(0));
        assertEquals("Docking Station", allTypes.stream().filter(a -> Objects.equals(a, "Docking Station")).collect(Collectors.toList()).get(0));
        assertEquals("Computer Tower", allTypes.stream().filter(a -> Objects.equals(a, "Computer Tower")).collect(Collectors.toList()).get(0));
    }

    @Test
    public void addAsset() throws Exception {
        AssetDAOImpl.addAsset(new AssetEntity("HP", "Laptop", "FDK990",
                "Laptop, Intel 11800H CPU, 16GB DDR4 RAM, Completely UNiQiS", 2, 3,
                TimeUtil.importMySQLToLocalDate("2021-03-27"), 1559.99));
        ObservableList<AssetEntity> allAssets = AssetDAOImpl.getAllAssets();

        //Add asset uses the default incremental primary key to assign IDs.
        //So we have to retrieve the newly created asset using no primary key.
        //Hence the very unique asset description
        AssetEntity asset = allAssets.stream().filter(a -> Objects.equals(a.getAssetDescription(),
                "Laptop, Intel 11800H CPU, 16GB DDR4 RAM, Completely UNiQiS")).collect(Collectors.toList()).get(0);
        assertEquals("HP", asset.getAssetManufacturer());
        assertEquals("Laptop", asset.getAssetType());
        assertEquals("FDK990", asset.getAssetModel());
        assertEquals("Laptop, Intel 11800H CPU, 16GB DDR4 RAM, Completely UNiQiS", asset.getAssetDescription());
        assertEquals(2, asset.getAssignedToID());
        assertEquals(3, asset.getLocationID());
        assertEquals(TimeUtil.importMySQLToLocalDate("2021-03-27"), asset.getPurchasedDate());
        assertEquals(1559.99, asset.getPurchasedPrice(), .001);

        //Clean up after itself
        allAssets = AssetDAOImpl.getAllAssets();
        asset = allAssets.stream().filter(a -> Objects.equals(a.getAssetDescription(),
                "Laptop, Intel 11800H CPU, 16GB DDR4 RAM, Completely UNiQiS")).collect(Collectors.toList()).get(0);
        AssetDAOImpl.deleteAsset(asset);
    }

    @Test
    public void updateAsset() throws Exception {
        //Depends on addAsset otherwise I have to rewrite the add code. Works as a double check on addAsset.
        AssetEntity asset = new AssetEntity("HP", "Laptop", "FDK990",
                "Laptop, Intel 11800H CPU, 16GB DDR4 RAM, Completely UNiQiS", 2, 3,
                TimeUtil.importMySQLToLocalDate("2021-03-27"), 1559.99);
        AssetDAOImpl.addAssetWithCustomID(asset, -3);

        //Update asset information, asset is dependent on retrieving the asset entity with ID
        asset = AssetDAOImpl.getAssetByID(-3);
        asset.setAssetManufacturer("Lenovo");
        asset.setAssetModel("82JW0012US");
        AssetDAOImpl.updateAsset(asset);

        //Get assets to check if it's been updated
        asset = AssetDAOImpl.getAssetByID(-3);
        assertEquals("Lenovo", asset.getAssetManufacturer());
        assertEquals("82JW0012US", asset.getAssetModel());

        //Cleanup after method
        AssetDAOImpl.deleteAsset(asset);
    }

    @Test
    public void deleteAsset() throws Exception {
        AssetEntity asset = new AssetEntity("HP", "Laptop", "FDK990",
                "Laptop, Intel 11800H CPU, 16GB DDR4 RAM, Completely UNiQiS", 2, 3,
                TimeUtil.importMySQLToLocalDate("2021-03-27"), 1559.99);
        AssetDAOImpl.addAssetWithCustomID(asset, -4);

        asset = AssetDAOImpl.getAssetByID(-4);
        AssetDAOImpl.deleteAsset(asset);
        asset = AssetDAOImpl.getAssetByID(-4);
        assertNull(asset);
    }

    @Test
    public void getAssetsByEmployeeID() throws Exception {
        AssetEntity asset = AssetDAOImpl.getAssetsByEmployeeID(9999).get(0); //Assumption that there's only 1 asset with employee 9999
        assertEquals("Lenovo", asset.getAssetManufacturer());
        assertEquals("Laptop", asset.getAssetType());
        assertEquals("82JW0012US", asset.getAssetModel());
        assertEquals("Gaming Laptop, AMD 5800H CPU, 16GB DDR4 RAM", asset.getAssetDescription());
        assertEquals(9999, asset.getAssignedToID());
        assertEquals(2, asset.getLocationID());
        assertEquals(TimeUtil.importMySQLToLocalDate("2021-09-27"), asset.getPurchasedDate());
        assertEquals(1049.99, asset.getPurchasedPrice(), .001);
    }

    @Test
    public void getAllUnassignedAssets() throws Exception {
        //The logic behind unassigned in this program is that the asset when saved as unassigned is -1.
        AssetDAOImpl.addAssetWithCustomID(new AssetEntity("HP", "Laptop", "FDK990",
                "Laptop, Intel 11800H CPU, 16GB DDR4 RAM, Completely UNiQiS", -1, 3,
                TimeUtil.importMySQLToLocalDate("2021-03-27"), 1559.99), -2);
        ObservableList<AssetEntity> allUnassignedAssets = AssetDAOImpl.getAllUnassignedAssets();
        AssetEntity asset = FXUtil.getEntityByID(allUnassignedAssets, -2);
        assertEquals(-2, asset.getAssetID());
        assertEquals(-1, asset.getAssignedToID());

        //Clean up
        AssetDAOImpl.deleteAsset(asset);
    }
}