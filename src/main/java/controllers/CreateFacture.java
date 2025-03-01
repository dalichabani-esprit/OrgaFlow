package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.devis;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreateFacture {



    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ComboBox<String> comboBoxDemande;


    @FXML
    private ComboBox<String> comboBoxDevis;


    @FXML
    private ComboBox<String> comboBoxDestinataire;


    @FXML
    private TextField montant;

    @FXML
    private TextField statutField;

    @FXML
    private Button submitButton;
    @FXML
    private ComboBox<String> statutComboBox; // Updated to ComboBox
    @FXML
    private VBox vboxDevis; // Updated to reference the VBox in FXML

    private static final String DB_URL = "jdbc:mysql://localhost:3306/gestfacturation"; // Remplacez par votre URL
    private static final String USER = "root"; // Remplacez par votre utilisateur
    private static final String PASS = ""; // Remplacez par votre mot de passe

    @FXML
    public void initialize() {
        // Charger les IDs des devis dans la ComboBox
        List<String> demandeIds = fetchDemandeIds();
        comboBoxDemande.getItems().addAll(demandeIds);

        List<String> devisIds = fetchDevisIds();
        comboBoxDevis.getItems().addAll(devisIds);

        List<String> destinataireIds = fetchDestinataireIds();
        comboBoxDestinataire.getItems().addAll(destinataireIds);
        loadFactures();

    }

    private List<String> fetchDemandeIds() {
        List<String> demandeIds = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id_demande FROM demande")) { // Remplacez "demandes" par votre table

            while (rs.next()) {
                demandeIds.add(rs.getString("id_demande"));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Gérer les exceptions de manière appropriée
        }

        return demandeIds;
    }





    private List<String> fetchDevisIds() {
        List<String> devisIds = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id_devis FROM devis")) { // Remplacez "demandes" par votre table

            while (rs.next()) {
                devisIds.add(rs.getString("id_devis"));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Gérer les exceptions de manière appropriée
        }

        return devisIds;
    }



    private List<String> fetchDestinataireIds() {
        List<String> devisIds = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id_devis FROM devis")) { // Remplacez "demandes" par votre table

            while (rs.next()) {
                devisIds.add(rs.getString("id_devis"));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Gérer les exceptions de manière appropriée
        }

        return devisIds;
    }





    @FXML
    private void handleSubmit() {
        String selectedDemandeId = comboBoxDemande.getValue();
        String selectedDevisId = comboBoxDevis.getValue();
        String selectedDestinataireId = comboBoxDestinataire.getValue();
        String montantFinal = montant.getText();
        String statut = statutComboBox.getValue(); // Get selected value from ComboBox

        // Obtenir la date système
        LocalDate dateFacture = LocalDate.now(); // Date actuelle
        Date sqlDate = Date.valueOf(dateFacture); // Convertir en java.sql.Date



        // Logique pour insérer les données dans la base de données
        String insertSQL = "INSERT INTO facture (id_demande, id_devis, montant_final, statut, date_facture, destinataire_id) VALUES (?, ?, ?, ?, ?, ?)"; // Assurez-vous que la table et les colonnes sont correctes

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, selectedDemandeId);
            pstmt.setString(2, selectedDevisId);

            pstmt.setString(3, montantFinal);
            pstmt.setString(4, statut);
            pstmt.setDate(5, sqlDate); // Ajouter la date
            pstmt.setString(6, selectedDestinataireId);


            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Données de la facture insérées avec succès !");
            } else {
                System.out.println("Aucune donnée insérée.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Gérer les exceptions de manière appropriée
        }
    }


    @FXML
    private void Retour(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AfficherDemande.fxml"));
        switchScene(event, root);
    }

    // Méthode auxiliaire pour changer de scène
    private void switchScene(ActionEvent event, Parent root) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    private void loadFactures() {
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
                    new Label("ID"),
                    new Label("Id Demande"),
                    new Label("Montant Estime"),
                    new Label("Date Devis"),
                    new Label("Statut")

            );
            vboxDevis.getChildren().add(headerRow);

            // Iterate through the result set and create rows
            while (rs.next()) {
                devis devis = new devis(

                        rs.getInt("id_devis"),
                        rs.getInt("id_demande"),
                        rs.getFloat("montant_estime"),
                        rs.getDate("date_devis"),
                        rs.getString("statut")


                );

                HBox row = new HBox(50);
                row.setStyle("-fx-padding: 10;");
                row.getChildren().addAll(
                        new Label(String.valueOf(devis.getId_devis())),
                        new Label(String.valueOf(devis.getId_demande())),
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


}