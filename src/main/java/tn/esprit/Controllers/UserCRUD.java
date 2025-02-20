package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class UserCRUD {

    @FXML
    private Button gestioncandidat;

    @FXML
    private Button gestionemployes;

    @FXML
    private Button logout;

    @FXML
    void Oncliclogout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/logginUser.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login page");
        stage.show();
    }

    @FXML
    void Onclicpageemployes(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GestionEmployes.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Gestion Employes");
        stage.show();

    }

    @FXML
    void onclicpagecandidat(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Gestioncandidat.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Gestion Candidat");
        stage.show();

    }
}