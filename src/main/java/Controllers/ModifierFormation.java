package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Formateur;
import models.Formation;
import services.ServiceFormation;
import java.sql.Date;

public class ModifierFormationController {

    @FXML
    private TextField nomField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField dureeField;
    @FXML
    private DatePicker dateDebutPicker;
    @FXML
    private DatePicker dateFinPicker;
    @FXML
    private ComboBox<String> categorieComboBox;
    @FXML
    private ComboBox<String> formateurComboBox;
    @FXML
    private Button updateButton;

    private ServiceFormation serviceFormation = new ServiceFormation();

    // Méthode pour initialiser le formulaire avec les données de la formation à modifier
    public void initialize(Formation formation) {
        nomField.setText(formation.getNom());
        descriptionField.setText(formation.getDescription());
        dureeField.setText(String.valueOf(formation.getDuree()));
        dateDebutPicker.setValue(formation.getDateDebut().toLocalDate());
        dateFinPicker.setValue(formation.getDateFin().toLocalDate());
        categorieComboBox.setValue(formation.getCategorie());
        formateurComboBox.setValue(formation.getFormateur().getNom());  // Par exemple
    }

    // Méthode qui est appelée lorsqu'on clique sur le bouton de mise à jour
    @FXML
    private void handleUpdateAction() {
        String nom = nomField.getText();
        String description = descriptionField.getText();
        int duree = Integer.parseInt(dureeField.getText());
        Date dateDebut = Date.valueOf(dateDebutPicker.getValue());
        Date dateFin = Date.valueOf(dateFinPicker.getValue());
        String categorie = categorieComboBox.getValue();
        // Il faut récupérer l'ID du formateur sélectionné dans le ComboBox (assure-toi de l'avoir peuplé avec les données des formateurs)
        Formateur formateur = new Formateur();  // Assure-toi de récupérer un objet Formateur valide

        Formation updatedFormation = new Formation(
                formation.getIdFormation(),  // ID de la formation que tu veux modifier
                nom, description, duree, dateDebut, dateFin, categorie, formateur
        );

        serviceFormation.update(updatedFormation);  // Appel à la méthode update pour mettre à jour dans la base de données
    }
}
