module com.bauerperception.itassetmanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.bauerperception.itassetmanager to javafx.fxml;
    exports com.bauerperception.itassetmanager;
}