package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.services.PasswordResetService;

import java.io.IOException;
import java.util.regex.Pattern;

public class ResetPasswordController {

    @FXML
    private TextField coderei;

    @FXML
    private PasswordField confirmpassfield;

    @FXML
    private PasswordField passwordfield;

    @FXML
    private Button retour;

    @FXML
    private Button valider;

    @FXML
    void onclicreretour(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/logginUser.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Connexion");
        stage.show();
    }
    public void setUserEmail(String email) {
        System.out.println("Email reçu : " + email);
    }

    @FXML
    void onclicvalider(ActionEvent event) {
        String code = coderei.getText().trim();
        String newPassword = passwordfield.getText().trim();
        String confirmPassword = confirmpassfield.getText().trim();

        if (code.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs !");
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Les mots de passe ne correspondent pas !");
            return;
        }
        if (!isValidPassword(newPassword)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le mot de passe doit contenir au moins 8 caractères, une majuscule, un chiffre et un caractère spécial.");
            return;
        }
        String email = PasswordResetService.getEmailByCode(code);
        if (email == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Code invalide ou expiré !");
            return;
        }
        boolean success = PasswordResetService.resetPassword(email, code, newPassword);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Mot de passe réinitialisé avec succès !");
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/logginUser.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Connexion");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la réinitialisation du mot de passe.");
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        return Pattern.matches(passwordRegex, password);
    }
}
