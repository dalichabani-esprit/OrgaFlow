package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Projet;
import tn.esprit.services.ServiceProjet;

import javafx.scene.control.DatePicker;


public class ModifierProjet{
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfDesc;
    @FXML
    private DatePicker dpDateDebut;
    @FXML
    private DatePicker dpDateFin;
    @FXML
    private TextField tfStatut;

    ServiceProjet sp = new ServiceProjet();
    //private Label lbPersonnes;

    @FXML
    public void modifierProjet(ActionEvent actionEvent) {
        int id = sp.getIdByNom(tfNom.getText());

        if (id != -1) {
            Projet p = new Projet(
                    id,
                    tfNom.getText(),
                    tfDesc.getText(),
                    dpDateDebut.getValue().toString(),
                    dpDateFin.getValue().toString(),
                    tfStatut.getText()
            );

            sp.update(p);

        }
    }
}