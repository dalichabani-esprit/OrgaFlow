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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Entretien;
import tn.esprit.services.ServiceEntretien;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ShowEntret implements Initializable {
    @FXML
    private ComboBox<String> Detail, cbtrier;
    @FXML
    private GridPane grid;
    @FXML
    private ScrollPane scroll;
    @FXML
    private Button btnAdd_Cdtr, btnMod_Cdtr, btnStat_Cdtr, btnSupp_Cdtr;

    private final IService<Entretien> entService = new ServiceEntretien();
    private List<Entretien> entretiens;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbtrier.setItems(FXCollections.observableArrayList("par heure", "par date", "par lieu", "par interviewer"));
        cbtrier.setOnAction(event -> updateDetailComboBox());
        Detail.setOnAction(event -> filterEntretien());
        loadEntretien();
    }

    private void updateDetailComboBox() {
        String selected = cbtrier.getValue();
        if (selected == null) return;

        switch (selected) {
            case "par heure":
                Detail.setItems(FXCollections.observableArrayList("croissant", "décroissant"));
            case "par date":
                Detail.setItems(FXCollections.observableArrayList("croissant", "décroissant"));
                break;
            case "par lieu":
                Detail.setItems(FXCollections.observableArrayList(
                        entService.getAll().stream().map(Entretien::getLieuEntret).distinct().collect(Collectors.toList())));
                break;
            case "par interviewer":
                Detail.setItems(FXCollections.observableArrayList(
                        entService.getAll().stream().map(Entretien::getInterviewerEntret).distinct().collect(Collectors.toList())));
                break;
        }
    }

    private void filterEntretien() {
        String critere = cbtrier.getValue();
        String valeur = Detail.getValue();

        if (critere == null || valeur == null) {
            loadEntretien();
            return;
        }

        List<Entretien> filteredList = entretiens;

        switch (critere) {
            case "par heure":
                filteredList.sort("croissant".equals(valeur) ?
                        (e1, e2) -> e1.getTimeEntret().compareTo(e2.getTimeEntret()) :
                        (e1, e2) -> e2.getTimeEntret().compareTo(e1.getTimeEntret()));
                break;
            case "par date":
                filteredList.sort("croissant".equals(valeur) ?
                        (e1, e2) -> e1.getDateEntret().compareTo(e2.getDateEntret()) :
                        (e1, e2) -> e2.getDateEntret().compareTo(e1.getDateEntret()));
                break;
            case "par lieu":
                filteredList = filteredList.stream()
                        .filter(e -> e.getLieuEntret().equals(valeur))
                        .collect(Collectors.toList());
                break;
            case "par interviewer":
                filteredList = filteredList.stream()
                        .filter(e -> e.getInterviewerEntret().equals(valeur))
                        .collect(Collectors.toList());
                break;
        }

        updateGrid(filteredList);
    }

    private void loadEntretien() {
        entretiens = entService.getAll();
        updateGrid(entretiens);
    }

    private void updateGrid(List<Entretien> entretiens) {
        grid.getChildren().clear();
        int column = 0, row = 1, maxColumns = 4;

        try {
            for (Entretien ent : entretiens) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ItemEntret.fxml"));
                AnchorPane pane = fxmlLoader.load();

                ItemEntret controller = fxmlLoader.getController();
                controller.setData(ent);

                if (column == maxColumns) {
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
            showError("Erreur de chargement des entretiens", "Impossible de charger les entretiens.");
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
    void refresh(ActionEvent event) {
        loadEntretien();
    }

    @FXML
    void sceneDel(ActionEvent event) {
        loadPopupScene("/DelEntret.fxml", "Suppression");
    }

    @FXML
    void sceneCal(ActionEvent event) {
        loadPopupScene("/ShowCal.fxml", "Calendrier");
    }

    @FXML
    void sceneModify(ActionEvent event) throws IOException {
        loadScene(event, "/ModifyEntret.fxml");
    }

    @FXML
    void sceneadd(ActionEvent event) throws IOException {
        loadScene(event, "/AddEntret.fxml");
    }

    @FXML
    void sceneQ(ActionEvent event) {
        loadPopupScene("/ShowQuestionnaire.fxml", "Questions");
    }

    @FXML
    void retourCandidature(ActionEvent event) throws IOException {
        loadScene(event, "/ShowCandidature.fxml");
    }

    @FXML
    void retourOffre(ActionEvent event) throws IOException {
        loadScene(event, "/ShowOffre.fxml");
    }

    private void loadScene(ActionEvent event, String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(fxmlPath.substring(1, fxmlPath.indexOf("."))); // Extrait le nom du fichier sans ".fxml"
        stage.show();
    }

    private void loadPopupScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            showError("Erreur", "Impossible de charger la fenêtre.");
            e.printStackTrace();
        }
    }

    @FXML
    void IAORGA(MouseEvent event) throws IOException {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/chatbot.fxml"));
            Parent root1 = loader.load();

            // Créer un nouveau stage pour la nouvelle fenêtre
            Stage stage = new Stage();
            stage.setTitle("Chatbot"); // Optionnel, pour définir un titre pour la fenêtre
            stage.setScene(new Scene(root1));

            // Afficher la nouvelle fenêtre
            stage.show();
        } catch (IOException e) {
            showError("Erreur", "Impossible de charger le chatbot");
            e.printStackTrace();
        }
    }
}
