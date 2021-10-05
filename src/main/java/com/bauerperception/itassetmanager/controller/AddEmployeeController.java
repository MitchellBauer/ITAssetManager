package com.bauerperception.itassetmanager.controller;

import com.bauerperception.itassetmanager.DAO.EmployeeDAOImpl;
import com.bauerperception.itassetmanager.DAO.LoadOutDAOImpl;
import com.bauerperception.itassetmanager.DAO.LocationDAOImpl;
import com.bauerperception.itassetmanager.model.EmployeeEntity;
import com.bauerperception.itassetmanager.model.LoadOutEntity;
import com.bauerperception.itassetmanager.model.LocationEntity;
import com.bauerperception.itassetmanager.util.FXUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {

    Stage stage;
    boolean doLocationsExist;
    boolean doLoadOutsExist;
    boolean secondaryLocationExists;
    boolean secondaryLoadOutExists;

    @FXML
    private Label wizardTitle;

    @FXML
    private GridPane locationAssignmentPane;

    @FXML
    private ChoiceBox<LocationEntity> secondaryWorkLocationChoice;

    @FXML
    private ChoiceBox<LocationEntity> primaryWorkLocationChoice;

    @FXML
    private GridPane loadOutAssignmentPane;

//    @FXML
//    private CheckBox travelBagCheckBox;

    @FXML
    private Label secondaryLoadOutLbl;

    @FXML
    private ChoiceBox<LoadOutEntity> primaryLoadOutChoice;

    @FXML
    private ChoiceBox<LoadOutEntity> secondaryLoadOutChoice;

    @FXML
    private Label primaryLoadOutLbl;

    @FXML
    private Label primaryLoadOutLbl1;

    @FXML
    private GridPane employeeDataPane;

    @FXML
    private Label employeeFirstNameLbl;

    @FXML
    private TextField employeeFirstNameTxt;

    @FXML
    private Label employeeMiddleNameLbl;

    @FXML
    private TextField employeeMiddleNameTxt;

    @FXML
    private Label employeeLastNameLbl;

    @FXML
    private TextField employeeLastNameTxt;

    @FXML
    private Label employeeEmailLbl;

    @FXML
    private TextField employeeEmailTxt;

    @FXML
    private Button backButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label errorNoLocationsLbl;

    @FXML
    private Button goToLocationEditorBtn;

    @FXML
    private Label errorNoLoadOutsLbl;

    @FXML
    private Button goToLoadOutEditorBtn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Load all locations and set values
        try {
            primaryWorkLocationChoice.setItems(LocationDAOImpl.getAllLocations());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            secondaryWorkLocationChoice.setItems(LocationDAOImpl.getAllLocations());
        } catch (Exception e) {
            e.printStackTrace();
        }

        doLocationsExist = primaryWorkLocationChoice.getItems().size() > 0;

        //Add listeners to primary location and primary loadou

        //Load loadout entities
        try {
            primaryLoadOutChoice.setItems(LoadOutDAOImpl.getAllLoadOuts());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            secondaryLoadOutChoice.setItems(LoadOutDAOImpl.getAllLoadOuts());
        } catch (Exception e) {
            e.printStackTrace();
        }

        doLoadOutsExist = primaryLoadOutChoice.getItems().size() > 0;

        //Add listeners to choice boxes, to be able to move on to the next step.
        primaryWorkLocationChoice.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            nextButton.setDisable(false);
        });

        secondaryWorkLocationChoice.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            secondaryLocationExists = true;
        });

        primaryLoadOutChoice.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            //Annoying validation where one could click on the secondary loadout first.
            if (!secondaryLocationExists || (primaryLoadOutChoice.getValue() != null)) {
                nextButton.setDisable(false);
            }
        });

        secondaryLoadOutChoice.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (primaryLoadOutChoice.getValue() != null) {
                nextButton.setDisable(false);
            }
        });


        /*
        This only needs to be checked at the beginning of the application. Probably could get around it by giving some starter data.
        But if someone fails to install the MySQL database properly then there wouldn't be any data anyways.
        Basically, this checks for location and loadout data. If they don't exist, we show an error, and the user can't continue adding
        an employee, because we need a location and loadout.
         */
        if (doLocationsExist) {
            if (doLoadOutsExist) {
                //All data exists, setup the wizard
                wizardTitle.setText("Assign Location to Employee");
                locationAssignmentPane.setVisible(true);
                nextButton.setVisible(true);
                nextButton.setDisable(true);
            } else {
                wizardTitle.setText("Error");
                errorNoLoadOutsLbl.setVisible(true);
                goToLoadOutEditorBtn.setVisible(true);
            }
        } else {
            wizardTitle.setText("Error");
            errorNoLocationsLbl.setVisible(true);
            goToLocationEditorBtn.setVisible(true);
        }
    }

    @FXML
    void cancelAdd(ActionEvent event) throws Exception {
        if (FXUtil.cancelWizard()) {
            FXUtil.goToMainScene(event).openEmployees(event);
        }
    }

    @FXML
    void saveAddEmployee(ActionEvent event) throws Exception {
        LocationEntity primaryLocation;
        LocationEntity secondaryLocation;
        LoadOutEntity primaryLoadOut;
        LoadOutEntity secondaryLoadOut;

        primaryLocation = primaryWorkLocationChoice.getValue();
        secondaryLocation = secondaryWorkLocationChoice.getValue();
        primaryLoadOut = primaryLoadOutChoice.getValue();
        secondaryLoadOut = secondaryLoadOutChoice.getValue();

        //if(travelBagCheckBox.isSelected()){
        //Future assign travel bag equipment to employee
        //}

        primaryLocation.setLoadOutID(primaryLoadOut.getLoadOutID());
        if (secondaryLoadOut != null) {
            secondaryLocation.setLoadOutID(secondaryLoadOut.getLoadOutID());
        }

        String firstName = employeeFirstNameTxt.getText();
        String middleName = employeeMiddleNameTxt.getText();
        String lastName = employeeLastNameTxt.getText();
        String emailAddress = employeeEmailTxt.getText();

        if (!employeeFirstNameTxt.getText().isEmpty()) {
            if (!employeeLastNameTxt.getText().isEmpty()) {
                if (primaryWorkLocationChoice.getValue() != null) {
                    if (secondaryLoadOut != null) {
                        EmployeeDAOImpl.addEmployee(new EmployeeEntity(firstName, middleName, lastName, emailAddress, primaryLocation.getLocationID(), secondaryLocation.getLocationID()));
                        FXUtil.goToMainScene(event).openEmployees(event);
                    } else {
                        EmployeeDAOImpl.addEmployee(new EmployeeEntity(firstName, middleName, lastName, emailAddress, primaryLocation.getLocationID()));
                        FXUtil.goToMainScene(event).openEmployees(event);
                    }
                } else {
                    FXUtil.throwAlert("Entry Data Missing", "A employee requires a primary work location.");
                }
            } else {
                FXUtil.throwAlert("Entry Data Missing", "A employee requires a last name.");
            }
        } else {
            FXUtil.throwAlert("Entry Data Missing", "A employee requires a first name.");
        }
    }

    @FXML
    void goToLocationEditor(ActionEvent event) throws IOException {
        FXUtil.goToMainScene(event).openLocations();
    }

    @FXML
    void goToLoadOutEditor(ActionEvent event) throws IOException {
        FXUtil.goToMainScene(event).openLoadOuts(event);
    }

    @FXML
    void backButton(ActionEvent event) {
        if (loadOutAssignmentPane.isVisible()) {
            backButton.setVisible(false);
            loadOutAssignmentPane.setVisible(false);
            locationAssignmentPane.setVisible(true);
            nextButton.setDisable(true);
            return;
        }

        if (employeeDataPane.isVisible()) {
            employeeDataPane.setVisible(false);
            loadOutAssignmentPane.setVisible(true);
            nextButton.setDisable(true);
            nextButton.setVisible(true);
            return;
        }
    }

    @FXML
    void nextButton(ActionEvent event) {
        if (locationAssignmentPane.isVisible()) {
            locationAssignmentPane.setVisible(false);
            backButton.setVisible(true);
            nextButton.setDisable(true);
            loadOutAssignmentPane.setVisible(true);
            if (primaryWorkLocationChoice.getValue().getLoadOutID() > 0) {
                primaryLoadOutChoice.setValue(FXUtil.getEntityByID(primaryLoadOutChoice.getItems(), primaryWorkLocationChoice.getValue().getLoadOutID()));
            }
            if (secondaryWorkLocationChoice.getValue() != null) {
                if (secondaryWorkLocationChoice.getValue().getLoadOutID() > 0) {
                    secondaryLoadOutChoice.setValue(FXUtil.getEntityByID(secondaryLoadOutChoice.getItems(), secondaryWorkLocationChoice.getValue().getLoadOutID()));
                }
            }
            return;
        }

        if (loadOutAssignmentPane.isVisible()) {
            loadOutAssignmentPane.setVisible(false);
            nextButton.setVisible(false);
            addButton.setVisible(true);
            employeeDataPane.setVisible(true);
            return;
        }
    }
}
