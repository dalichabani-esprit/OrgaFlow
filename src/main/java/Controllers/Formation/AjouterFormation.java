package Controllers.Formation;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Formation;
import models.Formateur;
import services.ServiceFormation;
import services.ServiceFormateur;
import utils.EmailAPI;
import utils.SmsApi;

import java.sql.Date;
import java.time.LocalDate;

public class AjouterFormation {

    @FXML
    private TextField tfNom;

    @FXML
    private TextArea tfDescription;

    @FXML
    private TextField tfDuree;

    @FXML
    private DatePicker dpDateDebut;

    @FXML
    private DatePicker dpDateFin;

    @FXML
    private TextField tfCategorie;

    @FXML
    private ComboBox<Formateur> cbFormateur;

    @FXML
    private Button btnAjouter;

    private final ServiceFormation serviceFormation = new ServiceFormation();
    private final ServiceFormateur serviceFormateur = new ServiceFormateur();

    @FXML
    public void initialize() {
        loadFormateurs();
    }

    private void loadFormateurs() {
        for (Formateur formateur : serviceFormateur.getAll()) {
            cbFormateur.getItems().add(formateur);
        }


        cbFormateur.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Formateur formateur, boolean empty) {
                super.updateItem(formateur, empty);
                if (empty || formateur == null) {
                    setText(null);
                } else {
                    setText(formateur.getNom() + " - " + formateur.getSpecialite());
                }
            }
        });

        cbFormateur.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Formateur formateur, boolean empty) {
                super.updateItem(formateur, empty);
                if (empty || formateur == null) {
                    setText(null);
                } else {
                    setText(formateur.getNom() + " - " + formateur.getSpecialite());
                }
            }
        });
    }

    @FXML
    private void ajouterFormation() {
        try {
            if (tfNom.getText().isEmpty() || tfDescription.getText().isEmpty() || tfDuree.getText().isEmpty() ||
                    dpDateDebut.getValue() == null || dpDateFin.getValue() == null || tfCategorie.getText().isEmpty() ||
                    cbFormateur.getValue() == null) {
                showAlert("Erreur", "Veuillez remplir tous les champs !");
                return;
            }

            String nom = tfNom.getText();
            String description = tfDescription.getText();
            int duree = Integer.parseInt(tfDuree.getText());
            LocalDate dateDebut = dpDateDebut.getValue();
            LocalDate dateFin = dpDateFin.getValue();
            String categorie = tfCategorie.getText();
            Formateur formateur = cbFormateur.getValue();

            Formation formation = new Formation(0, nom, description, duree, Date.valueOf(dateDebut), Date.valueOf(dateFin), categorie, formateur);
            serviceFormation.add(formation);
            /*------------------------Envoyer QR code dans un email--------------------*/
            EmailAPI e = new EmailAPI();
            e.sendMail(formation);
            /*------------------------Envoyer QR code dans un email--------------------*/

           SmsApi.sendSMS();

            showAlert("Succès", "Formation ajoutée avec succès !");
            clearFields();

        } catch (NumberFormatException e) {
            showAlert("Erreur", "Durée invalide !");
        }
    }

    private void clearFields() {
        tfNom.clear();
        tfDescription.clear();
        tfDuree.clear();
        dpDateDebut.setValue(null);
        dpDateFin.setValue(null);
        tfCategorie.clear();
        cbFormateur.setValue(null);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
