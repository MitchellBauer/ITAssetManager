package com.bauerperception.itassetmanager.DAO;

import com.bauerperception.itassetmanager.model.EmployeeEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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

            EmployeeEntity employeeResult = new EmployeeEntity(employeeID, firstName, middleName, lastName, emailAddress, assignedWorkLocation);
            allEmployees.add(employeeResult);
        }
        DBConn.closeConn();
        return allEmployees;
    }
}
