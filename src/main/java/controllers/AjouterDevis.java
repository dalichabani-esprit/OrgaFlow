package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.devis;
import services.ServiceDevis;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Random;

public class AjouterDevis {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField idField;

    @FXML
    private TextField idDemandeField;

    @FXML
    private TextField montantEstimeField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField statutField;

    private ServiceDevis serviceDevis = new ServiceDevis(); // Instance du service

    @FXML
    private void ajouterDevis() {
        try {
            // Récupérer les valeurs des champs
            String idText = idField.getText();
            String idDemandeText = idDemandeField.getText();
            String montantEstimeText = montantEstimeField.getText();
            Date date = Date.valueOf(datePicker.getValue()); // Utilisation de java.sql.Date
            String statut = statutField.getText();
            String reference = generateUniqueReference(); // Générer une référence unique

            // Vérifier si tous les champs sont remplis
            if (idText.isEmpty() || idDemandeText.isEmpty() || montantEstimeText.isEmpty() || date == null || statut.isEmpty()) {
                showAlert("Erreur", "Veuillez remplir tous les champs.");
                return;
            }

            // Convertir ID et Id Demande en entiers et Montant Estimé en float
            int id = Integer.parseInt(idText);
            int idDemande = Integer.parseInt(idDemandeText);
            float montantEstime = Float.parseFloat(montantEstimeText);  // Utilisation de float

            // Créer un devis et l'ajouter via le service
            devis devis = new devis(id, idDemande, montantEstime, date, statut, reference);  // Utilisation de la classe devis
            serviceDevis.add(devis);

            // Réinitialiser les champs après l'ajout
            idField.clear();
            idDemandeField.clear();
            montantEstimeField.clear();
            datePicker.setValue(null);
            statutField.clear();

            // Afficher un message de succès
            showAlert("Succès", "Devis ajouté avec succès !");
        } catch (NumberFormatException e) {
            showAlert("Erreur", "L'ID, l'ID Demande, et le Montant Estimé doivent être des valeurs valides.");
        }
    }

    private String generateUniqueReference() {
        String reference;
        do {
            reference = generateReference();
        } while (!isReferenceUnique(reference));
        return reference;
    }

    private String generateReference() {
        Random random = new Random();
        int randomNumber = random.nextInt(10000); // Générer un nombre aléatoire
        return "DV-" + randomNumber; // Formater la référence
    }

    private boolean isReferenceUnique(String reference) {
        String query = "SELECT COUNT(*) FROM devis WHERE referenceDevis = ?";
        try (Connection conn = serviceDevis.getConnection(); // Récupérer la connexion depuis le service
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, reference);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // Si le compteur est 0, la référence est unique
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification de l'unicité : " + e.getMessage());
        }
        return false;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
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

    // Méthode auxiliaire pour changer de scène
    private void switchScene(ActionEvent event, Parent root) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}