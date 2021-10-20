package com.bauerperception.itassetmanager.controller;

import com.bauerperception.itassetmanager.model.*;
import com.bauerperception.itassetmanager.DAO.*;
import com.bauerperception.itassetmanager.util.FXUtil;
import com.bauerperception.itassetmanager.util.TimeUtil;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {

    /*
        Public variables
         */
    ObservableList<String> assetEntityTypeList;
    ObservableList<EmployeeEntity> employeeList;
    ObservableList<LocationEntity> locationList;
    ObservableList<AssetEntity> assetList;
    ObservableList<LoadOutEntity> loadOutList;

    Stage stage;

    /*
    JavaFX Access Variables
     */

    //<editor-fold desc="Description">
    @FXML
    private HBox topRightMenu;

    @FXML
    private Button settingsButton;

    @FXML
    private Button minimizeButton;

    @FXML
    private Button maximizeButton;

    @FXML
    private Button exitButton;

    @FXML
    private AnchorPane navigationMenu;


    @FXML
    private Button naviOpenInventoryBtn;

    @FXML
    private Button naviOpenEmployeeBtn;

    @FXML
    private Button naviOpenLoadoutBtn;

    @FXML
    private Button naviOpenLocationBtn;

    @FXML
    private Button naviOpenReportBtn;

    @FXML
    private Label titleLbl;

    /*
    Task/Todoo Pane
     */

//    @FXML
//    private BorderPane toDoPane;
//
//    @FXML
//    private Button goToTaskButton;

    /*
    Employee Pane
     */

    @FXML
    private BorderPane employeePane;

    @FXML
    private Button addEmployeeButton;

    @FXML
    private Button saveEmployeeButton;

    @FXML
    private Button deleteEmployeeButton;

    @FXML
    private Label employeeIDTitleLbl;

    @FXML
    private Label employeeIDLbl;

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
    private ChoiceBox<LocationEntity> employeeWorkLocChoice;

    @FXML
    private Label employeeSecondaryWorkLocLbl;

    @FXML
    private ChoiceBox<LocationEntity> employeeSecondaryWorkLocChoice;

    @FXML
    private TableView<EmployeeEntity> employeeTblView;

    @FXML
    private TableColumn<EmployeeEntity, Integer> employeeIDCol;

    @FXML
    private TableColumn<EmployeeEntity, String> employeeNameCol;

    @FXML
    private TextField employeeSearchTextBar;

    @FXML
    private Button employeeSearchButton;

    /*
    LoadOut Pane and Controls
     */

    @FXML
    private HBox loadOutControls;

    @FXML
    private Button addLoadOutButton;

    @FXML
    private Button editEquipmentButton;

    @FXML
    private Button addEquipmentButton;

    @FXML
    private Button deleteLoadOutButton;

    @FXML
    private Button editLoadOutButton;

    @FXML
    private Button deleteEquipmentButton;

    @FXML
    private BorderPane loadoutPane;

    @FXML
    private TableView<LoadOutEntity> loadOutTblView;

    @FXML
    private TableColumn<LoadOutEntity, Integer> loadOutIDCol;

    @FXML
    private TableColumn<LoadOutEntity, String> loadOutNameCol;

    @FXML
    private TableView<EquipmentEntity> equipmentTblView;

    @FXML
    private TableColumn<LoadOutEntity, Integer> loadOutEquipSlotCol;

    @FXML
    private TableColumn<LoadOutEntity, String> loadOutEquipMfrCol;

    @FXML
    private TableColumn<LoadOutEntity, String> loadOutEquipModelNumCol;

    @FXML
    private TableColumn<LoadOutEntity, String> loadOutEquipTypeCol;

    @FXML
    private TableColumn<LoadOutEntity, Double> loadOutEquipPriceCol;

    @FXML
    private TableColumn<LoadOutEntity, String> loadOutEquipPurchaseURLCol;

    @FXML
    private TableColumn<LoadOutEntity, Integer> loadOutEquipQuantityCol;

    @FXML
    private TextField loadOutSearchTextBar;

    @FXML
    private Button loadOutSearchButton;

    /*
    Location Pane
     */

    @FXML
    private BorderPane locationsPane;

    @FXML
    private Button addLocationButton;

    @FXML
    private Button saveLocationButton;

    @FXML
    private Button deleteLocationButton;

    @FXML
    private Label locationIDTitle;

    @FXML
    private Label locationIDLbl;

    @FXML
    private Label locationNameLbl;

    @FXML
    private TextField locationNameTxt;

    @FXML
    private Label loadOutAssignedLbl;

    @FXML
    private ChoiceBox<LoadOutEntity> assignedLoadOutChoice;

    @FXML
    private TableView<LocationEntity> locationTblView;

    @FXML
    private TableColumn<LocationEntity, Integer> locationIDCol;

    @FXML
    private TableColumn<LocationEntity, String> locationNameCol;

    @FXML
    private TextField locationSearchTextBar;

    @FXML
    private Button locationSearchButton;

    /*
     Inventory Pane
     */

    @FXML
    private BorderPane inventoryPane;

    @FXML
    private Button addAssetButton;

    @FXML
    private Button saveAssetButton;

    @FXML
    private Button deleteAssetButton;

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
    private Label inventoryIDLbl;

    @FXML
    private TextField inventoryAssetMfrTxtBox;

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
    private TableView<AssetEntity> inventoryTblView;

    @FXML
    private TableColumn<AssetEntity, Integer> inventoryTblID;

    @FXML
    private TableColumn<AssetEntity, String> inventoryTblAssetMfr;

    @FXML
    private TableColumn<AssetEntity, String> inventoryTblAssignedTo;

    @FXML
    public TableColumn<AssetEntity, String> inventoryTblAssetType;

    @FXML
    private TextField inventorySearchTextBar;

    @FXML
    private Button inventorySearchButton;

    /*
    Reports Module
     */

    @FXML
    private ChoiceBox<String> reportsChoiceBox;

    @FXML
    private Button reportsRunButton;

    @FXML
    private Button reportExportButton;

    @FXML
    private BorderPane reportsPane;

    @FXML
    private StackPane reportsStackPane;

    @FXML
    private TableView<EquipmentEntity> reportOneTblView;

    @FXML
    private TableColumn<EquipmentEntity, String> reportOneEmployeeCol;

    @FXML
    private TableColumn<EquipmentEntity, String> reportOneLocationCol;

    @FXML
    private TableColumn<EquipmentEntity, String> reportOneTypeCol;

    @FXML
    private TableColumn<EquipmentEntity, Integer> reportOneQtyCol;

    @FXML
    private TableColumn<EquipmentEntity, String> reportOneMfgCol;

    @FXML
    private TableColumn<EquipmentEntity, String> reportOneModelNumCol;

    @FXML
    private TableColumn<EquipmentEntity, Double> reportOnePurchasePriceCol;

    @FXML
    private TableColumn<EquipmentEntity, String> reportOnePurchaseUrlCol;

    @FXML
    private TableView<AssetEntity> reportTwoTblView;

    @FXML
    private TableColumn<AssetEntity, Integer> reportTwoIDCol;

    @FXML
    private TableColumn<AssetEntity, String> reportTwoTypeCol;

    @FXML
    private TableColumn<AssetEntity, String> reportTwoMfgCol;

    @FXML
    private TableColumn<AssetEntity, String> reportTwoModelNumCol;

    @FXML
    private TableColumn<AssetEntity, Double> reportTwoPurchasePriceCol;

    @FXML
    private TableView<MissingData> reportThreeTblView;

    @FXML
    private TableColumn<MissingData, Integer> reportThreeIDCol;

    @FXML
    private TableColumn<MissingData, String> reportThreeTypeCol;

    @FXML
    private TableView<AssetEntity> reportFourTblView;

    @FXML
    private TableColumn<AssetEntity, Integer> reportFourIDCol;

    @FXML
    private TableColumn<AssetEntity, String> reportFourMfgCol;

    @FXML
    private TableColumn<AssetEntity, String> reportFourTypeCol;

    @FXML
    private TableColumn<AssetEntity, String> reportFourModelNumCol;

    @FXML
    private TableColumn<AssetEntity, LocalDate> reportFourPurchasedDateCol;

    @FXML
    private TableColumn<AssetEntity, Double> reportFourPurchasePriceCol;

    @FXML
    private TableColumn<AssetEntity, String> reportFourEmployeeCol;

    @FXML
    private TableColumn<AssetEntity, String> reportFourLocationCol;

    //</editor-fold>

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Setup database connection and open up default module.

        inventoryPane.setVisible(false);

        String applicationPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        String jarName = new java.io.File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
        applicationPath = applicationPath.replaceAll(jarName,"");
        applicationPath = applicationPath.replaceAll("%20", " ");
        applicationPath = applicationPath.trim();
        DBConn.applicationPath = applicationPath;

        File propFile = new File(DBConn.applicationPath + "dbconnection.properties");
        //System.out.println(DBConn.applicationPath + "dbconnection.properties");
        if (!propFile.exists()){
            try {
                createDBConnectionFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Connection conn = null;
        try {
            conn = DBConn.getConn();
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        if (conn != null){
            try {
                //openToDo(new ActionEvent());
                inventoryTblView.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection) -> {
                    if (newSelection != null){
                        loadAssetData();
                    }
                });

                employeeTblView.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection) -> {
                    if (newSelection != null){
                        loadEmployeeData();
                    }
                });

                locationTblView.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection) -> {
                    if (newSelection != null){
                        try {
                            loadLocationData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                loadOutTblView.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection) -> {
                    if (newSelection != null){
                        try {
                            loadLoadOutEquipmentData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                equipmentTblView.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection) -> {
                    if (newSelection != null){
                        editEquipmentButton.setDisable(false);
                        deleteEquipmentButton.setDisable(false);
                    }
                });

                /*
                Load defaults
                 */
                try {
                    assetEntityTypeList = TypeDAOImpl.getAllTypes();
                    assetList = AssetDAOImpl.getAllAssets();
                    employeeList = EmployeeDAOImpl.getAllEmployees();
                    locationList = LocationDAOImpl.getAllLocations();
                    loadOutList = LoadOutDAOImpl.getAllLoadOuts();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                openInventory(new ActionEvent());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            titleLbl.setText("Database Connection Error - Please Fix in Settings Menu");
            naviOpenInventoryBtn.setDisable(true);
            naviOpenEmployeeBtn.setDisable(true);
            naviOpenLoadoutBtn.setDisable(true);
            naviOpenLocationBtn.setDisable(true);
            naviOpenReportBtn.setDisable(true);
        }
    }

    @FXML
    void exitApplication(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void goToSettings(ActionEvent event) throws IOException {
        FXUtil.goToScene(event, "settings", "addwizard");
    }

//    @FXML
//    void goToTask(ActionEvent event) {
//
//    }

//    @FXML
//    void maximize(ActionEvent event) {
//        //Link Maximize with the redesign is needs to be redone
//        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
//        // is stage minimizable into task bar. (true | false)
//        if (stage.isMaximized()){
//            stage.setMaximized(false);
//        } else {
//            stage.setMaximized(true);
//        }
//    }

    @FXML
    void minimize(ActionEvent event) {
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
        reportsPane.setVisible(false);
        //toDoPane.setVisible(false);

        //Set Title
        titleLbl.setText("Employee Module");


        //Disable delete button so no one accidentally clicks
        deleteEmployeeButton.setDisable(true);
        saveEmployeeButton.setDisable(true);

        //Hide editable
        setVisibilityEmployeeEditable(false);

        //Populate Inventory Table View
        try{
            employeeTblView.setItems(EmployeeDAOImpl.getAllEmployees());
            employeeList = EmployeeDAOImpl.getAllEmployees();
        } catch (Exception e){
            e.printStackTrace();
        }

        employeeIDCol.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        employeeNameCol.setCellValueFactory(employee -> {
            if(employee.getValue().getMiddleName().isEmpty()) {
                return new SimpleStringProperty(employee.getValue().getFirstName() + " " + employee.getValue().getLastName());
            } else {
                String shortenedMiddleName = employee.getValue().getMiddleName().charAt(0) + ".";
                return new SimpleStringProperty(employee.getValue().getFirstName() + " " + shortenedMiddleName + " " + employee.getValue().getLastName());
            }
        });

        //Interactive filter search bar for Employee
        FilteredList<EmployeeEntity> employeeFilteredData = new FilteredList<>(employeeList, p -> true);
        employeeSearchTextBar.textProperty().addListener((observable, oldValue, newValue) -> {
            employeeFilteredData.setPredicate(employee -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                try {
                    if (employee.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (employee.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Compare ID
                if (FXUtil.isNumeric(newValue)){
                    return employee.getEmployeeID() == Integer.parseInt(newValue);
                }

                return false;
            });
        });

        SortedList<EmployeeEntity> sortedEmployeeData = new SortedList<>(employeeFilteredData);
        sortedEmployeeData.comparatorProperty().bind(employeeTblView.comparatorProperty());
        employeeTblView.setItems(sortedEmployeeData);
    }

    void setVisibilityEmployeeEditable(boolean b) {
        employeeIDLbl.setVisible(b);
        employeeIDTitleLbl.setVisible(b);
        employeeFirstNameLbl.setVisible(b);
        employeeFirstNameTxt.setVisible(b);
        employeeMiddleNameLbl.setVisible(b);
        employeeMiddleNameTxt.setVisible(b);
        employeeLastNameLbl.setVisible(b);
        employeeLastNameTxt.setVisible(b);
        employeeEmailLbl.setVisible(b);
        employeeEmailTxt.setVisible(b);
        employeeWorkLocLbl.setVisible(b);
        employeeWorkLocChoice.setVisible(b);
        employeeSecondaryWorkLocLbl.setVisible(b);
        employeeSecondaryWorkLocChoice.setVisible(b);
    }

    private void loadEmployeeData(){
        EmployeeEntity selectedEmployee = employeeTblView.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null){
            setVisibilityEmployeeEditable(true);

            //Re-enable delete and save button
            deleteEmployeeButton.setDisable(false);
            saveEmployeeButton.setDisable(false);

            //Assign selected data to fields
            employeeIDLbl.setText(Integer.toString(selectedEmployee.getEmployeeID()));
            employeeFirstNameTxt.setText(selectedEmployee.getFirstName());
            employeeMiddleNameTxt.setText(selectedEmployee.getMiddleName());
            employeeLastNameTxt.setText(selectedEmployee.getLastName());
            employeeEmailTxt.setText(selectedEmployee.getEmailAddress());
            employeeWorkLocChoice.setValue(FXUtil.getEntityByID(locationList, selectedEmployee.getPrimaryWorkLocation()));

            //Load locations
            try {
                employeeWorkLocChoice.setItems(LocationDAOImpl.getAllLocations());
                employeeSecondaryWorkLocChoice.setItems(LocationDAOImpl.getAllLocations());
            } catch (Exception e) {
                e.printStackTrace();
            }

            employeeWorkLocChoice.setValue(FXUtil.getEntityByID(employeeWorkLocChoice.getItems(), selectedEmployee.getPrimaryWorkLocation()));
            employeeSecondaryWorkLocChoice.setValue(FXUtil.getEntityByID(employeeSecondaryWorkLocChoice.getItems(), selectedEmployee.getSecondaryWorkLocation()));
        }
    }

    @FXML
    void openInventory(ActionEvent event) throws Exception {
        //Show Panel
        employeePane.setVisible(false);
        inventoryPane.setVisible(true);
        loadoutPane.setVisible(false);
        locationsPane.setVisible(false);
        reportsPane.setVisible(false);
        //toDoPane.setVisible(false);

        //Set Title
        titleLbl.setText("Inventory Module");

        //Disable delete button so no one accidentally clicks
        deleteAssetButton.setDisable(true);
        saveAssetButton.setDisable(true);

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
        inventoryTblAssetMfr.setCellValueFactory(new PropertyValueFactory<>("assetManufacturer"));
        inventoryTblAssetType.setCellValueFactory(new PropertyValueFactory<>("assetType"));

        //Takes the passed in AssetEntity and extracts the employee's ID that is assigned to it. Then returns the name of the employee in the column.
        inventoryTblAssignedTo.setCellValueFactory(asset -> {
            EmployeeEntity assignedEmployee = null;
            try {
                if (asset.getValue().getAssignedToID() > 0){
                    assignedEmployee = FXUtil.getEntityByID(employeeList, asset.getValue().getAssignedToID());
                } else {
                    return new SimpleStringProperty("Unassigned");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(assignedEmployee.getMiddleName().isEmpty()) {
                return new SimpleStringProperty(assignedEmployee.getFirstName() + " " + assignedEmployee.getLastName());
            } else {
                String shortenedMiddleName = assignedEmployee.getMiddleName().charAt(0) + ".";
                return new SimpleStringProperty(assignedEmployee.getFirstName() + " " + shortenedMiddleName + " " + assignedEmployee.getLastName());
            }
        });

        //Interactive filter search bar for Inventory
        FilteredList<AssetEntity> inventoryFilteredData = new FilteredList<>(assetList, p -> true);
        inventorySearchTextBar.textProperty().addListener((observable, oldValue, newValue) -> {
            inventoryFilteredData.setPredicate(asset -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                EmployeeEntity assignedEmployee = null;
                try {
                    if (asset.getAssignedToID() > 0){
                        assignedEmployee = FXUtil.getEntityByID(employeeList, asset.getAssignedToID());
                    } else {
                        return false;
                    }
                    if (assignedEmployee.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (assignedEmployee.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Compare ID
                if (FXUtil.isNumeric(newValue)){
                    if (asset.getAssetID() == Integer.parseInt(newValue)){
                        return true;
                    }
                }

                //Compare Asset Type
                if (asset.getAssetType().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }

                //Compare manufacturer
                return asset.getAssetManufacturer().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<AssetEntity> sortedInventoryData = new SortedList<>(inventoryFilteredData);
        sortedInventoryData.comparatorProperty().bind(inventoryTblView.comparatorProperty());
        inventoryTblView.setItems(sortedInventoryData);
    }

    void setVisibilityInventoryEditable(boolean b) {
        //Link condense this https://stackoverflow.com/questions/29061483/grouping-objects-in-javafx
        inventoryIDLbl.setVisible(b);
        inventoryAssetMfrTxtBox.setVisible(b);
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

    void loadAssetData() {
        AssetEntity selectedAsset = inventoryTblView.getSelectionModel().getSelectedItem();
        if (selectedAsset != null){
            setVisibilityInventoryEditable(true);

            //Re-enable delete and save button
            deleteAssetButton.setDisable(false);
            saveAssetButton.setDisable(false);

            inventoryIDLbl.setText(Integer.toString(selectedAsset.getID()));
            inventoryAssetMfrTxtBox.setText(selectedAsset.getAssetManufacturer());
            inventoryAssetModelNum.setText(selectedAsset.getAssetModel());
            inventoryAssetDesc.setText(selectedAsset.getAssetDescription());
            inventoryAssetPurchasedPrice.setText(Double.toString(selectedAsset.getPurchasedPrice()));
            inventoryAssetPurchasedDate.setValue(selectedAsset.getPurchasedDate());

            //Load all asset types and set value
            inventoryAssetType.setItems(assetEntityTypeList);
            inventoryAssetType.setValue(selectedAsset.getAssetType());

            //Load all employees and set value
            inventoryAssetAssignedTo.setItems(employeeList);
            inventoryAssetAssignedTo.setValue(FXUtil.getEntityByID(inventoryAssetAssignedTo.getItems(), selectedAsset.getAssignedToID()));

            //Load all locations and set value
            inventoryAssetLocation.setItems(locationList);
            inventoryAssetLocation.setValue(FXUtil.getEntityByID(inventoryAssetLocation.getItems(), selectedAsset.getLocationID()));
        }
    }

    @FXML
    void openLoadOuts(ActionEvent event) {
        //Show Panel
        employeePane.setVisible(false);
        inventoryPane.setVisible(false);
        loadoutPane.setVisible(true);
        locationsPane.setVisible(false);
        reportsPane.setVisible(false);
        //toDoPane.setVisible(false);

        //Set Title
        titleLbl.setText("Loadout Module");

        //Hide unneeded controls
        equipmentTblView.setVisible(false);
        editEquipmentButton.setVisible(false);
        editEquipmentButton.setDisable(true);
        deleteEquipmentButton.setVisible(false);
        deleteEquipmentButton.setDisable(true);
        addEquipmentButton.setVisible(false);

        deleteLoadOutButton.setDisable(true);
        editLoadOutButton.setDisable(true);

        //Populate LoadOut table
        //Populate Inventory Table View
        try{
            loadOutTblView.setItems(LoadOutDAOImpl.getAllLoadOuts());
            loadOutList = LoadOutDAOImpl.getAllLoadOuts();
        } catch (Exception e){
            e.printStackTrace();
        }

        loadOutIDCol.setCellValueFactory(new PropertyValueFactory<>("loadOutID"));
        loadOutNameCol.setCellValueFactory(new PropertyValueFactory<>("loadOutName"));
    }

    private void loadLoadOutEquipmentData() throws Exception {
        LoadOutEntity selectedLoadOut = loadOutTblView.getSelectionModel().getSelectedItem();

        //Show needed controls
        equipmentTblView.setVisible(true);
        editEquipmentButton.setVisible(true);
        deleteEquipmentButton.setVisible(true);
        addEquipmentButton.setVisible(true);
        deleteLoadOutButton.setDisable(false);
        editLoadOutButton.setDisable(false);

        equipmentTblView.setItems(EquipmentDAOImpl.equipmentByLoadOutID(selectedLoadOut.getLoadOutID()));
        loadOutEquipSlotCol.setCellValueFactory(new PropertyValueFactory<>("loadOutSlotNum"));
        loadOutEquipMfrCol.setCellValueFactory(new PropertyValueFactory<>("mfr"));
        loadOutEquipModelNumCol.setCellValueFactory(new PropertyValueFactory<>("modelNum"));
        loadOutEquipTypeCol.setCellValueFactory(new PropertyValueFactory<>("equipmentType"));
        loadOutEquipPriceCol.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        loadOutEquipPurchaseURLCol.setCellValueFactory(new PropertyValueFactory<>("whereToPurchaseURL"));
        loadOutEquipQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantityNeeded"));

        //Interactive filter search bar for LoadOut
        FilteredList<LoadOutEntity> loadOutFilteredData = new FilteredList<>(loadOutList, p -> true);
        loadOutSearchTextBar.textProperty().addListener((observable, oldValue, newValue) -> {
            loadOutFilteredData.setPredicate(loadout -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (loadout.getLoadOutName().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }

                //Compare ID
                if (FXUtil.isNumeric(newValue)){
                    return loadout.getLoadOutID() == Integer.parseInt(newValue);
                }

                return false;
            });
        });

        SortedList<LoadOutEntity> sortedLoadOutData = new SortedList<>(loadOutFilteredData);
        sortedLoadOutData.comparatorProperty().bind(loadOutTblView.comparatorProperty());
        loadOutTblView.setItems(sortedLoadOutData);
    }

    public void addLoadOut(ActionEvent event) throws IOException {
        FXUtil.goToScene(event, "addloadout", "addwizard");
    }

    public void addEquipment(ActionEvent event) throws Exception{
        ModifyEquipmentController controller = FXUtil.goToScene(event, "modifyequipment", "addwizard").getController();
        controller.addEquipmentFromMain(loadOutTblView.getSelectionModel().getSelectedItem());
    }

    public void editEquipment(ActionEvent event) throws IOException {
        EquipmentEntity selectedEquipment = equipmentTblView.getSelectionModel().getSelectedItem();
        ModifyEquipmentController controller = FXUtil.goToScene(event, "modifyequipment", "addwizard").getController();
        controller.editEquipmentFromMain(selectedEquipment);
    }

    public void editLoadOut(ActionEvent event) throws Exception {
        LoadOutEntity selectedLoadOut = loadOutTblView.getSelectionModel().getSelectedItem();
        AddLoadOutController controller = FXUtil.goToScene(event, "addloadout", "addwizard").getController();
        controller.editLoadData(event, selectedLoadOut.getLoadOutID(), EquipmentDAOImpl.equipmentByLoadOutID(selectedLoadOut.getLoadOutID()), selectedLoadOut.getLoadOutName());
    }

    public void deleteLoadOut(ActionEvent event) throws Exception {
        LoadOutEntity selectedLoadOut = loadOutTblView.getSelectionModel().getSelectedItem();
        if (selectedLoadOut != null) {
            if (FXUtil.confirmDeletion(event)) {
                LoadOutDAOImpl.deleteLoadOut(selectedLoadOut);
                loadOutList = LoadOutDAOImpl.getAllLoadOuts();
                loadOutTblView.setItems(loadOutList);
                openLoadOuts(event);
            }
        }
    }

    public void deleteEquipmentFromLoadout(ActionEvent event) throws Exception {
        EquipmentEntity selectedEquipment = equipmentTblView.getSelectionModel().getSelectedItem();
        if (selectedEquipment != null){
            if (FXUtil.confirmDeletion(event)){
                EquipmentDAOImpl.deleteEquipment(selectedEquipment);
                equipmentTblView.setItems(EquipmentDAOImpl.equipmentByLoadOutID(loadOutTblView.getSelectionModel().getSelectedItem().getLoadOutID()));
                openLoadOuts(event);
            }
        }
    }

    @FXML
    void openLocations() {
        //Show Panel
        employeePane.setVisible(false);
        inventoryPane.setVisible(false);
        loadoutPane.setVisible(false);
        locationsPane.setVisible(true);
        reportsPane.setVisible(false);
        //toDoPane.setVisible(false);

        //Set Title
        titleLbl.setText("Locations Module");

        //Disable delete button so no one accidentally clicks
        deleteLocationButton.setDisable(true);
        saveLocationButton.setDisable(true);

        //Hide editable
        setVisibilityLocationEditable(false);

        //Populate Inventory Table View
        try{
            locationTblView.setItems(LocationDAOImpl.getAllLocations());
            locationList = LocationDAOImpl.getAllLocations();
        } catch (Exception e){
            e.printStackTrace();
        }

        locationIDCol.setCellValueFactory(new PropertyValueFactory<>("locationID"));
        locationNameCol.setCellValueFactory(new PropertyValueFactory<>("locationName"));

        //Interactive filter search bar for Location
        FilteredList<LocationEntity> locationFilteredData = new FilteredList<>(locationList, p -> true);
        locationSearchTextBar.textProperty().addListener((observable, oldValue, newValue) -> {
            locationFilteredData.setPredicate(ilocation -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (ilocation.getLocationName().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }

                //Compare ID
                if (FXUtil.isNumeric(newValue)){
                    return ilocation.getLocationID() == Integer.parseInt(newValue);
                }

                return false;
            });
        });

        SortedList<LocationEntity> sortedLocationData = new SortedList<>(locationFilteredData);
        sortedLocationData.comparatorProperty().bind(locationTblView.comparatorProperty());
        locationTblView.setItems(sortedLocationData);
    }

    private void setVisibilityLocationEditable(boolean b) {
        locationIDLbl.setVisible(b);
        locationNameLbl.setVisible(b);
        locationIDTitle.setVisible(b);
        locationNameTxt.setVisible(b);
        assignedLoadOutChoice.setVisible(b);
        loadOutAssignedLbl.setVisible(b);
    }

    private void loadLocationData() throws Exception {
        LocationEntity selectedLocation = locationTblView.getSelectionModel().getSelectedItem();
        if (selectedLocation != null){
            setVisibilityLocationEditable(true);

            //Re-enable delete and save button
            deleteLocationButton.setDisable(false);
            saveLocationButton.setDisable(false);

            locationIDLbl.setText(Integer.toString(selectedLocation.getLocationID()));
            locationNameTxt.setText(selectedLocation.getLocationName());

            assignedLoadOutChoice.setItems(LoadOutDAOImpl.getAllLoadOuts());
            assignedLoadOutChoice.setValue(FXUtil.getEntityByID(assignedLoadOutChoice.getItems(), selectedLocation.getLoadOutID()));
        }
    }

    @FXML
    void openReports(ActionEvent event) {
        //Show Panel
        employeePane.setVisible(false);
        inventoryPane.setVisible(false);
        loadoutPane.setVisible(false);
        locationsPane.setVisible(false);
        reportsPane.setVisible(true);
        //toDoPane.setVisible(false);

        //Set Title
        titleLbl.setText("Reports Module");
        reportOneTblView.setVisible(false);
        reportTwoTblView.setVisible(false);
        reportThreeTblView.setVisible(false);
        reportFourTblView.setVisible(false);
        reportExportButton.setDisable(true);

        //Fill choice box
        ObservableList<String> listOfReports = FXCollections.observableArrayList();
        listOfReports.add("Need to Purchase");
        listOfReports.add("Unassigned Assets");
        listOfReports.add("Missing Data on Assets");
        listOfReports.add("All Assets");
        reportsChoiceBox.setItems(listOfReports);
    }

    public void runReports(ActionEvent event) throws Exception {
        //Since the switching is on the run button, we have to set all reports to not visible
        reportExportButton.setDisable(false);
        reportOneTblView.setVisible(false);
        reportTwoTblView.setVisible(false);
        reportThreeTblView.setVisible(false);
        reportFourTblView.setVisible(false);

        if (reportsChoiceBox.getValue() != null){
            if (reportsChoiceBox.getValue().compareTo("Need to Purchase") == 0){
                reportOneNeedToPurchase();
            } else if (reportsChoiceBox.getValue().compareTo("Unassigned Assets") == 0){
                reportTwoUnassignedAssets();
            } else if(reportsChoiceBox.getValue().compareTo("Missing Data on Assets") == 0){
                reportThreeMissingDataOnAssets();
            } else if(reportsChoiceBox.getValue().compareTo("All Assets") == 0){
                reportFourAllAssets();
            }
        } else {
            reportExportButton.setDisable(true);
            FXUtil.throwAlert("Choice Box Empty", "Please choose an item on before running.");
        }
    }

    private void reportOneNeedToPurchase() throws Exception {
        ObservableList<EquipmentEntity> primaryWorkEquipmentList = FXCollections.observableArrayList();
        ObservableList<EquipmentEntity> secondaryWorkEquipmentList = FXCollections.observableArrayList();
        ObservableList<AssetEntity> assetsAssignedToEmployee = FXCollections.observableArrayList();
        ObservableList<EquipmentEntity> needToPurchase = FXCollections.observableArrayList();

        //iterate over each employee
        for (EmployeeEntity e : employeeList){
            //See what loadout is assigned to them
            LocationEntity primaryWorkLocation = FXUtil.getEntityByID(locationList, e.getPrimaryWorkLocation());
            LocationEntity secondaryWorkLocation = FXUtil.getEntityByID(locationList, e.getSecondaryWorkLocation());
            //FUTURE Travel bag

            //Sum up the list of equipment for each loadout
            if (primaryWorkLocation != null && primaryWorkLocation.getLoadOutID() > 0) {
                primaryWorkEquipmentList = EquipmentDAOImpl.equipmentByLoadOutID(primaryWorkLocation.getLoadOutID());
                //Need to set the purchase ID for all this equipment. Otherwise it looks horrible in the report.
                for (EquipmentEntity z : primaryWorkEquipmentList){
                    z.setPurchaseForLocation(e.getPrimaryWorkLocation());
                    z.setPurchaseForEmployeeID(e.getEmployeeID());
                }
                needToPurchase.addAll(primaryWorkEquipmentList);
            }
            if (secondaryWorkLocation != null && secondaryWorkLocation.getLoadOutID() > 0) {
                secondaryWorkEquipmentList = EquipmentDAOImpl.equipmentByLoadOutID(secondaryWorkLocation.getLoadOutID());
                //Need to set the purchase ID for all this equipment. Otherwise it looks horrible in the report.
                for (EquipmentEntity z : secondaryWorkEquipmentList){
                    z.setPurchaseForLocation(e.getSecondaryWorkLocation());
                    z.setPurchaseForEmployeeID(e.getEmployeeID());
                }
                needToPurchase.addAll(secondaryWorkEquipmentList);
            }

            //pull in all assets assigned to the employee
            assetsAssignedToEmployee = AssetDAOImpl.getAssetsByEmployeeID(e.getEmployeeID());

            //compare the sum of the equipment to the assets (comparison quantity and type)
            /*FUTURE: make this a possible setting, how strict this comparison is.
             In my case, we have existing assets that can work or be a substitute to the prescribed loadout.
             This saves the company money. A big corporation may not care and would rather have all the same equipment.*/
            for (AssetEntity a : assetsAssignedToEmployee){
                for (EquipmentEntity i : needToPurchase){
                    //Compare asset type to equipment type
                    if (a.getAssetType().compareTo(i.getEquipmentType()) == 0 && !a.getIsAccounted()){
                        //Make sure to check if the equipment quantity is above 0
                        if (i.getQuantityNeeded() > 0){
                            //Subtract the quantity needed by 1 for a success asset match to equipment
                            i.setQuantityNeeded(i.getQuantityNeeded() - 1);
                            a.setAccountedFor(true);
                        }
                    }
                }
            }

            //Remove if Quantity is 0
            needToPurchase.removeIf(i -> i.getQuantityNeeded() == 0);
        }

        reportOneTblView.setVisible(true);
        reportOneTblView.setItems(needToPurchase);

        //Need to figure out whom the equipment will be purchased for.
        reportOneEmployeeCol.setCellValueFactory(equipment -> {
            EmployeeEntity assignedEmployee = null;

            try {
                if (equipment.getValue().getPurchaseForEmployeeID() > 0){
                    assignedEmployee = FXUtil.getEntityByID(employeeList, equipment.getValue().getPurchaseForEmployeeID());
                } else {
                    return new SimpleStringProperty("Unassigned");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(assignedEmployee.getMiddleName().isEmpty()) {
                return new SimpleStringProperty(assignedEmployee.getFirstName() + " " + assignedEmployee.getLastName());
            } else {
                String shortenedMiddleName = assignedEmployee.getMiddleName().charAt(0) + ".";
                return new SimpleStringProperty(assignedEmployee.getFirstName() + " " + shortenedMiddleName + " " + assignedEmployee.getLastName());
            }
        });
        reportOneLocationCol.setCellValueFactory(equipment -> {
            LocationEntity assignedLocation = null;
            try {
                assignedLocation = FXUtil.getEntityByID(locationList, equipment.getValue().getPurchaseForLocation());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new SimpleStringProperty(assignedLocation.getLocationName());
        });
        reportOneMfgCol.setCellValueFactory(new PropertyValueFactory<>("mfr"));
        reportOneModelNumCol.setCellValueFactory(new PropertyValueFactory<>("modelNum"));
        reportOnePurchasePriceCol.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        reportOnePurchaseUrlCol.setCellValueFactory(new PropertyValueFactory<>("whereToPurchaseURL"));
        reportOneQtyCol.setCellValueFactory(new PropertyValueFactory<>("quantityNeeded"));
        reportOneTypeCol.setCellValueFactory(new PropertyValueFactory<>("equipmentType"));
    }

    private void reportTwoUnassignedAssets() {
        reportTwoTblView.setVisible(true);
        //Look up all assets with unassigned employees
        ObservableList<AssetEntity> unassignedAssets = null;
        try {
            unassignedAssets = AssetDAOImpl.getAllUnassignedAssets();
        } catch (Exception e) {
            e.printStackTrace();
        }

        reportTwoTblView.setItems(unassignedAssets);
        reportTwoIDCol.setCellValueFactory(new PropertyValueFactory<>("assetID"));
        reportTwoMfgCol.setCellValueFactory(new PropertyValueFactory<>("assetManufacturer"));
        reportTwoModelNumCol.setCellValueFactory(new PropertyValueFactory<>("assetModel"));
        reportTwoTypeCol.setCellValueFactory(new PropertyValueFactory<>("assetType"));
        reportTwoPurchasePriceCol.setCellValueFactory(new PropertyValueFactory<>("purchasedPrice"));
    }

    private void reportThreeMissingDataOnAssets() throws Exception {
        reportThreeTblView.setVisible(true);
        ObservableList<MissingData> missingDataList = FXCollections.observableArrayList();
        //Iterate over every asset

        for (AssetEntity i : assetList){
            //We are checking for purchase dates, purchase price, model number, manufacturer, and serial no.
            if (i.getAssetManufacturer().isEmpty()){
                missingDataList.add(new MissingData(i.getAssetID(), "Missing Manufacturer"));
            }

            if (i.getAssetModel().isEmpty()){
                missingDataList.add(new MissingData(i.getAssetID(), "Missing Model Number"));
            }

            if (i.getPurchasedDate() == null){
                missingDataList.add(new MissingData(i.getAssetID(), "Missing Purchased Date"));
            }

            if (i.getPurchasedPrice() < 0){
                missingDataList.add(new MissingData(i.getAssetID(), "Missing Purchase Price"));
            }

            if (i.getPurchasedPrice() == 0){
                missingDataList.add(new MissingData(i.getAssetID(), "Purchased Price is Zero"));
            }

            if (i.getPurchasedDate().isEqual(TimeUtil.importMySQLToLocalDate("1990-01-01"))){
                missingDataList.add(new MissingData(i.getAssetID(), "Default Purchased Date"));
            }

            //Future Serial NO.
        }

        reportThreeTblView.setItems(missingDataList);
        reportThreeIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        reportThreeTypeCol.setCellValueFactory(new PropertyValueFactory<>("missingDataType"));
    }

    private void reportFourAllAssets() {
        reportFourTblView.setVisible(true);
        reportFourTblView.setItems(assetList);
        reportFourEmployeeCol.setCellValueFactory(asset -> {
            EmployeeEntity assignedEmployee = null;
            try {
                if (asset.getValue().getAssignedToID() > 0){
                    assignedEmployee = FXUtil.getEntityByID(employeeList, asset.getValue().getAssignedToID());
                } else {
                    return new SimpleStringProperty("Unassigned");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(assignedEmployee.getMiddleName().isEmpty()) {
                return new SimpleStringProperty(assignedEmployee.getFirstName() + " " + assignedEmployee.getLastName());
            } else {
                String shortenedMiddleName = assignedEmployee.getMiddleName().charAt(0) + ".";
                return new SimpleStringProperty(assignedEmployee.getFirstName() + " " + shortenedMiddleName + " " + assignedEmployee.getLastName());
            }
        });
        reportFourIDCol.setCellValueFactory(new PropertyValueFactory<>("assetID"));
        reportFourLocationCol.setCellValueFactory(asset -> {
            LocationEntity assignedLocation = null;

            if (asset.getValue().getLocationID() > 0){
                assignedLocation = FXUtil.getEntityByID(locationList, asset.getValue().getLocationID());
                return new SimpleStringProperty(assignedLocation.getLocationName());
            } else {
                return new SimpleStringProperty("Unassigned");
            }
        });
        reportFourMfgCol.setCellValueFactory(new PropertyValueFactory<>("assetManufacturer"));
        reportFourModelNumCol.setCellValueFactory(new PropertyValueFactory<>("assetModel"));
        reportFourTypeCol.setCellValueFactory(new PropertyValueFactory<>("assetType"));
        reportFourPurchasePriceCol.setCellValueFactory(new PropertyValueFactory<>("purchasedPrice"));
        reportFourPurchasedDateCol.setCellValueFactory(new PropertyValueFactory<>("purchasedDate"));
    }

    public void runReportExport(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.CSV)", "*.CSV");
        fileChooser.setTitle("Save Report");
        fileChooser.setInitialFileName(reportsChoiceBox.getValue() + "Report");
        fileChooser.getExtensionFilters().add(extFilter);
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try {
                PrintWriter writer;
                writer = new PrintWriter(file);
                Date date = new Date();
                switch (reportsChoiceBox.getValue()){
                    case "Need to Purchase":
                        writer.println("Report: Need to Purchase," + new Timestamp(date.getTime()));
                        writer = writeTableToCSV(writer, reportOneTblView);
                        break;
                    case "Unassigned Assets":
                        writer.println("Report: Unassigned Assets," + new Timestamp(date.getTime()));
                        writer = writeTableToCSV(writer, reportTwoTblView);
                        break;
                    case "Missing Data on Assets":
                        writer.println("Report: Missing Data on Assets," + new Timestamp(date.getTime()));
                        writer = writeTableToCSV(writer, reportThreeTblView);
                        break;
                    case "All Assets":
                        writer.println("Report: All Assets," + new Timestamp(date.getTime()));
                        writer = writeTableToCSV(writer, reportFourTblView);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + reportsChoiceBox.getValue());
                }
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private PrintWriter writeTableToCSV(PrintWriter writer, TableView reportTable) {
        ObservableList<TableColumn> columns = reportTable.getColumns();

        //Populate column headers into excel spreadsheet
        for (TableColumn column : columns){
            writer.print(column.getText() + ",");
        }
        writer.println(); //Go to next line to start actual data.
        for (Object row : reportTable.getItems()){
            for (TableColumn column : columns){
                writer.print(column.getCellObservableValue(row).getValue() + ",");
            }
            writer.println();
        }

        return writer;
    }

    private void createDBConnectionFile() throws IOException {
        File file = new File(DBConn.applicationPath + "dbconnection.properties");
        file.createNewFile();

        PrintWriter writer;
        writer = new PrintWriter(file);
        writer.println("ipaddress=");
        writer.println("user=");
        writer.println("databasename=");
        writer.println("password=");
        writer.close();
    }

    /*
    @FXML
    void openToDo(ActionEvent event) {
        //Show Panel
        employeePane.setVisible(false);
        inventoryPane.setVisible(false);
        loadoutPane.setVisible(false);
        locationsPane.setVisible(false);
        toDoPane.setVisible(true);

        //Set Title
        titleLbl.setText("Task Module");
    }*/

    public void addEntity(ActionEvent event) throws IOException {
        if (inventoryPane.isVisible()){
            FXUtil.goToScene(event,"addasset", "addwizard");
        }

        if (employeePane.isVisible()){
            FXUtil.goToScene(event,"addemployee", "addwizard");
        }

        if (locationsPane.isVisible()){
            FXUtil.goToScene(event,"addlocation", "addwizard");
        }
    }

    public void saveEntity(ActionEvent event) throws Exception {
        if (inventoryPane.isVisible()) {
            if (!inventoryAssetType.getValue().isEmpty()){
                if (FXUtil.isNumeric(inventoryAssetPurchasedPrice.getText())){
                    int assetID = Integer.parseInt(inventoryIDLbl.getText());
                    String assetManufacturer = inventoryAssetMfrTxtBox.getText();
                    String assetModelNum = inventoryAssetModelNum.getText();
                    String assetDescription = inventoryAssetDesc.getText();
                    double assetPurchasedPrice = Double.parseDouble(inventoryAssetPurchasedPrice.getText());
                    String assetType = inventoryAssetType.getValue();

                    int employeeID = 0;
                    if (inventoryAssetAssignedTo.getValue() != null){
                        employeeID = inventoryAssetAssignedTo.getValue().getEmployeeID();
                    }

                    int locationID = 0;
                    if (inventoryAssetLocation.getValue() != null){
                        locationID = inventoryAssetLocation.getValue().getLocationID();
                    }

                    LocalDate assetPurchasedDate = inventoryAssetPurchasedDate.getValue();
                    AssetDAOImpl.updateAsset(new AssetEntity(assetID, assetManufacturer, assetType, assetModelNum, assetDescription, employeeID, locationID, assetPurchasedDate, assetPurchasedPrice));
                    assetList = AssetDAOImpl.getAllAssets();
                    inventoryTblView.setItems(assetList);
                    openInventory(event);
                } else {
                    FXUtil.throwAlert("Incorrect Data Entry", "The purchased price has to be numeric.");
                }
            } else {
                FXUtil.throwAlert("Entry Data Missing", "An asset requires a type.");
            }
        }

        if (employeePane.isVisible()){
            if (!employeeFirstNameTxt.getText().isEmpty()){
                if (!employeeMiddleNameTxt.getText().isEmpty()){
                    if (!employeeLastNameTxt.getText().isEmpty()){
                        int employeeID = Integer.parseInt(employeeIDLbl.getText());
                        String firstName = employeeFirstNameTxt.getText();
                        String middleName = employeeMiddleNameTxt.getText();
                        String lastName = employeeLastNameTxt.getText();
                        String emailAddress = employeeEmailTxt.getText();

                        int assignedWorkLocation = 0;
                        if (employeeWorkLocChoice.getValue() != null){
                            assignedWorkLocation = employeeWorkLocChoice.getValue().getLocationID();
                        }

                        int secondaryWorkLocation = 0;
                        if (employeeSecondaryWorkLocChoice.getValue() != null){
                            secondaryWorkLocation = employeeSecondaryWorkLocChoice.getValue().getLocationID();
                        }

                        EmployeeDAOImpl.updateEmployee(new EmployeeEntity(employeeID, firstName, middleName, lastName, emailAddress, assignedWorkLocation, secondaryWorkLocation));
                        employeeTblView.setItems(EmployeeDAOImpl.getAllEmployees());
                        employeeList = EmployeeDAOImpl.getAllEmployees();
                        openEmployees(event);
                    } else {
                        FXUtil.throwAlert("Entry Data Missing", "An employee requires a last name.");
                    }
                } else {
                    FXUtil.throwAlert("Entry Data Missing", "An employee requires a middle name.");
                }
            } else {
                FXUtil.throwAlert("Entry Data Missing", "An employee requires a first name.");
            }
        }

        if (locationsPane.isVisible()){
            if (!locationNameTxt.getText().isEmpty()){
                int locationID = Integer.parseInt(locationIDLbl.getText());
                String locationName = locationNameTxt.getText();
                int loadOutID = 0;
                if (assignedLoadOutChoice.getValue() != null){
                    loadOutID = assignedLoadOutChoice.getValue().getLoadOutID();
                }
                LocationDAOImpl.updateLocation(new LocationEntity(locationID, locationName, loadOutID));
                locationTblView.setItems(LocationDAOImpl.getAllLocations());
                locationList = LocationDAOImpl.getAllLocations();
                openLocations();
            } else {
                FXUtil.throwAlert("Entry Data Missing", "A location requires a name.");
            }
        }
    }

    public void deleteEntity(ActionEvent event) throws Exception {
        //Future Convert actual deletion to "inactive" for accounting like deletion
        if (inventoryPane.isVisible()){
            AssetEntity selectedAsset = inventoryTblView.getSelectionModel().getSelectedItem();
            if (selectedAsset != null) {
                if (FXUtil.confirmDeletion(event)) {
                    AssetDAOImpl.deleteAsset(selectedAsset);
                    assetList = AssetDAOImpl.getAllAssets();
                    inventoryTblView.setItems(assetList);
                    openInventory(event);
                }
            }
        } else if (employeePane.isVisible()){
            EmployeeEntity selectedEmployee = employeeTblView.getSelectionModel().getSelectedItem();
            if (selectedEmployee != null){
                if (FXUtil.confirmDeletion(event)){
                    EmployeeDAOImpl.deleteEmployee(selectedEmployee);
                    employeeList = EmployeeDAOImpl.getAllEmployees();
                    employeeTblView.setItems(employeeList);
                    openEmployees(event);
                }
            }
        } else if (locationsPane.isVisible()){
            LocationEntity selectedLocation = locationTblView.getSelectionModel().getSelectedItem();
            if (selectedLocation != null){
                if (FXUtil.confirmDeletion(event)){
                    LocationDAOImpl.deleteLocation(selectedLocation);
                    locationList = LocationDAOImpl.getAllLocations();
                    locationTblView.setItems(locationList);
                    openLocations();
                }
            }
        }
    }
}
