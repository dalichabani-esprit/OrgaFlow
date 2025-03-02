package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tn.esprit.interfaces.IService;
import tn.esprit.models.OffreEmploi;
import tn.esprit.services.ServiceOffreEmploi;

public class ItemOffre {

    @FXML
    private Label Description;

    @FXML
    private Label Titre;

    @FXML
    private Label dd;

    @FXML
    private Label departement;

    @FXML
    private Label df;

    @FXML
    private Label id;

    @FXML
    private Label statut;

    IService<OffreEmploi> O =  new ServiceOffreEmploi();

    /*public void initialize() {
        for (OffreEmploi offre : O.getAll()) {
            id.setText(String.valueOf(offre.getIdOffre()));
            Titre.setText(offre.getTitreOffre());
            statut.setText(offre.getStatutOffre());
            departement.setText(offre.getDepartementOffre());
            Description.setText(offre.getDescriptionOffre());
            dd.setText(offre.getDate_publicationOffre().toString());
            df.setText(offre.getDate_limiteOffre().toString());

        }
    }*/

    private OffreEmploi offre;

    public void setData(OffreEmploi offre) {
        this.offre = offre;
        //id.setText(String.valueOf(offre.getIdOffre()));
        Titre.setText(offre.getTitreOffre());
        statut.setText(offre.getStatutOffre());
        departement.setText(offre.getDepartementOffre());
        Description.setText(offre.getDescriptionOffre());
        dd.setText(offre.getDate_publicationOffre().toString());
        df.setText(offre.getDate_limiteOffre().toString());

        if (statut.getText().equalsIgnoreCase("ouvert")) {
            statut.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        } else {
            statut.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }


    }

}
