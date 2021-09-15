module com.bauerperception.itassetmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.bauerperception.itassetmanager to javafx.fxml;
    opens com.bauerperception.itassetmanager.model to javafx.fxml;

    exports com.bauerperception.itassetmanager;
    exports com.bauerperception.itassetmanager.util;
    exports com.bauerperception.itassetmanager.DAO;
    exports com.bauerperception.itassetmanager.model;
    exports com.bauerperception.itassetmanager.controller;
    opens com.bauerperception.itassetmanager.controller to javafx.fxml;
}