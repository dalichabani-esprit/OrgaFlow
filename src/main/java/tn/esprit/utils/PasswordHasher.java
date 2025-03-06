/*package tn.esprit.utils;


import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {
    // Hacher un mot de passe
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12)); // Facteur de coût : 12
    }

    // Vérifier un mot de passe
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}*/