package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Employes;
import tn.esprit.models.User;
import tn.esprit.services.ServiceUser;

import java.io.IOException;
import java.util.List;

public class modifieremploye {

    @FXML
    private Button afficheremployes;

    @FXML
    private ComboBox<String> cbrole;

    @FXML
    private DatePicker dateembauche;

    @FXML
    private TextField departement;

    @FXML
    private TextField idemploye;

    @FXML
    private ListView<String > listview;

    @FXML
    private Button logoutS;

    @FXML
    private Button modifieremploye;

    @FXML
    private PasswordField pfmotdepasse;

    @FXML
    private TextField salaire;

    @FXML
    private Button supprimeremploye;

    @FXML
    private TextField tfemail;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfprenom;
    private final IService<User> serviceUser = new ServiceUser();
    @FXML
    void Onclickaafficheremploye(ActionEvent event) {
        List<User> users = serviceUser.getAll();
        ObservableList<String> employeList = FXCollections.observableArrayList();
        for (User user : users) {
            if (user instanceof Employes) {
                Employes employe = (Employes) user;
                String employeInfo = "ID: " + employe.getIduser() +
                        "\nNom: " + employe.getNom() +
                        "\nPrénom: " + employe.getPrenom() +
                        "\nEmail: " + employe.getEmail() +
                        "\nMot de passe: " + employe.getMotDePasse() +
                        "\nRôle: " + employe.getRole() +
                        "\nDate Embauche: " + employe.getSalaire() +
                        "\nSalaire: " + (employe.getDateEmbauche() != null ? employe.getDateEmbauche().toString() : "Non renseignée") +
                        "\nDepartement: " + employe.getDepartement();
                employeList.add(employeInfo);
            }
        }
        if (employeList.isEmpty()) {
            employeList.add("Aucun candidat trouvé.");
        }
        listview.setItems(employeList);
    }
    @FXML
    void Onclickmodifieremploye(ActionEvent event) {
        try {
            if (idemploye.getText().isEmpty() || departement.getText().isEmpty() || salaire.getText().isEmpty()) {
                System.out.println("Veuillez remplir tous les champs !");
                return;
            }
            int id = Integer.parseInt(idemploye.getText());
            String nouveauStatut = departement.getText();
            String nouveSalaire = salaire.getText();
            Employes employe = (Employes) serviceUser.getByIduser(id);
            if (employe == null) {
                System.out.println("Candidat non trouvé !");
                return;
            }
           employe.setDepartement(nouveauStatut);
            employe.setSalaire(nouveSalaire);
            serviceUser.update(employe);
            System.out.println("Mis à jour avec succès !");
        } catch (NumberFormatException e) {
            System.out.println("L'ID du employe doit être un nombre !");
        } catch (Exception e) {
            System.out.println("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }
    @FXML
    void Onclicksupprimeremploye(ActionEvent event) {
        try {
            if (idemploye.getText().isEmpty()) {
                System.out.println("Veuillez entrer l'ID du candidat à supprimer !");
                return;
            }
            int id = Integer.parseInt(idemploye.getText());
            Employes  employe = (Employes) serviceUser.getByIduser(id);
            if (employe == null) {
                System.out.println("Candidat non trouvé !");
                return;
            }
            serviceUser.delete(employe);
            System.out.println("Employe supprimé avec succès !");
            idemploye.clear();
        } catch (NumberFormatException e) {
            System.out.println("L'ID du candidat doit être un nombre valide !");
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    @FXML
    void onclickLogout(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GestionEmployes.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
