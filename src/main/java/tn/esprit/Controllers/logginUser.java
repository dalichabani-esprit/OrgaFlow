package tn.esprit.Controllers;

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
import tn.esprit.interfaces.IService;
import tn.esprit.models.User;
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
    private TextField tfemailS;

    @FXML
    private Label wrongpassword;
    private final IService<User> serviceUser = new ServiceUser();
    private  Connection cnx;
    @FXML
    void OnclicShowpasswords(ActionEvent event) {
        if (chboxshowpasswords.isSelected()) {
            pfmotdepasseS.setVisible(true);
            pfmotdepasseS.setDisable(false);
        } else {
            pfmotdepasseS.setVisible(false);
            pfmotdepasseS.setDisable(true);
        }
    }
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText("Champs manquants !");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }
        if (!serviceUser.emailExiste(email)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText("Email non trouvé !");
            alert.setContentText("Aucun compte n'est associé à cet email.");
            alert.showAndWait();
            return;
        }
        if (!serviceUser.emailExiste(email)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText("Mot de passe incorrect !");
            alert.setContentText("Le mot de passe est incorrect pour cet email.");
            alert.showAndWait();
            return;
        }
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GestionAdmin.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("DashBoard");
            stage.show();
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de DashBoard.fxml : " + e.getMessage());
        }
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



