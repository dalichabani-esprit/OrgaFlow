package tn.esprit.Controllers;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Employes;
import tn.esprit.models.User;
import tn.esprit.services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class Addemploye implements Initializable {

    @FXML
    private Button addcandidat;

    @FXML
    private Button addemployes;

    @FXML
    private ComboBox<String> cbrole;

    @FXML
    private Button dashboard;

    @FXML
    private DatePicker dateembauche;

    @FXML
    private TextField departement;
    @FXML
    private Button supprimeremploye;
    @FXML
    private Button modifieremploye;

    @FXML
    private Button home;

    @FXML
    private Button listcandidat;

    @FXML
    private Button listeemp;

    @FXML
    private Button logout;

    @FXML
    private PasswordField pfmotdepasse;

    @FXML
    private TextField salaire;

    @FXML
    private TextField search;

    @FXML
    private TextField tfemail;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfprenom;
    private final IService<User> serviceUser = new ServiceUser();
    @FXML
    void onclicaddcandidat(ActionEvent event) {

    }

    @FXML
    void onclicaddemployes(ActionEvent event) {

        if ("employe".equals(cbrole.getValue())) {
            Employes employe = new Employes();
            employe.setNom(tfnom.getText());
            employe.setPrenom(tfprenom.getText());
            employe.setEmail(tfemail.getText());
            employe.setMotDePasse(pfmotdepasse.getText());
            employe.setRole((String) cbrole.getValue());
            employe.setDateEmbauche(Date.valueOf(dateembauche.getValue()));
            employe.setDepartement(departement.getText());
            employe.setSalaire(salaire.getText());

            System.out.println("Ajout de l'employé : " + employe);
            serviceUser.add(employe);
        }

    }

    @FXML
    void onclicdashboard(ActionEvent event) {

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
    void Onclickmodifieremploye(ActionEvent event) {
        try {
            if (tfemail.getText().isEmpty() || departement.getText().isEmpty() || salaire.getText().isEmpty()) {
                System.out.println("Veuillez remplir tous les champs !");
                return;
            }

            String emailEmploye = tfemail.getText().trim();
            String nouveauDepartement = departement.getText().trim();
            String nouveauSalaire = salaire.getText().trim();  // Reste un String

            // Récupération de l'employé par email
            Employes employe = (Employes) serviceUser.getByEmail(emailEmploye);
            if (employe == null) {
                System.out.println("Employé non trouvé avec l'email : " + emailEmploye);
                return;
            }

            // Mise à jour des informations
            employe.setDepartement(nouveauDepartement);
            employe.setSalaire(nouveauSalaire);  // Conserve String

            serviceUser.update(employe);
            System.out.println(" Mise à jour réussie pour l'employé : " + emailEmploye);
        } catch (Exception e) {
            System.out.println(" Erreur lors de la mise à jour : " + e.getMessage());
        }
    }

    @FXML
    void Onclicksupprimeremploye(ActionEvent event) {
        try {
            if (tfemail.getText().isEmpty()) {
                System.out.println(" Veuillez entrer l'email de l'employé à supprimer !");
                return;
            }

            String emailEmploye = tfemail.getText().trim();

            // Récupération de l'employé par email
            Employes employe = (Employes) serviceUser.getByEmail(emailEmploye);
            if (employe == null) {
                System.out.println(" Aucun employé trouvé avec cet email !");
                return;
            }

            // Suppression de l'employé
            serviceUser.delete(employe);
            System.out.println(" Employé supprimé avec succès : " + emailEmploye);

            // Nettoyage des champs après suppression
            tfemail.clear();
            departement.clear();
            salaire.clear();
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
    @FXML
    void oncliclistecand(ActionEvent event) {

    }

    @FXML
    void oncliclisteemplo(ActionEvent event) {

    }

    @FXML
    void oncliclogout(ActionEvent event) {

    }

    @FXML
    void onclicsearch(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbrole.getItems().addAll("employe");
    }
}
