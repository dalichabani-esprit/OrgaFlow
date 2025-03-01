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

public class CreateDevis {



    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ComboBox<String> comboBoxDemande;

    @FXML
    private TextField montantEstimeField;

    @FXML
    private TextField statutField;

    @FXML
    private Button submitButton;
    @FXML
    private ComboBox<String> statutComboBox; // Updated to ComboBox

    private static final String DB_URL = "jdbc:mysql://localhost:3306/gestfacturation"; // Remplacez par votre URL
    private static final String USER = "root"; // Remplacez par votre utilisateur
    private static final String PASS = ""; // Remplacez par votre mot de passe

    @FXML
    public void initialize() {
        // Charger les IDs des devis dans la ComboBox
        List<String> devisIds = fetchDevisIds();
        comboBoxDemande.getItems().addAll(devisIds);
    }

    private List<String> fetchDevisIds() {
        List<String> devisIds = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id_demande FROM demande")) { // Remplacez "demandes" par votre table

            while (rs.next()) {
                devisIds.add(rs.getString("id_demande"));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Gérer les exceptions de manière appropriée
        }

        return devisIds;
    }

    @FXML
    private void handleSubmit() {
        String selectedDevisId = comboBoxDemande.getValue();
        String montantEstime = montantEstimeField.getText();
        String statut = statutComboBox.getValue(); // Get selected value from ComboBox

        // Vérifier si tous les champs sont remplis
        if (selectedDevisId == null || selectedDevisId.isEmpty()) {
            showAlert("Erreur", "Le champ 'ID Demande' est vide.");
            return;
        }
        if (montantEstime.isEmpty()) {
            showAlert("Erreur", "Le champ 'Montant Estimé' est vide.");
            return;
        }
        if (statut.isEmpty()) {
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

        // Obtenir la date système
        LocalDate dateDevis = LocalDate.now(); // Date actuelle
        Date sqlDate = Date.valueOf(dateDevis); // Convertir en java.sql.Date

        // Logique pour insérer les données dans la base de données
        String insertSQL = "INSERT INTO devis (id_demande, montant_estime, statut, date_devis) VALUES (?, ?, ?, ?)"; // Assurez-vous que la table et les colonnes sont correctes

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, selectedDevisId);
            pstmt.setDouble(2, montant); // Utiliser setDouble pour le montant
            pstmt.setString(3, statut);
            pstmt.setDate(4, sqlDate); // Ajouter la date

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