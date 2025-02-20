package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.User;

import java.io.IOException;


public class AjouterUser {

    @FXML
    private Button btcreercompte;

    @FXML
    private Button btidentifier;

    @FXML
    private ComboBox<?> cbrole;

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

    @FXML
    public void OnclicShowpassword(ActionEvent event) {
        if (chboxshowpassword.isSelected()) {
            pfmotdepasse.setVisible(true);
            pfmotdepasse.setDisable(false);
        } else {
            pfmotdepasse.setVisible(false);
            pfmotdepasse.setDisable(true);
        }
    }


    @FXML
    public void Onclicloginpage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/logginUser.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



   @FXML
    void Oncliclogup(ActionEvent event) {

    }

}
