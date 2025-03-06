package tn.esprit.Controllers.Formation;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tn.esprit.models.Formation;

public class ListFormationCell extends ListCell<Formation> {
    private Label nom = new Label();
    private Label description = new Label();
    private Label duree = new Label();
    private Label dateDebut = new Label();
    private Label dateFin = new Label();
    private Label categorie = new Label();
    private Label formateur = new Label();

    // Icônes
    private ImageView iconNom = new ImageView(new Image("img/nom.png"));
    private ImageView iconDescription = new ImageView(new Image("img/la-description.png"));
    private ImageView iconDuree = new ImageView(new Image("img/sablier.png"));
    private ImageView iconDateDebut = new ImageView(new Image("img/calendrier.png"));
    private ImageView iconDateFin = new ImageView(new Image("img/calendrier.png"));
    private ImageView iconCategorie = new ImageView(new Image("img/specialite.png"));
    private ImageView iconFormateur = new ImageView(new Image("img/profil.png"));

    public ListFormationCell() {
        super();

        // Taille des icônes
        setIconSize(iconNom, 16);
        setIconSize(iconDescription, 16);
        setIconSize(iconDuree, 16);
        setIconSize(iconDateDebut, 16);
        setIconSize(iconDateFin, 16);
        setIconSize(iconCategorie, 16);
        setIconSize(iconFormateur, 16);

        // Mise en forme des textes
        nom.setFont(Font.font("System", FontWeight.BOLD, 14));
        description.setFont(Font.font("System", FontWeight.BOLD, 14));
        duree.setFont(Font.font("System", FontWeight.BOLD, 14));
        dateDebut.setFont(Font.font("System", FontWeight.BOLD, 14));
        dateFin.setFont(Font.font("System", FontWeight.BOLD, 14));
        categorie.setFont(Font.font("System", FontWeight.BOLD, 14));
        formateur.setFont(Font.font("System", FontWeight.BOLD, 14));

        setGraphic(getListCell());
    }

    @Override
    protected void updateItem(Formation formation, boolean empty) {
        super.updateItem(formation, empty);
        if (empty || formation == null) {
            setText(null);
            setGraphic(null);
        } else {
            nom.setText("Nom : " + formation.getNom());
            description.setText("Description : " + formation.getDescription());
            duree.setText("Durée : " + formation.getDuree());
            dateDebut.setText("Date de début : " + formation.getDateDebut());
            dateFin.setText("Date de fin : " + formation.getDateFin());
            categorie.setText("Catégorie : " + formation.getCategorie());

            // Vérification du formateur avant d'accéder à ses informations
            if (formation.getFormateur() != null) {
                formateur.setText("Formateur : " + formation.getFormateur().getPrenom() + " " + formation.getFormateur().getNom());
            } else {
                formateur.setText("Formateur : Inconnu");
            }

            setGraphic(getListCell());
        }
    }


    private HBox getListCell() {
        VBox textContainer = new VBox(
                createHBox(iconNom, nom),
                createHBox(iconDescription, description),
                createHBox(iconDuree, duree),
                createHBox(iconDateDebut, dateDebut),
                createHBox(iconDateFin, dateFin),
                createHBox(iconCategorie, categorie),
                createHBox(iconFormateur, formateur)
        );
        textContainer.setSpacing(5);

        HBox cellContainer = new HBox(iconNom, textContainer);
        cellContainer.setSpacing(15);
        cellContainer.setPadding(new Insets(10));
        cellContainer.setStyle("-fx-background-color: #5d808c; -fx-background-radius: 10px;");

        return cellContainer;
    }

    private HBox createHBox(ImageView icon, Label label) {
        HBox hbox = new HBox(icon, label);
        hbox.setSpacing(5);
        return hbox;
    }

    private void setIconSize(ImageView icon, int size) {
        icon.setFitWidth(size);
        icon.setFitHeight(size);
        icon.setPreserveRatio(true);
    }
}