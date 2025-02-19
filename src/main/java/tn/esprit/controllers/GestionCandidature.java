package tn.esprit.controllers;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidature;
import tn.esprit.services.ServiceCandidature;


import java.io.IOException;
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
        //sca.update((Candidature) sca);

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

    @FXML
    private Button BtnRetour_Addcdtr;

    @FXML
    private Button BtnValider_Addcdtr;



    @FXML
    public void sceneadd(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/AddCandidature.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void sceneModify(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/ModifyCandidature.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void sceneDel(ActionEvent actionEvent) throws IOException {

        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/DelCandidature.fxml"));
            Parent root1 =(Parent) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("--------Suppression--------- ");
            stage.show();

        } catch (IOException e) {
            System.out.println("Erreur de chargement du fichier FXML : " + e.getMessage());
        }

    }
    @FXML
    void refresh(ActionEvent event) {
        List<Candidature> C = sca.getAll();
        ListViewCandidature.getItems().setAll(C);


    }
    @FXML
    void retourEntretien(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ShowEntret.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


}


