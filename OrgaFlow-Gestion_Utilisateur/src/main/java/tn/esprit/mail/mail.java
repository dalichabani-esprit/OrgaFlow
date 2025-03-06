package tn.esprit.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

public class mail {
    static final String user = "manelhomri4@gmail.com";
    static final String pass = "tbfq pvgd sern snhc"; // Use App Password if you have 2FA enabled
    Properties props = new Properties();

    public mail() {
        setupMailProperties();
    }

    private void setupMailProperties() {
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true"); // Enable StartTLS
        props.setProperty("mail.smtp.port", "587"); // Use port 587
        props.put("mail.smtp.auth", "true"); // Enable authentication
        props.put("mail.debug", "true"); // Enable debug output
    }

    public void envoyer(String toEmail, String subject, String body) {
        try {
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pass);
                }
            });

            // Create a new message
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(user));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            msg.setSubject(subject);
            msg.setText(body);
            msg.setSentDate(new Date());
            Transport.send(msg);
            System.out.println("Message sent.");
        } catch (MessagingException e) {
            System.out.println("Error sending message: " + e);
            e.printStackTrace();
        }
    }

    public void envoyerAvecPieceJointe(String toEmail, String subject, String body, String pdfFilePath) {
        try {
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pass);
                }
            });

            // Create a new message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            message.setSubject(subject);
            message.setSentDate(new Date());

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Body part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);
            multipart.addBodyPart(messageBodyPart);

            // Attachment part
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(pdfFilePath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("facture.pdf");
            multipart.addBodyPart(messageBodyPart);

            // Set the complete message parts
            message.setContent(multipart);

            // Send the message
            Transport.send(message);
            System.out.println("Email with attachment sent.");
        } catch (MessagingException e) {
            System.out.println("Error sending email with attachment: " + e);
            e.printStackTrace();
        }
    }
}