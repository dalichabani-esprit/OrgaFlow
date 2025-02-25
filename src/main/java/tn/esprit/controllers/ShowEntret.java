package tn.esprit.controllers;

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
import tn.esprit.models.Entretien;
import java.io.IOException;

import tn.esprit.models.OffreEmploi;
import tn.esprit.services.ServiceEntretien;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowEntret implements Initializable {

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Label LblAcceuil;

    @FXML
    private Label LblDeconnexion;

    @FXML
    private Label LblFormation;

    @FXML
    private Label LblProjet;

    @FXML
    private Label LblRecrutement;

    @FXML
    private ListView<Entretien> ListViewEntret;

    @FXML
    private Button btnAdd_Cdtr;

    @FXML
    private Button btnMod_Cdtr;

    @FXML
    private Button btnStat_Cdtr;

    @FXML
    private Button btnSupp_Cdtr;

    @FXML
    private Button btnTrie_Cdtr;

    @FXML
    private Label lblEntret;

    @FXML
    private Label lblOffre;

    IService<Entretien> Ent =new ServiceEntretien();

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Entretien> entretiens = Ent.getAll();
        int column = 0;
        int row = 1;
        int maxColumns = 4; // Nombre max de colonnes affichées dynamiquement

        try {
            for (Entretien ent : entretiens) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ItemEntret.fxml"));
                AnchorPane pane = fxmlLoader.load();

                ItemEntret controller = fxmlLoader.getController();
                controller.setData(ent);

                if (column == 4) {
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
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void refresh(ActionEvent event) {
        grid.getChildren().clear();
        initialize(null, null);

    }

    @FXML
    void sceneDel(ActionEvent event) {
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/DelEntret.fxml"));
            Parent root1 =(Parent) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle("--------Suppression--------- ");
            stage.show();

        } catch (IOException e) {
            System.out.println("Erreur de chargement du fichier FXML : " + e.getMessage());
        }

    }

    @FXML
    void sceneModify(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ModifyEntret.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void sceneadd(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AddEntret.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();

    }


    @FXML
    void retourCandidature(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ShowCandidature.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Candidature ");
        stage.show();

    }

    @FXML
    void retourOffre(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ShowOffre.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Offre ");
        stage.show();

    }

}
