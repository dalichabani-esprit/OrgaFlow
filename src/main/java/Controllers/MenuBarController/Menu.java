package Controllers.MenuBarController;

import Controllers.Formateur.AfficherFormateurs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Menu {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private void GoToAffciherFormations (ActionEvent event) {
        changerScene(event, "/AfficherFormations.fxml");
    }

    @FXML
    private void GoToMenu (ActionEvent event) {
        changerScene(event, "/Menu/Menu.fxml");
    }

    @FXML
    private void GoToformateurs(ActionEvent event) {
        changerScene(event, "/Formateurs/AfficherFormateurs.fxml");
    }

    private void changerScene(ActionEvent event, String cheminFXML) {
        try {
            root = FXMLLoader.load(getClass().getResource(cheminFXML));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void GoToTheHome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu/Home.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();



        } catch (IOException ex) {
            Logger.getLogger(AfficherFormateurs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
