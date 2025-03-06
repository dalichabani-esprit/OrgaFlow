package tn.esprit.Controllers;

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
import tn.esprit.models.demande;

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
    private ComboBox<String> typeComboBox;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField dateDemandeField;
    @FXML
    private ComboBox<String> statutComboBox;

    private demande currentDemande;

    // This method is called to initialize the controller with a demande
    public void initialize(demande demande) {
        if (demande != null) {
            currentDemande = demande;
            typeComboBox.setValue(demande.getType());
            descriptionField.setText(demande.getDescription());
            dateDemandeField.setText(demande.getDate_demande().toString());
            statutComboBox.setValue(demande.getStatut());
        }
    }

    @FXML
    private void saveDemande() {
        String url = "jdbc:mysql://localhost:3306/orgaflowdb";
        String user = "root";
        String password = "";

        String type = typeComboBox.getValue();
        String description = descriptionField.getText().trim();
        String dateDemandeStr = dateDemandeField.getText().trim();
        String statut = statutComboBox.getValue();

        // Validate input
        if (type == null || description.isEmpty() || dateDemandeStr.isEmpty() || statut == null) {
            showAlert("Input Error", "All fields must be filled out.");
            return;
        }

        try {
            // Prepare the SQL update statement
            String query = "UPDATE demande SET type = ?, description = ?, date_demande = ?, statut = ? WHERE reference = ?"; // Updated to remove demandeur_id

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, type);
                stmt.setString(2, description);
                stmt.setDate(3, java.sql.Date.valueOf(dateDemandeStr));
                stmt.setString(4, statut);
                stmt.setString(5, currentDemande.getReference()); // Use reference for the update

                // Execute the update
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Demande updated successfully!");
                } else {
                    showAlert("Update Error", "No demande found with the specified reference.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Failed to update demande: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            showAlert("Input Error", "Date must be in the format YYYY-MM-DD.");
        }
    }

    @FXML
    private void Retour(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AdminCrud.fxml")); // Adjust the FXML file as needed
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