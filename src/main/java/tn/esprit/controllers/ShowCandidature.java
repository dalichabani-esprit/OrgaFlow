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


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ShowCandidature implements Initializable {


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

    IService<Candidature> sca = new ServiceCandidature();

    @FXML
    private ListView<Candidature> ListViewCandidature;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Candidature> can = sca.getAll();
        int column = 0;
        int row = 1;
        int maxColumns = 4; // Nombre max de colonnes affichées dynamiquement

        try {
            for (Candidature c : can) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ItemCandidature.fxml"));
                AnchorPane pane = fxmlLoader.load();

                ItemCandidature controller = fxmlLoader.getController();
                controller.setData(c);

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

    @FXML
    private Label lblcandidat;

    @FXML
    private Label lblcdtr;

    @FXML
    private Button BtnRetour_Addcdtr;

    @FXML
    private Button BtnValider_Addcdtr;



    @FXML
    public void sceneadd(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/AddCandidature.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    @FXML
    public void sceneModify(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/ModifyCandidature.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void sceneDel(ActionEvent actionEvent) throws IOException {

        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/DelCandidature.fxml"));
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
    void refresh(ActionEvent event) {
        grid.getChildren().clear();
        initialize(null, null);


    }
    @FXML
    void retourEntretien(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ShowEntret.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Entretien ");
        stage.show();

    }
    @FXML
    void retourOffre(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ShowOffre.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Offre ");
        stage.show();

    }


}


