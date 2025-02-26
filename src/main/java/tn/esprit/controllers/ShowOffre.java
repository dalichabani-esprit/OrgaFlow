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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Entretien;
import tn.esprit.models.OffreEmploi;
import tn.esprit.services.ServiceEntretien;
import tn.esprit.services.ServiceOffreEmploi;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ShowOffre implements Initializable {

    IService<OffreEmploi> offreService = new ServiceOffreEmploi();

    @FXML
    private GridPane grid;
    @FXML
    private ComboBox<String> cbtrier;
    @FXML
    private ComboBox<String> Detail;
    @FXML
    private ScrollPane scroll;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbtrier.setItems(FXCollections.observableArrayList("par Département", "par statut", "par date d'ouverture", "par date limite"));
        cbtrier.setOnAction(event -> updateDetailComboBox());
        Detail.setOnAction(event -> filterOffre());
        loadOffre();
    }

    private void updateDetailComboBox() {
        String selected = cbtrier.getValue();
        if (selected == null) return;

        switch (selected) {
            case "par date d'ouverture":
                Detail.setItems(FXCollections.observableArrayList("croissant", "décroissant"));
                break;
            case "par date limite":
                Detail.setItems(FXCollections.observableArrayList("croissant", "décroissant"));
                break;
            case "par statut":
                Detail.setItems(FXCollections.observableArrayList(
                        offreService.getAll().stream().map(OffreEmploi::getStatutOffre).distinct().collect(Collectors.toList())));
                break;
            case "par Département":
                Detail.setItems(FXCollections.observableArrayList(
                        offreService.getAll().stream().map(OffreEmploi::getDepartementOffre).distinct().collect(Collectors.toList())));
                break;
        }
    }

    private void filterOffre() {
        String critere = cbtrier.getValue();
        String valeur = Detail.getValue();

        if (critere == null || valeur == null) {
            loadOffre();
            return;
        }

        List<OffreEmploi> filteredList = offreService.getAll();

        switch (critere) {
            case "par date d'ouverture":
                filteredList.sort("croissant".equals(valeur) ?
                        (e1, e2) -> e1.getDate_publicationOffre().compareTo(e2.getDate_publicationOffre()) :
                        (e1, e2) -> e2.getDate_publicationOffre().compareTo(e1.getDate_publicationOffre()));
                break;
            case "par date limite":
                filteredList.sort("croissant".equals(valeur) ?
                        (e1, e2) -> e1.getDate_limiteOffre().compareTo(e2.getDate_limiteOffre()) :
                        (e1, e2) -> e2.getDate_limiteOffre().compareTo(e1.getDate_limiteOffre()));
                break;
            case "par statut":
                filteredList = filteredList.stream()
                        .filter(e -> e.getStatutOffre().equals(valeur))
                        .collect(Collectors.toList());
                break;
            case "par Département":
                filteredList = filteredList.stream()
                        .filter(e -> e.getDepartementOffre().equals(valeur))
                        .collect(Collectors.toList());
                break;
        }

        updateGrid(filteredList);
    }

    private void loadOffre() {
        List<OffreEmploi> offres = offreService.getAll();
        updateGrid(offres);
    }

    private void updateGrid(List<OffreEmploi> offres) {
        grid.getChildren().clear();
        int column = 0;
        int row = 1;

        try {
            for (OffreEmploi offre : offres) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ItemOffre.fxml"));
                AnchorPane pane = fxmlLoader.load();

                ItemOffre controller = fxmlLoader.getController();
                controller.setData(offre);

                if (column == 8) {
                    column = 0;
                    row++;
                }
                grid.add(pane, column++, row);

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
            e.printStackTrace();
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
        loadOffre();
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
