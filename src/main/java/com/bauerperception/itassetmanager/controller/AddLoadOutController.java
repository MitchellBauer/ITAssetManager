package com.bauerperception.itassetmanager.controller;

import com.bauerperception.itassetmanager.DAO.EquipmentDAOImpl;
import com.bauerperception.itassetmanager.DAO.LoadOutDAOImpl;
import com.bauerperception.itassetmanager.model.EquipmentEntity;
import com.bauerperception.itassetmanager.model.LoadOutEntity;
import com.bauerperception.itassetmanager.util.FXUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddLoadOutController implements Initializable {

    Stage stage;
    int loadOutID;
    ObservableList<EquipmentEntity> equipmentList;
    LoadOutEntity workInProgressLoadOut;
    String loadOutName;

    //<editor-fold desc="Description">
    @FXML
    private Label wizardTitle;

    @FXML
    private TextField loadOutNameTxt;

    @FXML
    private Button saveButton;

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
    private TableColumn<EquipmentEntity, String> mfrCol;

    @FXML
    private TableColumn<EquipmentEntity, String> modelNumCol;

    @FXML
    private TableColumn<EquipmentEntity, String> equipmentTypeCol;

    @FXML
    private TableColumn<EquipmentEntity, Integer> qtyCol;

    @FXML
    private TableColumn<EquipmentEntity, Double> purchasePriceCol;

    @FXML
    private TableColumn<EquipmentEntity, String> purchaseUrlCol;
    //</editor-fold>

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        equipmentTblView.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection) -> {
            editEquipmentButton.setDisable(false);
            deleteEquipmentButton.setDisable(false);
        });

        loadOutID = 0;
        equipmentList = FXCollections.observableArrayList();
        editEquipmentButton.setVisible(false);
        deleteEquipmentButton.setVisible(false);
        saveButton.setDisable(true);
    }

    @FXML
    void addEquipment(ActionEvent event) throws Exception {
        if (loadOutID == 0){
            loadOutID = LoadOutDAOImpl.getNewLoadOutID();
        }

        loadOutName = loadOutNameTxt.getText();

        ModifyEquipmentController controller = FXUtil.goToScene(event,"modifyequipment", "addwizard").getController();
        controller.addEquipmentFromLoadOutWizard(event, loadOutID, equipmentList, loadOutName);
    }

    @FXML
    void cancel(ActionEvent event) throws IOException {
        if (FXUtil.cancelWizard()) {
            FXUtil.goToMainScene(event).openLoadOuts(event);
        }
    }

    @FXML
    void deleteEquipment(ActionEvent event) {
        if (FXUtil.confirmDeletion(event)){
            equipmentList.remove(equipmentTblView.getSelectionModel().getSelectedItem());
            equipmentTblView.setItems(equipmentList);
        }
    }

    @FXML
    void editEquipment(ActionEvent event) throws IOException {
        EquipmentEntity selectedEquipment = equipmentTblView.getSelectionModel().getSelectedItem();
        ModifyEquipmentController controller = FXUtil.goToScene(event,"modifyequipment", "addwizard").getController();
        controller.editEquipmentFromLoadOutWizard(event, loadOutID, equipmentList, loadOutName, selectedEquipment);
    }

    @FXML
    void save(ActionEvent event) throws Exception {
        if (!equipmentList.isEmpty()){
            for (EquipmentEntity i : equipmentList){
                i.setAssignedLoadOutID(loadOutID);
            }
            EquipmentDAOImpl.saveListOfEquipment(equipmentList);
        } else {
            FXUtil.throwAlert("Entry Data Missing", "Please assign at least one piece of equipment to the loadout.");
        }

        if (!loadOutNameTxt.getText().isEmpty()){
            LoadOutDAOImpl.addLoadOut(new LoadOutEntity(loadOutID, loadOutNameTxt.getText()));
            FXUtil.goToMainScene(event).openLoadOuts(event);
        } else {
            FXUtil.throwAlert("Entry Data Missing", "Please enter a name for the loadout.");
        }
    }

    public void loadData(ActionEvent event, int loadOutID, ObservableList<EquipmentEntity> equipmentList, String loadOutName) {
        this.loadOutID = loadOutID;
        System.out.println("My loadOut ID is: " + this.loadOutID);
        this.equipmentList = equipmentList;
        this.loadOutName = loadOutName;

        loadOutNameTxt.setText(loadOutName);
        editEquipmentButton.setVisible(true);
        editEquipmentButton.setDisable(true);
        deleteEquipmentButton.setVisible(true);
        deleteEquipmentButton.setDisable(true);
        saveButton.setDisable(false);

        equipmentTblView.setItems(equipmentList);
        slotNumCol.setCellValueFactory(new PropertyValueFactory<>("loadOutSlotNum"));
        equipmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("equipmentType"));
        mfrCol.setCellValueFactory(new PropertyValueFactory<>("mfr"));
        modelNumCol.setCellValueFactory(new PropertyValueFactory<>("modelNum"));
        purchasePriceCol.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantityNeeded"));
        purchaseUrlCol.setCellValueFactory(new PropertyValueFactory<>("whereToPurchaseURL"));
    }
}
