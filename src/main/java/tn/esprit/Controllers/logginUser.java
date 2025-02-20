package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import tn.esprit.models.User;

public class logginUser {
    @FXML
    private Button btcreercomptes;

    @FXML
    private CheckBox chboxshowpasswords;

    @FXML
    private Hyperlink forgotpass;

    @FXML
    private PasswordField pfmotdepasseS;

    @FXML
    private TextField tfemailS;

    @FXML
    private Label wrongpassword;

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
    void Oncliclogin(ActionEvent event) {
        checkLoggin();
    }

    private void checkLoggin() {
        String email = tfemailS.getText().trim();
        String password = pfmotdepasseS.isVisible() ? pfmotdepasseS.getText().trim() : pfmotdepasseS.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            wrongpassword.setText("Veuillez entrer vos informations.");
            wrongpassword.setStyle("-fx-text-fill: red;");
            return;
        }

        // Vérification statique (exemple) - Remplace cela par une vérification avec la base de données
        if (email.equals("javacoding") && password.equals("123")) {
            wrongpassword.setText("Connexion réussie !");
            wrongpassword.setStyle("-fx-text-fill: green;");

            try {
                Parent root = FXMLLoader.load(getClass().getResource("/UserCRUD.fxml"));
                Stage stage = (Stage) btcreercomptes.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            wrongpassword.setText("Email ou mot de passe incorrect.");
            wrongpassword.setStyle("-fx-text-fill: red;");
        }
    }
}
