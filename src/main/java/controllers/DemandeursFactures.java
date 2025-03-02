package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.facture;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DemandeursFactures {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private VBox vboxFactures; // Updated to reference the VBox in FXML

    private int demandeurId = 1;

    @FXML
    public void initialize() {
        loadFactures();
    }



    private void loadFactures() {
        String url = "jdbc:mysql://localhost:3306/gestfacturation";
        String user = "root";
        String password = "";

        String query = "SELECT * FROM facture WHERE destinataire_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, demandeurId);

            ResultSet rs = stmt.executeQuery();

            // Clear existing entries in the VBox
            vboxFactures.getChildren().clear();

            // Create header row
            HBox headerRow = new HBox(50);
            headerRow.setStyle("-fx-background-color: #d2d2d2; -fx-padding: 10;");
            headerRow.getChildren().addAll(

                    new Label("Montant"),
                    new Label("Date"),
                    new Label("Statut")

            );
            vboxFactures.getChildren().add(headerRow);

            // Iterate through the result set and create rows
            while (rs.next()) {
                facture facture = new facture(

                        rs.getFloat("montant_final"),
                        rs.getDate("date_facture"),
                        rs.getString("statut")
                );

                HBox row = new HBox(50);
                row.setStyle("-fx-padding: 10;");
                row.getChildren().addAll(

                        new Label(String.valueOf(facture.getMontant_final())),
                        new Label(facture.getDate_facture().toString()),
                        new Label(facture.getStatut())

                );

                vboxFactures.getChildren().add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /*
    private Button createEditButton(facture facture) {
        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> {
            try {
                // Load the EditFacture FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditDemande.fxml"));
                Parent editRoot = loader.load();

                // Get the controller and initialize it with the selected facture
                EditFactureController editController = loader.getController();
                editController.initialize(facture);

                // Create a new scene and stage for the edit interface
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
*/


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
}