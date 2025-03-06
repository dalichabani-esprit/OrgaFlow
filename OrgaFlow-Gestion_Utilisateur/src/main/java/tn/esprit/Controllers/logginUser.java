package tn.esprit.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import org.json.JSONObject;
import tn.esprit.interfaces.IService;
import tn.esprit.models.User;
import tn.esprit.services.GoogleAuthService;
import tn.esprit.services.ServiceUser;




public class logginUser {


    @FXML
    private Button btcreercomptes;

    @FXML
    private CheckBox chboxshowpasswords;

    @FXML
    private Hyperlink forgotpass;
    @FXML
    private Hyperlink logup;
    @FXML
    private PasswordField pfmotdepasseS;
    @FXML
    private Hyperlink loginwithgoogle;
    @FXML
    private TextField tfemailS;

    @FXML
    private Label wrongpassword;
    private final IService<User> serviceUser = new ServiceUser();
    private  Connection cnx;

    @FXML
    void Onclicforgotpass(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ForgotPassword.fxml"));
            Stage stage = (Stage) forgotpass.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Oncliclogin(ActionEvent event) {
        String email = tfemailS.getText();
        String motDePasse = pfmotdepasseS.getText();
        if (email.isEmpty() || motDePasse.isEmpty()) {
            showAlert("Erreur de connexion", "Champs manquants !", "Veuillez remplir tous les champs.");
            return;
        }
        if (!serviceUser.emailExiste(email)) {
            showAlert("Erreur de connexion", "Email non trouvé !", "Aucun compte n'est associé à cet email.");
            return;
        }
        if (!serviceUser.isMotDePasseCorrect(email, motDePasse)) {
            showAlert("Erreur de connexion", "Mot de passe incorrect !", "Le mot de passe est incorrect pour cet email.");
            return;
        }

        String role = serviceUser.getRoleByEmail(email);
        System.out.println("Rôle récupéré pour l'utilisateur : " + role);
        if (role == null || role.isEmpty()) {
            showAlert("Erreur de connexion", "Rôle non défini", "Votre compte n'a pas de rôle attribué.");
            return;
        }

        try {
            Parent root;
            if ("admin".equalsIgnoreCase(role)) {
                root = FXMLLoader.load(getClass().getResource("/AdminCrud.fxml"));
                System.out.println("Connexion réussie en tant qu'admin");
            } else if ("employe".equalsIgnoreCase(role)) {
                root = FXMLLoader.load(getClass().getResource("/UserCRUD.fxml"));
                System.out.println("Connexion réussie en tant qu'employé");
            } else {
                showAlert("Erreur de connexion", "Rôle inconnu", "Votre rôle n'est pas reconnu.");
                return;
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Tableau de bord");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Problème technique", "Impossible de charger la page.");
        }
    }


    @FXML
    void Onclicloginwithgoogle(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        GoogleAuthService googleAuthService = new GoogleAuthService();
        googleAuthService.authenticateUser(primaryStage);
    }

    public  void processGoogleLogin(JSONObject userInfo, Stage primaryStage) {
        if (userInfo == null) {
            System.err.println("Erreur : Impossible de récupérer les informations utilisateur.");
            return;
        }

        String email = userInfo.getString("email");
        String nom = userInfo.getString("name");

        Platform.runLater(() -> {
            if (!serviceUser.emailExiste(email)) {
                showAlert("Erreur de connexion", "Compte inexistant", "Aucun compte n'est associé à cet email.");
                return;
            }

            String role = serviceUser.getRoleByEmail(email);
            if (role == null || role.isEmpty()) {
                showAlert("Erreur de connexion", "Rôle non défini", "Votre compte n'a pas de rôle attribué.");
                return;
            }

            showAlert("Succès", "Connexion réussie", "Bienvenue, " + nom + " !");

            try {
                Parent root;
                if ("admin".equalsIgnoreCase(role)) {
                    root = FXMLLoader.load(getClass().getResource("/AdminCrud.fxml"));
                } else if ("employe".equalsIgnoreCase(role)) {
                    root = FXMLLoader.load(getClass().getResource("/UserCRUD.fxml"));
                } else {
                    showAlert("Erreur de connexion", "Rôle inconnu", "Votre rôle n'est pas reconnu.");
                    return;
                }

                primaryStage.setScene(new Scene(root));
                primaryStage.setTitle("Tableau de bord");
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur", "Problème technique", "Impossible de charger la page.");
            }
        });
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void Oncliclogup(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AjouterUser.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Creation de compte");
        stage.show();

    }



}



