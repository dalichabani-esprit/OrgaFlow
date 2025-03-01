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
import javafx.fxml.FXML;
import models.facture;
//blabla
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
        String url = "jdbc:mysql://localhost:3306/gestfacturation";
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
                    new Label("ID"),
                    new Label("Montant Final"),
                    new Label("Date Facture"),
                    new Label("Statut"),
                    new Label("Destinataire ID"),
                    new Label("Actions") // Add header for actions
            );
            vboxDevis.getChildren().add(headerRow);

            // Iterate through the result set and create rows
            while (rs.next()) {
                facture facture = new facture(
                        rs.getInt("id_facture"),
                        rs.getFloat("montant_final"),
                        rs.getDate("date_facture"),
                        rs.getString("statut"),
                        rs.getInt("destinataire_id")
                );

                HBox row = new HBox(50);
                row.setStyle("-fx-padding: 10;");
                row.getChildren().addAll(
                        new Label(String.valueOf(facture.getId_facture())),
                        new Label(String.valueOf(facture.getMontant_final())),
                        new Label(String.valueOf(facture.getDate_facture())),
                        new Label(facture.getStatut()),
                        new Label(String.valueOf(facture.getDestinataire_id())),
                        createEditButton(facture), // Add the Edit button
                        createDeleteButton(facture.getId_facture()) // Add the Delete button
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditFacture.fxml"));
                Parent editRoot = loader.load();

                EditFacture editController = loader.getController();
                editController.initialize(facture);

                Scene editScene = new Scene(editRoot);
                Stage editStage = new Stage();
                editStage.setScene(editScene);
                editStage.setTitle("Edit Facture");
                editStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return editButton;
    }

    private Button createDeleteButton(int factureId) {
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            try {
                deleteFacture(factureId);
                loadFactures(); // Refresh the list after deletion
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Database Error", "Failed to delete facture: " + e.getMessage());
            }
        });
        return deleteButton;
    }

    private void deleteFacture(int factureId) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/gestfacturation";
        String user = "root";
        String password = "";

        String query = "DELETE FROM facture WHERE id_facture = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, factureId);
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