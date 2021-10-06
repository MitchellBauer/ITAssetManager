package com.bauerperception.itassetmanager.controller;

import com.bauerperception.itassetmanager.DAO.EquipmentDAOImpl;
import com.bauerperception.itassetmanager.DAO.TypeDAOImpl;
import com.bauerperception.itassetmanager.model.EquipmentEntity;
import com.bauerperception.itassetmanager.model.LoadOutEntity;
import com.bauerperception.itassetmanager.util.FXUtil;
import javafx.collections.FXCollections;
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
import java.util.Comparator;
import java.util.ResourceBundle;

public class ModifyEquipmentController implements Initializable {

    Stage stage;
    int loadOutID;
    private ObservableList<EquipmentEntity> equipmentList;
    String loadOutName;
    boolean addingEquipmentToLoadOutWizard;
    private boolean editingEquipmentFromLoadOutWizard;
    private EquipmentEntity editingEquipmentEntity;
    private boolean editingEquipmentFromMain;
    private boolean addEquipmentFromMain;

    //<editor-fold desc="Description">
    @FXML
    private Label wizardTitle;

    @FXML
    private TextField equipmentMfrTxt;

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
    //</editor-fold>

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        existingEquipmentChoice.setVisible(false);
        existingEquipmentLbl.setVisible(false);

        addingEquipmentToLoadOutWizard = false;
        editingEquipmentFromLoadOutWizard = false;
        editingEquipmentFromMain = false;
        addEquipmentFromMain = false;

