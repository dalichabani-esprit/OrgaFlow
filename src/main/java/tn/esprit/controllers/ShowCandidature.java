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
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidature;
import tn.esprit.models.OffreEmploi;
import tn.esprit.services.ServiceCandidature;
import tn.esprit.services.ServiceOffreEmploi;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ShowCandidature implements Initializable {
    @FXML
    private ComboBox<String> cbtrier;
    @FXML
    private ComboBox<String> Détail;
    @FXML
    private GridPane grid;
    @FXML
    private ScrollPane scroll;
    @FXML
    private Label LblAcceuil, LblDeconnexion, LblFormation, LblProjet, LblRecrutement;

    private final IService<Candidature> sca = new ServiceCandidature();
    private List<Candidature> candidatures = new ArrayList<>();
    private final ServiceOffreEmploi serviceOffre = new ServiceOffreEmploi();

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbtrier.setItems(FXCollections.observableArrayList("par offre", "par date", "par statut"));
        cbtrier.setOnAction(event -> updateDetailComboBox());
        Détail.setOnAction(event -> filterCandidatures());
        loadCandidatures();
    }

    private void updateDetailComboBox() {
        String selected = cbtrier.getValue();
        if (selected == null) return;

        switch (selected) {
            case "par statut":
                Détail.setItems(FXCollections.observableArrayList("en attente", "entretien", "rejeté", "embauché"));
                break;
            case "par offre":
                // Récupérer les titres des offres et associer l'ID correspondant
                List<String> titresOffres = serviceOffre.getAll().stream()
                        .map(OffreEmploi::getTitreOffre)
                        .collect(Collectors.toList());
                Détail.setItems(FXCollections.observableArrayList(titresOffres));
                break;
            case "par date":
                Détail.setItems(FXCollections.observableArrayList("croissant", "décroissant"));
                break;
        }
    }

    private void filterCandidatures() {
        String critere = cbtrier.getValue();
        String valeur = Détail.getValue();

        if (critere == null || valeur == null) {
            loadCandidatures();
            return;
        }

        List<Candidature> filteredList = new ArrayList<>(candidatures);

        switch (critere) {
            case "par statut":
                filteredList = filteredList.stream()
                        .filter(c -> c.getStatutCandidature().equals(valeur))
                        .collect(Collectors.toList());
                break;
            case "par offre":
                // Récupérer l'ID de l'offre correspondant au titre sélectionné
                Optional<OffreEmploi> selectedOffre = serviceOffre.getAll().stream()
                        .filter(offre -> offre.getTitreOffre().equals(valeur))
                        .findFirst();

                if (selectedOffre.isPresent()) {
                    String selectedOffreID = String.valueOf(selectedOffre.get().getIdOffre());
                    filteredList = filteredList.stream()
                            .filter(c -> String.valueOf(c.getOffreID()).equals(selectedOffreID))
                            .collect(Collectors.toList());
                }
                break;
            case "par date":
                if ("croissant".equals(valeur)) {
                    filteredList.sort(Comparator.comparing(Candidature::getDate_candidature));
                } else if ("décroissant".equals(valeur)) {
                    filteredList.sort(Comparator.comparing(Candidature::getDate_candidature).reversed());
                }
                break;
        }

        updateGrid(filteredList);
    }

    private void loadCandidatures() {
        candidatures = sca.getAll();
        updateGrid(candidatures);
    }

    private void updateGrid(List<Candidature> candidatures) {
        grid.getChildren().clear();
        int column = 0;
        int row = 1;

        try {
            for (Candidature c : candidatures) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ItemCandidature.fxml"));
                AnchorPane pane = fxmlLoader.load();

                ItemCandidature controller = fxmlLoader.getController();
                controller.setData(c);

                if (column == 4) {
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
            showError("Erreur de chargement", "Impossible de charger les candidatures.");
            e.printStackTrace();
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void refresh(ActionEvent event) {
        loadCandidatures();
    }

    @FXML
    public void sceneadd(ActionEvent actionEvent) throws IOException {
        loadScene(actionEvent, "/AddCandidature.fxml");
    }

    @FXML
    public void sceneModify(ActionEvent actionEvent) throws IOException {
        loadScene(actionEvent, "/ModifyCandidature.fxml");
    }

    @FXML
    public void sceneDel(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DelCandidature.fxml"));
            Parent root1 = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("Suppression");
            stage.show();
        } catch (IOException e) {
            showError("Erreur", "Impossible de charger la suppression de candidature.");
            e.printStackTrace();
        }
    }

    @FXML
    void retourEntretien(ActionEvent event) throws IOException {
        loadScene(event, "/ShowEntret.fxml");
    }

    @FXML
    void retourOffre(ActionEvent event) throws IOException {
        loadScene(event, "/ShowOffre.fxml");
    }

    private void loadScene(ActionEvent event, String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
}
