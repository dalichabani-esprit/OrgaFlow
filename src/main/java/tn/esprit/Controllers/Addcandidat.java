package tn.esprit.Controllers;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidat;
import tn.esprit.models.User;
import tn.esprit.services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class Addcandidat implements Initializable {

    @FXML
    private Button addcandidat;
    @FXML
    private ComboBox<String> cbrole;
    @FXML
    private Button addemployes;


    @FXML
    private TextField cvcandidat;

    @FXML
    private Button dashboard;

    @FXML
    private DatePicker datecandi;

    @FXML
    private Button home;

    @FXML
    private Button listcandidat;

    @FXML
    private Button listeemp;

    @FXML
    private Button logout;

    @FXML
    private PasswordField pfmotdepasse;

    @FXML
    private TextField search;

    @FXML
    private TextField statutcandidat;

    @FXML
    private TextField tfemail;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfprenom;
    private final IService<User> serviceUser = new ServiceUser();
    @FXML
    void onclicaddcandidat(ActionEvent event) {

        if ("candidat".equals(cbrole.getValue())) {

            Candidat candidat = new Candidat();
            candidat.setNom(tfnom.getText());
            candidat.setPrenom(tfprenom.getText());
            candidat.setEmail(tfemail.getText());
            candidat.setMotDePasse(pfmotdepasse.getText());
            candidat.setRole(cbrole.getValue());
            candidat.setCvCandidat(cvcandidat.getText());
            candidat.setDateCandidature(Date.valueOf(datecandi.getValue()));
            candidat.setStatutCandidat(statutcandidat.getText());

            System.out.println("Ajout du candidat : " + candidat);
            serviceUser.add(candidat);

        }

    }

    @FXML
    void onclicaddemployes(ActionEvent event) {

    }

    @FXML
    void onclicdashboard(ActionEvent event) {

    }

    @FXML
    void onclichome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GestionAdmin.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("DashBoard");
        stage.show();
    }

    @FXML
    void oncliclistecand(ActionEvent event) {

    }

    @FXML
    void oncliclisteemplo(ActionEvent event) {

    }

    @FXML
    void oncliclogout(ActionEvent event) {

    }

    @FXML
    void onclicsearch(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbrole.getItems().addAll("candidat");
    }
}
