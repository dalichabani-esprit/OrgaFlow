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

public class ModifierProjet{
    @FXML
    private TextField tfId;

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

    IService<Projet> sp = new ServiceProjet();
    //private Label lbPersonnes;

    @FXML
    public void modifierProjet(ActionEvent actionEvent) {
        Projet p = new Projet(
                Integer.parseInt(tfId.getText()),
                tfNom.getText(),
                tfDesc.getText(),
                tfDateDebut.getText(),
                tfDateFin.getText(),
                tfStatut.getText()
        );
        

        sp.update(p);
    }


}