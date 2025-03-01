package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args); // Démarre l'application
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger le fichier FXML de la page principale "home.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sidebar.fxml"));
            Parent root = loader.load(); // Charger la vue

            // Créer la scène et la mettre dans la fenêtre principale
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Home"); // Titre de la fenêtre

            // Ajouter une icône si tu en as une (facultatif)
            // Image image = new Image("path/to/your/icon.png");
            // primaryStage.getIcons().add(image);

            primaryStage.show(); // Afficher la fenêtre
        } catch (IOException e) {
            System.out.println("Erreur de chargement du fichier FXML : " + e.getMessage());
        }
    }
}
