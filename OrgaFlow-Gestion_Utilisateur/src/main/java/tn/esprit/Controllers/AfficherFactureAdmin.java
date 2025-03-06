package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.facture;

import java.io.IOException;
import java.sql.*;

public class AfficherFactureAdmin {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private VBox vboxDevis; // Updated to reference the VBox in FXML

    @FXML
    public void initialize() {
        loadFactures();
    }

    private void loadFactures() {
        String url = "jdbc:mysql://localhost:3306/orgaflowdb";
        String user = "root";
        String password = "";

        String query = "SELECT * FROM facture";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            // Clear existing entries in the VBox
            vboxDevis.getChildren().clear();

            // Create header row
            HBox headerRow = new HBox(50);
            headerRow.setStyle("-fx-background-color: #d2d2d2; -fx-padding: 10;");
            headerRow.getChildren().addAll(
                    new Label("Reference"),
                    new Label("Montant Final"),
                    new Label("Date Facture"),
                    new Label("Statut"),
                    new Label("Actions") // Add header for actions
            );
            vboxDevis.getChildren().add(headerRow);

            // Iterate through the result set and create rows
            while (rs.next()) {
                facture facture = new facture(
                        rs.getString("refFacture"),
                        rs.getFloat("montant_final"),
                        rs.getDate("date_facture"),
                        rs.getString("statut")
                );

                HBox row = new HBox(50);
                row.setStyle("-fx-padding: 10;");
                row.getChildren().addAll(
                        new Label(String.valueOf(facture.getRefFacture())),
                        new Label(String.valueOf(facture.getMontant_final())),
                        new Label(String.valueOf(facture.getDate_facture())),
                        new Label(facture.getStatut()),
                        createEditButton(facture), // Add the Edit button
                        createDeleteButton(facture.getRefFacture()) // Updated to use refFacture
                );

                vboxDevis.getChildren().add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Button createEditButton(facture facture) {
        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> {
            try {
                // Load the FXML for the Edit Facture interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditFacture.fxml"));
                Parent editRoot = loader.load();

                // Get the controller for the Edit Facture interface
                EditFacture editController = loader.getController();
                editController.initialize(facture); // Pass the facture to the controller

                // Get the current stage from the button event
                Stage stage = (Stage) editButton.getScene().getWindow();

                // Create a new scene with the loaded FXML
                Scene editScene = new Scene(editRoot);

                // Set the new scene to the current stage
                stage.setScene(editScene);
                stage.setTitle("Edit Facture"); // Optionally set the title
                stage.show(); // Show the updated stage
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return editButton;
    }

    private Button createDeleteButton(String refFacture) { // Updated to use String refFacture
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            try {
                deleteFacture(refFacture); // Updated to use refFacture
                loadFactures(); // Refresh the list after deletion
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Database Error", "Failed to delete facture: " + e.getMessage());
            }
        });
        return deleteButton;
    }

    private void deleteFacture(String refFacture) throws SQLException { // Updated to use String refFacture
        String url = "jdbc:mysql://localhost:3306/orgaflowdb";
        String user = "root";
        String password = "";

        String query = "DELETE FROM facture WHERE refFacture = ?"; // Updated query to use refFacture

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, refFacture); // Set refFacture
            stmt.executeUpdate();
            System.out.println("Facture deleted successfully!");
        }
    }

    @FXML
    private void CreateDevis(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CreateDevis.fxml"));
        switchScene(event, root);
    }

    @FXML
    private void CreateFacture(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CreateFacture.fxml"));
        switchScene(event, root);
    }

    @FXML
    private void Retour(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/home.fxml"));
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