package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.demande;
import services.ServiceDemande;

import java.io.IOException;

public class AjouterDemande {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ComboBox<String> typeComboBox; // Updated to ComboBox

    @FXML
    private ComboBox<String> statutComboBox; // Updated to ComboBox


    @FXML
    private TextField descriptionField;

    @FXML
    private TextField demandeurIdField;



    private ServiceDemande serviceDemande = new ServiceDemande();

    @FXML
    private void ajouterDemande() {
        try {
            // Récupérer les valeurs des champs
            String type = typeComboBox.getValue(); // Get selected value from ComboBox
            String description = descriptionField.getText();
            String demandeurIdText = demandeurIdField.getText();
            String statut = statutComboBox.getValue(); // Get selected value from ComboBox

            // Vérifier si tous les champs sont remplis
            if (type == null || type.isEmpty()) {
                showAlert("Erreur", "Le champ 'Type' est vide.");
                return;
            }
            if (description.isEmpty()) {
                showAlert("Erreur", "Le champ 'Description' est vide.");
                return;
            }
            if (demandeurIdText.isEmpty()) {
                showAlert("Erreur", "Le champ 'Demandeur Id' est vide.");
                return;
            }
            if (statut.isEmpty()) {
                showAlert("Erreur", "Le champ 'Statut' est vide.");
                return;
            }

            int demandeurId = Integer.parseInt(demandeurIdText);

            // Récupérer la date système (date actuelle)
            java.util.Date date = java.sql.Date.valueOf(java.time.LocalDate.now());

            // Créer une nouvelle demande et l'ajouter via le service
            demande demande = new demande(type, description, demandeurId, date, statut);
            serviceDemande.add(demande);

            // Réinitialiser les champs après l'ajout
            clearFields();

            // Afficher un message de succès avec ID de la demande
            showAlert("Succès", "Demande ajoutée avec succès !");
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue, veuillez réessayer.");
            e.printStackTrace(); // Log the error for debugging
        }
    }

    // Méthode pour réinitialiser les champs
    private void clearFields() {
        typeComboBox.getSelectionModel().clearSelection(); // Clear the ComboBox selection
        descriptionField.clear();
        demandeurIdField.clear();
    }

    // Méthode pour afficher des alertes
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour "Retour"
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