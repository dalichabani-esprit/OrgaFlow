package tn.esprit.Controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import tn.esprit.models.Candidature;
import tn.esprit.services.ServiceCandidature;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class CandidatureController implements Initializable {
    ServiceCandidature sca=new ServiceCandidature();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Candidature> items = sca.getAll();
        ListViewCandidature.getItems().addAll(String.valueOf(items));
    }

    @FXML
    private ListView<String> ListViewCandidature;



}
