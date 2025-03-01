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
import models.demande;  // Ensure you have a 'demande' model.

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditDemandeController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ComboBox<String> typeComboBox; // Updated to ComboBox
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField demandeurIdField;
    @FXML
    private TextField dateDemandeField;
    @FXML
    private ComboBox<String> statutComboBox; // Updated to ComboBox

    private demande currentDemande;

    // This method is called to initialize the controller with a demande
    public void initialize(demande demande) {
        if (demande != null) {
            currentDemande = demande;
            typeComboBox.setValue(demande.getType()); // Set selected value for ComboBox
            descriptionField.setText(demande.getDescription());
            demandeurIdField.setText(String.valueOf(demande.getDemandeur_id()));
            dateDemandeField.setText(demande.getDate_demande().toString());
            statutComboBox.setValue(demande.getStatut()); // Set selected value for ComboBox
        }
    }

    @FXML
    private void saveDemande() {
        String url = "jdbc:mysql://localhost:3306/gestfacturation";
        String user = "root";
        String password = "";

        // Retrieve updated values from the fields
        String type = typeComboBox.getValue(); // Get selected value from ComboBox
        String description = descriptionField.getText().trim();
        String demandeurIdStr = demandeurIdField.getText().trim();
        String dateDemandeStr = dateDemandeField.getText().trim();
        String statut = statutComboBox.getValue(); // Get selected value from ComboBox

        // Validate input
        if (type == null || description.isEmpty() || demandeurIdStr.isEmpty() || dateDemandeStr.isEmpty() || statut == null) {
            showAlert("Input Error", "All fields must be filled out.");
            return;
        }

        try {
            int demandeurId = Integer.parseInt(demandeurIdStr);

            // Prepare the SQL update statement
            String query = "UPDATE demande SET type = ?, description = ?, demandeur_id = ?, date_demande = ?, statut = ? WHERE id_demande = ?";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, type);
                stmt.setString(2, description);
                stmt.setInt(3, demandeurId);
                stmt.setDate(4, java.sql.Date.valueOf(dateDemandeStr)); // Ensure the date format is correct
                stmt.setString(5, statut);
                stmt.setInt(6, currentDemande.getId_demande());

                // Execute the update
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Demande updated successfully!");
                } else {
                    showAlert("Update Error", "No demande found with the specified ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Failed to update demande: " + e.getMessage());
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Demandeur ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            showAlert("Input Error", "Date must be in the format YYYY-MM-DD.");
        }
    }

    @FXML
    private void Retour(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AfficherDemande.fxml")); // Adjust the FXML file as needed
        switchScene(event, root);
    }

    // Method to switch scenes
    private void switchScene(ActionEvent event, Parent root) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}