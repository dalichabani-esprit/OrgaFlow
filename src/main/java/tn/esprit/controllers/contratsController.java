package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tn.esprit.models.Contrat;
import tn.esprit.services.ServiceContrat;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Comparator;

import tn.esprit.utils.ExcelExport;
import tn.esprit.utils.QrCodeGen;

import javafx.collections.FXCollections;

public class contratsController implements Initializable {
    @FXML private ComboBox<String> typeComboBox;
    @FXML private DatePicker dateDebutPicker;
    @FXML private DatePicker dateFinPicker;
    @FXML private CheckBox periodeEssaiCheckBox;
    @FXML private CheckBox renouvelableCheckBox;
    @FXML private TextField salaireField;
    @FXML private ComboBox<String> statutComboBox;
    @FXML private Label messageLabel;
    @FXML private ListView<Contrat> contratsListView;
    @FXML private TextField searchField;

    private List<Contrat> contratsList;  // Stocker la liste complète des contrats
    private final ServiceContrat sc = new ServiceContrat();
    private Contrat selectedContrat;  // Contrat sélectionné dans la liste

    public contratsController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialiser les ComboBox
        typeComboBox.getItems().addAll("CDI", "CDD", "Stage", "Alternance");
        statutComboBox.getItems().addAll("Actif", "Terminé", "Renouvelé");

        // Charger les contrats depuis le service
        chargerContrats();

        // Écouteur pour la sélection d'un contrat dans la ListView
        contratsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedContrat = newSelection;
                remplirFormulaire(newSelection);
            }
        });
    }

    private void chargerContrats() {
        contratsList = sc.getAll();  // Récupérer les contrats depuis le service
        refreshContratsList();       // Rafraîchir la ListView
    }

    private void refreshContratsList() {
        contratsListView.getItems().clear();
        contratsListView.getItems().addAll(contratsList);
    }

    private void remplirFormulaire(Contrat contrat) {
        typeComboBox.setValue(contrat.getTypeContrat());
        dateDebutPicker.setValue(convertToLocalDate(contrat.getDateDebut()));
        dateFinPicker.setValue(convertToLocalDate(contrat.getDateFin()));
        periodeEssaiCheckBox.setSelected(contrat.isPeriodeEssai());
        renouvelableCheckBox.setSelected(contrat.isRenouvelable());
        salaireField.setText(String.valueOf(contrat.getSalaire()));
        statutComboBox.setValue(contrat.getStatut());
    }

    @FXML
    private void handleAddButton() {
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

            sc.add(contrat);  // Ajouter le contrat via le service
            contratsList.add(contrat);  // Ajouter le contrat à la liste
            refreshContratsList();  // Rafraîchir la ListView
            messageLabel.setText("Contrat ajouté avec succès !");
        } catch (Exception e) {
            messageLabel.setText("Erreur lors de l'ajout du contrat.");
        }
    }

    @FXML
    private void handleUpdateButton() {
        if (selectedContrat != null) {
            try {
                selectedContrat.setTypeContrat(typeComboBox.getValue());
                selectedContrat.setDateDebut(convertToDate(dateDebutPicker.getValue()));
                selectedContrat.setDateFin(convertToDate(dateFinPicker.getValue()));
                selectedContrat.setPeriodeEssai(periodeEssaiCheckBox.isSelected());
                selectedContrat.setRenouvelable(renouvelableCheckBox.isSelected());
                selectedContrat.setSalaire(Double.parseDouble(salaireField.getText()));
                selectedContrat.setStatut(statutComboBox.getValue());

                sc.update(selectedContrat);  // Mettre à jour le contrat via le service
                refreshContratsList();  // Rafraîchir la ListView
                messageLabel.setText("Contrat mis à jour avec succès !");
            } catch (Exception e) {
                messageLabel.setText("Erreur lors de la mise à jour du contrat.");
            }
        } else {
            messageLabel.setText("Aucun contrat sélectionné.");
        }
    }

    @FXML
    private void handleDeleteButton() {
        if (selectedContrat != null) {
            try {
                sc.delete(selectedContrat);  // Supprimer le contrat via le service
                contratsList.remove(selectedContrat);  // Supprimer le contrat de la liste
                refreshContratsList();  // Rafraîchir la ListView
                messageLabel.setText("Contrat supprimé avec succès !");
            } catch (Exception e) {
                messageLabel.setText("Erreur lors de la suppression du contrat.");
            }
        } else {
            messageLabel.setText("Aucun contrat sélectionné.");
        }
    }

    @FXML
    private void handleAddReclamationButton() {
        if (selectedContrat != null) {
            // Rediriger vers l'interface de gestion des réclamations avec l'ID du contrat sélectionné
            // Vous pouvez utiliser un mécanisme de navigation ou une nouvelle fenêtre
            System.out.println("Ajouter une réclamation pour le contrat : " + selectedContrat.getIdContrat());
        } else {
            messageLabel.setText("Aucun contrat sélectionné.");
        }
    }

    @FXML
    private void rechercherContrat() {
        String searchText = searchField.getText().toLowerCase();

        List<Contrat> filteredList = contratsList.stream()
                .filter(c -> c.getTypeContrat().toLowerCase().contains(searchText) ||
                        String.valueOf(c.getSalaire()).contains(searchText))
                .toList();

        // Mettre à jour la ListView avec les résultats filtrés
        contratsListView.setItems(FXCollections.observableArrayList(filteredList));
    }

    @FXML
    private void trierContratsParType() {
        List<Contrat> sortedList = contratsList.stream()
                .sorted(Comparator.comparing(Contrat::getTypeContrat))  // Trier par type de contrat
                .toList();

        // Mettre à jour la ListView avec les résultats triés
        contratsListView.setItems(FXCollections.observableArrayList(sortedList));
    }

    @FXML
    private void trierContratsParDateDebut() {
        List<Contrat> sortedList = contratsList.stream()
                .sorted(Comparator.comparing(Contrat::getDateDebut))  // Trier par date de début
                .toList();

        // Mettre à jour la ListView avec les résultats triés
        contratsListView.setItems(FXCollections.observableArrayList(sortedList));
    }

    private Date convertToDate(java.time.LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

    private java.time.LocalDate convertToLocalDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    @FXML
    public void generateQrCode() throws IOException {
        QrCodeGen.GenerateQrCode();
    }
    @FXML
    private void handleExportToExcel() {
        try {
            List<Contrat> contrats = sc.getAll();
            String filePath = "contrats_export.xlsx";
            ExcelExport.exportContratsToExcel(contrats, filePath);

            messageLabel.setText("Exportation réussie : " + filePath);
        } catch (Exception e) {
            messageLabel.setText("Erreur lors de l'exportation vers Excel.");
            e.printStackTrace();
        }
    }
}