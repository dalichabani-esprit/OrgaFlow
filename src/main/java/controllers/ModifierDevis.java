package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.devis;
import services.ServiceDevis;

import java.io.IOException;
import java.sql.Date;

public class ModifierDevis {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField idField;

    @FXML
    private TextField idDemandeField;

    @FXML
    private TextField montantEstimeField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField statutField;

    @FXML
    private Button modifierButton;

    @FXML
    private Button annulerButton;

    @FXML
    private DatePicker dateDevisPicker; // Declare the DatePicker
    private final ServiceDevis serviceDevis = new ServiceDevis();
    private devis devisAModifier;

    public void setDevis(devis d) {
        this.devisAModifier = d;
        if (d != null) {
            idField.setText(String.valueOf(d.getId_devis()));
            idDemandeField.setText(String.valueOf(d.getId_demande()));
            montantEstimeField.setText(String.valueOf(d.getMontant_estime()));
            datePicker.setValue(d.getDate_devis().toLocalDate());
            statutField.setText(d.getStatut());
        }
    }



    @FXML
    private void modifierDevis() {
        // Récupérer l'ID depuis le champ
        int idDevis;
        try {
            idDevis = Integer.parseInt(idField.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur: L'ID du devis doit être un nombre valide.");
            return;
        }

        // Récupérer les nouvelles valeurs depuis les champs
        int idDemande;
        try {
            idDemande = Integer.parseInt(idDemandeField.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur: L'ID de la demande doit être un nombre valide.");
            return;
        }

        float montantEstime;
        try {
            montantEstime = Float.parseFloat(montantEstimeField.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur: Le montant estimé doit être un nombre valide.");
            return;
        }

        // Vérifier si la date est sélectionnée
        if (datePicker.getValue() == null) {
            showAlert("Veuillez choisir une date.");
            return;
        }

        // Convertir LocalDate en java.sql.Date
        Date dateDevis = Date.valueOf(datePicker.getValue());

        // Créer un nouvel objet devis à mettre à jour
        devis updatedDevis = new devis();
        updatedDevis.setId_devis(idDevis); // Set the ID for the devis to be updated
        updatedDevis.setId_demande(idDemande);
        updatedDevis.setMontant_estime(montantEstime);
        updatedDevis.setDate_devis(dateDevis);
        updatedDevis.setStatut(statutField.getText());

        // Appeler la fonction de mise à jour
        try {
            serviceDevis.update(updatedDevis);
            showAlert("Devis mis à jour avec succès !");
        } catch (Exception e) {
            showAlert("Erreur lors de la mise à jour du devis : " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void fermerFenetre() {
        Stage stage = (Stage) annulerButton.getScene().getWindow();
        stage.close();
    }

    private void afficherAlerte(String titre, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
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