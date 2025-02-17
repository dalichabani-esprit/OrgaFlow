package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidature;
import tn.esprit.services.ServiceCandidature;

import java.util.List;


public class GestionCandidature {
    IService<Candidature> sca = new ServiceCandidature();

    @FXML
    private ListView<Candidature> ListViewCandidature;
    @FXML
    public void afficherListCandidature(ActionEvent event) {
        List<Candidature> candidatures = sca.getAll(); // Récupère toutes les candidatures

    }

}

