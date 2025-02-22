package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidature;
import tn.esprit.models.Entretien;
import java.io.IOException;
import tn.esprit.services.ServiceEntretien;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowEntret implements Initializable {

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
    private ListView<Entretien> ListViewEntret;

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

    IService<Entretien> Ent =new ServiceEntretien();

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Entretien> E = Ent.getAll();
        ListViewEntret.getItems().setAll(E);
        //sca.update((Candidature) sca);

    }

    @FXML
    void refresh(ActionEvent event) {
        List<Entretien> C = Ent.getAll();
        ListViewEntret.getItems().setAll(C);

    }

    @FXML
    void sceneDel(ActionEvent event) {
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/DelEntret.fxml"));
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
    void sceneModify(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ModifyEntret.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void sceneadd(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AddEntret.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

    }


    @FXML
    void retourCandidature(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GestionCandidature.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Candidature ");
        stage.show();

    }

    @FXML
    void retourOffre(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ShowOffre.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Offre ");
        stage.show();

    }

}
