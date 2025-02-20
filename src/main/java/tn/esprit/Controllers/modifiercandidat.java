package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidat;
import tn.esprit.models.User;
import tn.esprit.services.ServiceUser;

import java.io.IOException;
import java.util.List;

public class modifiercandidat {

    @FXML
    private ComboBox<String> cbrole;

    @FXML
    private TextField cvcandidat;

    @FXML
    private DatePicker datecandi;

    @FXML
    private TextField idcandidat;

    @FXML
    private ListView<String> listviews;
    @FXML
    private Button supprimercandidat;
    @FXML
    private Button logout;
    @FXML
    private Button affichercandidat;
    @FXML
    private Button modifiercandidat;
    @FXML
    private VBox Vbox;
    @FXML
    private PasswordField pfmotdepasse;

    @FXML
    private TextField statutcandidat;

    @FXML
    private TextField tfemail;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfprenom;
    private final IService<User> serviceUser = new ServiceUser();

    @FXML
    void oncliclogout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Gestioncandidat.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    void onclicmodifier(ActionEvent event) {
        try {
            if (idcandidat.getText().isEmpty() || statutcandidat.getText().isEmpty()) {
                System.out.println("Veuillez remplir tous les champs !");
                return;
            }
            int id = Integer.parseInt(idcandidat.getText());
            String nouveauStatut = statutcandidat.getText();
            Candidat candidat = (Candidat) serviceUser.getByIduser(id);
            if (candidat == null) {
                System.out.println("Candidat non trouvé !");
                return;
            }
            candidat.setStatutCandidat(nouveauStatut);
            serviceUser.update(candidat);
            System.out.println("Statut mis à jour avec succès !");
        } catch (NumberFormatException e) {
            System.out.println("L'ID du candidat doit être un nombre !");
        } catch (Exception e) {
            System.out.println("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }
    @FXML
    void Onclicsupprimercandidat(ActionEvent event) {
        try {
            if (idcandidat.getText().isEmpty()) {
                System.out.println("Veuillez entrer l'ID du candidat à supprimer !");
                return;
            }
            int id = Integer.parseInt(idcandidat.getText());
            Candidat candidat = (Candidat) serviceUser.getByIduser(id);
            if (candidat == null) {
                System.out.println("Candidat non trouvé !");
                return;
            }
            serviceUser.delete(candidat);
            System.out.println("Candidat supprimé avec succès !");
            idcandidat.clear();
        } catch (NumberFormatException e) {
            System.out.println("L'ID du candidat doit être un nombre valide !");
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    public void onclicaffiche(ActionEvent actionEvent) {
       Vbox.getChildren().clear(); // Nettoyer le contenu précédent

        List<User> users = serviceUser.getAll();
        boolean found = false;

        for (User user : users) {
            if (user instanceof Candidat) {
                found = true;
                Candidat candidat = (Candidat) user;

                String candidatInfo = "ID: " + candidat.getIduser() +
                        "\tNom: " + candidat.getNom() +
                        "\tPrénom: " + candidat.getPrenom() +
                        "\tEmail: " + candidat.getEmail() +
                        "\tMot de passe: " + candidat.getMotDePasse() +
                        "\tRôle: " + candidat.getRole() +
                        "\tCV: " + candidat.getCvCandidat() +
                        "\tDate de candidature: " + (candidat.getDateCandidature() != null ? candidat.getDateCandidature().toString() : "Non renseignée") +
                        "\tStatut: " + candidat.getStatutCandidat();

                Label label = new Label(candidatInfo);
                Vbox.getChildren().add(label);
            }
        }

        if (!found) {
            Label noDataLabel = new Label("Aucun candidat trouvé.");
           Vbox.getChildren().add(noDataLabel);
        }
    }
    }
