package com.bauerperception.itassetmanager.controller;

import com.bauerperception.itassetmanager.DAO.AssetDAOImpl;
import com.bauerperception.itassetmanager.DAO.EmployeeDAOImpl;
import com.bauerperception.itassetmanager.DAO.LocationDAOImpl;
import com.bauerperception.itassetmanager.model.AssetEntity;
import com.bauerperception.itassetmanager.model.EmployeeEntity;
import com.bauerperception.itassetmanager.model.LocationEntity;
import com.bauerperception.itassetmanager.util.TimeUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddAssetController implements Initializable {

    Stage stage;

    @FXML
    private TextField inventoryAssetNameTxtBox;

    @FXML
    private ChoiceBox<String> inventoryAssetType;

    @FXML
    private TextField inventoryAssetModelNum;

    @FXML
    private TextField inventoryAssetDesc;

    @FXML
    private ChoiceBox<EmployeeEntity> inventoryAssetAssignedTo;

    @FXML
    private ChoiceBox<LocationEntity> inventoryAssetLocation;

    @FXML
    private DatePicker inventoryAssetPurchasedDate;

    @FXML
    private TextField inventoryAssetPurchasedPrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Load all asset types and set value
        try {
            inventoryAssetType.setItems(AssetDAOImpl.getAssetTypes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Load all employees and set value
        try {
            inventoryAssetAssignedTo.setItems(EmployeeDAOImpl.getAllEmployees());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Load all locations and set value
        try {
            inventoryAssetLocation.setItems(LocationDAOImpl.getAllLocations());
        } catch (Exception e) {
            e.printStackTrace();
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
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bauerperception/itassetmanager/main.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/com/bauerperception/itassetmanager/mainstyles.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            MainController controller = loader.getController();
            controller.openInventory(event);
        }
    }

    @FXML
    void saveAddAsset(ActionEvent event) throws Exception {
        String assetName = inventoryAssetNameTxtBox.getText();
        String assetModelNum = inventoryAssetModelNum.getText();
        String assetDescription = inventoryAssetDesc.getText();
        //TODO Need to add validation
        Float assetPurchasedPrice = Float.parseFloat(inventoryAssetPurchasedPrice.getText());
        String assetType = inventoryAssetType.getValue();
        int employeeID = inventoryAssetAssignedTo.getValue().getEmployeeID();
        int locationID = inventoryAssetLocation.getValue().getLocationID();
        LocalDate assetPurchasedDate = inventoryAssetPurchasedDate.getValue();
        AssetDAOImpl.addAsset(new AssetEntity(assetName, assetType, assetModelNum, assetDescription, employeeID, locationID, assetPurchasedDate, assetPurchasedPrice));

        //TODO Can extract this method I think
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bauerperception/itassetmanager/main.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/com/bauerperception/itassetmanager/mainstyles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
        MainController controller = loader.getController();
        controller.openInventory(event);
    }


}
