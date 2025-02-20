package tn.esprit.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Projet;
import tn.esprit.services.ServiceProjet;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProjetsController implements Initializable {
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfDesc;
    @FXML
    private TextField tfDateDebut;
    @FXML
    private TextField tfDateFin;
    @FXML
    private TextField tfStatut;
    @FXML
    private TextField tfId;
    @FXML
    private ListView<String> lvProjets;


    IService<Projet> sp = new ServiceProjet();
    //private Label lbPersonnes;

    @FXML
    public void ajouterProjet(ActionEvent actionEvent) {
        Projet p = new Projet(
                tfNom.getText(),
                tfDesc.getText(),
                tfDateDebut.getText(),
                tfDateFin.getText(),
                tfStatut.getText()
        );

        sp.add(p);
    }

    @FXML
    public void supprimerProjet(ActionEvent actionEvent) {
        Projet p = new Projet(Integer.parseInt(tfId.getText().toString()));
        sp.delete(p);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Projet> projets = sp.getAll();
        String[] projets_s = new String[projets.size()];

        for (int i = 0; i < projets.size(); i++) {
            projets_s[i] = projets.get(i).toString();
        }

        lvProjets.getItems().addAll(projets_s);

        lvProjets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String currentId = lvProjets.getSelectionModel().getSelectedItem();
                tfId.setText(currentId.split(" ")[1]);
            }
        });
    }


    @FXML
    public void afficherProjets(ActionEvent actionEvent) {
        lvProjets.getItems().clear();

        List<Projet> projets = sp.getAll();
        String[] projets_s = new String[projets.size()];

        for (int i = 0; i < projets.size(); i++) {
            projets_s[i] = projets.get(i).toString();
        }

        lvProjets.getItems().addAll(projets_s);

    }

}