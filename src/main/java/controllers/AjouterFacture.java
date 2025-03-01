package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.facture;
import services.ServiceFacture;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

public class AjouterFacture {


    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField idField;

    @FXML
    private TextField idDevisField;

    @FXML
    private TextField idDemandeField;

    @FXML
    private TextField montantField;

    @FXML
    private DatePicker dateFacturePicker;

    @FXML
    private TextField statutField;

    @FXML
    private TextField destinataireIdField;

    private ServiceFacture serviceFacture = new ServiceFacture();

    @FXML
    private void ajouterFacture() {
        try {
            // Récupérer les valeurs des champs
            String idText = idField.getText();
            String idDevisText = idDevisField.getText();
            String idDemandeText = idDemandeField.getText();
            String montantText = montantField.getText();
            // Convertir DatePicker en java.sql.Date
            Date dateFacture = new java.sql.Date(dateFacturePicker.getValue().atStartOfDay().toEpochSecond(java.time.ZoneOffset.UTC) * 1000);
            String statut = statutField.getText();
            String destinataireIdText = destinataireIdField.getText();

            // Vérifier si tous les champs sont remplis
            if (idText.isEmpty() || idDevisText.isEmpty() || idDemandeText.isEmpty() || montantText.isEmpty() ||
                    dateFacture == null || statut.isEmpty() || destinataireIdText.isEmpty()) {
                showAlert("Erreur", "Veuillez remplir tous les champs.");
                return;
            }

            // Convertir les ID et Montant en entier et float
            int id = Integer.parseInt(idText);
            int idDevis = Integer.parseInt(idDevisText);
            int idDemande = Integer.parseInt(idDemandeText);
            float montant = Float.parseFloat(montantText);
            int destinataireId = Integer.parseInt(destinataireIdText);

            // Créer une nouvelle facture et l'ajouter via le service
            facture fact = new facture(id, idDevis, idDemande, montant, dateFacture, statut, destinataireId);
            serviceFacture.add(fact);

            // Réinitialiser les champs après l'ajout
            idField.clear();
            idDevisField.clear();
            idDemandeField.clear();
            montantField.clear();
            dateFacturePicker.setValue(null);
            statutField.clear();
            destinataireIdField.clear();

            // Afficher un message de succès
            showAlert("Succès", "Facture ajoutée avec succès !");
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Les IDs et Montant doivent être des valeurs numériques valides.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
