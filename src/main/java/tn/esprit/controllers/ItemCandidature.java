package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidature;
import tn.esprit.models.OffreEmploi;
import tn.esprit.services.ServiceCandidature;
import tn.esprit.services.ServiceOffreEmploi;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemCandidature {
    // Service pour les candidatures
    IService<Candidature> O =  new ServiceCandidature();

    // Service pour les offres
    ServiceOffreEmploi serviceOffre = new ServiceOffreEmploi();

    @FXML
    private Label Labelcan;


    @FXML
    private Label labelid;

    @FXML
    private Label Labeld;

    @FXML
    private Label Labeloffre;

    @FXML
    private Label labelstat;

    private Candidature cn;

    public void setData(Candidature cn) {
        this.cn = cn;
        // Affichage de l'ID du candidat
        Labelcan.setText(String.valueOf(cn.getCandidatID()));

        // Utilisation de Stream pour récupérer le titre de l'offre associée à la candidature
        List<OffreEmploi> offres = serviceOffre.getAll(); // Récupérer la liste de toutes les offres
        Optional<OffreEmploi> offreOptional = offres.stream()
                .filter(offre -> offre.getIdOffre() == cn.getOffreID()) // Filtrer l'offre en fonction de l'ID de l'offre dans la candidature
                .findFirst(); // Trouver la première offre correspondant

        if (offreOptional.isPresent()) {
            Labeloffre.setText(offreOptional.get().getTitreOffre()); // Afficher le titre de l'offre
        } else {
            Labeloffre.setText("Offre introuvable");
        }

        // Affichage du statut de la candidature
        labelstat.setText(cn.getStatutCandidature());
        Labeld.setText(cn.getDate_candidature().toString());
        labelid.setText(String.valueOf(cn.getIdCandidature()));

        // Changer la couleur du statut en fonction du type de statut
        switch (cn.getStatutCandidature()) {
            case "embauché":
                labelstat.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                break;
            case "rejeté":
                labelstat.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                break;
            case "en attente":
                labelstat.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
                break;
            case "entretien":
                labelstat.setStyle("-fx-text-fill: #FFA500; -fx-font-weight: bold;");
                break;
        }
    }
}
