package com.bauerperception.itassetmanager.controller;

import com.bauerperception.itassetmanager.DAO.AssetDAOImpl;
import com.bauerperception.itassetmanager.DAO.EmployeeDAOImpl;
import com.bauerperception.itassetmanager.DAO.LocationDAOImpl;
import com.bauerperception.itassetmanager.model.AssetEntity;
import com.bauerperception.itassetmanager.model.EmployeeEntity;
import com.bauerperception.itassetmanager.model.LocationEntity;
import com.bauerperception.itassetmanager.util.FXUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddAssetController implements Initializable {

    Stage stage;

    @FXML
    private TextField inventoryAssetMfgTxtBox;

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
        if (FXUtil.cancelWizard()) {
            FXUtil.goToMainScene(event).openInventory(event);
        }
    }

    @FXML
    void saveAddAsset(ActionEvent event) throws Exception {
        if(!inventoryAssetType.getValue().isEmpty()){
            String assetManufacturer = inventoryAssetMfgTxtBox.getText();
            String assetType = inventoryAssetType.getValue();

            //TODO need a better way to handle a "0" ID. Right now this might help?
            int employeeID = -1;
            if (inventoryAssetAssignedTo.getValue() != null){
                employeeID = inventoryAssetAssignedTo.getValue().getEmployeeID();
            }

            int locationID = -1;
            if (inventoryAssetLocation.getValue() != null){
                locationID = inventoryAssetLocation.getValue().getLocationID();
            }

            String assetModelNum = inventoryAssetModelNum.getText();
            String assetDescription = inventoryAssetDesc.getText();
            double assetPurchasedPrice = Double.parseDouble(inventoryAssetPurchasedPrice.getText());
            LocalDate assetPurchasedDate = inventoryAssetPurchasedDate.getValue();
            AssetDAOImpl.addAsset(new AssetEntity(assetManufacturer, assetType, assetModelNum, assetDescription, employeeID, locationID, assetPurchasedDate, assetPurchasedPrice));

            FXUtil.goToMainScene(event).openInventory(event);
        } else {
            FXUtil.throwAlert("Entry Data Missing", "An asset requires a type.");
        }
    }


}
