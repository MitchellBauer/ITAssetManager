package com.bauerperception.itassetmanager.DAO;

import com.bauerperception.itassetmanager.model.EmployeeEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class EmployeeDAOImpl {
    private static Statement stmt;

    public static ObservableList<EmployeeEntity> getAllEmployees() throws Exception{
        ObservableList<EmployeeEntity> allEmployees = FXCollections.observableArrayList();
        DBConn.makeConn();
        String sqlStatement = "SELECT * FROM employees";
        stmt = DBConn.conn.createStatement();
        ResultSet result = stmt.executeQuery(sqlStatement);

        while(result.next()){
            int employeeID = result.getInt("id");
            String firstName = result.getString("first_name");
            String middleName = result.getString("middle_name");
            String lastName = result.getString("last_name");
            String emailAddress = result.getString("email_address");
            int assignedWorkLocation = result.getInt("work_location");
            int secondaryWorkLocation = result.getInt("second_work_location");

            EmployeeEntity employeeResult = new EmployeeEntity(employeeID, firstName, middleName, lastName,
                    emailAddress, assignedWorkLocation, secondaryWorkLocation);
            allEmployees.add(employeeResult);
        }
        DBConn.closeConn();
        return allEmployees;
    }

    public static void updateEmployee(EmployeeEntity employeeEntity) throws Exception {
        PreparedStatement ps;
        Connection conn = DBConn.getConn();

        String sqlStatement = "UPDATE employees SET first_name = ?, middle_name = ?, last_name = ?, " +
                "email_address = ?, work_location = ?, second_work_location = ? WHERE id = ?;";
        ps = getPreparedEmployeeStatement(employeeEntity, conn, sqlStatement);
        ps.setInt(7, employeeEntity.getEmployeeID());
        ps.executeUpdate();
        DBConn.closeConn();
    }

    public static void addEmployee(EmployeeEntity employeeEntity) throws Exception {
        PreparedStatement ps;
        Connection conn = DBConn.getConn();

        String sqlStatement = "INSERT INTO employees (first_name, middle_name, last_name, email_address, work_location, " +
                "second_work_location, id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        ps = getPreparedEmployeeStatement(employeeEntity, conn, sqlStatement);
        ps.setInt(7, getNewEmployeeID());
        ps.executeUpdate();
        DBConn.closeConn();
    }

    private static int getNewEmployeeID() throws Exception {
        DBConn.makeConn();
        String sqlStatement = "SELECT MAX(id) AS highestID FROM employees";
        stmt = DBConn.conn.createStatement();
        ResultSet result = stmt.executeQuery(sqlStatement);
        int highestID = 0;

        while(result.next()){
            highestID = result.getInt("highestID");
        }
        DBConn.closeConn();
        return ++highestID;
    }

    private static PreparedStatement getPreparedEmployeeStatement(EmployeeEntity employeeEntity, Connection conn, String sqlStatement) throws SQLException {
        PreparedStatement ps;
        ps = conn.prepareStatement(sqlStatement);
        ps.setString(1, employeeEntity.getFirstName());
        ps.setString(2, employeeEntity.getMiddleName());
        ps.setString(3, employeeEntity.getLastName());
        ps.setString(4, employeeEntity.getEmailAddress());
        ps.setInt(5, employeeEntity.getPrimaryWorkLocation());
        ps.setInt(6, employeeEntity.getSecondaryWorkLocation());
        return ps;
    }

    public static void deleteEmployee(EmployeeEntity employeeEntity) throws Exception {
        DBConn.makeConn();
        String sqlStatement = "DELETE FROM employees WHERE id  = '" + employeeEntity.getEmployeeID() + "'";
        stmt = DBConn.conn.createStatement();
        stmt.executeUpdate(sqlStatement);
        DBConn.closeConn();
    }

    public static void addEmployeeWithCustomID(EmployeeEntity employeeEntity, int customID) throws Exception {
        PreparedStatement ps;
        Connection conn = DBConn.getConn();
        String sqlStatement = "INSERT INTO itassetdb.employees (id, first_name, middle_name, last_name, email_address, work_location, " +
                "second_work_location) VALUES (?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sqlStatement);
        ps.setInt(1, customID);
        ps.setString(2, employeeEntity.getFirstName());
        ps.setString(3, employeeEntity.getMiddleName());
        ps.setString(4, employeeEntity.getLastName());
        ps.setString(5, employeeEntity.getEmailAddress());
        ps.setInt(6, employeeEntity.getPrimaryWorkLocation());
        ps.setInt(7, employeeEntity.getSecondaryWorkLocation());
        ps.executeUpdate();
        DBConn.closeConn();
    }

    public static EmployeeEntity getEmployeeByID(int selectedEmployeeID) throws Exception {
        ObservableList<EmployeeEntity> allEmployees = FXCollections.observableArrayList();
        DBConn.makeConn();
        String sqlStatement = "SELECT * FROM employees WHERE id = " + selectedEmployeeID;
        stmt = DBConn.conn.createStatement();
        ResultSet result = stmt.executeQuery(sqlStatement);

        while(result.next()){
            int employeeID = result.getInt("id");
            String firstName = result.getString("first_name");
            String middleName = result.getString("middle_name");
            String lastName = result.getString("last_name");
            String emailAddress = result.getString("email_address");
            int assignedWorkLocation = result.getInt("work_location");
            int secondaryWorkLocation = result.getInt("second_work_location");

            EmployeeEntity employeeResult = new EmployeeEntity(employeeID, firstName, middleName, lastName,
                    emailAddress, assignedWorkLocation, secondaryWorkLocation);
            allEmployees.add(employeeResult);
        }
        DBConn.closeConn();
        if (allEmployees.size() > 0){
            return allEmployees.get(0);
        } else {
            return null;
        }
    }

    public static void deleteLocation(int locationID) throws Exception {
        //Need to run this twice to make sure both work location columns have been set to 0

        PreparedStatement ps;
        Connection conn = DBConn.getConn();
        String sqlStatement = "UPDATE employees SET work_location = ? WHERE work_location = ?;";
        ps = conn.prepareStatement(sqlStatement);
        ps.setInt(1, 0);
        ps.setInt(2, locationID);
        ps.executeUpdate();

        conn = DBConn.getConn();
        sqlStatement = "UPDATE employees SET second_work_location = ? WHERE second_work_location = ?;";
        ps = conn.prepareStatement(sqlStatement);
        ps.setInt(1, 0);
        ps.setInt(2, locationID);
        ps.executeUpdate();
        DBConn.closeConn();
    }
}
