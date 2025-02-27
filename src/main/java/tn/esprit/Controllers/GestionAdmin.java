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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidat;
import tn.esprit.models.Employes;
import tn.esprit.models.User;
import tn.esprit.services.ServiceUser;

import java.io.IOException;

import java.util.ArrayList;
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

    @FXML
    private HBox totalemployes;
    private final IService<User> serviceUser = new ServiceUser();
    @FXML
    void onclicaddcandidat(ActionEvent event)  throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Addcandidat.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Modification ");
        stage.show();
    }

    @FXML
    void onclicaddemployes(ActionEvent event)  throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Addemploye.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Modification ");
        stage.show();
    }

    @FXML
    void onclicdashboard(ActionEvent event) {

        int candidatCount = serviceUser.getTotalCandidats();
        int employeCount = serviceUser.getTotalEmployes();

        totalcandidats.getChildren().clear();
        totalemployes.getChildren().clear();

        totalcandidats.getChildren().add(new Label("Total Candidats: " + candidatCount));
        totalemployes.getChildren().add(new Label("Total Employés: " + employeCount));
    }


    @FXML
    void onclichome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GestionAdmin.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("DashBoard");
        stage.show();
    }


    @FXML
    void oncliclistecand(ActionEvent event) {
        gridpane.getChildren().clear();


        for (int i = 0; i < 9; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPercentWidth(11.1);
            gridpane.getColumnConstraints().add(colConstraints);
        }


        gridpane.add(new Label("ID"), 0, 0);
        gridpane.add(new Label("Nom"), 1, 0);
        gridpane.add(new Label("Prénom"), 2, 0);
        gridpane.add(new Label("Email"), 3, 0);
        gridpane.add(new Label("Rôle"), 5, 0);
        gridpane.add(new Label("CV"), 6, 0);
        gridpane.add(new Label("Date Candidature"), 7, 0);
        gridpane.add(new Label("Statut"), 8, 0);

        List<User> users = serviceUser.getAll();
        int row = 1;

        for (User user : users) {
            if (user instanceof Candidat) {
                Candidat candidat = (Candidat) user;

                Label idLabel = new Label(String.valueOf(candidat.getIduser()));  // Ajout de l'ID
                Label nomLabel = new Label(candidat.getNom());
                Label prenomLabel = new Label(candidat.getPrenom());
                Label emailLabel = new Label(candidat.getEmail());
                Label roleLabel = new Label(candidat.getRole());
                Label cvLabel = new Label(candidat.getCvCandidat() != null ? "Disponible" : "Non disponible");
                Label dateLabel = new Label(candidat.getDateCandidature() != null ? candidat.getDateCandidature().toString() : "Non renseignée");
                Label statutLabel = new Label(candidat.getStatutCandidat());

                GridPane.setMargin(idLabel, new Insets(5));
                GridPane.setMargin(nomLabel, new Insets(5));
                GridPane.setMargin(prenomLabel, new Insets(5));
                GridPane.setMargin(emailLabel, new Insets(5));
                GridPane.setMargin(roleLabel, new Insets(5));
                GridPane.setMargin(cvLabel, new Insets(5));
                GridPane.setMargin(dateLabel, new Insets(5));
                GridPane.setMargin(statutLabel, new Insets(5));


                gridpane.add(idLabel, 0, row);
                gridpane.add(nomLabel, 1, row);
                gridpane.add(prenomLabel, 2, row);
                gridpane.add(emailLabel, 3, row);
                gridpane.add(roleLabel, 5, row);
                gridpane.add(cvLabel, 6, row);
                gridpane.add(dateLabel, 7, row);
                gridpane.add(statutLabel, 8, row);


                Button btnSelect = new Button("Sélectionner");
                btnSelect.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informations Candidat");
                    alert.setHeaderText("Détails du candidat sélectionné");
                    alert.setContentText(
                            "ID: " + candidat.getIduser() + "\n" +  // Afficher l'ID
                                    "Nom: " + candidat.getNom() + "\n" +
                                    "Prénom: " + candidat.getPrenom() + "\n" +
                                    "Email: " + candidat.getEmail() + "\n" +
                                    "Rôle: " + candidat.getRole() + "\n" +
                                    "CV: " + (candidat.getCvCandidat() != null ? "Disponible" : "Non disponible") + "\n" +
                                    "Date de Candidature: " + (candidat.getDateCandidature() != null ? candidat.getDateCandidature().toString() : "Non renseignée") + "\n" +
                                    "Statut: " + candidat.getStatutCandidat()
                    );
                    alert.showAndWait();
                });

                gridpane.add(btnSelect, 9, row);
                row++;
            }
        }

        if (row == 1) {
            gridpane.add(new Label("Aucun candidat trouvé."), 0, 1, 9, 1); // Mettre à jour la largeur de la cellule "Aucun candidat trouvé"
        }
    }



    @FXML
    void oncliclisteemplo(ActionEvent event) {
        gridpane.getChildren().clear();


        for (int i = 0; i < 8; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPercentWidth(12.5);  // Chaque colonne occupe 12.5% de la largeur totale
            gridpane.getColumnConstraints().add(colConstraints);
        }


        gridpane.add(new Label("ID"), 0, 0);
        gridpane.add(new Label("Nom"), 1, 0);
        gridpane.add(new Label("Prénom"), 2, 0);
        gridpane.add(new Label("Email"), 3, 0);
        gridpane.add(new Label("Salaire"), 4, 0);
        gridpane.add(new Label("Date Embauche"), 5, 0);
        gridpane.add(new Label("Département"), 6, 0);
        gridpane.add(new Label("Action"), 7, 0);

        List<User> users = serviceUser.getAll();
        int row = 1;

        for (User user : users) {
            if (user instanceof Employes) {
                Employes employe = (Employes) user;

                Label idLabel = new Label(String.valueOf(employe.getIduser()));
                Label nomLabel = new Label(employe.getNom());
                Label prenomLabel = new Label(employe.getPrenom());
                Label emailLabel = new Label(employe.getEmail());
                Label salaireLabel = new Label(String.valueOf(employe.getSalaire()));
                Label dateLabel = new Label(employe.getDateEmbauche() != null ? employe.getDateEmbauche().toString() : "Non renseignée");
                Label departementLabel = new Label(employe.getDepartement());


                GridPane.setMargin(idLabel, new Insets(5));
                GridPane.setMargin(nomLabel, new Insets(5));
                GridPane.setMargin(prenomLabel, new Insets(5));
                GridPane.setMargin(emailLabel, new Insets(5));
                GridPane.setMargin(salaireLabel, new Insets(5));
                GridPane.setMargin(dateLabel, new Insets(5));
                GridPane.setMargin(departementLabel, new Insets(5));


                gridpane.add(idLabel, 0, row);
                gridpane.add(nomLabel, 1, row);
                gridpane.add(prenomLabel, 2, row);
                gridpane.add(emailLabel, 3, row);
                gridpane.add(salaireLabel, 4, row);
                gridpane.add(dateLabel, 5, row);
                gridpane.add(departementLabel, 6, row);

                // Bouton de sélection
                Button btnSelect = new Button("Sélectionner");
                btnSelect.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informations Employé");
                    alert.setHeaderText("Détails de l'employé sélectionné");
                    alert.setContentText(
                            "ID: " + employe.getIduser() + "\n" +
                                    "Nom: " + employe.getNom() + "\n" +
                                    "Prénom: " + employe.getPrenom() + "\n" +
                                    "Email: " + employe.getEmail() + "\n" +
                                    "Salaire: " + employe.getSalaire() + "€\n" +
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
            gridpane.add(new Label("Aucun employé trouvé."), 0, 1, 8, 1);
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
    void onclicsearch(ActionEvent event) {
        String keyword = search.getText();

        gridpane.getChildren().clear();

        if (keyword != null && !keyword.trim().isEmpty()) {
            List<Candidat> resultCandidats = serviceUser.searchCandidatsByKeyword(keyword);
            List<Employes> resultEmployes = serviceUser.searchEmployesByKeyword(keyword);
            List<User> combinedResults = new ArrayList<>();
            combinedResults.addAll(resultCandidats);
            combinedResults.addAll(resultEmployes);
            if (!combinedResults.isEmpty()) {
                int row = 0;
                for (User user : combinedResults) {
                    Label nameLabel = new Label(user.getNom() + " " + user.getPrenom());
                    Label emailLabel = new Label(user.getEmail());
                    Label roleLabel = new Label(user.getRole());

                    gridpane.add(nameLabel, 0, row);
                    gridpane.add(emailLabel, 1, row);
                    gridpane.add(roleLabel, 2, row);


                    row++;
                }
            } else {
                Label noResultsLabel = new Label("Aucun utilisateur trouvé.");
                gridpane.add(noResultsLabel, 0, 0);
            }
        } else {
            Label emptySearchLabel = new Label("Veuillez entrer un mot-clé.");
            gridpane.add(emptySearchLabel, 0, 0);
        }
    }


}
