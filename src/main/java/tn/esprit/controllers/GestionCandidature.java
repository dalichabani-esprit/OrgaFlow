package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidature;
import tn.esprit.services.ServiceCandidature;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class GestionCandidature implements Initializable {

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

    IService<Candidature> sca = new ServiceCandidature();

    @FXML
    private ListView<Candidature> ListViewCandidature;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Candidature> C = sca.getAll();
        ListViewCandidature.getItems().setAll(C);

        //ListViewCandidature.getItems().clear();

        //List<Candidature> candidatures = sca.getAll(); // Récupère toutes les candidatures

    }

    @FXML
    private Button btnAdd_Cdtr;

    @FXML
    private Button btnMod_Cdtr;

    @FXML
    private Button btnStat_Cdtr;

    @FXML
    private Button btnSupp_Cdtr;

    @FXML
    private Button btnTrie_Cdtr;

    @FXML
    private Label lblEntret;

    @FXML
    private Label lblOffre;

    @FXML
    private Label lblcandidat;

    @FXML
    private Label lblcdtr;

    /*@FXML
    public void ajouterCandidature(ActionEvent actionEvent) {
        Candidature c = new Candidature();
        c.setNom(tfNom.getText());
        c.setPrenom(tfPrenom.getText());
        c.setAge(Integer.parseInt(tfAge.getText()));

        sca.add(c);
    }*/

}


