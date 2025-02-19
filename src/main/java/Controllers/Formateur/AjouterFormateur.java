package Controllers.Formateur;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import models.Formateur;
import services.ServiceFormateur;

public class AjouterFormateur {

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

    private ServiceFormateur serviceFormateur = new ServiceFormateur();

    @FXML
    private void ajouterFormateur() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String telephone = telephoneField.getText();
        String specialite = specialiteField.getText();

        // Vérifier si les champs sont valides
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || telephone.isEmpty() || specialite.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        // Créer un formateur et ajouter via le service
        Formateur formateur = new Formateur(0, nom, prenom, email, telephone, specialite);
        serviceFormateur.add(formateur);

        // Réinitialiser les champs après l'ajout
        nomField.clear();
        prenomField.clear();
        emailField.clear();
        telephoneField.clear();
        specialiteField.clear();

        // Afficher un message de succès
        showAlert("Succès", "Formateur ajouté avec succès !");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
