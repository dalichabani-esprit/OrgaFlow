package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidature;

import tn.esprit.services.ServiceCandidature;


public class ItemCandidature {
    IService<Candidature> O =  new ServiceCandidature();


    @FXML
    private Label Labelcan;

    @FXML
    private Label Labeld;

    @FXML
    private Label Labeloffre;


    @FXML
    private Label labelstat;

    private Candidature cn;

    public void setData(Candidature cn) {
        this.cn = cn;
        Labelcan.setText(String.valueOf(cn.getCandidatID()));
        Labeloffre.setText(String.valueOf(cn.getOffreID()));
        labelstat.setText(String.valueOf(cn.getStatutCandidature()));
        Labeld.setText(cn.getDate_candidature().toString());

        switch (cn.getStatutCandidature()) {

            case "embauché":
                labelstat.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                break;
            case "rejeté":
                labelstat.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                break;
            case "en attente":
                labelstat.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
                break;
            case "entretien":
                labelstat.setStyle("-fx-text-fill: #FFA500; -fx-font-weight: bold;");
        }






    }

}
