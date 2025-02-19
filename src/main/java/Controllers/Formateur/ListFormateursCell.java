package Controllers.Formateur;

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
import models.Formateur;
import models.Formation;

public class ListFormateursCell extends ListCell<Formateur> {
    private Label nom = new Label();
    private Label prenom = new Label();
    private Label email = new Label();
    private Label telephone = new Label();
    private Label specialite = new Label();

    public ListFormateursCell() {
        super();
        VBox vBox = new VBox(nom,prenom, email,telephone,specialite);
        HBox hBox = new HBox(vBox);
        hBox.setSpacing(10);
        vBox.setSpacing(5);
        setGraphic(hBox);
        nom.setFont(Font.font("System", FontWeight.BOLD, 14));
        nom.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        nom.setFont(Font.font("System", FontWeight.BOLD, 14));
        prenom.setFont(Font.font("System", FontWeight.BOLD, 12));
        email.setFont(Font.font("System", FontWeight.BOLD, 12));
        telephone.setFont(Font.font("System", FontWeight.BOLD, 12));
        specialite.setFont(Font.font("System", FontWeight.BOLD, 12));


    }

    @Override
    public void updateItem(Formateur formateur, boolean empty) {
        super.updateItem(formateur, empty);
        if (empty || formateur == null) {
            setText(null);
            setGraphic(null);
        } else
        {   nom.setText("Nom : " + formateur.getNom());
            prenom.setText("Prenom : "+formateur.getPrenom());
            email.setText("E-mail : "+formateur.getEmail());
            telephone.setText("Telephone : "+formateur.getTelephone());
            specialite.setText("specialite : "+formateur.getSpecialite() );

            setGraphic(getListCell());
        }
    }
    private HBox getListCell() {
        HBox hBox = new HBox( new VBox(nom,prenom, email,telephone,specialite));
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10));
        hBox.setStyle("-fx-background-color: #edece6; -fx-background-radius: 10px;");
        Separator separator = new Separator(Orientation.HORIZONTAL);
        VBox.setVgrow(separator, Priority.ALWAYS);
        VBox vBox = new VBox(hBox, separator);
        return hBox;
    }
}
