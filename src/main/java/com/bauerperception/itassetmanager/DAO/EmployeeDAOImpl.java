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
            int employeeID = result.getInt("idemployees");
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
                "email_address = ?, work_location = ?, second_work_location WHERE idasset = ?;";
        ps = getPreparedEmployeeStatement(employeeEntity, conn, sqlStatement);
        ps.setInt(7, employeeEntity.getEmployeeID());
        ps.executeUpdate();
        DBConn.closeConn();
    }

    public static void addEmployee(EmployeeEntity employeeEntity) throws Exception {
        PreparedStatement ps;
        Connection conn = DBConn.getConn();

        String sqlStatement = "INSERT INTO employees (first_name, middle_name, last_name, email_address, work_location, " +
                "second_work_location) VALUES (?, ?, ?, ?, ?, ?)";
        ps = getPreparedEmployeeStatement(employeeEntity, conn, sqlStatement);
        ps.executeUpdate();
        DBConn.closeConn();
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
        String sqlStatement = "DELETE FROM employees WHERE idemployees  = '" + employeeEntity.getEmployeeID() + "'";
        stmt = DBConn.conn.createStatement();
        stmt.executeUpdate(sqlStatement);
        DBConn.closeConn();
    }
}
