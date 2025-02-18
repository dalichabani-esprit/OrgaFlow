package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidat;
import tn.esprit.models.Candidature;
import tn.esprit.services.ServiceCandidat;
import tn.esprit.services.ServiceCandidature;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class ModifyCandidature implements Initializable {

    @FXML
    private Button BtnValider_Modcdtr;

    @FXML
    private ComboBox<String> CbStatut_Addcdtr;

    @FXML
    private DatePicker DpDate_Addcdtr;

    @FXML
    private Label LblAcceuil;

    @FXML
    private Label LblDeconnexion;

    @FXML
    private Label LblFormation;

    @FXML
    private Label LblProjet;

    @FXML
    private Label LblRecrutement;

    @FXML
    private Label lblEntret;

    @FXML
    private Label lblOffre;

    @FXML
    private Label lblcandidat;

    @FXML
    private Label lblcdtr;

    @FXML
    private TextField tfIDoffre_Addcdtr;

    @FXML
    private ComboBox<Integer> CbId;
    @FXML
    private ComboBox<Integer> IdCdtr;

    IService<Candidature> sca = new ServiceCandidature();
    IService<Candidat>sc = new ServiceCandidat();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CbStatut_Addcdtr.setItems(FXCollections.observableArrayList("en attente", "entretien", "rejeté", "embauché"));
        CbId.setItems(FXCollections.observableArrayList(sc.getAll().stream().map(Candidat::getIdCandidat).toList()));
        IdCdtr.setItems(FXCollections.observableArrayList(sca.getAll().stream().map(Candidature::getIdCandidature).toList()));

    }



    @FXML
    void ModifierCandidature(ActionEvent event) {
        Candidature c = new Candidature();
        c.setIdCandidature(Integer.parseInt(String.valueOf(IdCdtr.getValue())));
        c.setCandidatID(Integer.parseInt(String.valueOf(CbId.getValue())));
        c.setOffreID(Integer.parseInt(tfIDoffre_Addcdtr.getText()));
        c.setStatutCandidature(CbStatut_Addcdtr.getValue());
        c.setDate_candidature(Date.valueOf(DpDate_Addcdtr.getValue()));

        sca.update(c);

    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GestionCandidature.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
