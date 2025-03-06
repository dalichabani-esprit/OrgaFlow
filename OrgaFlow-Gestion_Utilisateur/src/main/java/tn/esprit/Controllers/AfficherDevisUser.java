package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.devis;

import java.io.IOException;
import java.sql.*;

public class AfficherDevisUser {


    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private TableView<devis> devisTable;

    @FXML
    private TableColumn<devis, Integer> idColumn;
    @FXML
    private TableColumn<devis, Double> montantColumn;
    @FXML
    private TableColumn<devis, Date> dateColumn;
    @FXML
    private TableColumn<devis, String> statutColumn;


    @FXML
    private VBox vboxDevis; // Updated to reference the VBox in FXML

    private int demandeID = 38;



    @FXML
    public void initialize() {
        loadFactures();
    }



    private void loadFactures() {
        String url = "jdbc:mysql://localhost:3306/orgaflowdb";
        String user = "root";
        String password = "";

        String query = "SELECT * FROM devis where id_demande=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, demandeID);

            ResultSet rs = stmt.executeQuery();

            // Clear existing entries in the VBox
            vboxDevis.getChildren().clear();

            // Create header row
            HBox headerRow = new HBox(50);
            headerRow.setStyle("-fx-background-color: #d2d2d2; -fx-padding: 10;");
            headerRow.getChildren().addAll(
                    new Label("Montant Estime"),
                    new Label("Date Devis"),
                    new Label("Statut")

            );
            vboxDevis.getChildren().add(headerRow);

            // Iterate through the result set and create rows
            while (rs.next()) {
                devis devis = new devis(
                        rs.getFloat("montant_estime"),
                        rs.getDate("date_devis"),
                        rs.getString("statut")



                );

                HBox row = new HBox(50);
                row.setStyle("-fx-padding: 10;");
                row.getChildren().addAll(
                        new Label(String.valueOf(devis.getMontant_estime())),
                        new Label(devis.getDate_devis().toString()),
                        new Label(devis.getStatut())

                );

                vboxDevis.getChildren().add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    @FXML
    private void Retour(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/UserCRUD.fxml"));
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