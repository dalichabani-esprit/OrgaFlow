/*package tn.esprit.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import tn.esprit.models.Candidature;
import tn.esprit.services.ServiceCandidature;

import java.util.List;
import java.io.IOException;


public class GestionCandidature {
    @FXML
    void ShowCandidature(ActionEvent event) {
        ServiceCandidature sca = new ServiceCandidature();
        List<Candidature> candidature= sca.getAll();
        for (candidature sca : candidature){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GestionCandidature.fxml")) ;
                HBox boc =fxmlLoader.load();
                 c = fxmlLoader.getController();
                c.setPersonnes(p);

                boxh.getChildren().add(boc);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }



    }

}
*/