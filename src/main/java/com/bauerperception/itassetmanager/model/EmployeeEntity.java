package com.bauerperception.itassetmanager.model;

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
        //TODO Reduce middle name if it exists to middle letter
        return "ID: " + employeeID + ' ' + firstName + ' ' + middleName + ' ' + lastName;
    }

    @Override
    public int getID() {
        return employeeID;
    }
}
