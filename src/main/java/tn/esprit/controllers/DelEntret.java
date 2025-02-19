package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidature;
import tn.esprit.models.Entretien;
import tn.esprit.services.ServiceCandidature;
import tn.esprit.services.ServiceEntretien;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DelEntret implements Initializable {

    @FXML
    private ListView<Entretien> listview;

    IService<Entretien> se = new ServiceEntretien();


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Entretien> C = se.getAll();
        listview.getItems().setAll(C);
        listview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }


    @FXML
    public void DeleteEntret(ActionEvent actionEvent) {
        ArrayList<Entretien> selectedCandidatures = new ArrayList<>(listview.getSelectionModel().getSelectedItems());

        if (!selectedCandidatures.isEmpty()) {
            for (Entretien c : selectedCandidatures) {
                se.delete(c); // Supprime chaque candidature de la base de données
            }
            listview.getItems().removeAll(selectedCandidatures); // Supprime les candidatures de la ListView
        }
    }

}
