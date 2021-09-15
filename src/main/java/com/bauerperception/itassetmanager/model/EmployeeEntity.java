package com.bauerperception.itassetmanager.model;

import javafx.beans.property.SimpleStringProperty;

public class EmployeeEntity implements Entity{
    private int employeeID;
    private String firstName;
    private String middleName;
    private String lastName;
    private String emailAddress;
    private int assignedWorkLocation;

    public EmployeeEntity(int employeeID, String firstName, String middleName, String lastName, String emailAddress, int assignedWorkLocation) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.assignedWorkLocation = assignedWorkLocation;
    }

    public EmployeeEntity(String firstName, String middleName, String lastName, String emailAddress, int assignedWorkLocationID) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.assignedWorkLocation = assignedWorkLocationID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getAssignedWorkLocation() {
        return assignedWorkLocation;
    }

    public void setAssignedWorkLocation(int assignedWorkLocation) {
        this.assignedWorkLocation = assignedWorkLocation;
    }

    @Override
    public String toString() {
        if(middleName.isEmpty()) {
            return "ID: " + employeeID + ' ' + firstName + ' ' + lastName;
        } else {
            String shortenedMiddleName = middleName.charAt(0) + ".";
            return "ID: " + employeeID + ' ' + firstName + ' ' + shortenedMiddleName + ' ' + lastName;
        }
    }

    @Override
    public int getID() {
        return employeeID;
    }
}
