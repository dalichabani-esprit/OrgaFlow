package controllers;

import java.io.IOException;
import java.sql.*;

import javafx.event.ActionEvent;
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
import models.devis;
import javafx.fxml.FXML;

public class AfficherDevisAdmin {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private VBox vboxDevis; // Updated to reference the VBox in FXML

    @FXML
    public void initialize() {
        loadDevis(); // Changed from loadFactures to loadDevis
    }

    private void loadDevis() {
        String url = "jdbc:mysql://localhost:3306/gestfacturation";
        String user = "root";
        String password = "";

        String query = "SELECT * FROM devis";

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
                    new Label("Montant Estime"),
                    new Label("Date Devis"),
                    new Label("Statut"),
                    new Label("Actions") // Add header for actions
            );
            vboxDevis.getChildren().add(headerRow);

            // Iterate through the result set and create rows
            while (rs.next()) {
                devis devis = new devis(
                        rs.getFloat("montant_estime"),
                        rs.getDate("date_devis"),
                        rs.getString("statut"),
                        rs.getString("referenceDevis") // Assuming this is the unique identifier
                );

                HBox row = new HBox(50);
                row.setStyle("-fx-padding: 10;");
                row.getChildren().addAll(
                        new Label(String.valueOf(devis.getReferenceDevis())),
                        new Label(String.valueOf(devis.getMontant_estime())),
                        new Label(String.valueOf(devis.getDate_devis())),
                        new Label(devis.getStatut()),
                        createEditButton(devis), // Add the Edit button
                        createDeleteButton(devis.getReferenceDevis()) // Updated to use referenceDevis
                );

                vboxDevis.getChildren().add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Button createEditButton(devis devis) {
        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditDevis.fxml"));
                Parent editRoot = loader.load();

                EditDevisController editController = loader.getController();
                editController.initialize(devis);

                Scene editScene = new Scene(editRoot);
                Stage editStage = new Stage();
                editStage.setScene(editScene);
                editStage.setTitle("Edit Devis");
                editStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return editButton;
    }

    private Button createDeleteButton(String referenceDevis) { // Updated to use String referenceDevis
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            try {
                deleteDevis(referenceDevis); // Updated to use referenceDevis
                loadDevis(); // Refresh the list after deletion
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Database Error", "Failed to delete devis: " + e.getMessage());
            }
        });
        return deleteButton;
    }

    private void deleteDevis(String referenceDevis) throws SQLException { // Updated to use String referenceDevis
        String url = "jdbc:mysql://localhost:3306/gestfacturation";
        String user = "root";
        String password = "";

        String query = "DELETE FROM devis WHERE referenceDevis = ?"; // Updated query to use referenceDevis

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, referenceDevis); // Set referenceDevis
            stmt.executeUpdate();
            System.out.println("Devis deleted successfully!");
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