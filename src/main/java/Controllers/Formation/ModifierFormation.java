package Controllers.Formation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.Formation;
import services.ServiceFormation;

import java.sql.Date;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class ModifierFormation implements Initializable {
    @FXML
    private TextField nomField;

    @FXML
    private javafx.scene.control.TextField categorieField;

    @FXML
    private TextField dureeField;

    @FXML
    private DatePicker dateDebutPicker;

    @FXML
    private DatePicker dateFinPicker;

    @FXML
    private TextArea descriptionArea;

    private Formation formation;
    Preferences prefs = Preferences.userNodeForPackage(ModifierFormation.class);
    int selectedFormationId = prefs.getInt("selectedFormationId", -1); // -1 est la valeur par défaut si l'ID n'existe pas


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }


    void ModifyData(Formation formation) {
        this.formation = formation;
        nomField.setText(formation.getNom());
        categorieField.setText(formation.getCategorie());
        dureeField.setText(String.valueOf(formation.getDuree()));
        dateDebutPicker.setValue(formation.getDateDebut().toLocalDate());
        dateFinPicker.setValue(formation.getDateFin().toLocalDate());
        descriptionArea.setText(formation.getDescription());

    }

    @FXML
    private void modifierFormation(ActionEvent event) {
        ServiceFormation sf = new ServiceFormation();

        // Récupération des valeurs et conversion des types
        String nom = nomField.getText();
        String categorie = categorieField.getText();
        String description = descriptionArea.getText();
        int duree = Integer.parseInt(dureeField.getText());

        // Conversion des dates depuis DatePicker
        Date dateDebut = Date.valueOf(dateDebutPicker.getValue());
        Date dateFin = Date.valueOf(dateFinPicker.getValue());

        // Création de l'objet Formation avec les bons types
        Formation formationModifie = new Formation();
        formationModifie.setIdFormation(selectedFormationId);
        formationModifie.setNom(nom);
        formationModifie.setCategorie(categorie);
        formationModifie.setDuree(duree);
        formationModifie.setDateDebut(dateDebut);
        formationModifie.setDateFin(dateFin);
        formationModifie.setDescription(description);
        System.out.println(nom+"-----------------");
        // Mise à jour de la formation
        sf.update(formationModifie);

        // Confirmation de la modification
       Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
        confirmation.setContentText("Formation modifiée avec succès !");
        confirmation.show();

    }


    @FXML
    private void SupprimerFormation(ActionEvent event) {
        ServiceFormation sf = new ServiceFormation();
        int id = selectedFormationId ;
        sf.delete(id);
        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
        confirmation.setContentText("Formation  est Supprimee avec succes");
        confirmation.show();
    }
}
