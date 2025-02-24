package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.OffreEmploi;
import tn.esprit.services.ServiceOffreEmploi;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowOffre implements Initializable {

    IService<OffreEmploi> O = new ServiceOffreEmploi();

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<OffreEmploi> emploi = O.getAll();
        int column = 0;
        int row = 1;
        int maxColumns = 4; // Nombre max de colonnes affichées dynamiquement

        try {
            for (OffreEmploi offre : emploi) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ItemOffre.fxml"));
                AnchorPane pane = fxmlLoader.load();

                ItemOffre controller = fxmlLoader.getController();
                controller.setData(offre);

                if (column == 8) {
                    column = 0;
                    row++;
                }
                grid.add(pane, column++, row);

                // Configurer la grille
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(pane, new Insets(10));
            }
        } catch (IOException e) {
            showError("Erreur de chargement des offres", "Impossible de charger les offres d'emploi.");
        }
    }

    @FXML
    void retourCandidature(ActionEvent event) throws IOException {
        changeScene(event, "/ShowCandidature.fxml", "Candidature");
    }

    @FXML
    void sceneadd(ActionEvent event) throws IOException {
        changeScene(event, "/AddOffre.fxml", "Ajout d'une offre");
    }

    @FXML
    void sceneModify(ActionEvent event) throws IOException {
        changeScene(event, "/ModifyOffre.fxml", "Modification d'une offre");
    }

    @FXML
    void sceneDel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DelOffre.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Suppression");
            stage.show();
        } catch (IOException e) {
            showError("Erreur", "Impossible d'ouvrir la fenêtre de suppression.");
        }
    }

    @FXML
    void refresh(ActionEvent event) {
        grid.getChildren().clear();
        initialize(null, null);
    }

    private void changeScene(ActionEvent event, String fxmlPath, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
