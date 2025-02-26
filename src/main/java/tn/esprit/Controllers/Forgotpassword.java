import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Forgotpassword {

    @FXML
    private Button btrecevoirlien;
    @FXML
    private Button retour;
    @FXML
    private TextField tfemails;

    @FXML
    void Onclicrecevoirliengmail(ActionEvent event) {


    }
    @FXML
    void Onclicretour(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/logginUser.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Creation de compte");
        stage.show();

    }

}
