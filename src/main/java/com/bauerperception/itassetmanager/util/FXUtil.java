package com.bauerperception.itassetmanager.util;

import com.bauerperception.itassetmanager.DAO.AssetDAOImpl;
import com.bauerperception.itassetmanager.controller.MainController;
import com.bauerperception.itassetmanager.model.Entity;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class FXUtil {
    private static Stage stage;

    /*
    Header is the title.
    Contents is the general subject area.
     */
    public static void throwAlert(String header, String contents) {
        Alert cancelConfirmation = new Alert(Alert.AlertType.ERROR);
        cancelConfirmation.setTitle("");
        cancelConfirmation.setHeaderText(header);
        cancelConfirmation.setContentText(contents);
        Optional<ButtonType> confirmationResult = cancelConfirmation.showAndWait();
    }

    /*
    Use by calling this method by putting in an event. Put a .(period) after and choose which open method that you would like to use.
     */
    public static MainController goToMainScene(ActionEvent event) throws java.io.IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(FXUtil.class.getResource("/com/bauerperception/itassetmanager/main.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(Objects.requireNonNull(FXUtil.class.getResource("/com/bauerperception/itassetmanager/mainstyles.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
        MainController controller = loader.getController();
        return controller;
    }

    public static boolean cancelWizard(){
        Alert cancelConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        cancelConfirmation.setTitle("");
        cancelConfirmation.setHeaderText("Cancel Confirmation");
        cancelConfirmation.setContentText("Do you really want to cancel? You will lose any data entered");
        Optional<ButtonType> confirmationResult = cancelConfirmation.showAndWait();

        return confirmationResult.get() == ButtonType.OK;
    }

    public static boolean confirmDeletion(ActionEvent event){
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Alert deleteConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        deleteConfirmation.setTitle("");
        deleteConfirmation.setHeaderText("Delete Confirmation");
        deleteConfirmation.setContentText("Do you really want to delete this entity?");
        Optional<ButtonType> confirmationResult = deleteConfirmation.showAndWait();

        return confirmationResult.get() == ButtonType.OK;
    }

    public static FXMLLoader goToScene(ActionEvent event, String fxmlName, String stylesName) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(FXUtil.class.getResource("/com/bauerperception/itassetmanager/" + fxmlName + ".fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(Objects.requireNonNull(FXUtil.class.getResource("/com/bauerperception/itassetmanager/" + stylesName + ".css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
        return loader;
    }

    public static <T extends Entity> T getEntityByID(ObservableList<T> genericList, int entityID) {
        for (T i : genericList) {
            if (i.getID() == entityID){
                return i;
            }
        }
        //If this returns null the ID for an entity in the list
        return null;
    }

    //TODO https://www.baeldung.com/java-check-string-number
    public static boolean isNumeric(String inputString) {
        if (inputString == null) {
            return false;
        }
        try {
            float d = Float.parseFloat(inputString);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
