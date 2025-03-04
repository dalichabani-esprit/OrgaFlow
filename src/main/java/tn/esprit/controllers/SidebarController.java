
package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class SidebarController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void GotoEmployes(ActionEvent event) {
        changerScene(event, "/tn/esprit/test/menuEmployes.fxml");
    }
    @FXML
    public void gestionProjets(ActionEvent event) {
        changerScene(event, "/tn/esprit/test/menuProjets.fxml");
    }

    private void changerScene(ActionEvent event, String cheminFXML) {
        try {
            // Load the new FXML file
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(cheminFXML)));

            // Get the stage from the event source
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene
            scene = new Scene(root);

            // Set the new scene on the stage
            stage.setScene(scene);

            // Show the new scene
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // If there's an exception, print the stack trace
        }

    }

}
