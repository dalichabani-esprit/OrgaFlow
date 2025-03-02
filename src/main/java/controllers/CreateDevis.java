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
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateDevis {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ComboBox<String> comboBoxDemande; // Pour les références

    @FXML
    private TextField montantEstimeField;

    @FXML
    private ComboBox<String> statutComboBox; // Remplacer statutField par statutComboBox

    @FXML
    private Button submitButton;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/gestfacturation"; // Remplacez par votre URL
    private static final String USER = "root"; // Remplacez par votre utilisateur
    private static final String PASS = ""; // Remplacez par votre mot de passe

    @FXML
    public void initialize() {
        // Charger les références des demandes dans la ComboBox
        List<String> references = fetchDevisReferences();
        comboBoxDemande.getItems().addAll(references);
    }

    private List<String> fetchDevisReferences() {
        List<String> references = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT reference FROM demande")) { // Remplacez "demande" par votre table

            while (rs.next()) {
                references.add(rs.getString("reference"));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Gérer les exceptions de manière appropriée
        }

        return references;
    }

    private String generateUniqueReference() {
        String reference;
        do {
            reference = "DV-" + new Random().nextInt(100000); // Generate random number
        } while (!isReferenceUnique(reference)); // Ensure uniqueness
        return reference;
    }

    private boolean isReferenceUnique(String reference) {
        String query = "SELECT COUNT(*) FROM devis WHERE referenceDevis = ?"; // Assurez-vous que le nom de la colonne est correct
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, reference);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // Return true if no matching reference found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    private void ajouterDevis() {
        String selectedReference = comboBoxDemande.getValue();
        String montantEstime = montantEstimeField.getText();
        String statut = statutComboBox.getValue(); // Obtenir la valeur sélectionnée du ComboBox

        // Vérifier si tous les champs sont remplis
        if (selectedReference == null || selectedReference.isEmpty()) {
            showAlert("Erreur", "Le champ 'Référence' est vide.");
            return;
        }
        if (montantEstime.isEmpty()) {
            showAlert("Erreur", "Le champ 'Montant Estimé' est vide.");
            return;
        }
        if (statut == null || statut.isEmpty()) { // Vérification pour statutComboBox
            showAlert("Erreur", "Le champ 'Statut' est vide.");
            return;
        }

        // Valider le montant estimé pour s'assurer qu'il est un nombre valide
        double montant;
        try {
            montant = Double.parseDouble(montantEstime);
            if (montant < 0) {
                showAlert("Erreur", "Le 'Montant Estimé' doit être un nombre positif.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le 'Montant Estimé' doit être un nombre valide.");
            return;
        }

        // Obtenir l'ID de la demande basé sur la référence sélectionnée
        String selectedIdDemande = getIdDemandeByReference(selectedReference);

        // Obtenir la date système
        LocalDate dateDevis = LocalDate.now(); // Date actuelle
        Date sqlDate = Date.valueOf(dateDevis); // Convertir en java.sql.Date

        // Generate unique referenceDevis
        String referenceDevis = generateUniqueReference();

        // Logique pour insérer les données dans la base de données
        String insertSQL = "INSERT INTO devis (id_demande, montant_estime, statut, date_devis, referenceDevis) VALUES (?, ?, ?, ?, ?)"; // Assurez-vous que la table et les colonnes sont correctes

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, selectedIdDemande);
            pstmt.setDouble(2, montant); // Utiliser setDouble pour le montant
            pstmt.setString(3, statut);
            pstmt.setDate(4, sqlDate); // Ajouter la date
            pstmt.setString(5, referenceDevis); // Ajouter la référence générée

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Données insérées avec succès !");
            } else {
                System.out.println("Aucune donnée insérée.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Gérer les exceptions de manière appropriée
        }
    }

    private String getIdDemandeByReference(String reference) {
        String idDemande = null;
        String query = "SELECT id_demande FROM demande WHERE reference = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, reference);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                idDemande = rs.getString("id_demande"); // Récupérer l'ID correspondant
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idDemande;
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
        root = FXMLLoader.load(getClass().getResource("/AfficherDemande.fxml"));
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