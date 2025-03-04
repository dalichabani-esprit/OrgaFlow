package tn.esprit.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.stage.DirectoryChooser;
import tn.esprit.models.Tache;
import tn.esprit.services.ServiceTache;

import javafx.scene.control.DatePicker;
import tn.esprit.utils.PdfGen;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TachesController implements Initializable {
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfNomProjet;
    @FXML
    private TextField tfDesc;
    @FXML
    private TextField tfStatut;
    @FXML
    private TextField tfNomDel;
    @FXML
    private DatePicker dpDateDebut;
    @FXML
    private DatePicker dpDateFin;

    @FXML
    private ChoiceBox<String> choiceBoxTri;

    @FXML
    private ListView<String> lvTaches;

    private String[] criterias = {"nom", "description", "date_debut", "date_fin", "statut"};

    ServiceTache st = new ServiceTache();

    @FXML
    public void ajouterTache(ActionEvent actionEvent) {
        int idProjet = st.getIdProjetByNom(tfNomProjet.getText());
        if (idProjet != -1) {
            Tache t = new Tache(
                    tfNom.getText(),
                    tfDesc.getText(),
                    dpDateDebut.getValue().toString(),
                    dpDateFin.getValue().toString(),
                    tfStatut.getText(),
                    idProjet
            );

            st.add(t);
            fillLvTaches();
        }
    }

    @FXML
    public void supprimerTache(ActionEvent actionEvent) {
        int id = st.getIdByNom(tfNomDel.getText());
        if (id != -1) {
            Tache t = new Tache(id);
            st.delete(t);
            fillLvTaches();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fillLvTaches();

        choiceBoxTri.getItems().addAll(criterias);

        lvTaches.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String current = lvTaches.getSelectionModel().getSelectedItem();
                tfNomDel.setText(current.split("\n")[0]);
            }
        });
    }


    @FXML
    public void fillLvTaches() {
        lvTaches.getItems().clear();

        List<Tache> taches = st.getAll();
        String[] taches_s = new String[taches.size()];

        for (int i = 0; i < taches.size(); i++) {
            taches_s[i] = taches.get(i).toString();
        }

        lvTaches.getItems().addAll(taches_s);

    }

    @FXML
    public void trierLvTaches() {
        String criteria = choiceBoxTri.getValue();
        List<Tache> taches = st.getAllSort(criteria);
        String[] taches_s = new String[taches.size()];

        for (int i = 0; i < taches.size(); i++) {
            taches_s[i] = taches.get(i).toString();
        }

        lvTaches.getItems().clear();
        lvTaches.getItems().addAll(taches_s);
    }

    @FXML
    public void exportPDF(ActionEvent event) throws FileNotFoundException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            // Construct the full path for the PDF file (in the selected directory)
            String dest = selectedDirectory.getAbsolutePath() + File.separator + "taches.pdf";
            PdfGen.generateTachePdf(dest);
        }
    }

}