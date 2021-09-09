package com.bauerperception.itassetmanager;

import com.bauerperception.itassetmanager.model.AssetEntity;
import com.bauerperception.itassetmanager.DAO.*;
import com.bauerperception.itassetmanager.model.EmployeeEntity;
import com.bauerperception.itassetmanager.model.LocationEntity;
import com.bauerperception.itassetmanager.util.TimeUtil;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    /*
    Public variables
     */
    ObservableList<String> assetEntityTypeList;
    ObservableList<EmployeeEntity> employeeList;
    ObservableList<LocationEntity> locationList;

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

    @FXML
    private BorderPane toDoPane;

    @FXML
    private Label titleLabelToDoList;

    @FXML
    private Button goToTaskButton;

    @FXML
    private BorderPane inventoryPane;

    @FXML
    private Label titleLabelInventoryList;

    @FXML
    private BorderPane employeePane;

    @FXML
    private Label titleLabelEmployeeList;

    @FXML
    private BorderPane loadoutPane;

    @FXML
    private Label titleLabelLoadoutEditor;

    @FXML
    private BorderPane locationsPane;

    /*
     Inventory Tab
     */

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
    private ChoiceBox<String> inventoryAssetAssignedTo;

    @FXML
    private ChoiceBox<String> inventoryAssetLocation;

    @FXML
    private DatePicker inventoryAssetPurchasedDate;

    @FXML
    private TextField inventoryAssetPurchasedPrice;

    @FXML
    private Label titleLabelLocationList;

    @FXML
    private Button inventoryAddButton;

    @FXML
    private Button inventorySaveButton;

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
                //inventoryTblView.getSelectionModel().clearSelection();
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
        employeePane.setVisible(true);
        inventoryPane.setVisible(false);
        loadoutPane.setVisible(false);
        locationsPane.setVisible(false);
        toDoPane.setVisible(false);
    }

    @FXML
    void openInventory(ActionEvent event) throws Exception {
        employeePane.setVisible(false);
        inventoryPane.setVisible(true);
        loadoutPane.setVisible(false);
        locationsPane.setVisible(false);
        toDoPane.setVisible(false);

        //Populate Inventory Table View
        //TODO Cache Assets
        try{
            inventoryTblView.setItems(AssetDAOImpl.getAllAssets());
        } catch (Exception e){
            e.printStackTrace();
        }

        inventoryTblID.setCellValueFactory(new PropertyValueFactory<>("assetID"));
        inventoryTblAssetName.setCellValueFactory(new PropertyValueFactory<>("assetName"));
        inventoryTblAssignedTo.setCellValueFactory(new PropertyValueFactory<>("assignedToName"));
        inventoryTblView.refresh();
    }

    private void loadAssetData() {
        AssetEntity selectedAsset = inventoryTblView.getSelectionModel().getSelectedItem();
        if (selectedAsset != null){
            inventoryIDLbl.setText(Integer.toString(selectedAsset.getAssetID()));
            inventoryAssetNameTxtBox.setText(selectedAsset.getAssetName());

            //Load all asset types
            inventoryAssetType.setItems(assetEntityTypeList);
            inventoryAssetType.setValue(selectedAsset.getAssetType());

            inventoryAssetModelNum.setText(selectedAsset.getAssetModel());
            inventoryAssetDesc.setText(selectedAsset.getAssetDescription());
            //TODO Need to load other employee names in the database
            inventoryAssetAssignedTo.setValue(selectedAsset.getAssignedToName());
            //TODO Need to load correct location and other locations
            inventoryAssetLocation.setValue(selectedAsset.getLocation());

            //TODO Convert String to LocalDate for date picker
            inventoryAssetPurchasedDate.setValue(TimeUtil.calendarToLocalDate(selectedAsset.getPurchasedDate()));

            inventoryAssetPurchasedPrice.setText(Float.toString(selectedAsset.getPurchasedPrice()));
        }
    }

    @FXML
    void openLoadouts(ActionEvent event) {
        employeePane.setVisible(false);
        inventoryPane.setVisible(false);
        loadoutPane.setVisible(true);
        locationsPane.setVisible(false);
        toDoPane.setVisible(false);
    }

    @FXML
    void openLocations(ActionEvent event) {
        employeePane.setVisible(false);
        inventoryPane.setVisible(false);
        loadoutPane.setVisible(false);
        locationsPane.setVisible(true);
        toDoPane.setVisible(false);
    }

    @FXML
    void openReports(ActionEvent event) {
        employeePane.setVisible(false);
        inventoryPane.setVisible(false);
        loadoutPane.setVisible(false);
        locationsPane.setVisible(false);
        toDoPane.setVisible(false);
    }

    @FXML
    void openToDo(ActionEvent event) {
        employeePane.setVisible(false);
        inventoryPane.setVisible(false);
        loadoutPane.setVisible(false);
        locationsPane.setVisible(false);
        toDoPane.setVisible(true);
    }

    private void throwAlert(String header, String contents) {
        Alert cancelConfirmation = new Alert(Alert.AlertType.ERROR);
        cancelConfirmation.setTitle("");
        cancelConfirmation.setHeaderText(header);
        cancelConfirmation.setContentText(contents);
        Optional<ButtonType> confirmationResult = cancelConfirmation.showAndWait();
    }

    public void addAssetToInventory(ActionEvent actionEvent) {
    }

    public void saveAssetChanges(ActionEvent actionEvent) {
    }
}
