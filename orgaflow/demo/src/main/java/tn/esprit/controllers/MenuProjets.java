package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import javafx.scene.Parent;



public class MenuProjets {

    private Stage stage;
    private Scene scene;
    private Parent root;


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

    @FXML
    public void gestionProjets(ActionEvent event) {
        changerScene(event, "/tn/esprit/test/gestion_projets.fxml");
    }

    @FXML
    public void gestionTaches(ActionEvent event) {
        changerScene(event, "/tn/esprit/test/gestion_taches.fxml");
    }

    @FXML
    public void modifierProjet(ActionEvent event) {
        changerScene(event, "/tn/esprit/test/modifier_projet.fxml");
    }

    @FXML
    public void modifierTache(ActionEvent event) {
        changerScene(event, "/tn/esprit/test/modifier_tache.fxml");
    }

    @FXML
    public void Chatbot(ActionEvent event) {
        changerScene(event, "/tn/esprit/test/chatbot.fxml");
    }

}
