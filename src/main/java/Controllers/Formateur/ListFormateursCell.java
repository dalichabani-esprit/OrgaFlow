package Controllers.Formateur;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    // Icônes
    private ImageView iconUser = new ImageView(new Image("img/nom.png"));
    private ImageView iconEmail = new ImageView(new Image("img/ouvrir-le-courrier.png"));
    private ImageView iconPhone = new ImageView(new Image("img/ancien-telephone-typique.png"));
    private ImageView iconSpeciality = new ImageView(new Image("img/specialite.png"));

    public ListFormateursCell() {
        super();

        // Taille des icônes
        setIconSize(iconUser, 16);
        setIconSize(iconUser, 16);
        setIconSize(iconEmail, 16);
        setIconSize(iconPhone, 16);
        setIconSize(iconSpeciality, 16);

        // Mise en forme des textes
        nom.setFont(Font.font("System", FontWeight.BOLD, 14));
        prenom.setFont(Font.font("System", FontWeight.BOLD, 14));
        email.setFont(Font.font("System", FontWeight.BOLD, 14));
        telephone.setFont(Font.font("System", FontWeight.BOLD, 14));
        specialite.setFont(Font.font("System", FontWeight.BOLD, 14));

        setGraphic(getListCell());
    }

    @Override
    protected void updateItem(Formateur formateur, boolean empty) {
        super.updateItem(formateur, empty);
        if (empty || formateur == null) {
            setText(null);
            setGraphic(null);
        } else {
            nom.setText("Nom : " + formateur.getNom());
            prenom.setText("Prénom : " + formateur.getPrenom());
            email.setText("Email :"+formateur.getEmail());
            telephone.setText("Telephone"+formateur.getTelephone());
            specialite.setText("Specialite"+formateur.getSpecialite());



            setGraphic(getListCell());
        }
    }

    private HBox getListCell() {
        VBox textContainer = new VBox(
                createHBox(iconUser, nom),
                createHBox(iconUser, prenom),
                createHBox(iconEmail, email),
                createHBox(iconPhone, telephone),
                createHBox(iconSpeciality, specialite)
        );
        textContainer.setSpacing(5);

        HBox cellContainer = new HBox(iconUser, textContainer);
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