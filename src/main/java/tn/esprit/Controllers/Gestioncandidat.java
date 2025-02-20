package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.User;
import tn.esprit.models.Candidat;
import tn.esprit.services.ServiceUser;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;

import java.util.List;
import java.util.ResourceBundle;


public class Gestioncandidat implements Initializable {

    @FXML
    private Button onclicaffiche;

    @FXML
    private Button ajoutercandidat;
    @FXML
    private VBox Vbox;
    @FXML
    private ComboBox<String> cbrole;

    @FXML
    private Button cherchercandidat;

    @FXML
    private TextField cvcandidat;

    @FXML
    private DatePicker datecandi;



    @FXML
    private Button logout;

    @FXML
    private Button modifiercandidat;



    @FXML
    private PasswordField pfmotdepasse;

    @FXML
    private PasswordField salaire;

    @FXML
    private TextField statutcandidat;

    @FXML
    private Button supprimercandidat;

    @FXML
    private TextField tfemail;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfprenom;


    private final IService<User> serviceUser = new ServiceUser();


    public void onclicaffiche(ActionEvent actionEvent) {
        Vbox.getChildren().clear(); // Nettoyer le contenu précédent

        List<User> users = serviceUser.getAll();
        boolean found = false;

        for (User user : users) {
            if (user instanceof Candidat) {
                found = true;
                Candidat candidat = (Candidat) user;

                String candidatInfo = "ID: " + candidat.getIduser() +
                        "\tNom: " + candidat.getNom() +
                        "\tPrénom: " + candidat.getPrenom() +
                        "\tEmail: " + candidat.getEmail() +
                        "\tMot de passe: " + candidat.getMotDePasse() +
                        "\tRôle: " + candidat.getRole() +
                        "\tCV: " + candidat.getCvCandidat() +
                        "\tDate de candidature: " + (candidat.getDateCandidature() != null ? candidat.getDateCandidature().toString() : "Non renseignée") +
                        "\tStatut: " + candidat.getStatutCandidat();

                Label label = new Label(candidatInfo);
                Vbox.getChildren().add(label);
            }
        }

        if (!found) {
            Label noDataLabel = new Label("Aucun candidat trouvé.");
            Vbox.getChildren().add(noDataLabel);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
  cbrole.getItems().addAll("candidat");
    }
    @FXML
    void onclicajoutecandidat(ActionEvent event) {

        if ("candidat".equals(cbrole.getValue())) {

            Candidat candidat = new Candidat();
            candidat.setNom(tfnom.getText());
            candidat.setPrenom(tfprenom.getText());
            candidat.setEmail(tfemail.getText());
            candidat.setMotDePasse(pfmotdepasse.getText());
            candidat.setRole(cbrole.getValue());
            candidat.setCvCandidat(cvcandidat.getText());
            candidat.setDateCandidature(Date.valueOf(datecandi.getValue()));
            candidat.setStatutCandidat(statutcandidat.getText());

            System.out.println("Ajout du candidat : " + candidat);
            serviceUser.add(candidat);

       }
    }



    @FXML
    void oncliclogout(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/UserCRUD.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Gestion Utilisateurs");
        stage.show();

    }

    @FXML
    void onclicmodifier(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/modifiercandidat.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Modification ");
        stage.show();

    }

    @FXML
    void onclicsupprimecandiddat(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/modifiercandidat.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Modification et Suppression");
        stage.show();

    }
}
