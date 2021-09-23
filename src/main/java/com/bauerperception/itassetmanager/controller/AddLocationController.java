package com.bauerperception.itassetmanager.controller;

import com.bauerperception.itassetmanager.DAO.LoadOutDAOImpl;
import com.bauerperception.itassetmanager.DAO.LocationDAOImpl;
import com.bauerperception.itassetmanager.model.LoadOutEntity;
import com.bauerperception.itassetmanager.model.LocationEntity;
import com.bauerperception.itassetmanager.util.FXUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.bauerperception.itassetmanager.util.FXUtil.goToMainScene;

public class AddLocationController implements Initializable{

    @FXML
    private TextField locationNameTxt;

    @FXML
    private ChoiceBox<LoadOutEntity> assignLoadOutChoice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            assignLoadOutChoice.setItems(LoadOutDAOImpl.getAllLoadOuts());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cancelAdd(ActionEvent event) throws IOException {
        if (FXUtil.cancelWizard()) {
            goToMainScene(event).openLocations();
        }
    }

    @FXML
    void saveAddLocation(ActionEvent event) throws Exception {
        String workLocationName = locationNameTxt.getText();

        if (!workLocationName.isEmpty()){
            if(assignLoadOutChoice.getValue() != null){
                LocationDAOImpl.addLocation(new LocationEntity(workLocationName, assignLoadOutChoice.getValue().getLoadOutID()));
            } else {
                LocationDAOImpl.addLocation(new LocationEntity(workLocationName));
            }
            goToMainScene(event).openLocations();
        } else {
            FXUtil.throwAlert("Missing Data", "A location has to have a name before being saved.");
        }
    }
}
