package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidature;
import tn.esprit.services.ServiceCandidature;

public class GestionCandidature {
    IService<Candidature> sca =new ServiceCandidature();
    @FXML
    private ListView<Candidature> listCandidature;

    @FXML
    public void afficherListCandidature(ActionEvent event) {
        ObservableList<Candidature> candidatures = FXCollections.observableArrayList(sca.getAll());
        listCandidature.setItems(candidatures);
    }
}
