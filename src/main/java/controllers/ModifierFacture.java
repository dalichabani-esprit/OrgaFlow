package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.facture;
import services.ServiceFacture;

import java.io.IOException;
import java.sql.Date;

public class ModifierFacture {

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

    private final ServiceFacture serviceFacture = new ServiceFacture();
    private facture factureToModify;

    public void setFacture(facture f) {
        this.factureToModify = f;

        if (f != null) {
            idField.setText(String.valueOf(f.getId_facture()));
            idDevisField.setText(String.valueOf(f.getId_devis()));
            idDemandeField.setText(String.valueOf(f.getId_demande()));
            montantField.setText(String.valueOf(f.getMontant_final()));
            dateFacturePicker.setValue(f.getDate_facture().toLocalDate());
            statutField.setText(f.getStatut());
            destinataireIdField.setText(String.valueOf(f.getDestinataire_id()));
        }
    }

    @FXML
    private void modifierFacture() {
        // Récupérer l'ID de la facture depuis le champ
        int idFacture;
        try {
            idFacture = Integer.parseInt(idField.getText()); // Assuming you have a TextField for ID
        } catch (NumberFormatException e) {
            showAlert("Erreur: L'ID de la facture doit être un nombre valide.");
            return;
        }

        // Récupérer les nouvelles valeurs depuis les champs
        int idDevis;
        try {
            idDevis = Integer.parseInt(idDevisField.getText()); // ID Devis
        } catch (NumberFormatException e) {
            showAlert("Erreur: L'ID du devis doit être un nombre valide.");
            return;
        }

        int idDemande;
        try {
            idDemande = Integer.parseInt(idDemandeField.getText()); // ID Demande
        } catch (NumberFormatException e) {
            showAlert("Erreur: L'ID de la demande doit être un nombre valide.");
            return;
        }

        float montantFinal;
        try {
            montantFinal = Float.parseFloat(montantField.getText()); // Montant
        } catch (NumberFormatException e) {
            showAlert("Erreur: Le montant final doit être un nombre valide.");
            return;
        }

        // Vérifier si la date est sélectionnée
        if (dateFacturePicker.getValue() == null) { // DatePicker
            showAlert("Veuillez choisir une date.");
            return;
        }

        // Convertir LocalDate en java.sql.Date
        Date dateFacture = Date.valueOf(dateFacturePicker.getValue());

        String statut = statutField.getText(); // Statut
        int destinataireId;
        try {
            destinataireId = Integer.parseInt(destinataireIdField.getText()); // ID Destinataire
        } catch (NumberFormatException e) {
            showAlert("Erreur: L'ID du destinataire doit être un nombre valide.");
            return;
        }

        // Créer un nouvel objet facture à mettre à jour
        facture updatedFacture = new facture();
        updatedFacture.setId_facture(idFacture); // Set the ID for the facture to be updated
        updatedFacture.setId_devis(idDevis);
        updatedFacture.setId_demande(idDemande);
        updatedFacture.setMontant_final(montantFinal);
        updatedFacture.setDate_facture(dateFacture);
        updatedFacture.setStatut(statut);
        updatedFacture.setDestinataire_id(destinataireId);

        // Appeler la fonction de mise à jour
        try {
            serviceFacture.update(updatedFacture); // Call the update method from the service
            showAlert("Facture mise à jour avec succès !");
        } catch (Exception e) {
            showAlert("Erreur lors de la mise à jour de la facture : " + e.getMessage());
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
        Stage stage = (Stage) idField.getScene().getWindow();
        stage.close();
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
