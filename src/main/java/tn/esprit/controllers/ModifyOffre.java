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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Entretien;
import tn.esprit.models.OffreEmploi;
import tn.esprit.services.ServiceEntretien;
import tn.esprit.services.ServiceOffreEmploi;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.ResourceBundle;

public class ModifyOffre implements Initializable {

    @FXML
    private ComboBox<String> cbdep;

    @FXML
    private ComboBox<String> cbstatus;

    @FXML
    private ComboBox<String> cbtitre;

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
    private Button valider;

    IService<OffreEmploi> so = new ServiceOffreEmploi();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbtitre.setItems(FXCollections.observableArrayList(so.getAll().stream().map(OffreEmploi::getTitreOffre).toList()));
        cbdep.setItems(FXCollections.observableArrayList("Ressources Humaines", "Formation", "Projet", "Facturation"));
        cbstatus.setItems(FXCollections.observableArrayList("ouvert", "fermé"));

    }

    @FXML
    void modifyOffre(ActionEvent event) {
        // Récupérer l'ID de l'offre en fonction du titre sélectionné
        int idOffre = so.getAll().stream()
                .filter(offre -> offre.getTitreOffre().equals(cbtitre.getValue()))
                .map(OffreEmploi::getIdOffre)
                .findFirst()
                .orElse(-1); // Retourne -1 si aucune offre ne correspond

        if (idOffre == -1) {
            System.out.println("Erreur : Aucune offre trouvée avec ce titre.");
            return;
        }

        // Récupérer l'objet existant avec son ID
        OffreEmploi o = so.getById(idOffre);

        if (o == null) {
            System.out.println("Erreur : Impossible de récupérer l'offre avec l'ID " + idOffre);
            return;
        }

        o.setDescriptionOffre(tfdesc.getText());
        o.setDepartementOffre(cbdep.getValue());
        o.setStatutOffre(cbstatus.getValue());
        o.setDate_publicationOffre(Date.valueOf(dppubli.getValue()));
        o.setDate_limiteOffre(Date.valueOf(dplimit.getValue()));

        so.update(o);

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
