package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import models.Formation;
import services.ServiceFormation;

import java.util.List;

public class AfficherFormations {

    @FXML
    private ListView<String> formationsListView; // ListView to display formations

    private ObservableList<String> formationList = FXCollections.observableArrayList(); // Observable list to bind to the ListView

    private ServiceFormation serviceFormation;

    // Initialize method to load formations when the controller is loaded
    @FXML
    private void initialize() {
        serviceFormation = new ServiceFormation(); // Initialize the ServiceFormation
        loadFormations(); // Load the formations from the service
    }

    // Handle refresh button click
    @FXML
    private void handleRefreshAction() {
        loadFormations(); // Reload formations when the refresh button is clicked
    }

    // Method to load the formations from the database and display them in the ListView
    private void loadFormations() {
        List<Formation> formations = serviceFormation.getAll(); // Get all formations from the service

        // Convert formations to a list of strings to display in ListView
        formationList.clear(); // Clear previous list
        for (Formation formation : formations) {
            formationList.add(formation.getNom() + " (" + formation.getCategorie() + ")");
        }

        // Set the observable list to the ListView
        formationsListView.setItems(formationList);
    }
}
