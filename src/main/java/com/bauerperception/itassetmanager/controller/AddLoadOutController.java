package com.bauerperception.itassetmanager.controller;

import com.bauerperception.itassetmanager.DAO.LoadOutDAOImpl;
import com.bauerperception.itassetmanager.model.EquipmentEntity;
import com.bauerperception.itassetmanager.model.LoadOutEntity;
import com.bauerperception.itassetmanager.util.HyperLinkCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddLoadOutController implements Initializable {

    Stage stage;
    int loadOutID;
    int newEquipmentSlotNum;
    ObservableList<EquipmentEntity> equipmentList;
    LoadOutEntity workInProgressLoadOut;
    String loadOutName;

    @FXML
    private Label wizardTitle;

    @FXML
    private TextField loadOutNameTxt;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button addEquipmentButton;

    @FXML
    private Button deleteEquipmentButton;

    @FXML
    private Button editEquipmentButton;

    @FXML
    private TableView<EquipmentEntity> equipmentTblView;

    @FXML
    private TableColumn<EquipmentEntity, Integer> slotNumCol;

    @FXML
    private TableColumn<EquipmentEntity, String> nameCol;

    @FXML
    private TableColumn<EquipmentEntity, String> modelNumCol;

    @FXML
    private TableColumn<EquipmentEntity, String> equipmentTypeCol;

    @FXML
    private TableColumn<EquipmentEntity, Integer> qtyCol;

    @FXML
    private TableColumn<EquipmentEntity, Float> purchasePriceCol;

    @FXML
    private TableColumn<EquipmentEntity, String> purchaseUrlCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        equipmentTblView.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection) -> {
            editEquipmentButton.setDisable(false);
            deleteEquipmentButton.setDisable(false);
        });

        loadOutID = 0;
        newEquipmentSlotNum = 0;
        equipmentList = FXCollections.observableArrayList();
        editEquipmentButton.setVisible(false);
        deleteEquipmentButton.setVisible(false);
    }

    @FXML
    void addEquipment(ActionEvent event) throws Exception {
        if (loadOutID == 0){
            loadOutID = LoadOutDAOImpl.getNewLoadOutID();
        }

        loadOutName = loadOutNameTxt.getText();

        //In all instances its better to add a slot number increase here
        newEquipmentSlotNum++;

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bauerperception/itassetmanager/modifyequipment.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/com/bauerperception/itassetmanager/addwizard.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
        ModifyEquipmentController controller = loader.getController();
        controller.addEquipmentFromLoadOutWizard(event, loadOutID, newEquipmentSlotNum, equipmentList, loadOutName);
    }

    @FXML
    void cancelAdd(ActionEvent event) throws IOException {
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
            controller.openLoadOuts(event);
        }
    }

    @FXML
    void deleteEquipment(ActionEvent event) {
        //TODO Be able to delete equipment from current equipment list
    }

    @FXML
    void editEquipment(ActionEvent event) {
        //TODO Be able to edit one of the equipment in the list
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bauerperception/itassetmanager/modifyequipment.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/com/bauerperception/itassetmanager/addwizard.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
        ModifyEquipmentController controller = loader.getController();
        controller.editEquipmentFromLoadOutWizard(event, loadOutID, newEquipmentSlotNum, equipmentList, loadOutName);
    }

    @FXML
    void saveAdd(ActionEvent event) {
//        String assetName = inventoryAssetNameTxtBox.getText();
//        String assetModelNum = inventoryAssetModelNum.getText();
//        String assetDescription = inventoryAssetDesc.getText();
//        //TODO Need to add validation
//        Float assetPurchasedPrice = Float.parseFloat(inventoryAssetPurchasedPrice.getText());
//        String assetType = inventoryAssetType.getValue();
//        int employeeID = inventoryAssetAssignedTo.getValue().getEmployeeID();
//        int locationID = inventoryAssetLocation.getValue().getLocationID();
//        LocalDate assetPurchasedDate = inventoryAssetPurchasedDate.getValue();
//        AssetDAOImpl.addAsset(new AssetEntity(assetName, assetType, assetModelNum, assetDescription, employeeID, locationID, assetPurchasedDate, assetPurchasedPrice));
//
//        //TODO Can extract this method I think
//        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bauerperception/itassetmanager/main.fxml"));
//        Scene scene = new Scene(loader.load());
//        scene.getStylesheets().add(getClass().getResource("/com/bauerperception/itassetmanager/mainstyles.css").toExternalForm());
//        stage.setScene(scene);
//        stage.show();
//        MainController controller = loader.getController();
//        controller.openInventory(event);
    }

    public void loadData(ActionEvent event, int loadOutID, int newEquipmentSlotNum, ObservableList<EquipmentEntity> equipmentList, String loadOutName) {
        this.loadOutID = loadOutID;
        this.newEquipmentSlotNum = newEquipmentSlotNum;
        this.equipmentList = equipmentList;
        this.loadOutName = loadOutName;

        loadOutNameTxt.setText(loadOutName);
        editEquipmentButton.setVisible(true);
        editEquipmentButton.setDisable(true);
        deleteEquipmentButton.setVisible(true);
        deleteEquipmentButton.setDisable(true);

        //TODO Fill in table view
        equipmentTblView.setItems(equipmentList);
        slotNumCol.setCellValueFactory(new PropertyValueFactory<>("loadOutSlotNum"));
        equipmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("equipmentType"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modelNumCol.setCellValueFactory(new PropertyValueFactory<>("modelNum"));
        purchasePriceCol.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantityNeeded"));
        purchaseUrlCol.setCellValueFactory(new PropertyValueFactory<>("whereToPurchaseURL"));
        //TODO Hyperlink url is still not working
//        purchaseUrlCol.setCellFactory(l -> {
//            return new TableCell<EquipmentEntity, Hyperlink>() {
//                @Override
//                protected void updateItem(Hyperlink item, boolean empty) {
//                    if (empty) {
//
//                    } else {
//                        item.setOnAction(e -> {
//                            Runtime rt = Runtime.getRuntime();
//                            try {
//                                rt.exec("rundll32 url.dll,FileProtocolHandler " + this.getText());
//                            } catch (IOException l) {
//                                l.printStackTrace();
//                            }
//                        });
//                    }
//                }
//            };
//        });

    }
}
