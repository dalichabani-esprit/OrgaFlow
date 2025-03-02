package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.demande;
import services.ServiceDemande;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class AjouterDemande {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private ComboBox<String> statutComboBox;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField demandeurIdField;

    private ServiceDemande serviceDemande = new ServiceDemande();

    private static final String DB_URL = "jdbc:mysql://localhost:3306/gestfacturation"; // Remplacez par votre URL
    private static final String USER = "root"; // Remplacez par votre utilisateur
    private static final String PASS = ""; // Remplacez par votre mot de passe

    @FXML
    private void ajouterDemande() {
        try {
            String type = typeComboBox.getValue();
            String description = descriptionField.getText();
            String demandeurIdText = demandeurIdField.getText();
            String statut = statutComboBox.getValue();

            // Générer la référence automatiquement
            String reference = generateUniqueReference();

            // Vérification des champs
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
            if (statut == null || statut.isEmpty()) {
                showAlert("Erreur", "Le champ 'Statut' est vide.");
                return;
            }

            int demandeurId;
            try {
                demandeurId = Integer.parseInt(demandeurIdText);
                if (demandeurId <= 0) {
                    showAlert("Erreur", "L'ID du demandeur doit être un nombre positif.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert("Erreur", "L'ID du demandeur doit être un nombre valide.");
                return;
            }

            java.util.Date date = java.sql.Date.valueOf(java.time.LocalDate.now());
            demande demande = new demande(type, description, demandeurId, date, statut, reference); // Passer la référence générée
            serviceDemande.add(demande);
            clearFields();
            showAlert("Succès", "Demande ajoutée avec succès !");
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue, veuillez réessayer.");
            e.printStackTrace();
        }
    }

    private String generateUniqueReference() {
        String reference;
        do {
            reference = generateReference(); // Générer une référence
        } while (!isReferenceUnique(reference)); // Vérifier l'unicité
        return reference;
    }

    private String generateReference() {
        Random random = new Random();
        int randomNumber = random.nextInt(10000); // Générer un nombre aléatoire
        return "DM-" + randomNumber; // Formater la référence
    }



    private boolean isReferenceUnique(String reference) {
        String query = "SELECT COUNT(*) FROM demande WHERE reference = ?"; // Assurez-vous que le nom de la colonne est correct
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, reference);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // Retourne vrai si aucune référence correspondante n'est trouvée
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void clearFields() {
        typeComboBox.getSelectionModel().clearSelection();
        descriptionField.clear();
        demandeurIdField.clear();
        statutComboBox.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void Retour(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/home.fxml"));
        switchScene(event, root);
    }

    private void switchScene(ActionEvent event, Parent root) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}