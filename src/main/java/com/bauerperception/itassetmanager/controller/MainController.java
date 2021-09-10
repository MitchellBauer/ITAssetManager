package com.bauerperception.itassetmanager.controller;

import com.bauerperception.itassetmanager.model.AssetEntity;
import com.bauerperception.itassetmanager.DAO.*;
import com.bauerperception.itassetmanager.model.EmployeeEntity;
import com.bauerperception.itassetmanager.model.Entity;
import com.bauerperception.itassetmanager.model.LocationEntity;
import com.bauerperception.itassetmanager.util.TimeUtil;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    /*
    Public variables
     */
    ObservableList<String> assetEntityTypeList;
    ObservableList<EmployeeEntity> employeeList;
    ObservableList<LocationEntity> locationList;
    ObservableList<AssetEntity> assetList;

    Stage stage;

    /*
    JavaFX Access Variables
     */

    @FXML
    private HBox topRightMenu;

    @FXML
    private ImageView settingsButton;

    @FXML
    private ImageView minimizeButton;

    @FXML
    private ImageView maximizeButton;

    @FXML
    private Button exitButton;

    @FXML
    private AnchorPane navigationMenu;

    /*
    Task/Todoo Pane
     */

    @FXML
    private BorderPane toDoPane;

    @FXML
    private BorderPane toDoControls;

    @FXML
    private Label titleLabelToDoList;

    @FXML
    private Button goToTaskButton;

    /*
    Employee Pane
     */

    @FXML
    private BorderPane employeePane;

    @FXML
    private BorderPane employeeControls;


    /*
    LoadOut Pane
     */

    @FXML
    private BorderPane loadoutControls;

    @FXML
    private BorderPane loadoutPane;

    /*
    Location Pane
     */

    @FXML
    private BorderPane locationsPane;

    @FXML
    private BorderPane locationControls;

    /*
     Inventory Pane
     */

    @FXML
    private HBox inventoryControls;

    @FXML
    private BorderPane inventoryPane;

    @FXML
    private Label inventoryLblIDTitle;

    @FXML
    private Label inventoryLblName;

    @FXML
    private Label inventoryLblType;

    @FXML
    private Label inventoryLblModelNum;

    @FXML
    private Label inventoryLblDesc;

    @FXML
    private Label inventoryLblAssignedTo;

    @FXML
    private Label inventoryLblLocation;

    @FXML
    private Label inventoryLblPurchasedDate;

    @FXML
    private Label inventoryLblPurchasedPrice;

    @FXML
    private Button inventoryAddButton;

    @FXML
    private Button inventorySaveButton;

    @FXML
    private Button inventoryEditButton;

    @FXML
    private Button inventoryDeleteButton;

    @FXML
    private Label inventoryIDLbl;

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

    @FXML
    private Label titleLabelLocationList;

    @FXML
    private TableView<AssetEntity> inventoryTblView;

    @FXML
    private TableColumn<AssetEntity, Integer> inventoryTblID;

    @FXML
    private TableColumn<AssetEntity, String> inventoryTblAssetName;

    @FXML
    private TableColumn<AssetEntity, String> inventoryTblAssignedTo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO: https://stackoverflow.com/questions/26424769/javafx8-how-to-create-listener-for-selection-of-row-in-tableview
        inventoryTblView.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection) -> {
            if (newSelection != null){
                loadAssetData();
            }
        });

        /*
        Load defaults
         */
        //Inventory Type Defaults
        try {
            assetEntityTypeList = AssetDAOImpl.getAssetTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Inventory Employee Assignment Defaults
        try {
            employeeList = EmployeeDAOImpl.getAllEmployees();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Inventory Location Assignment Defaults
        try {
            locationList = LocationDAOImpl.getAllLocations();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Show Panel
        employeePane.setVisible(false);
        inventoryPane.setVisible(false);
        loadoutPane.setVisible(false);
        locationsPane.setVisible(false);
        toDoPane.setVisible(true);

        //Show Specific Controls
        toDoControls.setVisible(false);
        locationControls.setVisible(false);
        loadoutControls.setVisible(false);
        inventoryControls.setVisible(false);
        employeeControls.setVisible(false);
    }

    @FXML
    void exitApplication(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void goToSettings(ActionEvent event) {

    }

    @FXML
    void goToTask(ActionEvent event) {

    }

    @FXML
    void maximize(ActionEvent event) {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        // is stage minimizable into task bar. (true | false)
        if (stage.isMaximized()){
            stage.setMaximized(false);
        } else {
            stage.setMaximized(true);
        }
    }

    @FXML
    void minimize(ActionEvent event) {
        //TODO: https://stackoverflow.com/questions/16591438/how-to-minimize-maximize-and-restore-down-through-buttons-in-java
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        // is stage minimizable into task bar. (true | false)
        stage.setIconified(true);
    }

    @FXML
    void openEmployees(ActionEvent event) {
        //Show Panel
        employeePane.setVisible(true);
        inventoryPane.setVisible(false);
        loadoutPane.setVisible(false);
        locationsPane.setVisible(false);
        toDoPane.setVisible(false);

        //Show Specific Controls
        toDoControls.setVisible(false);
        locationControls.setVisible(false);
        loadoutPane.setVisible(false);
        inventoryControls.setVisible(false);
        employeeControls.setVisible(true);
    }

    @FXML
    void openInventory(ActionEvent event) throws Exception {
        //Show Panel
        employeePane.setVisible(false);
        inventoryPane.setVisible(true);
        loadoutPane.setVisible(false);
        locationsPane.setVisible(false);
        toDoPane.setVisible(false);

        //Show Specific Controls
        toDoControls.setVisible(false);
        locationControls.setVisible(false);
        loadoutControls.setVisible(false);
        inventoryControls.setVisible(true);
        employeeControls.setVisible(false);

        //TODO possible enable edit button
        inventoryEditButton.setDisable(true);
        //Disable delete button so no one accidentally clicks
        inventoryDeleteButton.setDisable(true);
        inventorySaveButton.setDisable(true);

        //Hide editable
        setVisibilityInventoryEditable(false);

        //Populate Inventory Table View
        try{
            inventoryTblView.setItems(AssetDAOImpl.getAllAssets());
            assetList = AssetDAOImpl.getAllAssets();
        } catch (Exception e){
            e.printStackTrace();
        }

        inventoryTblID.setCellValueFactory(new PropertyValueFactory<>("assetID"));
        inventoryTblAssetName.setCellValueFactory(new PropertyValueFactory<>("assetName"));
        //TODO Not using Assigned To lol
    }

    private void setVisibilityInventoryEditable(boolean b) {
        inventoryIDLbl.setVisible(b);
        inventoryAssetNameTxtBox.setVisible(b);
        inventoryAssetModelNum.setVisible(b);
        inventoryAssetDesc.setVisible(b);
        inventoryAssetPurchasedPrice.setVisible(b);
        inventoryAssetType.setVisible(b);
        inventoryAssetAssignedTo.setVisible(b);
        inventoryAssetLocation.setVisible(b);
        inventoryAssetPurchasedDate.setVisible(b);
        inventoryLblIDTitle.setVisible(b);
        inventoryLblName.setVisible(b);
        inventoryLblType.setVisible(b);
        inventoryLblModelNum.setVisible(b);
        inventoryLblDesc.setVisible(b);
        inventoryLblAssignedTo.setVisible(b);
        inventoryLblLocation.setVisible(b);
        inventoryLblPurchasedDate.setVisible(b);
        inventoryLblPurchasedPrice.setVisible(b);
        inventoryLblIDTitle.setVisible(b);
    }

    private void loadAssetData() {
        AssetEntity selectedAsset = inventoryTblView.getSelectionModel().getSelectedItem();
        if (selectedAsset != null){
            setVisibilityInventoryEditable(true);

            //Re-enable delete and save button
            inventoryDeleteButton.setDisable(false);
            inventorySaveButton.setDisable(false);

            inventoryIDLbl.setText(Integer.toString(selectedAsset.getID()));
            inventoryAssetNameTxtBox.setText(selectedAsset.getAssetName());
            inventoryAssetModelNum.setText(selectedAsset.getAssetModel());
            inventoryAssetDesc.setText(selectedAsset.getAssetDescription());
            inventoryAssetPurchasedPrice.setText(Float.toString(selectedAsset.getPurchasedPrice()));
            inventoryAssetPurchasedDate.setValue(selectedAsset.getPurchasedDate());

            //Load all asset types and set value
            inventoryAssetType.setItems(assetEntityTypeList);
            inventoryAssetType.setValue(selectedAsset.getAssetType());

            //Load all employees and set value
            inventoryAssetAssignedTo.setItems(employeeList);
            inventoryAssetAssignedTo.setValue(getEntityByID(employeeList, selectedAsset.getAssignedToID()));

            //Load all locations and set value
            inventoryAssetLocation.setItems(locationList);
            inventoryAssetLocation.setValue(getEntityByID(locationList, selectedAsset.getLocationID()));
        }
    }

    //TODO Might make more sense to send this over to a Util module
    private <T extends Entity> T getEntityByID(ObservableList<T> genericList, int entityID) {
        for (T i : genericList) {
            if (i.getID() == entityID){
                return i;
            }
        }

        //TODO throw an expection?
        return null;
    }

    @FXML
    void openLoadOuts(ActionEvent event) {
        //Show Panel
        employeePane.setVisible(false);
        inventoryPane.setVisible(false);
        loadoutPane.setVisible(true);
        locationsPane.setVisible(false);
        toDoPane.setVisible(false);

        //Show Specific Controls
        toDoControls.setVisible(false);
        locationControls.setVisible(false);
        loadoutControls.setVisible(true);
        inventoryControls.setVisible(false);
        employeeControls.setVisible(false);
    }

    @FXML
    void openLocations(ActionEvent event) {
        //Show Panel
        employeePane.setVisible(false);
        inventoryPane.setVisible(false);
        loadoutPane.setVisible(false);
        locationsPane.setVisible(true);
        toDoPane.setVisible(false);

        //Show Specific Controls
        toDoControls.setVisible(false);
        locationControls.setVisible(true);
        loadoutControls.setVisible(false);
        inventoryControls.setVisible(false);
        employeeControls.setVisible(false);
    }

    @FXML
    void openReports(ActionEvent event) {
        //TODO Add reports module?
        //Show Panel
        employeePane.setVisible(false);
        inventoryPane.setVisible(false);
        loadoutPane.setVisible(false);
        locationsPane.setVisible(false);
        toDoPane.setVisible(false);

        //Show Specific Controls
        toDoControls.setVisible(false);
        locationControls.setVisible(false);
        loadoutControls.setVisible(false);
        inventoryControls.setVisible(false);
        employeeControls.setVisible(false);
    }

    @FXML
    void openToDo(ActionEvent event) {
        //Show Panel
        employeePane.setVisible(false);
        inventoryPane.setVisible(false);
        loadoutPane.setVisible(false);
        locationsPane.setVisible(false);
        toDoPane.setVisible(true);

        //Show Specific Controls
        toDoControls.setVisible(true);
        locationControls.setVisible(false);
        loadoutControls.setVisible(false);
        inventoryControls.setVisible(false);
        employeeControls.setVisible(false);
    }

    private void throwAlert(String header, String contents) {
        Alert cancelConfirmation = new Alert(Alert.AlertType.ERROR);
        cancelConfirmation.setTitle("");
        cancelConfirmation.setHeaderText(header);
        cancelConfirmation.setContentText(contents);
        Optional<ButtonType> confirmationResult = cancelConfirmation.showAndWait();
    }

    public void addAssetToInventory(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bauerperception/itassetmanager/addasset.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/com/bauerperception/itassetmanager/addwizard.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void saveAssetChanges(ActionEvent actionEvent) throws Exception {
        int assetID = Integer.parseInt(inventoryIDLbl.getText());
        String assetName = inventoryAssetNameTxtBox.getText();
        String assetModelNum = inventoryAssetModelNum.getText();
        String assetDescription = inventoryAssetDesc.getText();
        //TODO Need to add validation
        Float assetPurchasedPrice = Float.parseFloat(inventoryAssetPurchasedPrice.getText());
        String assetType = inventoryAssetType.getValue();
        int employeeID = inventoryAssetAssignedTo.getValue().getEmployeeID();
        int locationID = inventoryAssetLocation.getValue().getLocationID();
        LocalDate assetPurchasedDate = inventoryAssetPurchasedDate.getValue();
        AssetDAOImpl.updateAsset(new AssetEntity(assetID,assetName, assetType, assetModelNum, assetDescription, employeeID, locationID, assetPurchasedDate, assetPurchasedPrice));
        inventoryTblView.setItems(AssetDAOImpl.getAllAssets());
    }

    public void deleteAsset(ActionEvent actionEvent) throws Exception {
        //TODO Convert actual deletion to "inactive" for accounting like deletion
        AssetEntity selectedAsset = inventoryTblView.getSelectionModel().getSelectedItem();
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();

        if (selectedAsset != null) {
            Alert deleteConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            deleteConfirmation.setTitle("");
            deleteConfirmation.setHeaderText("Delete Confirmation");
            deleteConfirmation.setContentText("Do you really want to delete this asset?");
            Optional<ButtonType> confirmationResult = deleteConfirmation.showAndWait();

            if (confirmationResult.get() == ButtonType.OK) {
                AssetDAOImpl.deleteAsset(selectedAsset);
                inventoryTblView.setItems(AssetDAOImpl.getAllAssets());
            }
        }
    }
}
