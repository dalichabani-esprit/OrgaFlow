package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Formation;
import models.Formateur;
import services.ServiceFormation;
import services.ServiceFormateur;

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
    private TextField tfCategorie; // ✅ Remplacement du ComboBox par un TextField

    @FXML
    private ComboBox<Formateur> cbFormateur; // ✅ Stocker directement l'objet Formateur

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

        // ✅ Modifier l'affichage de la ComboBox pour afficher "Nom - Spécialité"
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
            String categorie = tfCategorie.getText(); // ✅ Récupération depuis TextField
            Formateur formateur = cbFormateur.getValue(); // ✅ Récupère l'objet Formateur sélectionné

            Formation formation = new Formation(0, nom, description, duree, Date.valueOf(dateDebut), Date.valueOf(dateFin), categorie, formateur);
            serviceFormation.add(formation);
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
