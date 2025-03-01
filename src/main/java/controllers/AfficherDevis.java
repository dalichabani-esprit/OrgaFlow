package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.devis;
import services.ServiceDevis;

import java.io.IOException;
import java.util.List;

public class AfficherDevis {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private VBox devisVBox;

    private ServiceDevis serviceDevis;

    public AfficherDevis() {
        serviceDevis = new ServiceDevis();
    }

    @FXML
    public void initialize() {
        // Fetch the list of devis from the service
        List<devis> devisList = serviceDevis.getAll();

        // Iterate through the devis and add them to the VBox
        for (devis d : devisList) {
            Text devisText = new Text();
            devisText.setText("ID: " + d.getId_devis() + " - Montant Estimé: " + d.getMontant_estime() +
                    " - Date: " + d.getDate_devis() + " - Statut: " + d.getStatut());
            devisVBox.getChildren().add(devisText);
        }
    }

    @FXML
    private void Retour(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/home.fxml"));
        switchScene(event, root);
    }

    // Méthode auxiliaire pour changer de scène
    private void switchScene(ActionEvent event, Parent root) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
