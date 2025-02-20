module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens tn.esprit.test to javafx.fxml;
    exports tn.esprit.test;
    exports tn.esprit.controllers;
    opens tn.esprit.controllers to javafx.fxml;
}