package com.bauerperception.itassetmanager.controller;

import com.bauerperception.itassetmanager.DAO.LocationDAOImpl;
import com.bauerperception.itassetmanager.model.LocationEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddLocationController {

    Stage stage;

    @FXML
    private Label wizardTitle;

    @FXML
    private Label locationNameLbl;

    @FXML
    private TextField locationNameTxt;

    @FXML
    private Button addLocationButton;

    @FXML
    private Button cancelAddLocationButton;

    @FXML
    void cancelAdd(ActionEvent event) throws IOException {
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
    void saveAddLocation(ActionEvent event) throws Exception {
        //TODO Need to add validation
        String workLocationName = locationNameTxt.getText();
        LocationDAOImpl.addLocation(new LocationEntity(workLocationName));

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
