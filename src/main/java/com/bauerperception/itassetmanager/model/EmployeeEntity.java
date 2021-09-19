package com.bauerperception.itassetmanager.model;

public class EmployeeEntity implements Entity{
    private int employeeID;
    private String firstName;
    private String middleName;
    private String lastName;
    private String emailAddress;
    private int primaryWorkLocation;
    private int secondaryWorkLocation;

    public EmployeeEntity(String firstName, String middleName, String lastName, String emailAddress, int primaryLocationID, int secondaryLocationID) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.primaryWorkLocation = primaryLocationID;
        this.secondaryWorkLocation = secondaryLocationID;
    }

    public EmployeeEntity(int employeeID, String firstName, String middleName, String lastName, String emailAddress, int assignedWorkLocation, int secondaryWorkLocation) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.primaryWorkLocation = assignedWorkLocation;
        this.secondaryWorkLocation = secondaryWorkLocation;
    }

    public EmployeeEntity(String firstName, String middleName, String lastName, String emailAddress, int primaryWorkLocation) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.primaryWorkLocation = primaryWorkLocation;
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

    public int getPrimaryWorkLocation() {
        return primaryWorkLocation;
    }

    public void setPrimaryWorkLocation(int primaryWorkLocation) {
        this.primaryWorkLocation = primaryWorkLocation;
    }

    public int getSecondaryWorkLocation() {
        return secondaryWorkLocation;
    }

    public void setSecondaryWorkLocation(int secondaryWorkLocation) {
        this.secondaryWorkLocation = secondaryWorkLocation;
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
