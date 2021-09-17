package com.bauerperception.itassetmanager.controller;

import com.bauerperception.itassetmanager.DAO.AssetDAOImpl;
import com.bauerperception.itassetmanager.DAO.EmployeeDAOImpl;
import com.bauerperception.itassetmanager.DAO.LocationDAOImpl;
import com.bauerperception.itassetmanager.model.AssetEntity;
import com.bauerperception.itassetmanager.model.EmployeeEntity;
import com.bauerperception.itassetmanager.model.LocationEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {

    Stage stage;

    @FXML
    private Label wizardTitle;

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
    private Label employeeWorkLocLbl;

    @FXML
    private ChoiceBox<LocationEntity> employeeWorkLocTxt;

    @FXML
    private Button addSaveButton;

    @FXML
    private Button cancelAddEmployeeButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Load all locations and set values
        try {
            employeeWorkLocTxt.setItems(LocationDAOImpl.getAllLocations());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO Need to script the add employee wizard
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
        String firstName = employeeFirstNameTxt.getText();
        String middleName = employeeMiddleNameTxt.getText();
        String lastName = employeeLastNameTxt.getText();
        String emailAddress = employeeEmailTxt.getText();
        int assignedWorkLocationID = employeeWorkLocTxt.getValue().getLocationID();
        EmployeeDAOImpl.addEmployee(new EmployeeEntity(firstName, middleName, lastName, emailAddress, assignedWorkLocationID));

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
}
