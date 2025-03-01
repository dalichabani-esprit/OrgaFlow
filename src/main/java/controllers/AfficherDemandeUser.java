package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.demande;
import models.facture;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class AfficherDemandeUser {


    private Stage stage;
    private Scene scene;
    private Parent root;



    @FXML
    private VBox vboxDemandes; // Updated to reference the VBox in FXML



    private int demandeurId = 1;

    @FXML
    public void initialize() {
        loadFactures();
    }



    private void loadFactures() {
        String url = "jdbc:mysql://localhost:3306/gestfacturation";
        String user = "root";
        String password = "";

        String query = "SELECT * FROM demande WHERE demandeur_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, demandeurId);

            ResultSet rs = stmt.executeQuery();

            // Clear existing entries in the VBox
            vboxDemandes.getChildren().clear();

            // Create header row
            HBox headerRow = new HBox(50);
            headerRow.setStyle("-fx-background-color: #d2d2d2; -fx-padding: 10;");
            headerRow.getChildren().addAll(
                    new Label("ID"),
                    new Label("Type"),
                    new Label("Date"),
                    new Label("Statut"),
                    new Label("Description")

            );
            vboxDemandes.getChildren().add(headerRow);

            // Iterate through the result set and create rows
            while (rs.next()) {
                demande demande = new demande(

                        rs.getInt("id_demande"),
                        rs.getString("type"),
                        rs.getDate("date_demande"),
                        rs.getString("statut"),
                        rs.getString("description")


                );

                HBox row = new HBox(50);
                row.setStyle("-fx-padding: 10;");
                row.getChildren().addAll(
                        new Label(String.valueOf(demande.getId_demande())),
                        new Label(demande.getDescription()),
                        new Label(demande.getDate_demande().toString()),
                        new Label(demande.getStatut()),
                        new Label(String.valueOf(demande.getType()))

                );

                vboxDemandes.getChildren().add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    @FXML
    private void Retour(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/home.fxml"));
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