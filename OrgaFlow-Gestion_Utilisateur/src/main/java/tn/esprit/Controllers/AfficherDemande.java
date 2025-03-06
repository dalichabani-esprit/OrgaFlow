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
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.demande;

import java.io.IOException;
import java.sql.*;

public class AfficherDemande {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private VBox vboxDemandes;
    @FXML
    private TextField searchField;

    @FXML
    public void initialize() {
        loadDemandes();
    }

    private void loadDemandes() {
        String url = "jdbc:mysql://localhost:3306/orgaflowdb";
        String user = "root";
        String password = "";

        String query = "SELECT * FROM demande";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            vboxDemandes.getChildren().clear();

            HBox headerRow = new HBox(50);
            headerRow.setStyle("-fx-background-color: #d2d2d2; -fx-padding: 10;");
            headerRow.getChildren().addAll(
                    new Label("Référence"),
                    new Label("Type"),
                    new Label("Description"),
                    new Label("Date Demande"),
                    new Label("Statut"),
                    new Label("Actions")
            );
            vboxDemandes.getChildren().add(headerRow);

            while (rs.next()) {
                demande demande = new demande(
                        rs.getString("reference"),
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
                new Label(demande.getReference()),
                new Label(demande.getType()),
                new Label(demande.getDescription()),
                new Label(demande.getDate_demande().toString()),
                new Label(demande.getStatut()),
                createEditButton(demande),
                createDeleteButton(demande.getReference()) // Updated to use reference
        );

        vboxDemandes.getChildren().add(row);
    }

    private Button createEditButton(demande demande) {
        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> {
            try {
                // Load the FXML for the Edit Demande interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditDemande.fxml"));
                Parent editRoot = loader.load();

                // Get the controller for the Edit Demande interface
                EditDemandeController editController = loader.getController();
                editController.initialize(demande); // Pass the demande to the controller

                // Get the current stage from the button event
                Stage stage = (Stage) editButton.getScene().getWindow();

                // Create a new scene with the loaded FXML
                Scene editScene = new Scene(editRoot);

                // Set the new scene to the current stage
                stage.setScene(editScene);
                stage.setTitle("Edit Demande"); // Optionally set the title
                stage.show(); // Show the updated stage
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return editButton;
    }

    private Button createDeleteButton(String reference) { // Use String reference instead of int
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            try {
                deleteDemande(reference); // Updated to use reference
                loadDemandes();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Database Error", "Failed to delete demande: " + e.getMessage());
            }
        });
        return deleteButton;
    }

    private void deleteDemande(String reference) throws SQLException { // Use String reference
        String url = "jdbc:mysql://localhost:3306/orgaflowdb";
        String user = "root";
        String password = "";

        String query = "DELETE FROM demande WHERE reference = ?"; // Updated query to use reference

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, reference); // Set reference
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
        String url = "jdbc:mysql://localhost:3306/orgaflowdb";
        String user = "root";
        String password = "";

        String sqlQuery = "SELECT * FROM demande WHERE reference LIKE ? OR type LIKE ? OR description LIKE ? OR demandeur_id LIKE ? OR date_demande LIKE ? OR statut LIKE ?"; // Removed id_demande

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            String likeQuery = "%" + query + "%";
            stmt.setString(1, likeQuery);
            stmt.setString(2, likeQuery);
            stmt.setString(3, likeQuery);
            stmt.setString(4, likeQuery);
            stmt.setString(5, likeQuery);
            stmt.setString(6, likeQuery);

            ResultSet rs = stmt.executeQuery();
            vboxDemandes.getChildren().clear();

            HBox headerRow = new HBox(50);
            headerRow.setStyle("-fx-background-color: #d2d2d2; -fx-padding: 10;");
            headerRow.getChildren().addAll(
                    new Label("Référence"),
                    new Label("Type"),
                    new Label("Description"),
                    new Label("Date Demande"),
                    new Label("Statut"),
                    new Label("Actions")
            );
            vboxDemandes.getChildren().add(headerRow);

            while (rs.next()) {
                demande demande = new demande(
                        rs.getString("reference"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getDate("date_demande"),
                        rs.getString("statut")
                );

                addDemandeRow(demande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Failed to search demandes: " + e.getMessage());
        }
    }

    private void switchScene(ActionEvent event, Parent root) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}