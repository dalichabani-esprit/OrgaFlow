package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidat;
import tn.esprit.models.User;
import tn.esprit.services.ServiceUser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
public class modifiercandidat {

    @FXML
    private ComboBox<?> cbrole;

    @FXML
    private TextField cvcandidat;

    @FXML
    private DatePicker datecandi;

    @FXML
    private TextField idcandidat;


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
    private Button CVpdf;
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
            if (tfemail.getText().isEmpty() || statutcandidat.getText().isEmpty()) {
                System.out.println("Veuillez remplir tous les champs !");
                return;
            }
            String email = tfemail.getText();
            String nouveauStatut = statutcandidat.getText();
            Candidat candidat = (Candidat) serviceUser.getByEmail(email);

            if (candidat == null) {
                System.out.println("Candidat non trouvé !");
                return;
            }
            candidat.setStatutCandidat(nouveauStatut);
            serviceUser.update(candidat);
            System.out.println("Statut mis à jour avec succès !");
        } catch (Exception e) {
            System.out.println("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }

    @FXML
    void Onclicsupprimercandidat(ActionEvent event) {
        try {
            if (tfemail.getText().isEmpty()) {
                System.out.println("Veuillez entrer l'email du candidat à supprimer !");
                return;
            }
            String email = tfemail.getText();
            Candidat candidat = (Candidat) serviceUser.getByEmail(email);
            if (candidat == null) {
                System.out.println("Candidat non trouvé !");
                return;
            }
            serviceUser.delete(candidat);
            System.out.println("Candidat supprimé avec succès !");
            tfemail.clear();
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
    public void onclicaffiche(ActionEvent actionEvent) {
       Vbox.getChildren().clear();
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
    @FXML
    void Onclicinsererpdf(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            File destinationDir = new File("C:/Users/Lenovo/IdeaProjects/Gestion_utilisateure/src/images");  // Modifiez ce chemin
            if (!destinationDir.exists()) {
                destinationDir.mkdirs();
            }
            File destinationFile = new File(destinationDir, selectedFile.getName());
            try {
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                CVpdf.setText(destinationFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
