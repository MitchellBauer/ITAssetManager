package com.bauerperception.itassetmanager.controller;

import com.bauerperception.itassetmanager.DAO.EquipmentDAOImpl;
import com.bauerperception.itassetmanager.model.EquipmentEntity;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyEquipmentController implements Initializable {

    Stage stage;
    int loadOutID;
    int newEquipmentSlotNum;
    private ObservableList<EquipmentEntity> equipmentList;
    String loadOutName;
    boolean addingEquipmentToLoadOutWizard;

    @FXML
    private Label wizardTitle;

    @FXML
    private TextField equipmentNameTxt;

    @FXML
    private ChoiceBox<EquipmentEntity> existingEquipmentChoice;

    @FXML
    private Label existingEquipmentLbl;

    @FXML
    private ChoiceBox<String> typeChoice;

    @FXML
    private TextField equipmentModelNumTxt;

    @FXML
    private TextField urlTxt;

    @FXML
    private TextField qtyNeededTxt;

    @FXML
    private TextField purchasePriceTxt;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        existingEquipmentChoice.setVisible(false);
        existingEquipmentLbl.setVisible(false);

        addingEquipmentToLoadOutWizard = false;
        //TODO Fill in choice boxs
    }

    @FXML
    void cancelAdd(ActionEvent event) throws IOException {
        if (addingEquipmentToLoadOutWizard){
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bauerperception/itassetmanager/addloadout.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/com/bauerperception/itassetmanager/addwizard.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            AddLoadOutController controller = loader.getController();
            controller.loadData(event, loadOutID, newEquipmentSlotNum, equipmentList, loadOutName);
        }
    }

    @FXML
    void saveAdd(ActionEvent event) throws Exception {
        if (addingEquipmentToLoadOutWizard){
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bauerperception/itassetmanager/addloadout.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/com/bauerperception/itassetmanager/addwizard.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            AddLoadOutController controller = loader.getController();

            //save new equipment to work in progress equipment list
            int equipmentID = EquipmentDAOImpl.newEquipmentID();
            String name = equipmentNameTxt.getText();
            String modelNum = equipmentModelNumTxt.getText();
            String equipmentType = typeChoice.getValue();
            int quantity = Integer.parseInt(qtyNeededTxt.getText());
            float purchasePrice = Float.parseFloat(purchasePriceTxt.getText());
            String purchaseUrl = urlTxt.getText();
            equipmentList.add(new EquipmentEntity(equipmentID, name, modelNum, equipmentType, loadOutID, newEquipmentSlotNum, quantity, purchasePrice, purchaseUrl));
            controller.loadData(event, loadOutID, newEquipmentSlotNum, equipmentList, loadOutName);
        }
    }

    public void addEquipmentFromLoadOutWizard(ActionEvent event, int loadOutID, int newEquipmentSlotNum, ObservableList<EquipmentEntity> equipmentList, String loadOutName) {
        //Enable existing equipment for faster editing. In this scenario, we know we aren't creating new equipment.
        existingEquipmentChoice.setVisible(true);
        existingEquipmentLbl.setVisible(true);

        //Set these values to public
        this.loadOutID = loadOutID;
        this.newEquipmentSlotNum = newEquipmentSlotNum;
        this.equipmentList = equipmentList;
        this.loadOutName = loadOutName;

        addingEquipmentToLoadOutWizard = true;

        //TODO Fill in existing choice box
    }
}
