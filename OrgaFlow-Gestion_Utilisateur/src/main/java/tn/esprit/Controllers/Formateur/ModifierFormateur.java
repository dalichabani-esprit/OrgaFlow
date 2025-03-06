package tn.esprit.Controllers.Formateur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.models.Formateur;
import tn.esprit.services.ServiceFormateur;

import java.net.URL;
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

        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String telephone =  telephoneField.getText();
        String specialite = specialiteField.getText();


        Formateur formateurModifie = new Formateur();
        formateurModifie.setIdFormateur(selectedFormateurId);
        formateurModifie.setPrenom(prenom);
        formateurModifie.setNom(nom);
        formateurModifie.setEmail(email);
        formateurModifie.setTelephone(telephone);
        formateurModifie.setSpecialite(specialite);

System.out.println(formateurModifie.toString());
        sf.update(formateurModifie);

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
