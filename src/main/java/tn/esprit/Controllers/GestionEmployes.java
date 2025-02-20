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
import tn.esprit.models.Employes;
import tn.esprit.models.User;
import tn.esprit.services.ServiceUser;
import javafx.scene.control.ListView;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class GestionEmployes implements Initializable {

    @FXML
    private Button Ajouteremployes;

    @FXML
    private Button afficheremployes;

    @FXML
    private ComboBox<String> cbrole;

    @FXML
    private Button chercheremploye;

    @FXML
    private DatePicker dateembauche;
    @FXML
    private ListView<String> listview;
    @FXML
    private TextField departement;

    @FXML
    private Label labelvieuw;

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
        List<User> users = serviceUser.getAll();
        ObservableList<String> employeList = FXCollections.observableArrayList();

        for (User user : users) {
            if (user instanceof Employes) {
              Employes employe = (Employes) user;
                String employeInfo = "ID: " + employe.getIduser() +
                        "\nNom: " + employe.getNom() +
                        "\nPrénom: " + employe.getPrenom() +
                        "\nEmail: " + employe.getEmail() +
                        "\nMot de passe: " + employe.getMotDePasse() +
                        "\nRôle: " + employe.getRole() +
                        "\nSalaire: " + employe.getSalaire() +
                        "\nDate Embauche " + (employe.getDateEmbauche() != null ? employe.getDateEmbauche().toString() : "Non renseignée") +
                        "\nDepartement: " + employe.getDepartement();
                employeList.add(employeInfo);
            }
        }
        if (employeList.isEmpty()) {
            employeList.add("Aucun candidat trouvé.");
        }

        listview.setItems(employeList);
    }

    @FXML
    void Onclickajouteremploye(ActionEvent event) {

        if ("employe".equals(cbrole.getValue())) {
            Employes employe = new Employes();
            employe.setNom(tfnom.getText());
            employe.setPrenom(tfprenom.getText());
            employe.setEmail(tfemail.getText());
            employe.setMotDePasse(pfmotdepasse.getText());
            employe.setRole(cbrole.getValue());
            employe.setDateEmbauche(Date.valueOf(dateembauche.getValue()));
            employe.setDepartement(departement.getText());
            employe.setSalaire(salaire.getText());

            System.out.println("Ajout de l'employé : " + employe);
            serviceUser.add(employe);
        }
    }

    @FXML
    void Onclickmodifieremploye(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/modifieremploye.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Modification et Suppression");
        stage.show();

    }
    @FXML
    void Onclicksupprimeremploye(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/modifieremploye.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Modification et Suppression");
        stage.show();

    }

    @FXML
    void onclickLogout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/UserCRUD.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Gestion Utilisateurs");
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbrole.getItems().addAll("employe");
    }
}
