package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.OffreEmploi;
import tn.esprit.services.ServiceOffreEmploi;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowOffre implements Initializable {

    IService<OffreEmploi> O =  new ServiceOffreEmploi();

    List<OffreEmploi> getData(){
        List<OffreEmploi> emploi = new ArrayList<>();
        OffreEmploi offreEmploi ;

        for(OffreEmploi i : O.getAll()){
            offreEmploi = new OffreEmploi();
            offreEmploi.setTitreOffre(i.getTitreOffre());
            offreEmploi.setDescriptionOffre(i.getDescriptionOffre());
            offreEmploi.setStatutOffre(i.getStatutOffre());
            offreEmploi.setIdOffre(i.getIdOffre());
            offreEmploi.setDate_publicationOffre(i.getDate_publicationOffre());
            offreEmploi.setDate_limiteOffre(i.getDate_limiteOffre());
            offreEmploi.setDepartementOffre(i.getDepartementOffre());
            emploi.add(offreEmploi);

        }
        return emploi;
    }

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<OffreEmploi> emploi = O.getAll(); // Récupérer les offres
        int column = 0;
        int row = 0;

        try {
            for (int i = 0; i < emploi.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ItemOffre.fxml"));

                AnchorPane pane = fxmlLoader.load();

                // Récupérer le contrôleur de l'élément
                ItemOffre controller = fxmlLoader.getController();
                controller.setData(emploi.get(i)); // Passer les données via `setData()`

                // Gestion du positionnement dans la grille
                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void retourCandidature(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GestionCandidature.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Candidature ");
        stage.setMaximized(true);
        stage.show();

    }

    @FXML
    void sceneadd(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AddOffre.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

    }
}
