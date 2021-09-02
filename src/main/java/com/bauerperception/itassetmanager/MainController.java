package com.bauerperception.itassetmanager;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainController {

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

    @FXML
    private Label titleLabelLocationList;

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
    void openInventory(ActionEvent event) {
        employeePane.setVisible(false);
        inventoryPane.setVisible(true);
        loadoutPane.setVisible(false);
        locationsPane.setVisible(false);
        toDoPane.setVisible(false);
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

}
