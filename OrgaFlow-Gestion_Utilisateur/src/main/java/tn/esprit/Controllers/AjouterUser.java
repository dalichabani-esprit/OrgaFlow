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
import java.util.Random;
import java.util.ResourceBundle;

public class AjouterUser implements Initializable {

    @FXML
    private Button btcreercompte, btidentifier;
    @FXML
    private ComboBox<String> cbrole;
    @FXML
    private PasswordField pfmotdepasse;
    @FXML
    private TextField tfemail, tfnom, tfprenom, captchaTextField;
    private final IService<User> serviceUser = new ServiceUser();
    @FXML
    private Label captchaLabel;
    private String generatedCaptchaText;

    @FXML
    public void Onclicloginpage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/logginUser.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Login page");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbrole.setItems(FXCollections.observableArrayList("employe"));
        generateCaptchaText();
        displayCaptcha();
    }
    private void generateCaptchaText() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder captcha = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            captcha.append(characters.charAt(random.nextInt(characters.length())));
        }
        generatedCaptchaText = captcha.toString();
    }
    private void displayCaptcha() {
        captchaLabel.setText(generatedCaptchaText);
    }

    @FXML
    private void Oncliclogup(ActionEvent event) throws IOException {
        String userCaptcha = captchaTextField.getText();
        if (userCaptcha.equals(generatedCaptchaText)) {  // Vérification du CAPTCHA local
            Employes employe = new Employes();
            employe.setEmail(tfemail.getText());
            employe.setNom(tfnom.getText());
            employe.setPrenom(tfprenom.getText());
            employe.setMotDePasse(pfmotdepasse.getText());
            employe.setRole("employe");

            if (serviceUser.emailExiste(employe.getEmail())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur d'inscription");
                alert.setHeaderText("Email déjà utilisé !");
                alert.setContentText("L'email " + employe.getEmail() + " est déjà associé à un compte.");
                alert.showAndWait();
                return;
            }
            serviceUser.add(employe);
            System.out.println("Utilisateur ajouté : " + employe);
            Parent root = FXMLLoader.load(getClass().getResource("/UserCRUD.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("User CRUD");
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur CAPTCHA");
            alert.setHeaderText("Code CAPTCHA incorrect");
            alert.setContentText("Le code CAPTCHA que vous avez saisi est incorrect. Veuillez réessayer.");
            alert.showAndWait();
        }
    }


}
