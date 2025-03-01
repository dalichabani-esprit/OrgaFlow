package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.demande;
import services.ServiceDemande;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.sql.Date;
import java.time.LocalDate;
public class ModifierDemande {


    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private TextField idField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField demandeurIdField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField statutField;
    @FXML
    private Button modifierButton;
    @FXML
    private Button annulerButton;

    private final ServiceDemande serviceDemande = new ServiceDemande();
    private demande demandeToEdit;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void setDemandeData(demande d) {
        this.demandeToEdit = d;

        idField.setText(String.valueOf(d.getId_demande()));
        typeField.setText(d.getType());
        descriptionField.setText(d.getDescription());
        demandeurIdField.setText(String.valueOf(d.getDemandeur_id()));
        statutField.setText(d.getStatut());

        if (d.getDate_demande() != null) {
            datePicker.setValue(d.getDate_demande().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        }
    }

    @FXML
    private void modifierDemande() {
        // Récupérer l'ID depuis le champ
        int idDemande;
        try {
            idDemande = Integer.parseInt(idField.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur: L'ID de la demande doit être un nombre valide.");
            return;
        }

        // Récupérer les nouvelles valeurs depuis les champs
        String type = typeField.getText();
        String description = descriptionField.getText();
        String statut = statutField.getText();
        int demandeurId;

        try {
            demandeurId = Integer.parseInt(demandeurIdField.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur: L'ID du demandeur doit être un nombre.");
            return;
        }

        if (datePicker.getValue() == null) {
            showAlert("Veuillez choisir une date.");
            return;
        }

        // Convertir LocalDate en java.sql.Date
        Date dateDemande = Date.valueOf(datePicker.getValue());

        // Créer un nouvel objet demande à mettre à jour
        demande updatedDemande = new demande();
        updatedDemande.setId_demande(idDemande); // Set the ID for the demande to be updated
        updatedDemande.setType(type);
        updatedDemande.setDescription(description);
        updatedDemande.setDemandeur_id(demandeurId);
        updatedDemande.setDate_demande(dateDemande);
        updatedDemande.setStatut(statut);

        // Appeler la fonction de mise à jour
        try {
            serviceDemande.update(updatedDemande);
            showAlert("Demande mise à jour avec succès !");
        } catch (Exception e) {
            showAlert("Erreur lors de la mise à jour de la demande : " + e.getMessage());
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
