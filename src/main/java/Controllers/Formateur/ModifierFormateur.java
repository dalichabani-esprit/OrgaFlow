package Controllers.Formateur;

import Controllers.Formation.ModifierFormation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.Formateur;
import models.Formation;
import services.ServiceFormateur;
import services.ServiceFormation;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class ModifierFormateur implements Initializable {
    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField telephoneField;
    @FXML
    private TextField specialiteField;


    Preferences prefs = Preferences.userNodeForPackage(ModifierFormateur.class);
    int selectedFormateurId = prefs.getInt("selectedFormateurId", -1); //

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void ModifyData(Formateur formateur) {
        nomField.setText(formateur.getNom());
        prenomField.setText(formateur.getPrenom());
        emailField.setText(formateur.getEmail());
        telephoneField.setText(formateur.getTelephone());
        specialiteField.setText(formateur.getSpecialite());

    }
    @FXML
    private void modifierFormateur(ActionEvent event) {
        ServiceFormateur sf = new ServiceFormateur();

        // Récupération des valeurs et conversion des types
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String telephone =  telephoneField.getText();
        String specialite = specialiteField.getText();


        // Création de l'objet Formation avec les bons types
        Formateur formateurModifie = new Formateur();
        formateurModifie.setIdFormateur(selectedFormateurId);
        formateurModifie.setPrenom(prenom);
        formateurModifie.setNom(nom);
        formateurModifie.setEmail(email);
        formateurModifie.setTelephone(telephone);
        formateurModifie.setSpecialite(specialite);

System.out.println(formateurModifie.toString());
        // Mise à jour de la formation
        sf.update(formateurModifie);

        // Confirmation de la modification
        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
        confirmation.setContentText("Formateur modifiée avec succès !");
        confirmation.show();

    }
    @FXML
    private void SupprimerFormateur(ActionEvent event) {
        ServiceFormateur sf = new ServiceFormateur();
        int id = selectedFormateurId ;
        System.out.println(id+"id eli tfasakhhhh");
        sf.delete(id);
        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
        confirmation.setContentText("Formation  est Supprimee avec succes");
        confirmation.show();
    }
}
