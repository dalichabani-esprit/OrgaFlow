package tn.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidat;
import tn.esprit.models.User;
import tn.esprit.services.ServiceUser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Addcandidat implements Initializable {


    @FXML
    private Button CVpdf;

    @FXML
    private Button addcandidat;

    @FXML
    private Button addemployes;

    @FXML
    private ComboBox<String> cbrole;

    @FXML
    private Button dashboard;

    @FXML
    private DatePicker datecandi;

    @FXML
    private Button home;

    @FXML
    private Button listcandidat;

    @FXML
    private Button listeemp;

    @FXML
    private Button logout;

    @FXML
    private PasswordField pfmotdepasse;

    @FXML
    private TextField search;

    @FXML
    private TextField statutcandidat;

    @FXML
    private TextField tfemail;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfprenom;
    private final IService<User> serviceUser = new ServiceUser();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
cbrole.getItems().addAll("candidat");
    }
    @FXML
    void Onclicinsererpdf(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            // Définir le répertoire de destination
            File destinationDir = new File("C:/Users/Lenovo/IdeaProjects/Gestion_utilisateure/src/images");  // Modifiez ce chemin
            if (!destinationDir.exists()) {
                destinationDir.mkdirs(); // Crée le répertoire s'il n'existe pas
            }

            // Copier le fichier dans le répertoire de destination
            File destinationFile = new File(destinationDir, selectedFile.getName());
            try {
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                // Affiche le chemin du fichier copié dans le TextField
                CVpdf.setText(destinationFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();  // Gestion des erreurs de copie
            }
        }
    }

    @FXML
    void onclicaddcandidat(ActionEvent event) {
        if ("candidat".equals(cbrole.getValue())) {
            // Validation des champs
            String email = tfemail.getText();
            if (!isValidEmail(email)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de validation");
                alert.setHeaderText(null);
                alert.setContentText("L'email n'est pas valide !");
                alert.showAndWait();
                return;
            }

            Candidat candidat = new Candidat();
            candidat.setNom(tfnom.getText());
            candidat.setPrenom(tfprenom.getText());
            candidat.setEmail(email);
            candidat.setMotDePasse(pfmotdepasse.getText());
            candidat.setRole((String) cbrole.getValue());
            candidat.setCvCandidat(CVpdf.getText());
            candidat.setDateCandidature(Date.valueOf(datecandi.getValue()));
            candidat.setStatutCandidat(statutcandidat.getText());

            System.out.println("Ajout du candidat : " + candidat);
            serviceUser.add(candidat);

            // Réinitialiser les champs
            tfnom.clear();
            tfprenom.clear();
            tfemail.clear();
            pfmotdepasse.clear();
            statutcandidat.clear();
            datecandi.setValue(null);
            cbrole.setValue(null);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Candidat ajouté avec succès !");
            alert.showAndWait();
        }
    }

    @FXML
    void onclicaddemployes(ActionEvent event) {

    }

    @FXML
    void onclicdashboard(ActionEvent event) {

    }

    @FXML
    void onclichome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GestionAdmin.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("DashBoard");
        stage.show();

    }

    @FXML
    void oncliclistecand(ActionEvent event) {

    }

    @FXML
    void oncliclisteemplo(ActionEvent event) {

    }

    @FXML
    void oncliclogout(ActionEvent event) {

    }

    @FXML
    void onclicsearch(ActionEvent event) {

    }
    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
