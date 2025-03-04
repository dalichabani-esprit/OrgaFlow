module tn.esprit.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.zxing;
    requires spire.barcode.free;
    requires org.apache.poi.ooxml;
    requires java.desktop;
    requires layout;
    requires kernel;
    requires java.net.http;
    requires com.google.gson;


    opens tn.esprit.test to javafx.fxml;
    exports tn.esprit.test;
    exports tn.esprit.controllers;
    opens tn.esprit.controllers to javafx.fxml;
}