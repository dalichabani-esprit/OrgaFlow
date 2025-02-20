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

public class ModifierTache{
    @FXML
    private TextField tfId;

    @FXML
    private TextField tfIdProjet;


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

    IService<Tache> st = new ServiceTache();


    @FXML
    public void modifierTache(ActionEvent actionEvent) {
        Tache t = new Tache(
                Integer.parseInt(tfId.getText()),
                tfNom.getText(),
                tfDesc.getText(),
                tfDateDebut.getText(),
                tfDateFin.getText(),
                tfStatut.getText(),
                Integer.parseInt(tfIdProjet.getText())
        );

        st.update(t);
    }


}