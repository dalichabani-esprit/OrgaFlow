package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.web.WebView;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Employes;
import tn.esprit.models.User;
import tn.esprit.services.RecaptchaAPI;
import tn.esprit.services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AjouterUser implements Initializable {

    @FXML
    private Button btcreercompte, btidentifier;
    @FXML
    private ComboBox<String> cbrole;
    @FXML
    private WebView recaptchaWebView;
    @FXML
    private CheckBox chboxshowpassword;
    @FXML
    private PasswordField pfmotdepasse;
    @FXML
    private TextField tfemail, tfnom, tfprenom;

    private final IService<User> serviceUser = new ServiceUser();
    private String recaptchaResponse = "";

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
        btcreercompte.setDisable(true);
        String recaptchaUrl = "http://localhost/Recaptcha_form.html";
        recaptchaWebView.getEngine().load(recaptchaUrl);
        recaptchaWebView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                System.out.println("Le WebView est chargé !");
            } else if (newState == Worker.State.FAILED) {
                System.out.println("Le WebView a échoué à se charger.");
            }
        });
    }
    @FXML
    private void Oncliclogup(ActionEvent event) {
        String recaptchaResponse = (String) recaptchaWebView.getEngine().executeScript("grecaptcha.getResponse()");

        if (recaptchaResponse == null || recaptchaResponse.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Vérification reCAPTCHA échouée");
            alert.setContentText("Veuillez compléter le reCAPTCHA.");
            alert.showAndWait();
            return;
        }
        recaptchaWebView.getEngine().executeScript("document.getElementById('recaptcha-container').style.display = 'none';");
        try {
            RecaptchaAPI.verifyCaptcha(recaptchaResponse);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Échec de la validation reCAPTCHA");
            alert.setContentText("La validation du reCAPTCHA a échoué. Essayez de rafraîchir la page.");
            alert.showAndWait();
            return;
        }
        if ("employe".equals(cbrole.getValue())) {
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
            serviceUser.add(employe);
            System.out.println("Utilisateur ajouté : " + employe);
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/UserCRUD.fxml"));
                Stage stage = (Stage) tfemail.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Gestion Utilisateur");
                stage.show();
            } catch (IOException e) {
                System.out.println("Erreur lors du chargement de UserCRUD.fxml : " + e.getMessage());
            }
        }
    }

    public void setRecaptchaResponse(String token) {
        if (token != null && !token.isEmpty()) {
            recaptchaResponse = token;
            // Activer le bouton de soumission
            btcreercompte.setDisable(false);
        }
    }
}
