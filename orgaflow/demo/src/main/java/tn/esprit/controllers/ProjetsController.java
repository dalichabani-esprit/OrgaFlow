package tn.esprit.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.esprit.models.Projet;
import tn.esprit.services.ServiceProjet;

import javafx.scene.control.DatePicker;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProjetsController implements Initializable {
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfDesc;
    @FXML
    private TextField tfStatut;
    @FXML
    private TextField tfNomDel;


    @FXML
    private ListView<String> lvProjets;

    @FXML
    private DatePicker dpDateDebut;
    @FXML
    private DatePicker dpDateFin;

    @FXML
    private ChoiceBox<String> choiceBoxTri;

    private String[] criterias = {"nom", "description", "date_debut", "date_fin", "statut"};

    ServiceProjet sp = new ServiceProjet();

    @FXML
    public void ajouterProjet(ActionEvent actionEvent) {
        Projet p = new Projet(
                tfNom.getText(),
                tfDesc.getText(),
                dpDateDebut.getValue().toString(),
                dpDateFin.getValue().toString(),
                tfStatut.getText()
        );

        sp.add(p);

        fillLvProjets();
    }

    @FXML
    public void supprimerProjet(ActionEvent actionEvent) {
        ServiceProjet sp2 = new ServiceProjet();
        int id = sp.getIdByNom(tfNomDel.getText());

        if (id != -1) {
            Projet p = new Projet(id);
            sp.delete(p);
            fillLvProjets();
        }
    }

    @FXML
    public void trierLvProjets() {
        String criteria = choiceBoxTri.getValue();
        List<Projet> projets = sp.getAllSort(criteria);

        String[] projets_s = new String[projets.size()];

        for (int i = 0; i < projets.size(); i++) {
            projets_s[i] = projets.get(i).toString();
        }

        lvProjets.getItems().clear();
        lvProjets.getItems().addAll(projets_s);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillLvProjets();
        
        choiceBoxTri.getItems().addAll(criterias);

        lvProjets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String current = lvProjets.getSelectionModel().getSelectedItem();
                tfNomDel.setText(current.split("\n")[0]);
            }
        });
    }


    @FXML
    public void fillLvProjets() {
        lvProjets.getItems().clear();

        List<Projet> projets = sp.getAll();
        String[] projets_s = new String[projets.size()];

        for (int i = 0; i < projets.size(); i++) {
            projets_s[i] = projets.get(i).toString();
        }

        lvProjets.getItems().addAll(projets_s);

    }

}