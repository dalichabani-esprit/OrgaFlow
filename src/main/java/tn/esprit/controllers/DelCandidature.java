package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidature;
import tn.esprit.services.ServiceCandidature;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DelCandidature implements Initializable {

    @FXML
    private ListView<Candidature> listview;

    IService<Candidature> sca = new ServiceCandidature();


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Candidature> C = sca.getAll();
        listview.getItems().setAll(C);
        listview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    /*
    @FXML
    public void DeleteCandidature(ActionEvent actionEvent) {
       List<Candidature> c = (List<Candidature>) listview.getSelectionModel().getSelectedItem();
        if (c!= null) {
            for (Candidature a : c)
            sca.delete(a);
            listview.getItems().remove(c);
        }

    }*/
    @FXML
    public void DeleteCandidature(ActionEvent actionEvent) {
        List<Candidature> selectedCandidatures = new ArrayList<>(listview.getSelectionModel().getSelectedItems());

        if (!selectedCandidatures.isEmpty()) {
            for (Candidature c : selectedCandidatures) {
                sca.delete(c); // Supprime chaque candidature de la base de données
            }
            listview.getItems().removeAll(selectedCandidatures); // Supprime les candidatures de la ListView
        }
    }




}
