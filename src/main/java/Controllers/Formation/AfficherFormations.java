package Controllers.Formation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Formation;
import services.ServiceFormation;

import java.util.logging.Level;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

public class AfficherFormations implements Initializable {
    @FXML
    private TextField searchField;

    private ObservableList<Formation> formationsList;

    @FXML
    private ListView<Formation> formationsListView;
    Preferences prefs = Preferences.userNodeForPackage(AfficherFormations.class);

    // Initialize method to load formations when the controller is loaded
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ServiceFormation sf = new ServiceFormation();



        formationsListView.setCellFactory(param -> new ListFormationCell());
        ServiceFormation serviceFormation = new ServiceFormation();
        List<Formation> formations = serviceFormation.getAll();

        //pour la recherche
        formationsList = FXCollections.observableArrayList(formations);

        ObservableList<Formation> items = FXCollections.observableArrayList(formations);
        formationsListView.setItems(items);
        // Ajouter les éléments de la liste à la ListView
        formationsListView.setItems(items);
        // 2. Créez une ArrayList de maps pour stocker les attributs de chaque hôtel
        formationsListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            // Récupérer l'index de l'élément sélectionné
            int selectedIndex = newValue.intValue();
            // Récupérer l'objet Hotel correspondant à cet index
            Formation selectedFormation = formations.get(selectedIndex);
            // Récupérer l'ID de l'hôtel
            int FormationId = selectedFormation.getIdFormation();
            prefs.putInt("selectedFormationId", FormationId);
            System.out.println(FormationId+"--------------------------");

        });
    }

    //recherche :
    @FXML
    private void rechercherFormation() {
        String searchText = searchField.getText().toLowerCase();

        // Filtrer les formations
        List<Formation> filteredList = formationsList.stream()
                .filter(f -> f.getNom().toLowerCase().contains(searchText) ||
                        f.getCategorie().toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        // Mettre à jour la ListView
        if (filteredList.isEmpty()) {
            System.out.println("Aucune formation trouvée !");
            // Option : formationsListView.setItems(formationsList); // Garder la liste actuelle
        } else {
            formationsListView.setItems(FXCollections.observableArrayList(filteredList));
        }
    }




    @FXML
    private void ajouterFormation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterFormation.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(AfficherFormations.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    @FXML
    private void modifierFormation(MouseEvent event) {
        Formation selectedFormation = formationsListView.getSelectionModel().getSelectedItem();
        Formation r1 = new Formation();
        ServiceFormation rs = new ServiceFormation();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierFormation.fxml"));
            Parent root = loader.load();
            ModifierFormation modifierReservationController = loader.getController();
            r1=selectedFormation;
            modifierReservationController.ModifyData(r1);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
            rafraichirListeFormations();
        } catch (IOException e) {
            e.printStackTrace();
        }}



    // Ajouter une méthode pour recharger la liste des formations
    private void rafraichirListeFormations() {
        ServiceFormation serviceFormation = new ServiceFormation();
        List<Formation> ReflechedListFormation = serviceFormation.getAll();
        formationsListView.setItems(FXCollections.observableArrayList(ReflechedListFormation));
    }
}
