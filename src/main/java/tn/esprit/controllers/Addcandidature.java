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
import tn.esprit.models.Candidature;
import tn.esprit.services.ServiceCandidature;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class Addcandidature implements Initializable {

    IService<Candidature> sca = new ServiceCandidature();

    @FXML
    private Button BtnRetour_Addcdtr;

    @FXML
    private Button BtnValider_Addcdtr;

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
    private TextField tfIDc_Addcdtr;

    @FXML
    private TextField tfIDoffre_Addcdtr;

    @FXML
    public void ajouterCandidature(ActionEvent actionEvent) {
        Candidature c = new Candidature();
        c.setCandidatID(Integer.parseInt(tfIDc_Addcdtr.getText()));
        c.setOffreID(Integer.parseInt(tfIDoffre_Addcdtr.getText()));
        c.setStatutCandidature(CbStatut_Addcdtr.getValue());//getItems().add(TextInputControl.getText()));//CbStatut_Addcdtr.getItems().add(TextInputControl.getText())
        c.setDate_candidature(Date.valueOf(DpDate_Addcdtr.getValue()));
        // c.setStatutCandidature(CbStatut_Addcdtr.getText());
        //c.setDate_candidature(DpDate_Addcdtr.getDate());
        // c.setStatutCandidature(Integer.parseInt(tfAge.getText()));

        sca.add(c);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CbStatut_Addcdtr.setItems(FXCollections.observableArrayList("en attente", "entretien", "rejeté", "embauché"));

    }

    @FXML
    public void retour(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GestionCandidature.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
