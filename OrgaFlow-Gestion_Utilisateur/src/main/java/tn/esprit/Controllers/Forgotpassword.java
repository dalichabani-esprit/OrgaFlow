package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.utils.EmailAPI;
import tn.esprit.utils.MyDatabase;
import tn.esprit.utils.PasswordResetDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class Forgotpassword {

    @FXML
    private Button btrecevoirCode;

    @FXML
    private Button retour;

    @FXML
    private TextField tfemails;

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private String generateResetCode() {
        Random rand = new Random();
        int code = 100000 + rand.nextInt(900000);
        return String.valueOf(code);
    }
    private int getUserIdByEmail(String email) {
        String sql = "SELECT iduser FROM utilisateur WHERE email = ?";
        try (Connection cnx = MyDatabase.getInstance().getCnx();
             PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("iduser");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public void Onclicretour(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/logginUser.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Connexion");
        stage.show();
    }

    public void Onclicrecevoirliengmail(ActionEvent actionEvent) {
        String email = tfemails.getText().trim();

        if (email.isEmpty()) {
            showAlert("Erreur", "Veuillez entrer un email.");
            return;
        }
        int userId = getUserIdByEmail(email);
        if (userId == -1) {
            showAlert("Erreur", "Aucun compte trouvé avec cet email.");
            return;
        }
        String resetCode = generateResetCode();
        PasswordResetDAO.saveResetCode(email, resetCode);
        EmailAPI.sendPasswordResetEmail(email, resetCode);
        showAlert("Succès", "Un code de réinitialisation a été envoyé à votre email.");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ResetPasswordController.fxml"));
            Parent root = loader.load();
            ResetPasswordController resetController = loader.getController();
            resetController.setUserEmail(email);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Réinitialisation du mot de passe");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
