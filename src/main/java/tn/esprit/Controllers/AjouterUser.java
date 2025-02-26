package tn.esprit.Controllers;

import javafx.collections.FXCollections;
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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AjouterUser implements Initializable {

    @FXML
    private Button btcreercompte;
    @FXML
    private Button btidentifier;
    @FXML
    private ComboBox<String> cbrole;

    @FXML
    private CheckBox chboxshowpassword;
    @FXML
    private PasswordField pfmotdepasse;
    @FXML
    private TextField tfemail;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;


    private final IService<User> serviceUser = new ServiceUser();

    @FXML
    public void OnclicShowpassword(ActionEvent event) {

    }

    @FXML
    public void Onclicloginpage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/logginUser.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login page");
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbrole.setItems(FXCollections.observableArrayList("admin","employe"));

    }
    @FXML
    public void Oncliclogup(ActionEvent event) {
        if ("admin".equals(cbrole.getValue())) {
            // Création de l'objet Admin
            User user = new User();
            user.setEmail(tfemail.getText());
            user.setNom(tfnom.getText());
            user.setPrenom(tfprenom.getText());
            user.setRole(cbrole.getSelectionModel().getSelectedItem());
            user.setMotDePasse(pfmotdepasse.getText());

            // Vérifier si l'email existe déjà
            if (serviceUser.emailExiste(user.getEmail())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur d'inscription");
                alert.setHeaderText("Email déjà utilisé !");
                alert.setContentText("L'email " + user.getEmail() + " est déjà associé à un compte.");
                alert.showAndWait();
                return;
            }

            System.out.println("Ajout de Admin : " + user);
            serviceUser.add(user);
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/GestionAdmin.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Gestion Utilisateur");
                stage.show();
            } catch (IOException e) {
                System.out.println("Erreur lors du chargement de GestionAdmin.fxml : " + e.getMessage());
            }

        } else if ("employe".equals(cbrole.getValue())) {
            // Création de l'objet Employe
            Employes employe = new Employes();
            employe.setEmail(tfemail.getText());
            employe.setNom(tfnom.getText());
            employe.setPrenom(tfprenom.getText());
            employe.setRole(cbrole.getSelectionModel().getSelectedItem());
            employe.setMotDePasse(pfmotdepasse.getText());


             if (serviceUser.emailExiste(employe.getEmail())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur d'inscription");
                alert.setHeaderText("Email déjà utilisé !");
                alert.setContentText("L'email " + employe.getEmail() + " est déjà associé à un compte.");
                alert.showAndWait();
                return;
            }

            System.out.println("Ajout d'Employé : " + employe);
            serviceUser.add(employe);
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/UserCRUD.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Gestion Utilisateur");
                stage.show();
            } catch (IOException e) {
                System.out.println("Erreur lors du chargement de UserCRUD.fxml : " + e.getMessage());
            }
        }
    }








}
