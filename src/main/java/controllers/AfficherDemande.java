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
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.demande;
import javafx.fxml.FXML;

public class AfficherDemande {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private VBox vboxDemandes; // Updated to reference the VBox in FXML
    @FXML
    private TextField searchField; // Reference to the search TextField

    @FXML
    public void initialize() {
        loadDemandes(); // Load demandes initially
    }

    private void loadDemandes() {
        String url = "jdbc:mysql://localhost:3306/gestfacturation";
        String user = "root";
        String password = "";

        String query = "SELECT * FROM demande";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            // Clear existing entries in the VBox
            vboxDemandes.getChildren().clear();

            // Create header row
            HBox headerRow = new HBox(50);
            headerRow.setStyle("-fx-background-color: #d2d2d2; -fx-padding: 10;");
            headerRow.getChildren().addAll(
                    new Label("Référence"), // Ajout de l'en-tête pour la référence
                    new Label("Type"),
                    new Label("Description"),
                    new Label("Date Demande"),
                    new Label("Statut"),
                    new Label("Actions") // Add header for actions
            );
            vboxDemandes.getChildren().add(headerRow);

            // Iterate through the result set and create rows
            while (rs.next()) {
                demande demande = new demande(
                        rs.getString("reference"), // Récupération de la référence
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getDate("date_demande"),
                        rs.getString("statut")

                );

                addDemandeRow(demande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addDemandeRow(demande demande) {
        HBox row = new HBox(50);
        row.setStyle("-fx-padding: 10;");
        row.getChildren().addAll(
                new Label(demande.getReference()), // Affichage de la référence
                new Label(demande.getType()),
                new Label(demande.getDescription()),
                new Label(demande.getDate_demande().toString()),
                new Label(demande.getStatut()),
                createEditButton(demande), // Add the Edit button
                createDeleteButton(demande.getId_demande()) // Add the Delete button
        );

        vboxDemandes.getChildren().add(row);
    }

    private Button createEditButton(demande demande) {
        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditDemande.fxml"));
                Parent editRoot = loader.load();

                EditDemandeController editController = loader.getController();
                editController.initialize(demande);

                Scene editScene = new Scene(editRoot);
                Stage editStage = new Stage();
                editStage.setScene(editScene);
                editStage.setTitle("Edit Demande");
                editStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return editButton;
    }

    private Button createDeleteButton(int demandeId) {
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            try {
                deleteDemande(demandeId);
                loadDemandes(); // Refresh the list after deletion
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Database Error", "Failed to delete demande: " + e.getMessage());
            }
        });
        return deleteButton;
    }

    private void deleteDemande(int demandeId) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/gestfacturation";
        String user = "root";
        String password = "";

        String query = "DELETE FROM demande WHERE id_demande = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, demandeId);
            stmt.executeUpdate();
            System.out.println("Demande deleted successfully!");
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

    @FXML
    private void searchDemandes() {
        String query = searchField.getText().trim();
        String url = "jdbc:mysql://localhost:3306/gestfacturation";
        String user = "root";
        String password = "";

        // Update SQL query to include all fields
        String sqlQuery = "SELECT * FROM demande WHERE id_demande LIKE ? OR type LIKE ? OR description LIKE ? OR demandeur_id LIKE ? OR date_demande LIKE ? OR statut LIKE ? OR reference LIKE ?"; // Ajout de la référence

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            String likeQuery = "%" + query + "%";
            stmt.setString(1, likeQuery);  // For ID
            stmt.setString(2, likeQuery);  // For Type
            stmt.setString(3, likeQuery);  // For Description
            stmt.setString(4, likeQuery);  // For Demandeur ID
            stmt.setString(5, likeQuery);  // For Date Demande
            stmt.setString(6, likeQuery);  // For Statut
            stmt.setString(7, likeQuery);  // For Référence

            ResultSet rs = stmt.executeQuery();

            // Clear existing entries in the VBox
            vboxDemandes.getChildren().clear();

            // Create header row
            HBox headerRow = new HBox(50);
            headerRow.setStyle("-fx-background-color: #d2d2d2; -fx-padding: 10;");
            headerRow.getChildren().addAll(
                    new Label("ID"),
                    new Label("Type"),
                    new Label("Description"),
                    new Label("Demandeur ID"),
                    new Label("Date Demande"),
                    new Label("Statut"),
                    new Label("Référence"), // Ajout de l'en-tête pour la référence
                    new Label("Actions") // Add header for actions
            );
            vboxDemandes.getChildren().add(headerRow);

            // Iterate through the result set and create rows
            while (rs.next()) {
                demande demande = new demande(
                        rs.getInt("id_demande"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getInt("demandeur_id"),
                        rs.getDate("date_demande"),
                        rs.getString("statut"),
                        rs.getString("reference") // Récupération de la référence
                );

                addDemandeRow(demande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Failed to search demandes: " + e.getMessage());
        }
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