package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class home {

    private Stage stage;
    private Scene scene;
    private Parent root;

    // Méthode pour "Ajouter Demande"
    @FXML
    private void ajouterDemande(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AjouterDemande.fxml"));
        switchScene(event, root);
    }





    // Méthode pour "Modifier Demande"
    @FXML
    private void modifierDemande(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/ModifierDemande.fxml"));
        switchScene(event, root);
    }

    // Méthode pour "Afficher Demande"
    @FXML
    private void afficherDemande(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AfficherDemande.fxml"));
        switchScene(event, root);
    }

    // Méthode pour "Ajouter Devis"
    @FXML
    private void ajouterDevis(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AjouterDevis.fxml"));
        switchScene(event, root);
    }

    // Méthode pour "Modifier Devis"
    @FXML
    private void modifierDevis(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/ModifierDevis.fxml"));
        switchScene(event, root);
    }

    // Méthode pour "Afficher Devis"
    @FXML
    private void afficherDevis(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AfficherDevis.fxml"));
        switchScene(event, root);
    }

    // Méthode pour "Ajouter Facture"
    @FXML
    private void ajouterFacture(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AjouterFacture.fxml"));
        switchScene(event, root);
    }

    // Méthode pour "Modifier Facture"
    @FXML
    private void modifierFacture(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/ModifierFacture.fxml"));
        switchScene(event, root);
    }

    // Méthode pour "Afficher Facture"
    @FXML
    private void afficherFacture(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AfficherDemandeUser.fxml"));
        switchScene(event, root);
    }

    @FXML
    private void afficherDevisDemandeur(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AfficherDevisUser.fxml"));
        switchScene(event, root);
    }

    @FXML
    private void afficherFactureDemandeur(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/DemandeurFactures.fxml"));
        switchScene(event, root);
    }


    @FXML
    private void afficherDevisUser(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AfficherDevisUser.fxml"));
        switchScene(event, root);
    }






    @FXML
    private void afficherDemandeDemandeur(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AfficherDemandeUser.fxml"));
        switchScene(event, root);
    }

    @FXML
    private void afficherDevisAdmin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AfficherDevisAdmin.fxml"));
        switchScene(event, root);
    }



    @FXML
    private void AfficheFactureAdmin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AfficherFactutreAdmin.fxml"));
        switchScene(event, root);
    }


    // Méthode auxiliaire pour changer de scène
    private void switchScene(ActionEvent event, Parent root) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
