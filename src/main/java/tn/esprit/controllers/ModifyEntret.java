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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Entretien;
import tn.esprit.services.ServiceEntretien;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.ResourceBundle;

public class ModifyEntret implements Initializable {

    @FXML
    private Button BtnValider_Modcdtr;

    @FXML
    private DatePicker DpDate_Addcdtr;

    @FXML
    private ComboBox<Integer> IdEntret;

    @FXML
    private ComboBox<Integer> Idcdtr;

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
    private TextArea tanote;

    @FXML
    private TextField tfheure;

    @FXML
    private TextField tfinter;

    @FXML
    private TextField tflieu;
    IService<Entretien> se = new ServiceEntretien();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IdEntret.setItems(FXCollections.observableArrayList(se.getAll().stream().map(Entretien::getIdEntret).toList()));
        Idcdtr.setItems(FXCollections.observableArrayList(se.getAll().stream().map(Entretien::getCandidature_id).toList()));

    }


    @FXML
    void ModifierEntret(ActionEvent event) {
        Entretien e = new Entretien();
        e.setCandidature_id(Integer.parseInt(String.valueOf(Idcdtr.getValue())));
        e.setIdEntret(Integer.parseInt(String.valueOf(IdEntret.getValue())));
        e.setLieuEntret(tflieu.getText());
        e.setInterviewerEntret(tfinter.getText());
        e.setNotes(tanote.getText());
        e.setTimeEntret(Time.valueOf(tfheure.getText()));
        e.setDateEntret(Date.valueOf(DpDate_Addcdtr.getValue()));

        se.update(e);

    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ShowEntret.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
