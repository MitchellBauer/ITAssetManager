package com.bauerperception.itassetmanager.controller;

import com.bauerperception.itassetmanager.DAO.DBConn;
import com.bauerperception.itassetmanager.util.FXUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private TextField settingsHostIPAddressTxt;

    @FXML
    private TextField settingsUserTxt;

    @FXML
    private TextField settingsDatabaseNameTxt;

    @FXML
    private TextField settingsPasswordTxt;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConn.getDatabaseConfig();

        if (!DBConn.ipaddress.isEmpty()){
            settingsHostIPAddressTxt.setText(DBConn.ipaddress);
        }
        if (!DBConn.user.isEmpty()){
            settingsUserTxt.setText(DBConn.user);
        }
        if (!DBConn.databaseName.isEmpty()){
            settingsDatabaseNameTxt.setText(DBConn.databaseName);
        }
        if (!DBConn.password.isEmpty()){
            settingsPasswordTxt.setText(DBConn.password);
        }
    }

    @FXML
    void cancel(ActionEvent event) throws Exception {
        if (FXUtil.cancelWizard()) {
            FXUtil.goToMainScene(event);
        }
    }

    @FXML
    void save(ActionEvent event){
        if (!settingsHostIPAddressTxt.getText().isEmpty()){
            if (!settingsUserTxt.getText().isEmpty()){
                if (!settingsDatabaseNameTxt.getText().isEmpty()){
                    if (!settingsPasswordTxt.getText().isEmpty()){
                        Properties properties = new Properties();
                        String fileName = "dbconnection.properties";

                        try {
                            //InputStream inputStream = SettingsController.class.getResourceAsStream("/com/bauerperception/itassetmanager/" + fileName);
                            FileInputStream inputStream = new FileInputStream(DBConn.applicationPath + "dbconnection.properties");
                            properties.load(inputStream);
                        } catch (FileNotFoundException e) {
                            System.out.println("File not found exception:" + e.getMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        properties.setProperty("ipaddress", settingsHostIPAddressTxt.getText());
                        properties.setProperty("user", settingsUserTxt.getText());
                        properties.setProperty("databasename", settingsDatabaseNameTxt.getText());
                        properties.setProperty("password", settingsPasswordTxt.getText());

                        try {
                            //System.out.println("Save file path: " + Objects.requireNonNull(SettingsController.class.getResource("/com/bauerperception/itassetmanager/" + fileName)).getPath());
                            //FileOutputStream fileOutputStream = new FileOutputStream(Objects.requireNonNull(SettingsController.class.getResource("/com/bauerperception/itassetmanager/" + fileName)).getPath());
                            //FileOutputStream fileOutputStream = new FileOutputStream(Objects.requireNonNull(SettingsController.class.getResource(fileName)).getPath());
                            FileOutputStream fileOutputStream = new FileOutputStream(DBConn.applicationPath + "dbconnection.properties");
                            properties.store(fileOutputStream, null);
                        } catch (IOException e) {
                            System.out.println("Can't find specified properties file.");
                        }

                        try {
                            FXUtil.goToMainScene(event);
                        } catch (IOException e) {
                            System.out.println("Main scene file not found:" + e.getMessage());
                        }
                    } else {
                        FXUtil.throwAlert("Data Missing", "Password is required.");
                    }
                } else {
                    FXUtil.throwAlert("Data Missing", "Database name is required, by default it should be \"itassetdb\"");
                }
            } else {
                FXUtil.throwAlert("Data Missing", "User is required.");
            }
        } else {
            FXUtil.throwAlert("Data Missing", "IP Address or Host Name is required.");
        }
    }
}
