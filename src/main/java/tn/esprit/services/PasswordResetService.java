package tn.esprit.services;

import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.time.LocalDateTime;

public class PasswordResetService {

    public static boolean verifyResetCode(String email, String resetCode) {
        String query = "SELECT expiration_time FROM password_reset WHERE email = ? AND reset_code = ?";
        try (Connection cnx = MyDatabase.getInstance().getCnx();
             PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, resetCode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                LocalDateTime expirationTime = rs.getTimestamp("expiration_time").toLocalDateTime();

                if (expirationTime.isAfter(LocalDateTime.now())) {
                    return true;
                } else {
                    System.out.println("Le code a expiré.");
                }
            } else {
                System.out.println("Code invalide.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public static boolean resetPassword(String email, String resetCode, String newPassword) {
        if (!verifyResetCode(email, resetCode)) {
            System.out.println("Le code est invalide ou expiré.");
            return false;
        }
        String query = "UPDATE utilisateur SET motDePasse = ? WHERE email = ?";
        try (Connection cnx = MyDatabase.getInstance().getCnx();
             PreparedStatement stmt = cnx.prepareStatement(query)) {

            stmt.setString(1, newPassword);
            stmt.setString(2, email);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la réinitialisation du mot de passe : " + e.getMessage());
        }
        return false;
    }
    public static String getEmailByCode(String code) {
        String query = "SELECT email, expiration_time FROM password_reset WHERE reset_code = ?";
        try (Connection cnx = MyDatabase.getInstance().getCnx();
             PreparedStatement stmt = cnx.prepareStatement(query)) {

            stmt.setString(1, code);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Timestamp expirationTime = rs.getTimestamp("expiration_time");
                    if (expirationTime != null && expirationTime.after(new Timestamp(System.currentTimeMillis()))) {
                        return rs.getString("email");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'email par le code : " + e.getMessage());
        }
        return null; // Si le code est invalide ou expiré
    }


}

