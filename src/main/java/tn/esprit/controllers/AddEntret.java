package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Entretien;
import tn.esprit.services.ServiceEntretien;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;

public class AddEntret {

    @FXML
    private Button BtnRetour_Addcdtr;

    @FXML
    private Button BtnValider_Addcdtr;

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
    private TextArea ta_note;

    @FXML
    private TextField tfIDc_Addentret;

    @FXML
    private TextField tf_time;

    @FXML
    private TextField tfinter;

    @FXML
    private TextField tflieu_Addentret;

    IService<Entretien> En = new ServiceEntretien();

    @FXML
    void ajouterEnt(ActionEvent event) {
        Entretien e = new Entretien();
        e.setCandidature_id(Integer.parseInt(tfIDc_Addentret.getText()));
        e.setLieuEntret(tflieu_Addentret.getText());
        e.setInterviewerEntret(tfinter.getText());
        e.setNotes(ta_note.getText());
        e.setTimeEntret(Time.valueOf((tf_time.getText())));
        e.setDateEntret(Date.valueOf(DpDate_Addcdtr.getValue()));
        En.add(e);

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
