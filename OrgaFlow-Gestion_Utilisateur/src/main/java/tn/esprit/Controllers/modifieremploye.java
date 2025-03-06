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
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Employes;
import tn.esprit.models.User;
import tn.esprit.services.ServiceUser;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class modifieremploye implements Initializable {

    @FXML
    private Button afficheremployes;

    @FXML
    private ComboBox<String> cbrole;
    @FXML
    private VBox Vbox;
    @FXML
    private DatePicker dateembauche;

    @FXML
    private TextField departement;

    @FXML
    private TextField idemploye;

    @FXML
    private Button logoutS;

    @FXML
    private Button modifieremploye;

    @FXML
    private PasswordField pfmotdepasse;

    @FXML
    private TextField salaire;

    @FXML
    private Button supprimeremploye;

    @FXML
    private TextField tfemail;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfprenom;
    private final IService<User> serviceUser = new ServiceUser();
    @FXML
    void Onclickaafficheremploye(ActionEvent event) {
        Vbox.getChildren().clear();

        List<User> users = serviceUser.getAll();

        boolean found = false;

        for (User user : users) {
            if (user instanceof Employes) {
                found = true;
                Employes employe = (Employes) user;

                String employeInfo = "ID: " + employe.getIduser() +
                        "\tNom: " + employe.getNom() +
                        "\tPrénom: " + employe.getPrenom() +
                        "\tEmail: " + employe.getEmail() +
                        "\tMot de passe: " + employe.getMotDePasse() +
                        "\tRôle: " + employe.getRole() +
                        "\talaire: " + employe.getSalaire() +
                        "\tDate Embauche: " + (employe.getDateEmbauche() != null ? employe.getDateEmbauche().toString() : "Non renseignée") +
                        "\tDépartement: " + employe.getDepartement();

                Label label = new Label(employeInfo);


                Vbox.getChildren().add(label);
            }
        }

        if (!found) {
            Label noDataLabel = new Label("Aucun employé trouvé.");
            Vbox.getChildren().add(noDataLabel);
        }
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
    void onclickLogout(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GestionEmployes.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbrole.getItems().addAll("employe");
    }
}
