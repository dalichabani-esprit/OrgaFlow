package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminCrud {

    @FXML
    private Button gestioncandidat;

    @FXML
    private Button gestionemployes;

    @FXML
    private Button logout;

    @FXML
    private BorderPane mainPane;

    @FXML
    void Oncliclogout(ActionEvent event) throws IOException {
        loadPage("/logginUser.fxml", event);
    }

    @FXML
    void Onclicpageemployes(ActionEvent event) throws IOException {
        loadPageInside("/GestionEmployes.fxml");
    }

    @FXML
    void onclicpagecandidat(ActionEvent event) throws IOException {
        loadPageInside("/Gestioncandidat.fxml");
    }

    private void loadPage(String fxmlPath, ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Application");
        stage.show();
    }
    @FXML
    private void loadPageInside(String fxmlPath) throws IOException {
        Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
        mainPane.setCenter(view);
    }


    @FXML
    public void onclicpagAfficheFactureAdmin(ActionEvent event) throws IOException {
        loadPageInside("/AfficherFactutreAdmin.fxml");
    }
    @FXML
    public void afficherDevisAdmin(ActionEvent event) throws IOException {
        loadPageInside("/AfficherDevisAdmin.fxml");
    }
    @FXML
    public void afficherDemande(ActionEvent event) throws IOException {
        loadPageInside("/AfficherDemande.fxml");
    }

    public void onclicpageAfficherFormations(ActionEvent event) throws IOException {
        loadPageInside("/AfficherFormations.fxml");
    }

    public void onclicpageAfficherFormateurs(ActionEvent event) throws IOException {
        loadPageInside("/Formateurs/AfficherFormateurs.fxml");
    }
}