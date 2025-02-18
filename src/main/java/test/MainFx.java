package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Charger le fichier FXML
       // FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterFormateur.fxml"));
       // FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterFormation.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherFormation.fxml"));


        try {
            Parent root = loader.load(); // Charger le fichier FXML

            // Créer la scène avec le fichier FXML chargé
            Scene scene = new Scene(root);

            // Définir la scène dans le stage principal
            primaryStage.setScene(scene);

            // Définir le titre de la fenêtre
            primaryStage.setTitle("Ajouter Formateur");

            // Afficher la fenêtre
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Afficher l'erreur dans la console
            throw new RuntimeException("Erreur de chargement du fichier FXML", e);
        }
    }
}
