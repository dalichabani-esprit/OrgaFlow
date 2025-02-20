package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tn.esprit.models.Reclamation;
import tn.esprit.services.ServiceReclamation;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ReclamationsController implements Initializable {
    @FXML
    private TextField tfIdReclamation;

    @FXML
    private TextField tfIdContrat;

    @FXML
    private TextField tfSujet;

    @FXML
    private TextArea tfDesc;

    @FXML
    private DatePicker dateSoumissionPicker;

    @FXML
    private ComboBox<String> statutComboBox;

    @FXML
    private ListView<String> reclamationsListView;


    ServiceReclamation sr = new ServiceReclamation();

    private Date convertToDate(java.time.LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }
    private java.time.LocalDate convertToLocalDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        statutComboBox.getItems().addAll("En attente", "Rejetée", "Validée");


        List<Reclamation> reclamations = sr.getAll();
        String[] reclamations_s = new String[reclamations.size()];
        for (int i = 0; i < reclamations.size(); i++ ) {
            reclamations_s[i] = reclamations.get(i).toString();
        }
        reclamationsListView.getItems().addAll(reclamations_s);

    }

    @FXML
    public void handleAddButton() {
        Reclamation r = new Reclamation(
                tfSujet.getText(),
                Integer.parseInt(tfIdContrat.getText()),
                tfDesc.getText(),
                convertToDate(dateSoumissionPicker.getValue()),
                statutComboBox.getValue()
        );

        sr.add(r);
    }

    @FXML
    public void handleUpdateButton() {
        Reclamation r = new Reclamation(
                Integer.parseInt(tfIdReclamation.getText()),
                Integer.parseInt(tfIdContrat.getText()),
                tfSujet.getText(),
                tfDesc.getText(),
                convertToDate(dateSoumissionPicker.getValue()),
                statutComboBox.getValue()
        );

        sr.update(r);
    }

    @FXML
    public void handleDeleteButton() {
        int id = Integer.parseInt(tfIdReclamation.getText());
        Reclamation r = new Reclamation(id);
        sr.delete(r);
    }

    @FXML
    public void handleSearchButton() {}

    @FXML
    public void afficherReclamations() {
        reclamationsListView.getItems().clear();

        List<Reclamation> reclamations = sr.getAll();
        String[] reclamations_s = new String[reclamations.size()];
        for (int i = 0; i < reclamations.size(); i++ ) {
            reclamations_s[i] = reclamations.get(i).toString();
        }
        reclamationsListView.getItems().addAll(reclamations_s);

    }

}
