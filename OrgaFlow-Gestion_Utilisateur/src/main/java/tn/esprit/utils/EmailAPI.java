package tn.esprit.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class EmailAPI {
    public static void sendPasswordResetEmail(String toEmail, String token) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("C:/Users/Lenovo/IdeaProjects/Gestion_utilisateure/src/main/resources/config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier de configuration : " + e.getMessage());
            return;
        }
        String from = props.getProperty("EMAIL_USER");
        String password = props.getProperty("EMAIL_PASS");

        if (from == null || password == null) {
            System.err.println("Erreur : Les informations d'authentification ne sont pas définies !");
            return;
        }

        Properties mailProps = new Properties();
        mailProps.put("mail.smtp.host", "smtp.gmail.com");
        mailProps.put("mail.smtp.port", "587");
        mailProps.put("mail.smtp.auth", "true");
        mailProps.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(mailProps, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Code de réinitialisation de votre mot de passe");

            String msg = "Bonjour,\n\n"
                    + "Votre code de réinitialisation de mot de passe est : " + token + "\n\n"
                    + "Ce code expirera dans 10 minutes.\n\n"
                    + "Si vous n'avez pas demandé de réinitialisation, ignorez cet e-mail.\n\n"
                    + "Cordialement,\n"
                    + "Votre équipe Orgaflow.";

            message.setText(msg);

            Transport.send(message);
            System.out.println("Email envoyé avec succès à " + toEmail);
        } catch (MessagingException e) {
            System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
        }
    }
    public class CodeGenerator {
        public static String generateResetCode() {
            Random random = new Random();
            int code = 100000 + random.nextInt(900000);
            return String.valueOf(code);
        }
    }
}
