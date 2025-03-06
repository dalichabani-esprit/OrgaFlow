package tn.esprit.services;

import tn.esprit.utils.MyDatabase;
import tn.esprit.utils.PasswordHasher;

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
                    System.out.println(" Le code a expiré.");
                }
            } else {
                System.out.println(" Code invalide.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification du code de réinitialisation : " + e.getMessage());
        }
        return false;
    }
    public static boolean resetPassword(String email, String resetCode, String newPassword) {
        if (!verifyResetCode(email, resetCode)) {
            System.out.println("Le code est invalide ou expiré.");
            return false;
        }

        //  Hachage du nouveau mot de passe
        String hashedPassword = PasswordHasher.hashPassword(newPassword);

        String query = "UPDATE utilisateur SET motDePasse = ? WHERE email = ?";
        try (Connection cnx = MyDatabase.getInstance().getCnx();
             PreparedStatement stmt = cnx.prepareStatement(query)) {

            stmt.setString(1, hashedPassword); //  Stocker le mot de passe haché
            stmt.setString(2, email);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Mot de passe réinitialisé avec succès !");
                return true;
            } else {
                System.out.println("Aucune mise à jour effectuée, email introuvable.");
            }
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
