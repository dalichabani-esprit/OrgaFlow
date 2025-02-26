package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidat;
import tn.esprit.models.Employes;
import tn.esprit.models.User;
import tn.esprit.services.ServiceUser;

import java.io.IOException;
import java.util.List;

public class GestionAdmin {

    @FXML
    private Button addcandidat;

    @FXML
    private Button addemployes;

    @FXML
    private Button dashboard;

    @FXML
    private GridPane gridpane;

    @FXML
    private Button home;

    @FXML
    private Button listcandidat;

    @FXML
    private Button listeemp;

    @FXML
    private Button logout;

    @FXML
    private TextField search;

    @FXML
    private HBox totalcandidats;
    private final IService<User> serviceUser = new ServiceUser();
    @FXML
    private HBox totalemployes;

    @FXML
    void onclicaddcandidat(ActionEvent event) {

    }

    @FXML
    void onclicaddemployes(ActionEvent event) {

    }

    @FXML
    void onclicdashboard(ActionEvent event) {

    }

    @FXML
    void onclichome(ActionEvent event) {

    }

    @FXML
    void oncliclistecand(ActionEvent event) {
        gridpane.getChildren().clear();
        gridpane.add(new Label("ID"), 0, 0);
        gridpane.add(new Label("Nom"), 1, 0);
        gridpane.add(new Label("Prénom"), 2, 0);
        gridpane.add(new Label("Email"), 3, 0);
        gridpane.add(new Label("Mot de Passe"), 4, 0);
        gridpane.add(new Label("Rôle"), 5, 0);
        gridpane.add(new Label("CV"), 6, 0);
        gridpane.add(new Label("Date de Candidature"), 7, 0);
        gridpane.add(new Label("Statut"), 8, 0);

        List<User> users = serviceUser.getAll();
        int row = 1; // Ligne où commencer l'ajout des candidats

        for (User user : users) {
            if (user instanceof Candidat) {
                Candidat candidat = (Candidat) user;

                // Ajouter les informations du candidat dans le GridPane
                gridpane.add(new Label(String.valueOf(candidat.getIduser())), 0, row);
                gridpane.add(new Label(candidat.getNom()), 1, row);
                gridpane.add(new Label(candidat.getPrenom()), 2, row);
                gridpane.add(new Label(candidat.getEmail()), 3, row);
                gridpane.add(new Label(candidat.getMotDePasse()), 4, row);
                gridpane.add(new Label(candidat.getRole()), 5, row);
                gridpane.add(new Label(candidat.getCvCandidat()), 6, row);
                gridpane.add(new Label(candidat.getDateCandidature() != null ? candidat.getDateCandidature().toString() : "Non renseignée"), 7, row);
                gridpane.add(new Label(candidat.getStatutCandidat()), 8, row);

                row++; // Passer à la ligne suivante
            }
        }

        if (row == 1) {
            // Aucun candidat trouvé
            gridpane.add(new Label("Aucun candidat trouvé."), 0, 1, 9, 1);
        }
    }

    @FXML
    void initialize() {
        gridpane.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
    }

    @FXML
    void oncliclisteemplo(ActionEvent event) {
        gridpane.getChildren().clear();
        gridpane.getStyleClass().add("gridpane");

        // En-têtes du tableau
        String[] headers = {"ID", "Nom", "Prénom", "Email", "Salaire", "Date Embauche", "Département", "Action"};
        for (int i = 0; i < headers.length; i++) {
            Label label = new Label(headers[i]);
            label.getStyleClass().add("label");
            gridpane.add(label, i, 0);
        }

        List<User> users = serviceUser.getAll();
        int row = 1;

        for (User user : users) {
            if (user instanceof Employes) {
                Employes employe = (Employes) user;

                // Ajouter les informations de l'employé avec un style
                Label[] labels = {
                        new Label(String.valueOf(employe.getIduser())),
                        new Label(employe.getNom()),
                        new Label(employe.getPrenom()),
                        new Label(employe.getEmail()),
                        new Label(String.valueOf(employe.getSalaire())),
                        new Label(employe.getDateEmbauche() != null ? employe.getDateEmbauche().toString() : "Non renseignée"),
                        new Label(employe.getDepartement())
                };

                for (int i = 0; i < labels.length; i++) {
                    labels[i].getStyleClass().add("grid-cell");
                    gridpane.add(labels[i], i, row);
                }

                // Bouton de sélection
                Button btnSelect = new Button("Sélectionner");
                btnSelect.getStyleClass().add("button");
                btnSelect.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informations Employé");
                    alert.setHeaderText("Détails de l'employé sélectionné");
                    alert.setContentText(
                            "ID: " + employe.getIduser() + "\n" +
                                    "Nom: " + employe.getNom() + "\n" +
                                    "Prénom: " + employe.getPrenom() + "\n" +
                                    "Email: " + employe.getEmail() + "\n" +
                                    "Salaire: " + employe.getSalaire() + "dt\n" +
                                    "Date d'embauche: " + (employe.getDateEmbauche() != null ? employe.getDateEmbauche().toString() : "Non renseignée") + "\n" +
                                    "Département: " + employe.getDepartement()
                    );
                    alert.showAndWait();
                });

                gridpane.add(btnSelect, 7, row);
                row++;
            }
        }

        if (row == 1) {
            Label noDataLabel = new Label("Aucun employé trouvé.");
            noDataLabel.getStyleClass().add("grid-cell");
            gridpane.add(noDataLabel, 0, 1, 8, 1);
        }
    }


    @FXML
    void oncliclogout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/logginUser.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    @FXML
    private void onclicsearch() {
        String keyword = search.getText().trim();
        if (!keyword.isEmpty()) {
            // Effectuer la recherche (à implémenter)
            System.out.println("Recherche en cours pour : " + keyword);
        }
    }
}
