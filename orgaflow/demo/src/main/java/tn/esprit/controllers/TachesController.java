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
import tn.esprit.models.Tache;
import tn.esprit.services.ServiceProjet;
import tn.esprit.services.ServiceTache;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TachesController implements Initializable {
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
    private TextField tfIdProjet;

    @FXML
    private ListView<String> lvTaches;


    IService<Tache> st = new ServiceTache();
    //private Label lbPersonnes;

    @FXML
    public void ajouterTache(ActionEvent actionEvent) {
        Tache t = new Tache(
                tfNom.getText(),
                tfDesc.getText(),
                tfDateDebut.getText(),
                tfDateFin.getText(),
                tfStatut.getText(),
                Integer.parseInt(tfIdProjet.getText())
        );

        st.add(t);
    }

    @FXML
    public void supprimerTache(ActionEvent actionEvent) {
        Tache t = new Tache(Integer.parseInt(tfId.getText().toString()));
        st.delete(t);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Tache> taches = st.getAll();
        String[] taches_s = new String[taches.size()];

        for (int i = 0; i < taches.size(); i++) {
            taches_s[i] = taches.get(i).toString();
        }

        lvTaches.getItems().addAll(taches_s);

        lvTaches.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String currentId = lvTaches.getSelectionModel().getSelectedItem();
                tfId.setText(currentId.split(" ")[1]);
            }
        });
    }


    @FXML
    public void afficherTaches(ActionEvent actionEvent) {
        lvTaches.getItems().clear();

        List<Tache> taches = st.getAll();
        String[] taches_s = new String[taches.size()];

        for (int i = 0; i < taches.size(); i++) {
            taches_s[i] = taches.get(i).toString();
        }

        lvTaches.getItems().addAll(taches_s);

    }

}