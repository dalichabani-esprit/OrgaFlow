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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidature;
import tn.esprit.models.OffreEmploi;
import tn.esprit.services.ServiceCandidature;
import tn.esprit.services.ServiceOffreEmploi;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class AddOffre implements Initializable {

    @FXML
    private ComboBox<String> cbdep;

    @FXML
    private ComboBox<String> cbstatus;

    @FXML
    private DatePicker dplimit;

    @FXML
    private DatePicker dppubli;

    @FXML
    private ProgressBar progression;

    @FXML
    private Button retour;

    @FXML
    private VBox sidebar;

    @FXML
    private TextArea tfdesc;

    @FXML
    private TextField tftitre;

    @FXML
    private Button valider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbdep.setItems(FXCollections.observableArrayList("Ressources Humaines", "Formation", "Projet", "Facturation"));
        cbstatus.setItems(FXCollections.observableArrayList("ouvert", "fermé"));

    }
    @FXML
    IService<OffreEmploi> so = new ServiceOffreEmploi();

    public void ajouterOffre(ActionEvent actionEvent) {
        OffreEmploi o = new OffreEmploi();
        o.setTitreOffre(tftitre.getText());
        o.setDescriptionOffre(tfdesc.getText());
        o.setDepartementOffre(cbdep.getValue());
        o.setDate_publicationOffre(Date.valueOf(dppubli.getValue()));
        o.setDate_limiteOffre(Date.valueOf(dplimit.getValue()));
        o.setStatutOffre(cbstatus.getValue());


        so.add(o);
    }



    @FXML
    void retour(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ShowOffre.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
