package tn.esprit.services;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CvUploadService {

    private Connection cnx;

    public CvUploadService(Connection cnx) {
        this.cnx = cnx;
    }

    public void uploadCv(int iduser, String filePath) throws SQLException, IOException {
        String sql = "UPDATE CvCandidat SET CvCandidat = ? WHERE iduser = ?";

        try (PreparedStatement statement =cnx.prepareStatement(sql)) {
            File file = new File(filePath);
            if (file.exists()) {
                statement.setString(1, file.getAbsolutePath());
                statement.setInt(2, iduser);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Le chemin du CV a été enregistré avec succès !");
                } else {
                    System.out.println("Erreur : Aucun candidat trouvé avec cet ID.");
                }
            } else {
                System.out.println("Erreur : Le fichier n'existe pas à l'emplacement spécifié.");
            }
        }
    }
}
