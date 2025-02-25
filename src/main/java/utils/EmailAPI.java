package utils;

import models.Formation;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailAPI {
    static Session sesh;
    static Properties prop = new Properties();
    public static void sendMail(Formation formation) {
        // Propriétés
        String from = "ychagouani@gmail.com";// Adresse email de l'expéditeur
        String password = "cknujauemvvvpqca";// Mot de passe de l'expéditeur

        // Configuration de la propriété système pour l'envoi d'un e-mail via le serveur SMTP d'Outlook
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Création d'une session pour l'authentification de l'expéditeur
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, password);

            }
        });

        try {
            // Création d'un objet Message pour composer l'e-mail
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(formation.getFormateur().getEmail()));
            message.setSubject("Affectation au seins de la formation "+formation.getNom());


            // Création d'un objet Multipart pour le message texte et l'image en pièce jointe
            Multipart multipart = new MimeMultipart();

            // Ajout du message texte
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            String msg = "Cher(e) " + formation.getFormateur().getPrenom() + ",\n\n" +
                    "Nous avons le plaisir de vous informer que vous avez été assigné(e) à une nouvelle formation. Voici les détails :\n\n" +
                    "- Nom de la formation : " + formation.getNom() + "\n" +
                    "- Description : " +  formation.getDescription() + "\n" +
                    "- Durée : " +  formation.getDuree() + " jours\n" +
                    "- Date de début : " +  formation.getDateDebut() + "\n" +
                    "- Date de fin : " +  formation.getDateFin() + "\n" +
                    "- Catégorie : " +  formation.getCategorie() + "\n\n" +
                    "Nous comptons sur votre expertise pour assurer le bon déroulement de cette formation. " +
                    "N’hésitez pas à nous contacter si vous avez besoin de plus d’informations.\n\n" +
                    "Cordialement,\n" +
                    "[Votre Nom / Votre Organisation]";

            messageBodyPart.setText(msg);
            multipart.addBodyPart(messageBodyPart);


            // Ajout du Multipart à l'e-mail
            message.setContent(multipart);

            // Envoi du message
            Transport.send(message);

            System.out.println("Le message a été envoyé avec succès. ");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
