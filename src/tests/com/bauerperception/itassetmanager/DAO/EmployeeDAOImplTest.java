package com.bauerperception.itassetmanager.DAO;

import com.bauerperception.itassetmanager.model.AssetEntity;
import com.bauerperception.itassetmanager.model.EmployeeEntity;
import javafx.collections.ObservableList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Statement;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class EmployeeDAOImplTest {

    @BeforeClass
    public static void setUp() throws Exception {
        EmployeeEntity employeeEntity = EmployeeDAOImpl.getEmployeeByID(-1);
        if (employeeEntity == null){
            employeeEntity = new EmployeeEntity("Conrad", "Jordan", "Whitfield", "cwhitfield@outdoorproducts.com", 10, 11);
            EmployeeDAOImpl.addEmployeeWithCustomID(employeeEntity, -1);
        } else {
            employeeEntity = new EmployeeEntity("Conrad", "Jordan", "Whitfield", "cwhitfield@outdoorproducts.com", 10, 11);
            employeeEntity.setEmployeeID(-1);
            EmployeeDAOImpl.updateEmployee(employeeEntity);
        }
    }

    @AfterClass
    public static void tearDown() throws Exception{
        DBConn.makeConn();
        String sqlStatement = "DELETE FROM employees WHERE idemployees  = '" + -1 + "'";
        Statement stmt = DBConn.conn.createStatement();
        stmt.executeUpdate(sqlStatement);
        DBConn.closeConn();

        //Future Take a snap shot at the beginning of test. Then delete anything extra if needed.
        //Future auto clean auto-increment in database
    }

    @Test
    public void getAllEmployees() throws Exception {
        ObservableList<EmployeeEntity> allEmployees = EmployeeDAOImpl.getAllEmployees();
        assertTrue(allEmployees.size() > 0);
        EmployeeEntity employeeEntity = allEmployees.stream().filter(a -> Objects.equals(a.getEmployeeID(),
                -1)).collect(Collectors.toList()).get(0);
        assertEquals("Conrad", employeeEntity.getFirstName());
        assertEquals("Jordan", employeeEntity.getMiddleName());
        assertEquals("Whitfield", employeeEntity.getLastName());
        assertEquals("cwhitfield@outdoorproducts.com", employeeEntity.getEmailAddress());
        assertEquals(10, employeeEntity.getPrimaryWorkLocation());
        assertEquals(11, employeeEntity.getSecondaryWorkLocation());
    }

    @Test
    public void updateEmployee() throws Exception {
        EmployeeEntity employeeEntity = EmployeeDAOImpl.getEmployeeByID(-1);
        employeeEntity.setEmailAddress("cwhitfield@products.com");
        employeeEntity.setPrimaryWorkLocation(11);
        employeeEntity.setSecondaryWorkLocation(12);
        employeeEntity.setFirstName("Con");
        employeeEntity.setMiddleName("Jor");
        employeeEntity.setLastName("Whit");
        EmployeeDAOImpl.updateEmployee(employeeEntity);
        employeeEntity = EmployeeDAOImpl.getEmployeeByID(-1);

        assertEquals("Con", employeeEntity.getFirstName());
        assertEquals("Jor", employeeEntity.getMiddleName());
        assertEquals("Whit", employeeEntity.getLastName());
        assertEquals("cwhitfield@products.com", employeeEntity.getEmailAddress());
        assertEquals(11, employeeEntity.getPrimaryWorkLocation());
        assertEquals(12, employeeEntity.getSecondaryWorkLocation());
    }

    @Test
    public void addEmployee() throws Exception {
        EmployeeEntity employeeEntity = new EmployeeEntity("Alya", "Portia", "Taylor", "ataylor@products.com", 8, 9);
        EmployeeDAOImpl.addEmployee(employeeEntity);
        ObservableList<EmployeeEntity> allEmployees = EmployeeDAOImpl.getAllEmployees();
        employeeEntity = allEmployees.get(allEmployees.size()-1); //Alya should be last

        assertEquals("Alya", employeeEntity.getFirstName());
        assertEquals("Portia", employeeEntity.getMiddleName());
        assertEquals("Taylor", employeeEntity.getLastName());
        assertEquals("ataylor@products.com", employeeEntity.getEmailAddress());
        assertEquals(8, employeeEntity.getPrimaryWorkLocation());
        assertEquals(9, employeeEntity.getSecondaryWorkLocation());

        //Clean up
        EmployeeDAOImpl.deleteEmployee(employeeEntity);
    }

    @Test
    public void deleteEmployee() throws Exception {
        //There's already a custom employee added in the setup
        EmployeeEntity employeeEntity = new EmployeeEntity("Alya", "Portia", "Taylor", "ataylor@products.com", 8, 9);
        EmployeeDAOImpl.addEmployeeWithCustomID(employeeEntity, -2);
        employeeEntity = EmployeeDAOImpl.getEmployeeByID(-2);
        if (employeeEntity != null){
            EmployeeDAOImpl.deleteEmployee(employeeEntity);
        } else {
            throw new NullPointerException();
        }

        employeeEntity = EmployeeDAOImpl.getEmployeeByID(-2);
        assertNull(employeeEntity);
    }

    @Test
    public void deleteLocation() throws Exception{
        EmployeeEntity employeeEntity = new EmployeeEntity("Conrad", "Jordan", "Whitfield", "cwhitfield@outdoorproducts.com", 50, 50);
        employeeEntity.setEmployeeID(-1);
        EmployeeDAOImpl.updateEmployee(employeeEntity);
        EmployeeDAOImpl.deleteLocation(50);
        employeeEntity = EmployeeDAOImpl.getEmployeeByID(-1);
        assertEquals(0, employeeEntity.getPrimaryWorkLocation());
        assertEquals(0, employeeEntity.getSecondaryWorkLocation());

        //Clean up
        employeeEntity = new EmployeeEntity("Conrad", "Jordan", "Whitfield", "cwhitfield@outdoorproducts.com", 10, 11);
        employeeEntity.setEmployeeID(-1);
        EmployeeDAOImpl.updateEmployee(employeeEntity);
    }
}