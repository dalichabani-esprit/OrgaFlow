package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import tn.esprit.models.Reclamation;
import tn.esprit.services.ServiceReclamation;
import tn.esprit.utils.ExcelExport;
import tn.esprit.utils.ExcelRec;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    @FXML
    private TextField searchSujetField;

    @FXML
    private ComboBox<String> searchStatutComboBox;
    @FXML
    private Label messageLabel;

    ServiceReclamation sr = new ServiceReclamation();

    private Date convertToDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

    private LocalDate convertToLocalDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statutComboBox.getItems().addAll("En attente", "Rejetée", "Validée");
        searchStatutComboBox.getItems().addAll("Tous", "En attente", "Rejetée", "Validée");
        searchStatutComboBox.setValue("Tous");

        afficherReclamations();
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
        afficherReclamations();
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
        afficherReclamations();
    }

    @FXML
    public void handleDeleteButton() {
        int id = Integer.parseInt(tfIdReclamation.getText());
        Reclamation r = new Reclamation(id);
        sr.delete(r);
        afficherReclamations();
    }

    @FXML
    public void afficherReclamations() {
        reclamationsListView.getItems().clear();

        List<Reclamation> reclamations = sr.getAll();

        // Trier par date de soumission
        reclamations.sort(Comparator.comparing(Reclamation::getDateSoumission));

        // Filtrer selon la recherche
        String searchSujet = searchSujetField.getText().toLowerCase();
        String searchStatut = searchStatutComboBox.getValue();

        List<String> filteredReclamations = reclamations.stream()
                .filter(r -> r.getSujet().toLowerCase().contains(searchSujet))
                .filter(r -> searchStatut.equals("Tous") || r.getStatut().equals(searchStatut))
                .map(Reclamation::toString)
                .collect(Collectors.toList());

        reclamationsListView.getItems().addAll(filteredReclamations);
    }

    @FXML
    public void handleSearchButton() {
        afficherReclamations();
    }
    @FXML
    private void handleExportToExcel() {

        try {
            List<Reclamation> reclamations = sr.getAll();

            /*
            String filePath = "reclamations_export.xlsx";
            ExcelRec.exportReclamationsToExcel(reclamations, filePath);

            messageLabel.setText("Exportation réussie : " + filePath);

             */
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(null);

            if (selectedDirectory != null) {
                // Construct the full path for the PDF file (in the selected directory)
                String dest = selectedDirectory.getAbsolutePath() + File.separator + "contats_export.xlsx";
                ExcelRec.exportReclamationsToExcel(reclamations, dest);
                messageLabel.setText("Exportation réussie : " + dest);

                //PdfGen.generateProjetPdf(dest);
            }
        } catch (Exception e) {
            messageLabel.setText("Erreur lors de l'exportation vers Excel.");
            e.printStackTrace();
        }
    }
}
