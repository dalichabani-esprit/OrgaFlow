package tn.esprit.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

public class PasswordResetDAO {
    public static String generateResetCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Génère un code à 6 chiffres
        return String.valueOf(code);
    }

    public static void saveResetCode(String email, String resetCode) {
        String query = "INSERT INTO password_reset (email, reset_code, expiration_time) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE reset_code = ?, expiration_time = ?";

        try (Connection cnx = MyDatabase.getInstance().getCnx();
             PreparedStatement stmt = cnx.prepareStatement(query)) {

            Timestamp expirationTime = Timestamp.valueOf(LocalDateTime.now().plusMinutes(10)); // Code valide 10 min

            stmt.setString(1, email);
            stmt.setString(2, resetCode);
            stmt.setTimestamp(3, expirationTime);
            stmt.setString(4, resetCode);
            stmt.setTimestamp(5, expirationTime);

            stmt.executeUpdate();
            System.out.println(" Code de réinitialisation envoyé à " + email + " : " + resetCode);
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'enregistrement du code de réinitialisation : " + e.getMessage());
        }
    }
}
