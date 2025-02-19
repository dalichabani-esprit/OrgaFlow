package Controllers.Formation;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.Formation;

public class ListFormationCell extends ListCell<Formation> {

    private Label nom = new Label();
    private Label description = new Label();
    private Label duree = new Label();
    private Label dateDebut = new Label();
    private Label dateFin = new Label();

    private Label categorie = new Label();

    private Label formateur = new Label();
    public ListFormationCell() {
        super();
        VBox vBox = new VBox(nom,description, duree,dateDebut,dateFin, categorie, formateur);
        HBox hBox = new HBox(vBox);
        hBox.setSpacing(10);
        vBox.setSpacing(5);
        setGraphic(hBox);
        nom.setFont(Font.font("System", FontWeight.BOLD, 14));
        nom.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        nom.setFont(Font.font("System", FontWeight.BOLD, 14));
        description.setFont(Font.font("System", FontWeight.BOLD, 12));
        duree.setFont(Font.font("System", FontWeight.BOLD, 12));
        dateDebut.setFont(Font.font("System", FontWeight.BOLD, 12));
        dateFin.setFont(Font.font("System", FontWeight.BOLD, 12));
        categorie.setFont(Font.font("System", FontWeight.BOLD, 12));
        formateur.setFont(Font.font("System", FontWeight.BOLD, 12));

}

    @Override
    public void updateItem(Formation formation, boolean empty) {
        super.updateItem(formation, empty);
        if (empty || formation == null) {
            setText(null);
            setGraphic(null);
        } else
        { nom.setText("nom : " + formation.getNom());
            description.setText("Description : "+formation.getDescription());
            duree.setText("Duree: "+String.valueOf(formation.getDuree()));
            dateDebut.setText("Date debut : "+(String.valueOf(formation.getDateDebut())));
            dateFin.setText("Date fin : "+(String.valueOf(formation.getDateFin())));
            categorie.setText("Categorie :"+String.valueOf(formation.getCategorie()));
            formateur.setText("Formateur : "+formation.getFormateur().getNom() + formation.getFormateur().getPrenom()+"\n"+" Specialite : "+formation.getFormateur().getSpecialite());
            setGraphic(getListCell());
        }
    }




    private HBox getListCell() {
        HBox hBox = new HBox( new VBox(nom,description, duree,dateDebut,dateFin, categorie,formateur));
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10));
        hBox.setStyle("-fx-background-color: #edece6; -fx-background-radius: 10px;");
        Separator separator = new Separator(Orientation.HORIZONTAL);
        VBox.setVgrow(separator, Priority.ALWAYS);
        VBox vBox = new VBox(hBox, separator);
        return hBox;
    }
}