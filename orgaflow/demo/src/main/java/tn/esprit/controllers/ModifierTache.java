package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Tache;
import tn.esprit.services.ServiceTache;
import javafx.scene.control.DatePicker;


public class ModifierTache{
    @FXML
    private TextField tfNomProjet;


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

    ServiceTache st = new ServiceTache();


    @FXML
    public void modifierTache(ActionEvent actionEvent) {
        int id = st.getIdByNom(tfNom.getText());
        int idProjet = st.getIdProjetByNom(tfNomProjet.getText());
        if (id != -1 && idProjet != -1) {
            Tache t = new Tache(
                    id,
                    tfNom.getText(),
                    tfDesc.getText(),
                    dpDateDebut.getValue().toString(),
                    dpDateFin.getValue().toString(),
                    tfStatut.getText(),
                    idProjet
            );

            st.update(t);
        }
    }


}