        try {
            existingEquipmentChoice.setItems(EquipmentDAOImpl.getAllEquipment());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            typeChoice.setItems(TypeDAOImpl.getAllTypes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Populate fields on existing choice box change
        existingEquipmentChoice.getSelectionModel().selectedItemProperty().addListener((observableValue, oldChoice, newChoice) -> populateFields(newChoice));
    }

    @FXML
    void cancelAdd(ActionEvent event) throws IOException {
        if (addingEquipmentToLoadOutWizard || editingEquipmentFromLoadOutWizard){
            AddLoadOutController controller = FXUtil.goToScene(event,"addloadout", "addwizard").getController();
            controller.loadData(event, loadOutID, equipmentList, loadOutName);
        } else {
            if (FXUtil.cancelWizard()){
                FXUtil.goToMainScene(event).openLoadOuts(event);
            }
        }
    }

    @FXML
    void saveAdd(ActionEvent event) throws Exception {
        if (addingEquipmentToLoadOutWizard){
            if (!equipmentMfrTxt.getText().isEmpty()){
                if (!equipmentModelNumTxt.getText().isEmpty()){
                    if (!typeChoice.getValue().isEmpty()){
                        if (FXUtil.isNumeric(purchasePriceTxt.getText())){
                            if (FXUtil.isNumeric(qtyNeededTxt.getText())){
                                if (Integer.parseInt(qtyNeededTxt.getText()) > 0){
                                    //save new equipment to work in progress equipment list
                                    int equipmentID = EquipmentDAOImpl.getNewEquipmentID();
                                    if (!equipmentList.isEmpty()) {
                                        int highestID = equipmentID;
                                        for (EquipmentEntity i : equipmentList) {
                                            if (i.getEquipmentID() > highestID) {
                                                highestID = i.getEquipmentID();
                                            }
                                        }
                                        equipmentID++;
                                    }
                                    String name = equipmentMfrTxt.getText();
                                    String modelNum = equipmentModelNumTxt.getText();
                                    String equipmentType = typeChoice.getValue();
                                    int quantity = Integer.parseInt(qtyNeededTxt.getText());
                                    double purchasePrice = Double.parseDouble(purchasePriceTxt.getText());
                                    String purchaseUrl = urlTxt.getText();
                                    equipmentList.add(new EquipmentEntity(equipmentID, name, modelNum, equipmentType, loadOutID, getEquipmentSlotNum(), quantity, purchasePrice, purchaseUrl));

                                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bauerperception/itassetmanager/addloadout.fxml"));
                                    Scene scene = new Scene(loader.load());
                                    scene.getStylesheets().add(getClass().getResource("/com/bauerperception/itassetmanager/addwizard.css").toExternalForm());
                                    stage.setScene(scene);
                                    stage.show();
                                    AddLoadOutController controller = loader.getController();
                                    controller.loadData(event, loadOutID, equipmentList, loadOutName);
                                } else {
                                    FXUtil.throwAlert("Incorrect Data Entry", "Quantity needs to be higher than zero or delete the equipment.");
                                }
                            } else {
                                FXUtil.throwAlert("Incorrect Data Entry", "The quantity has to be numeric.");
                            }
                        } else {
                            FXUtil.throwAlert("Incorrect Data Entry", "The purchase price has to be numeric.");
                        }
                    } else {
                        FXUtil.throwAlert("Entry Data Missing", "Equipment requires a type.");
                    }
                } else {
                    FXUtil.throwAlert("Entry Data Missing", "Equipment requires a model number.");
                }
            } else {
                FXUtil.throwAlert("Entry Data Missing", "Equipment requires a manufacturer.");
            }
        }

        if(editingEquipmentFromLoadOutWizard){
            if (!equipmentMfrTxt.getText().isEmpty()){
                if (!equipmentModelNumTxt.getText().isEmpty()){
                    if (FXUtil.isNumeric(purchasePriceTxt.getText())){
                        if (FXUtil.isNumeric(qtyNeededTxt.getText())){
                            if (Integer.parseInt(qtyNeededTxt.getText()) > 0){
                                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bauerperception/itassetmanager/addloadout.fxml"));
                                Scene scene = new Scene(loader.load());
                                scene.getStylesheets().add(getClass().getResource("/com/bauerperception/itassetmanager/addwizard.css").toExternalForm());
                                stage.setScene(scene);
                                stage.show();
                                AddLoadOutController controller = loader.getController();
                                //save new equipment to work in progress equipment list
                                int equipmentID = editingEquipmentEntity.getEquipmentID();
                                String name = equipmentMfrTxt.getText();
                                String modelNum = equipmentModelNumTxt.getText();
                                String equipmentType = typeChoice.getValue();
                                int quantity = Integer.parseInt(qtyNeededTxt.getText());
                                double purchasePrice = Double.parseDouble(purchasePriceTxt.getText());
                                String purchaseUrl = urlTxt.getText();

                                for (EquipmentEntity i : equipmentList){
                                    if (i.getEquipmentID() == equipmentID){
                                        equipmentList.remove(i);
                                        equipmentList.add(new EquipmentEntity(equipmentID, name, modelNum, equipmentType, loadOutID, getEquipmentSlotNum(), quantity, purchasePrice, purchaseUrl));
                                    }
                                }
                                controller.loadData(event, loadOutID, equipmentList, loadOutName);
                            } else {
                                FXUtil.throwAlert("Incorrect Data Entry", "Quantity needs to be higher than zero or delete the equipment.");
                            }
                        } else {
                            FXUtil.throwAlert("Incorrect Data Entry", "The quantity has to be numeric.");
                        }
                    } else {
                        FXUtil.throwAlert("Incorrect Data Entry", "The purchase price has to be numeric.");
                    }
                } else {
                    FXUtil.throwAlert("Entry Data Missing", "Equipment requires a model number.");
                }
            } else {
                FXUtil.throwAlert("Entry Data Missing", "Equipment requires a manufacturer.");
            }
        }

        if (editingEquipmentFromMain){
            if (!equipmentMfrTxt.getText().isEmpty()){
                if (!equipmentModelNumTxt.getText().isEmpty()){
                    if (FXUtil.isNumeric(purchasePriceTxt.getText())){
                        if (FXUtil.isNumeric(qtyNeededTxt.getText())){
                            if (Integer.parseInt(qtyNeededTxt.getText()) > 0){
                                String name = equipmentMfrTxt.getText();
                                String modelNum = equipmentModelNumTxt.getText();
                                String equipmentType = typeChoice.getValue();
                                int quantity = Integer.parseInt(qtyNeededTxt.getText());
                                double purchasePrice = Double.parseDouble(purchasePriceTxt.getText());
                                String purchaseUrl = urlTxt.getText();
                                EquipmentDAOImpl.updateEquipment(new EquipmentEntity(editingEquipmentEntity.getEquipmentID(), name, modelNum,
                                        equipmentType, editingEquipmentEntity.getAssignedLoadOutID(), editingEquipmentEntity.getLoadOutSlotNum(),
                                        quantity, purchasePrice, purchaseUrl));
                                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bauerperception/itassetmanager/main.fxml"));
                                Scene scene = new Scene(loader.load());
                                scene.getStylesheets().add(getClass().getResource("/com/bauerperception/itassetmanager/mainstyles.css").toExternalForm());
                                stage.setScene(scene);
                                stage.show();
                                MainController controller = loader.getController();
                                controller.openLoadOuts(event);
                            } else {
                                FXUtil.throwAlert("Incorrect Data Entry", "Quantity needs to be higher than zero or delete the equipment.");
                            }
                        } else {
                            FXUtil.throwAlert("Incorrect Data Entry", "The quantity has to be numeric.");
                        }
                    } else {
                        FXUtil.throwAlert("Incorrect Data Entry", "The purchase price has to be numeric.");
                    }
                } else {
                    FXUtil.throwAlert("Entry Data Missing", "Equipment requires a model number.");
                }
            } else {
                FXUtil.throwAlert("Entry Data Missing", "Equipment requires a manufacturer.");
            }
        }

        if (addEquipmentFromMain){
            if (!equipmentMfrTxt.getText().isEmpty()){
                if (!equipmentModelNumTxt.getText().isEmpty()){
                    if (!typeChoice.getValue().isEmpty()){
                        if (FXUtil.isNumeric(purchasePriceTxt.getText())){
                            if (FXUtil.isNumeric(qtyNeededTxt.getText())){
                                if (Integer.parseInt(qtyNeededTxt.getText()) > 0){
                                    String name = equipmentMfrTxt.getText();
                                    String modelNum = equipmentModelNumTxt.getText();
                                    String equipmentType = typeChoice.getValue();
                                    int quantity = Integer.parseInt(qtyNeededTxt.getText());
                                    double purchasePrice = Double.parseDouble(purchasePriceTxt.getText());
                                    String purchaseUrl = urlTxt.getText();

                                    EquipmentDAOImpl.addEquipment(new EquipmentEntity(name, modelNum,
                                            equipmentType, loadOutID, getEquipmentSlotNumByLoadOutID(loadOutID),
                                            quantity, purchasePrice, purchaseUrl));

                                    MainController controller = FXUtil.goToMainScene(event);
                                    controller.openLoadOuts(event);
                                } else {
                                    FXUtil.throwAlert("Incorrect Data Entry", "Quantity needs to be higher than zero or delete the equipment.");
                                }
                            } else {
                                FXUtil.throwAlert("Incorrect Data Entry", "The quantity has to be numeric.");
                            }
                        } else {
                            FXUtil.throwAlert("Incorrect Data Entry", "The purchase price has to be numeric.");
                        }
                    } else {
                        FXUtil.throwAlert("Entry Data Missing", "Equipment requires a type.");
                    }
                } else {
                    FXUtil.throwAlert("Entry Data Missing", "Equipment requires a model number.");
                }
            } else {
                FXUtil.throwAlert("Entry Data Missing", "Equipment requires a manufacturer.");
            }
        }
    }

    /*
    In theory this is a very simple way to find any empty slot number or available slot number in the equipment list that is being assigned to the loadout.
    Example, if the first item in the list has a slot number of 2, then 1 will be available.
     */
    private int getEquipmentSlotNum() {
        int equipmentSlotNumber = 1;
        //List has to be sorted
        equipmentList.sort(new Comparator<EquipmentEntity>() {
            public int compare(EquipmentEntity a, EquipmentEntity b) {
                return a.getLoadOutSlotNum() - b.getLoadOutSlotNum();
            }
        });

        for (EquipmentEntity i : equipmentList){
            if (equipmentSlotNumber == i.getLoadOutSlotNum()){
                equipmentSlotNumber++;
            }
        }
        return equipmentSlotNumber;
    }

    private int getEquipmentSlotNumByLoadOutID(int loadOutID) throws Exception {
        int equipmentSlotNumber = 1;
        ObservableList<EquipmentEntity> tempEquipmentList = FXCollections.observableArrayList();
        tempEquipmentList = EquipmentDAOImpl.equipmentByLoadOutID(loadOutID);

        //List has to be sorted
        tempEquipmentList.sort(new Comparator<EquipmentEntity>() {
            public int compare(EquipmentEntity a, EquipmentEntity b) {
                return a.getLoadOutSlotNum() - b.getLoadOutSlotNum();
            }
        });

        for (EquipmentEntity i : tempEquipmentList){
            if (equipmentSlotNumber == i.getLoadOutSlotNum()){
                equipmentSlotNumber++;
            }
        }
        return equipmentSlotNumber;
    }

    public void addEquipmentFromLoadOutWizard(ActionEvent event, int loadOutID, ObservableList<EquipmentEntity> equipmentList, String loadOutName) throws Exception {
        //Enable existing equipment for faster editing. In this scenario, we know we aren't creating new equipment.
        existingEquipmentChoice.setVisible(true);
        existingEquipmentLbl.setVisible(true);

        //Set these values to public
        this.loadOutID = loadOutID;
        this.equipmentList = equipmentList;
        this.loadOutName = loadOutName;

        addingEquipmentToLoadOutWizard = true;
    }

    public void editEquipmentFromLoadOutWizard(ActionEvent event, int loadOutID, ObservableList<EquipmentEntity> equipmentList, String loadOutName, EquipmentEntity selectedEquipment) {
        this.loadOutID = loadOutID;
        this.equipmentList = equipmentList;
        this.loadOutName = loadOutName;
        this.editingEquipmentEntity = selectedEquipment;

        editingEquipmentFromLoadOutWizard = true;

        populateFields(selectedEquipment);
    }

    void populateFields(EquipmentEntity selectedEquipment) {
        equipmentMfrTxt.setText(selectedEquipment.getMfr());
        equipmentModelNumTxt.setText(selectedEquipment.getModelNum());
        typeChoice.setValue(selectedEquipment.getEquipmentType());
        qtyNeededTxt.setText(Integer.toString(selectedEquipment.getQuantityNeeded()));
        purchasePriceTxt.setText(Double.toString(selectedEquipment.getPurchasePrice()));
        urlTxt.setText(selectedEquipment.getWhereToPurchaseURL());
    }

    public void editEquipmentFromMain(EquipmentEntity selectedEquipment) {
        editingEquipmentFromMain = true;
        populateFields(selectedEquipment);
        editingEquipmentEntity = selectedEquipment;
    }

    public void addEquipmentFromMain(LoadOutEntity selectedItem) {
        addEquipmentFromMain = true;
        this.loadOutID = selectedItem.getLoadOutID();
    }
}
