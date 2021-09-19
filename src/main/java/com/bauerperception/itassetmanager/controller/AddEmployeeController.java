package com.bauerperception.itassetmanager.controller;

import com.bauerperception.itassetmanager.DAO.AssetDAOImpl;
import com.bauerperception.itassetmanager.DAO.EmployeeDAOImpl;
import com.bauerperception.itassetmanager.DAO.LoadOutDAOImpl;
import com.bauerperception.itassetmanager.DAO.LocationDAOImpl;
import com.bauerperception.itassetmanager.model.AssetEntity;
import com.bauerperception.itassetmanager.model.EmployeeEntity;
import com.bauerperception.itassetmanager.model.LoadOutEntity;
import com.bauerperception.itassetmanager.model.LocationEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
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

    @FXML
    private CheckBox travelBagCheckBox;

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

        if (primaryWorkLocationChoice.getItems().size() > 0){
            doLocationsExist = true;
        } else {
            doLocationsExist = false;
        }

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

        if (primaryLoadOutChoice.getItems().size() > 0){
            doLoadOutsExist = true;
        } else {
            doLoadOutsExist = false;
        }

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
            if (primaryLoadOutChoice.getValue() != null){
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
                //TODO Need to script the add employee wizard
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
        Alert cancelConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        cancelConfirmation.setTitle("");
        cancelConfirmation.setHeaderText("Cancel Confirmation");
        cancelConfirmation.setContentText("Do you really want to cancel? You will lose any data entered");
        Optional<ButtonType> confirmationResult = cancelConfirmation.showAndWait();

        if (confirmationResult.get() == ButtonType.OK) {
            goToMainScene(event);
        }
    }

    @FXML
    void saveAddEmployee(ActionEvent event) throws Exception {
        //TODO Need to add validation
        LocationEntity primaryLocation;
        LocationEntity secondaryLocation;
        LoadOutEntity primaryLoadOut;
        LoadOutEntity secondaryLoadOut;

        //Safe check for null values in these entities
        if (primaryWorkLocationChoice.getValue() != null){
            primaryLocation = primaryWorkLocationChoice.getValue();
        } else {
            throw new Exception("Primary location can't be null.");
        }

        if (secondaryWorkLocationChoice.getValue() != null && secondaryLocationExists) {
            secondaryLocation = secondaryWorkLocationChoice.getValue();
        } else {
            throw new Exception("Secondary location can't be null.");
        }

        if (primaryLoadOutChoice.getValue() != null){
            primaryLoadOut = primaryLoadOutChoice.getValue();
        } else {
            throw new Exception("Primary loadout can't be null.");
        }

        if (secondaryLoadOutChoice.getValue() != null && secondaryLocationExists){
            secondaryLoadOut = secondaryLoadOutChoice.getValue();
        } else {
            throw new Exception("Secondary loadout can't be null.");
        }

        if (travelBagCheckBox.isSelected()){
            //TODO assign travel bag equipment to employee
        }

        primaryLocation.setLoadOutID(primaryLoadOut.getLoadOutID());
        secondaryLocation.setLoadOutID(secondaryLoadOut.getLoadOutID());

        String firstName = employeeFirstNameTxt.getText();
        String middleName = employeeMiddleNameTxt.getText();
        String lastName = employeeLastNameTxt.getText();
        String emailAddress = employeeEmailTxt.getText();

        if (secondaryLocation != null){
            EmployeeDAOImpl.addEmployee(new EmployeeEntity(firstName, middleName, lastName, emailAddress, primaryLocation.getLocationID(), secondaryLocation.getLocationID()));
        } else {
            EmployeeDAOImpl.addEmployee(new EmployeeEntity(firstName, middleName, lastName, emailAddress, primaryLocation.getLocationID()));
        }

        goToMainScene(event);
    }

    private void goToMainScene(ActionEvent event) throws java.io.IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bauerperception/itassetmanager/main.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/bauerperception/itassetmanager/mainstyles.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
        MainController controller = loader.getController();
        controller.openEmployees(event);
    }


    @FXML
    void goToLocationEditor(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bauerperception/itassetmanager/main.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/bauerperception/itassetmanager/mainstyles.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
        MainController controller = loader.getController();
        controller.openLocations();
    }

    @FXML
    void goToLoadOutEditor(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bauerperception/itassetmanager/main.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/bauerperception/itassetmanager/mainstyles.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
        MainController controller = loader.getController();
        controller.openLoadOuts(event);
    }

    @FXML
    void backButton(ActionEvent event) {
        //TODO Some annoying validation has to go into this. One could go back and delete an item from a choicebox possibly.
        if (loadOutAssignmentPane.isVisible()){
            backButton.setVisible(false);
            loadOutAssignmentPane.setVisible(false);
            locationAssignmentPane.setVisible(true);
            nextButton.setDisable(true);
        }

        if (employeeDataPane.isVisible()){
            employeeDataPane.setVisible(false);
            loadOutAssignmentPane.setVisible(true);
            nextButton.setDisable(true);
            nextButton.setVisible(true);
        }
    }

    @FXML
    void nextButton(ActionEvent event) {
        if (locationAssignmentPane.isVisible()){
            locationAssignmentPane.setVisible(false);
            backButton.setVisible(true);
            nextButton.setDisable(true);
            loadOutAssignmentPane.setVisible(true);
        }

        if (loadOutAssignmentPane.isVisible()){
            loadOutAssignmentPane.setVisible(false);
            nextButton.setVisible(false);
            addButton.setVisible(true);
            employeeDataPane.setVisible(true);
        }
    }
}
