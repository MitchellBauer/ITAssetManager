package com.bauerperception.itassetmanager.controller;

import com.bauerperception.itassetmanager.model.*;
import com.bauerperception.itassetmanager.DAO.*;
import com.bauerperception.itassetmanager.util.TimeUtil;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.util.Callback;

import javax.xml.stream.Location;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Objects;
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
    private Label titleLbl;

    /*
    Task/Todoo Pane
     */

    @FXML
    private BorderPane toDoPane;

    @FXML
    private Button goToTaskButton;

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
    private ChoiceBox<LocationEntity> employeeWorkLocTxt;

    @FXML
    private TableView<EmployeeEntity> employeeTblView;

    @FXML
    private TableColumn<EmployeeEntity, Integer> employeeIDCol;

    @FXML
    private TableColumn<EmployeeEntity, String> employeeNameCol;


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
    private TableColumn<LoadOutEntity, String> loadOutEquipNameCol;

    @FXML
    private TableColumn<LoadOutEntity, String> loadOutEquipModelNumCol;

    @FXML
    private TableColumn<LoadOutEntity, String> loadOutEquipTypeCol;

    @FXML
    private TableColumn<LoadOutEntity, Float> loadOutEquipPriceCol;

    @FXML
    private TableColumn<LoadOutEntity, String> loadOutEquipPurchaseURLCol;

    @FXML
    private TableColumn<LoadOutEntity, Integer> loadOutEquipQuantityCol;

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
    private TableView<LocationEntity> locationTblView;

    @FXML
    private TableColumn<LocationEntity, Integer> locationIDCol;

    @FXML
    private TableColumn<LocationEntity, String> locationNameCol;

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
    private TableView<AssetEntity> inventoryTblView;

    @FXML
    private TableColumn<AssetEntity, Integer> inventoryTblID;

    @FXML
    private TableColumn<AssetEntity, String> inventoryTblAssetName;

    @FXML
    private TableColumn<AssetEntity, String> inventoryTblAssignedTo;
    //</editor-fold>

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO: https://stackoverflow.com/questions/26424769/javafx8-how-to-create-listener-for-selection-of-row-in-tableview
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
                loadLocationData();
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

        //Call initial open
        openToDo(new ActionEvent());
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
        //TODO Maximize with the redesign is needs to be redone
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

        //TODO https://stackoverflow.com/questions/19209155/populating-a-javafx-tableview-when-not-all-the-properties-are-in-the-same-class
        employeeIDCol.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        employeeNameCol.setCellValueFactory(employee -> {
            if(employee.getValue().getMiddleName().isEmpty()) {
                return new SimpleStringProperty(employee.getValue().getFirstName() + " " + employee.getValue().getLastName());
            } else {
                String shortenedMiddleName = employee.getValue().getMiddleName().charAt(0) + ".";
                return new SimpleStringProperty(employee.getValue().getFirstName() + " " + shortenedMiddleName + " " + employee.getValue().getLastName());
            }
        });
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
        employeeWorkLocTxt.setVisible(b);
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
            employeeWorkLocTxt.setValue(getEntityByID(locationList, selectedEmployee.getAssignedWorkLocation()));
        }
    }

    @FXML
    void openInventory(ActionEvent event) throws Exception {
        //Show Panel
        employeePane.setVisible(false);
        inventoryPane.setVisible(true);
        loadoutPane.setVisible(false);
        locationsPane.setVisible(false);
        toDoPane.setVisible(false);

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
        inventoryTblAssetName.setCellValueFactory(new PropertyValueFactory<>("assetName"));

        //Takes the passed in AssetEntity and extracts the employee's ID that is assigned to it. Then returns the name of the employee in the column.
        inventoryTblAssignedTo.setCellValueFactory(asset -> {
            EmployeeEntity assignedEmployee = getEntityByID(employeeList, asset.getValue().getAssignedToID());
            if(assignedEmployee.getMiddleName().isEmpty()) {
                return new SimpleStringProperty(assignedEmployee.getFirstName() + " " + assignedEmployee.getLastName());
            } else {
                String shortenedMiddleName = assignedEmployee.getMiddleName().charAt(0) + ".";
                return new SimpleStringProperty(assignedEmployee.getFirstName() + " " + shortenedMiddleName + " " + assignedEmployee.getLastName());
            }
        });
    }

    void setVisibilityInventoryEditable(boolean b) {
        //TODO condense this https://stackoverflow.com/questions/29061483/grouping-objects-in-javafx
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

    void loadAssetData() {
        AssetEntity selectedAsset = inventoryTblView.getSelectionModel().getSelectedItem();
        if (selectedAsset != null){
            setVisibilityInventoryEditable(true);

            //Re-enable delete and save button
            deleteAssetButton.setDisable(false);
            saveAssetButton.setDisable(false);

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
    <T extends Entity> T getEntityByID(ObservableList<T> genericList, int entityID) {
        for (T i : genericList) {
            if (i.getID() == entityID){
                return i;
            }
        }
        //If this returns null the ID for an entity in the list
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

        //Set Title
        titleLbl.setText("Loadout Module");

        //Hide unneeded controls
        equipmentTblView.setVisible(false);
        editEquipmentButton.setVisible(false);
        editEquipmentButton.setDisable(true);
        deleteEquipmentButton.setVisible(false);
        deleteEquipmentButton.setDisable(true);
        addEquipmentButton.setVisible(false);

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

        equipmentTblView.setItems(EquipmentDAOImpl.equipmentByLoadOutID(selectedLoadOut.getLoadOutID()));
        loadOutEquipSlotCol.setCellValueFactory(new PropertyValueFactory<>("loadOutSlotNum"));
        loadOutEquipNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        loadOutEquipModelNumCol.setCellValueFactory(new PropertyValueFactory<>("modelNum"));
        loadOutEquipTypeCol.setCellValueFactory(new PropertyValueFactory<>("equipmentType"));
        loadOutEquipPriceCol.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        loadOutEquipPurchaseURLCol.setCellValueFactory(new PropertyValueFactory<>("whereToPurchaseURL"));
        loadOutEquipQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantityNeeded"));
    }

    public void addLoadOut(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bauerperception/itassetmanager/addloadout.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/bauerperception/itassetmanager/addwizard.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void addEquipment(ActionEvent actionEvent) throws Exception{
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bauerperception/itassetmanager/modifyequipment.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/bauerperception/itassetmanager/addwizard.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
        ModifyEquipmentController controller = loader.getController();
        controller.addEquipmentFromMain(loadOutTblView.getSelectionModel().getSelectedItem());
    }

    public void editEquipment(ActionEvent actionEvent) throws IOException {
        EquipmentEntity selectedEquipment = equipmentTblView.getSelectionModel().getSelectedItem();

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bauerperception/itassetmanager/modifyequipment.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/bauerperception/itassetmanager/addwizard.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
        ModifyEquipmentController controller = loader.getController();
        controller.editEquipmentFromMain(selectedEquipment);
    }

    public void deleteLoadOut(ActionEvent actionEvent) {
        //TODO Validation that no equipment is attached or do something with the equipment
    }

    public void deleteEquipmentFromLoadout(ActionEvent actionEvent) throws Exception {
        //TODO Validation, no warning
        EquipmentEntity selectedEquipment = equipmentTblView.getSelectionModel().getSelectedItem();
        EquipmentDAOImpl.deleteEquipment(selectedEquipment);
        equipmentTblView.setItems(EquipmentDAOImpl.equipmentByLoadOutID(loadOutTblView.getSelectionModel().getSelectedItem().getLoadOutID()));
    }

    @FXML
    void openLocations() {
        //Show Panel
        employeePane.setVisible(false);
        inventoryPane.setVisible(false);
        loadoutPane.setVisible(false);
        locationsPane.setVisible(true);
        toDoPane.setVisible(false);

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
    }

    private void setVisibilityLocationEditable(boolean b) {
        locationIDLbl.setVisible(b);
        locationNameLbl.setVisible(b);
        locationIDTitle.setVisible(b);
        locationNameTxt.setVisible(b);
    }

    private void loadLocationData() {
        LocationEntity selectedLocation = locationTblView.getSelectionModel().getSelectedItem();
        if (selectedLocation != null){
            setVisibilityLocationEditable(true);

            //Re-enable delete and save button
            deleteLocationButton.setDisable(false);
            saveLocationButton.setDisable(false);

            locationIDLbl.setText(Integer.toString(selectedLocation.getLocationID()));
            locationNameTxt.setText(selectedLocation.getLocationName());
        }
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

        //Set Title
        titleLbl.setText("Reports Module");
    }

    @FXML
    void openToDo(ActionEvent event) {
        //Show Panel
        employeePane.setVisible(false);
        inventoryPane.setVisible(false);
        loadoutPane.setVisible(false);
        locationsPane.setVisible(false);
        toDoPane.setVisible(true);

        //Set Title
        titleLbl.setText("ToDo Module");
    }

    private void throwAlert(String header, String contents) {
        Alert cancelConfirmation = new Alert(Alert.AlertType.ERROR);
        cancelConfirmation.setTitle("");
        cancelConfirmation.setHeaderText(header);
        cancelConfirmation.setContentText(contents);
        Optional<ButtonType> confirmationResult = cancelConfirmation.showAndWait();
    }

    public void addEntity(ActionEvent actionEvent) throws IOException {
        if (inventoryPane.isVisible()){
            //TODO Make a popup wizard
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bauerperception/itassetmanager/addasset.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/bauerperception/itassetmanager/addwizard.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();
        }

        if (employeePane.isVisible()){
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bauerperception/itassetmanager/addemployee.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/bauerperception/itassetmanager/addwizard.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();
        }

        if (locationsPane.isVisible()){
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bauerperception/itassetmanager/addlocation.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/bauerperception/itassetmanager/addwizard.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();
        }
    }

    public void saveEntity() throws Exception {
        if (inventoryPane.isVisible()) {
            int assetID = Integer.parseInt(inventoryIDLbl.getText());
            String assetName = inventoryAssetNameTxtBox.getText();
            String assetModelNum = inventoryAssetModelNum.getText();
            String assetDescription = inventoryAssetDesc.getText();
            //TODO Need to add validation
            float assetPurchasedPrice = Float.parseFloat(inventoryAssetPurchasedPrice.getText());
            String assetType = inventoryAssetType.getValue();
            int employeeID = inventoryAssetAssignedTo.getValue().getEmployeeID();
            int locationID = inventoryAssetLocation.getValue().getLocationID();
            LocalDate assetPurchasedDate = inventoryAssetPurchasedDate.getValue();
            AssetDAOImpl.updateAsset(new AssetEntity(assetID, assetName, assetType, assetModelNum, assetDescription, employeeID, locationID, assetPurchasedDate, assetPurchasedPrice));
            inventoryTblView.setItems(AssetDAOImpl.getAllAssets());
            assetList = AssetDAOImpl.getAllAssets();
        }

        if (employeePane.isVisible()){
            int employeeID = Integer.parseInt(employeeIDLbl.getText());
            String firstName = employeeFirstNameTxt.getText();
            String middleName = employeeMiddleNameTxt.getText();
            String lastName = employeeLastNameTxt.getText();
            String emailAddress = employeeEmailTxt.getText();
            int assignedWorkLocation = employeeWorkLocTxt.getValue().getLocationID();
            EmployeeDAOImpl.updateEmployee(new EmployeeEntity(employeeID, firstName, middleName, lastName, emailAddress, assignedWorkLocation));
            employeeTblView.setItems(EmployeeDAOImpl.getAllEmployees());
            employeeList = EmployeeDAOImpl.getAllEmployees();
        }

        if (locationsPane.isVisible()){
            int locationID = Integer.parseInt(locationIDLbl.getText());
            String locationName = locationNameTxt.getText();
            LocationDAOImpl.updateLocation(new LocationEntity(locationID, locationName));
            locationTblView.setItems(LocationDAOImpl.getAllLocations());
            locationList = LocationDAOImpl.getAllLocations();
        }
    }

    public void deleteEntity(ActionEvent actionEvent) throws Exception {
        if (inventoryPane.isVisible()){
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
                    assetList = AssetDAOImpl.getAllAssets();
                }
            }
        }

        //TODO Delete employee
    }
}
