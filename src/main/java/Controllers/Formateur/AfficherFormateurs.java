package Controllers.Formateur;


import Controllers.Formation.AfficherFormations;
import Controllers.Formation.ModifierFormation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Formateur;
import services.ServiceFormateur;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

public class AfficherFormateurs implements Initializable {
    @FXML
    private TextField searchField;

    private ObservableList<Formateur> formateursList;
    Preferences prefs = Preferences.userNodeForPackage(AfficherFormateurs.class);
    @FXML
    private ListView<Formateur> formateursListView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        ServiceFormateur sf = new ServiceFormateur();
        formateursListView.setCellFactory(param -> new ListFormateursCell() );
        ServiceFormateur serviceFormateur = new ServiceFormateur();
        List<Formateur> formateurs = serviceFormateur.getAll();

        //pour la recherche
        formateursList = FXCollections.observableArrayList(formateurs);

        ObservableList<Formateur> items = FXCollections.observableArrayList(formateurs);
        formateursListView.setItems(items);
        // Ajouter les éléments de la liste à la ListView
        formateursListView.setItems(items);
        // 2. Créez une ArrayList de maps pour stocker les attributs de chaque Formateur
        formateursListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            // Récupérer l'index de l'élément sélectionné
            int selectedIndex = newValue.intValue();
            // Récupérer l'objet Hotel correspondant à cet index
            Formateur selectedFormateur = formateurs.get(selectedIndex);
            // Récupérer l'ID de l'hôtel
            int FormateurId = selectedFormateur.getIdFormateur();
            prefs.putInt("selectedFormateurId", FormateurId);
            System.out.println(FormateurId+"--------------------------");

        });
    }

    //recherche :
    @FXML
    private void rechercherFormateur() {
        String searchText = searchField.getText().toLowerCase();

        // Filtrer les Formateurs
        List<Formateur> filteredList = formateursList.stream()
                .filter(f -> f.getNom().toLowerCase().contains(searchText) ||
                        f.getPrenom().toLowerCase().contains(searchText)||
                        f.getSpecialite().toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        // Mettre à jour la ListView
        if (filteredList.isEmpty()) {
            System.out.println("Aucune Formateur trouvée !");
            // Option : FormateursListView.setItems(FormateursList); // Garder la liste actuelle
        } else {
            formateursListView.setItems(FXCollections.observableArrayList(filteredList));
        }
        formateursListView.refresh();

    }

    @FXML
    private void ajouterFormateur(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterFormateur.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
            rafraichirListeFormateurs();
        } catch (IOException ex) {
            Logger.getLogger(AfficherFormateurs.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    @FXML
    private void modifierFormateur(MouseEvent event) {
        Formateur selectedFormateur = formateursListView.getSelectionModel().getSelectedItem();
        Formateur f1 = new Formateur();
        ServiceFormateur rs = new ServiceFormateur();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Formateurs/ModifierFormateur.fxml"));
            Parent root = loader.load();
            ModifierFormateur modifierFormateur = loader.getController();
            f1=selectedFormateur;
            modifierFormateur.ModifyData(f1);
            System.out.println(f1);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
            rafraichirListeFormateurs();
        } catch (IOException e) {
            e.printStackTrace();
        }}



    // Ajouter une méthode pour recharger la liste des Formateurs
    private void rafraichirListeFormateurs() {
        ServiceFormateur serviceFormateur = new ServiceFormateur();
        List<Formateur> ReflechedListFormateur = serviceFormateur.getAll();
        ObservableList<Formateur> items = FXCollections.observableArrayList(ReflechedListFormateur);
        formateursListView.setItems(items);
    }
    @FXML
    private void GoToTheMenu(ActionEvent event) {
        try {
            // Charger la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu/Menu.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle fenêtre (stage)
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            // Rafraîchir la liste des formateurs (si nécessaire)
            rafraichirListeFormateurs();

        } catch (IOException ex) {
            Logger.getLogger(AfficherFormateurs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
