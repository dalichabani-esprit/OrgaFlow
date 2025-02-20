package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tn.esprit.models.Contrat;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import tn.esprit.services.ServiceContrat;

public class ContratsController implements Initializable {
    @FXML private TextField idField;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private DatePicker dateDebutPicker;
    @FXML private DatePicker dateFinPicker;
    @FXML private CheckBox periodeEssaiCheckBox;
    @FXML private CheckBox renouvelableCheckBox;
    @FXML private TextField salaireField;
    @FXML private ComboBox<String> statutComboBox;
    @FXML private Label messageLabel;
    @FXML private ListView<String> contratsListView;

    ServiceContrat sc = new ServiceContrat();

    private List<Contrat> contrats = new ArrayList<>();

    @FXML
    private void initialize() {
        typeComboBox.getItems().addAll("CDI", "CDD", "Stage", "Alternance");
        statutComboBox.getItems().addAll("Actif", "Expiré", "En attente");
        refreshContratsList();
    }

    @FXML
    private void handleAddButton(ActionEvent event) {
        try {
            Contrat contrat = new Contrat(
                    typeComboBox.getValue(),
                    convertToDate(dateDebutPicker.getValue()),
                    convertToDate(dateFinPicker.getValue()),
                    periodeEssaiCheckBox.isSelected(),
                    renouvelableCheckBox.isSelected(),
                    Double.parseDouble(salaireField.getText()),
                    statutComboBox.getValue()
            );
            //System.out.println(contrat.toString());
            sc.add(contrat);

            contrats.add(contrat);
            refreshContratsList();
            messageLabel.setText("Contrat ajouté avec succès !");
        } catch (Exception e) {
            messageLabel.setText("Erreur lors de l'ajout du contrat.");
        }
    }

    @FXML
    private void handleUpdateButton() {
        Contrat contrat = new Contrat(
                Integer.parseInt(idField.getText()),
                typeComboBox.getValue(),
                convertToDate(dateDebutPicker.getValue()),
                convertToDate(dateFinPicker.getValue()),
                periodeEssaiCheckBox.isSelected(),
                renouvelableCheckBox.isSelected(),
                Double.parseDouble(salaireField.getText()),
                statutComboBox.getValue()
        );

        sc.update(contrat);
    }

    @FXML
    private void handleDeleteButton() {
        try {
            int id = Integer.parseInt(idField.getText());
            contrats.removeIf(c -> c.getIdContrat() == id);
            refreshContratsList();
            messageLabel.setText("Contrat supprimé avec succès !");

            Contrat c = new Contrat(id);
            sc.delete(c);

        } catch (Exception e) {
            messageLabel.setText("Erreur lors de la suppression du contrat.");
        }
    }
    @FXML
    private void handleSearchButton() {
        try {
            int id = Integer.parseInt(idField.getText());
            Contrat contrat = contrats.stream()
                    .filter(c -> c.getIdContrat() == id)
                    .findFirst()
                    .orElse(null);

            if (contrat != null) {
                typeComboBox.setValue(contrat.getTypeContrat());
                dateDebutPicker.setValue(convertToLocalDate(contrat.getDateDebut()));
                dateFinPicker.setValue(convertToLocalDate(contrat.getDateFin()));
                periodeEssaiCheckBox.setSelected(contrat.isPeriodeEssai());
                renouvelableCheckBox.setSelected(contrat.isRenouvelable());
                salaireField.setText(String.valueOf(contrat.getSalaire()));
                statutComboBox.setValue(contrat.getStatut());
                messageLabel.setText("Contrat trouvé !");
            } else {
                messageLabel.setText("Aucun contrat trouvé avec cet ID.");
            }
        } catch (Exception e) {
            messageLabel.setText("Erreur lors de la recherche du contrat.");
        }
    }
    private void refreshContratsList() {
        contratsListView.getItems().clear();
        for (Contrat contrat : contrats) {
            contratsListView.getItems().add(contrat.toString());
        }
    }
    private Date convertToDate(java.time.LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }
    private java.time.LocalDate convertToLocalDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeComboBox.getItems().addAll("CDI", "CDD", "Stage", "Alternance");
        statutComboBox.getItems().addAll("Actif", "Expiré", "En attente");
        refreshContratsList();

        List<Contrat> contrats = sc.getAll();
        String[] contrats_s = new String[contrats.size()];
        for (int i = 0; i < contrats.size(); i++ ) {
            contrats_s[i] = contrats.get(i).toString();
        }
        contratsListView.getItems().addAll(contrats_s);
    }

    @FXML
    public void afficherContrats(ActionEvent event) {
        contratsListView.getItems().clear();

        List<Contrat> contrats = sc.getAll();
        String[] contrats_s = new String[contrats.size()];
        for (int i = 0; i < contrats.size(); i++ ) {
            contrats_s[i] = contrats.get(i).toString();
        }
        contratsListView.getItems().addAll(contrats_s);

    }
}