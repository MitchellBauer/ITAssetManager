package com.bauerperception.itassetmanager.DAO;

import com.bauerperception.itassetmanager.model.EquipmentEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Statement;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class EquipmentDAOImplTest {

    @BeforeClass
    public static void setUp() throws Exception {
        EquipmentEntity equipmentEntity = EquipmentDAOImpl.getEquipmentByID(-1);
        if (equipmentEntity == null){
            equipmentEntity = new EquipmentEntity("LG", "34GP83A-B", "Monitor", -1, 1, 1, 800.00, "https://www.amazon.com/LG-34GP83A-B-Inch-Ultragear-Compatibility/dp/B08DWD38VX");
            EquipmentDAOImpl.addEquipmentWithCustomID(equipmentEntity, -1);
        } else {
            equipmentEntity = new EquipmentEntity("LG", "34GP83A-B", "Monitor", -1, 1, 1, 800.00, "https://www.amazon.com/LG-34GP83A-B-Inch-Ultragear-Compatibility/dp/B08DWD38VX");
            equipmentEntity.setEquipmentID(-1);
            EquipmentDAOImpl.updateEquipment(equipmentEntity);
        }
    }

    @AfterClass
    public static void tearDown() throws Exception {
        DBConn.makeConn();
        String sqlStatement = "DELETE FROM equipment WHERE id  = '" + -1 + "'";
        Statement stmt = DBConn.conn.createStatement();
        stmt.executeUpdate(sqlStatement);
        DBConn.closeConn();
    }

    @Test
    public void equipmentByLoadOutID() throws Exception {
        ObservableList<EquipmentEntity> allEquipment = EquipmentDAOImpl.equipmentByLoadOutID(-1);
        assertEquals("LG", allEquipment.get(0).getMfr());
        assertEquals("34GP83A-B", allEquipment.get(0).getModelNum());
        assertEquals("Monitor", allEquipment.get(0).getEquipmentType());
        assertEquals(-1, allEquipment.get(0).getAssignedLoadOutID());
        assertEquals(1, allEquipment.get(0).getLoadOutSlotNum());
        assertEquals(1, allEquipment.get(0).getQuantityNeeded());
        assertEquals("https://www.amazon.com/LG-34GP83A-B-Inch-Ultragear-Compatibility/dp/B08DWD38VX", allEquipment.get(0).getWhereToPurchaseURL());
        assertEquals(800.00, allEquipment.get(0).getPurchasePrice(), .001);
    }

    @Test
    public void updateEquipment() throws Exception {
        EquipmentEntity equipmentEntity = new EquipmentEntity(-1,"update1", "update2", "update3", -2, 3, 4, 900.00, "URL");
        EquipmentDAOImpl.updateEquipment(equipmentEntity);
        equipmentEntity = EquipmentDAOImpl.getEquipmentByID(-1);

        assertEquals("update1", equipmentEntity.getMfr());
        assertEquals("update2", equipmentEntity.getModelNum());
        assertEquals("update3", equipmentEntity.getEquipmentType());
        assertEquals(-2, equipmentEntity.getAssignedLoadOutID());
        assertEquals(3, equipmentEntity.getLoadOutSlotNum());
        assertEquals(4, equipmentEntity.getQuantityNeeded());
        assertEquals("URL", equipmentEntity.getWhereToPurchaseURL());
        assertEquals(900.00, equipmentEntity.getPurchasePrice(), .001);

        //Revert update
        equipmentEntity = new EquipmentEntity(-1,"LG", "34GP83A-B", "Monitor", -1, 1, 1, 800.00, "https://www.amazon.com/LG-34GP83A-B-Inch-Ultragear-Compatibility/dp/B08DWD38VX");
        EquipmentDAOImpl.updateEquipment(equipmentEntity);
    }

    @Test
    public void addEquipment() throws Exception {
        EquipmentEntity equipmentEntity = new EquipmentEntity("add1", "add2", "add3", -2, 3, 4, 900.00, "URL");
        EquipmentDAOImpl.addEquipment(equipmentEntity);
        equipmentEntity = EquipmentDAOImpl.getAllEquipment().get(EquipmentDAOImpl.getAllEquipment().size() - 1); //Grab the last added
        assertEquals("add1", equipmentEntity.getMfr());
        assertEquals("add2", equipmentEntity.getModelNum());
        assertEquals("add3", equipmentEntity.getEquipmentType());
        assertEquals(-2, equipmentEntity.getAssignedLoadOutID());
        assertEquals(3, equipmentEntity.getLoadOutSlotNum());
        assertEquals(4, equipmentEntity.getQuantityNeeded());
        assertEquals("URL", equipmentEntity.getWhereToPurchaseURL());
        assertEquals(900.00, equipmentEntity.getPurchasePrice(), .001);

        //Going to be lazy and check for delete equipment in here as well
        EquipmentDAOImpl.deleteEquipment(equipmentEntity);
        if (EquipmentDAOImpl.getEquipmentByID(equipmentEntity.getEquipmentID()) != null){
            throw new Exception("Delete equipment is not working.");
        }
    }

//    @Test
//    public void deleteEquipment() {
//    }

    @Test
    public void newEquipmentID() throws Exception {
        //Produce the highest ID a different way then the method.
        int highestID = EquipmentDAOImpl.getAllEquipment().get(EquipmentDAOImpl.getAllEquipment().size() - 1).getEquipmentID() + 1;
        assertEquals(highestID, EquipmentDAOImpl.getNewEquipmentID());
    }

    @Test
    public void getAllEquipment() throws Exception {
        ObservableList<EquipmentEntity> allEquipment = EquipmentDAOImpl.getAllEquipment();
        assertTrue(allEquipment.size() > 0);
        EquipmentEntity equipmentEntity = allEquipment.get(0);
        assertEquals("LG", allEquipment.get(0).getMfr());
        assertEquals("34GP83A-B", allEquipment.get(0).getModelNum());
        assertEquals("Monitor", allEquipment.get(0).getEquipmentType());
        assertEquals(-1, allEquipment.get(0).getAssignedLoadOutID());
        assertEquals(1, allEquipment.get(0).getLoadOutSlotNum());
        assertEquals(1, allEquipment.get(0).getQuantityNeeded());
        assertEquals("https://www.amazon.com/LG-34GP83A-B-Inch-Ultragear-Compatibility/dp/B08DWD38VX", allEquipment.get(0).getWhereToPurchaseURL());
        assertEquals(800.00, allEquipment.get(0).getPurchasePrice(), .001);
    }

    @Test
    public void saveListOfEquipment() throws Exception {
        ObservableList<EquipmentEntity> allEquipment = FXCollections.observableArrayList();
        allEquipment.add(new EquipmentEntity("add1", "add2", "add3", -2, 3, 4, 900.00, "URL"));
        allEquipment.add(new EquipmentEntity("add2", "add3", "add4", -2, 3, 4, 900.00, "URL"));
        EquipmentDAOImpl.saveListOfEquipment(allEquipment);

        allEquipment = EquipmentDAOImpl.getAllEquipment();
        EquipmentEntity first = allEquipment.get(allEquipment.size() - 2);
        EquipmentEntity second = allEquipment.get(allEquipment.size() - 1);

        assertEquals("add1", first.getMfr());
        assertEquals("add2", first.getModelNum());
        assertEquals("add3", first.getEquipmentType());
        assertEquals("add2", second.getMfr());
        assertEquals("add3", second.getModelNum());
        assertEquals("add4", second.getEquipmentType());

        //Cleanup
        EquipmentDAOImpl.deleteEquipment(first);
        EquipmentDAOImpl.deleteEquipment(second);
    }
}