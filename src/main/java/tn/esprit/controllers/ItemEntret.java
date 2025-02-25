package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidature;
import tn.esprit.models.Entretien;
import tn.esprit.services.ServiceCandidature;
import tn.esprit.services.ServiceEntretien;

public class ItemEntret {

    IService<Entretien> E =  new ServiceEntretien();

    @FXML
    private Label Labelcan;

    @FXML
    private Label Labeld;

    @FXML
    private Label Labelheure;

    @FXML
    private Label labelinter;

    @FXML
    private Label labellieu;

    @FXML
    private Label labelnote;


    private Entretien en;

    public void setData(Entretien en) {
        this.en = en;
        Labelcan.setText(String.valueOf(en.getCandidature_id()));
        Labelheure.setText(String.valueOf(en.getTimeEntret()));
        Labeld.setText(en.getDateEntret().toString());
        labellieu.setText(String.valueOf(en.getLieuEntret()));
        labelinter.setText(String.valueOf(en.getInterviewerEntret()));
        labelnote.setText(String.valueOf(en.getNotes()));


    }

}
