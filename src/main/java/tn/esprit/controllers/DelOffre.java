package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import tn.esprit.interfaces.IService;
import tn.esprit.models.OffreEmploi;
import tn.esprit.services.ServiceOffreEmploi;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DelOffre implements Initializable {

    @FXML
    private ListView<OffreEmploi> listview;
    IService<OffreEmploi> so = new ServiceOffreEmploi();


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<OffreEmploi> C = so.getAll();
        listview.getItems().setAll(C);
        listview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    @FXML
    void DeleteOffre(ActionEvent event) {
        ArrayList<OffreEmploi> selectedOffrre = new ArrayList<>(listview.getSelectionModel().getSelectedItems());

        if (!selectedOffrre.isEmpty()) {
            for (OffreEmploi c : selectedOffrre) {
                so.delete(c); // Supprime chaque candidature de la base de données
            }
            listview.getItems().removeAll(selectedOffrre); // Supprime les offres de la ListView


        }

    }

}
