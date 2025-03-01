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
import models.facture;  // Ensure you have a 'facture' model.

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditFacture {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField idField;  // Field for ID of the facture
    @FXML
    private TextField montantField;  // Field for montant
    @FXML
    private TextField dateField;  // Field for date
    @FXML
    private ComboBox<String> statutComboBox;

    private facture currentFacture;

    // This method is called to initialize the controller with a facture
    public void initialize(facture facture) {
        if (facture != null) {
            currentFacture = facture;

            montantField.setText(String.valueOf(facture.getMontant_final()));  // Adjust according to your facture model
            dateField.setText(facture.getDate_facture().toString());  // Adjust method as needed
            statutComboBox.setValue(facture.getStatut());
        }
    }

    @FXML
    private void saveFacture() {
        String url = "jdbc:mysql://localhost:3306/gestfacturation";
        String user = "root";
        String password = "";

        // Retrieve updated values from the text fields
        String montantStr = montantField.getText().trim();
        String dateFactureStr = dateField.getText().trim();
        String statut = statutComboBox.getValue(); // Get selected value from ComboBox

        // Validate input
        if (montantStr.isEmpty() || dateFactureStr.isEmpty() || statut.isEmpty()) {
            showAlert("Input Error", "All fields must be filled out.");
            return;
        }

        try {
            double montant = Double.parseDouble(montantStr); // Convert montant to double

            // Prepare the SQL update statement
            String query = "UPDATE facture SET montant_final = ?, date_facture = ?, statut = ? WHERE id_facture = ?";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDouble(1, montant);
                stmt.setDate(2, java.sql.Date.valueOf(dateFactureStr)); // Ensure the date format is correct
                stmt.setString(3, statut);
                stmt.setInt(4, currentFacture.getId_facture());  // Assuming you have this method

                // Execute the update
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Facture updated successfully!");
                } else {
                    showAlert("Update Error", "No facture found with the specified ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Failed to update facture: " + e.getMessage());
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Montant must be a valid number.");
        } catch (IllegalArgumentException e) {
            showAlert("Input Error", "Date must be in the format YYYY-MM-DD.");
        }
    }

    @FXML
    private void Retour(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AfficherFactutreAdmin.fxml")); // Adjust the FXML file as needed
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