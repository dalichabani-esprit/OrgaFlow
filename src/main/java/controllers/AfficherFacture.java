package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.facture;
import services.ServiceFacture;

import java.io.IOException;
import java.util.List;

public class AfficherFacture {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private VBox facturesVBox; // VBox pour afficher les factures

    private ServiceFacture serviceFacture;

    // Initialisation de la vue
    @FXML
    private void initialize() {
        serviceFacture = new ServiceFacture(); // Initialiser le ServiceFacture
        loadFactures(); // Charger les factures
    }

    // Méthode pour charger et afficher les factures dans le VBox
    private void loadFactures() {
        List<facture> factures = serviceFacture.getAll(); // Récupérer toutes les factures

        // Vider le VBox avant de rajouter de nouvelles factures
        facturesVBox.getChildren().clear();

        // Ajouter chaque facture dans le VBox
        for (facture f : factures) {
            // Créer le texte à afficher pour chaque facture
            Text factureText = new Text("ID: " + f.getId_facture() + " | Montant: " + f.getMontant_final() + " | Date: " + f.getDate_facture() + " | Statut: " + f.getStatut());
            facturesVBox.getChildren().add(factureText); // Ajouter le texte dans le VBox
        }
    }

    @FXML
    private void Retour(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/home.fxml"));
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
