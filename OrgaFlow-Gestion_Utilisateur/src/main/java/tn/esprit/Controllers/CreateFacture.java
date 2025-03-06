package tn.esprit.Controllers;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.mail.mail;
import tn.esprit.sms.SmsSender;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateFacture {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ComboBox<String> comboBoxDemande;

    @FXML
    private ComboBox<String> comboBoxDevis;

    @FXML
    private ComboBox<String> comboBoxDestinataire;

    @FXML
    private TextField montant;

    @FXML
    private Label labelDevis;

    @FXML
    private ComboBox<String> statutComboBox;

    @FXML
    private Button submitButton;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/orgaflowdb"; // Database URL
    private static final String USER = "root"; // Database username
    private static final String PASS = ""; // Database password

    @FXML
    public void initialize() {
        loadComboBoxData();
    }

    private void loadComboBoxData() {
        comboBoxDemande.getItems().addAll(fetchDemandeReferences());
        comboBoxDevis.getItems().addAll(fetchDevisReferences());
    }

    private List<String> fetchDemandeReferences() {
        return fetchReferences("SELECT reference FROM demande");
    }

    private List<String> fetchDevisReferences() {
        return fetchReferences("SELECT referenceDevis FROM devis");
    }

    private List<String> fetchReferences(String query) {
        List<String> references = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                references.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return references;
    }

    @FXML
    private void handleSubmit() {
        String selectedDemandeReference = comboBoxDemande.getValue();
        String selectedDevisReference = comboBoxDevis.getValue();
        String montantFinal = montant.getText();
        String statut = statutComboBox.getValue();

        Integer demandeId = fetchDemandeIdByReference(selectedDemandeReference);
        Integer devisId = fetchDevisIdByReference(selectedDevisReference);

        String refFacture = generateUniqueReference();
        LocalDate dateFacture = LocalDate.now();

        String insertSQL = "INSERT INTO facture (id_demande, id_devis, montant_final, statut, date_facture, destinataire_id, refFacture) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setInt(1, demandeId);
            pstmt.setInt(2, devisId);
            pstmt.setString(3, montantFinal);
            pstmt.setString(4, statut);
            pstmt.setDate(5, Date.valueOf(dateFacture));
            pstmt.setInt(6, 1); // Default destinataire_id
            pstmt.setString(7, refFacture);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Données de la facture insérées avec succès !");
                String pdfFilePath = generatePDF(montantFinal, refFacture, demandeId);

                // Send email with PDF attachment
                sendEmailWithAttachment("manelhomri5@gmail.com", "New Facture", "Please find the attached facture.", pdfFilePath);

                // Send SMS notification
                sendSmsNotification("25472331", "Facture créée avec succès: " + refFacture+ ",Check your email !");
            } else {
                System.out.println("Aucune donnée insérée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorDialog("Database Error", e.getMessage());
        }
    }

    private void sendEmailWithAttachment(String to, String subject, String body, String attachment) {
        mail mailSender = new mail();
        mailSender.envoyerAvecPieceJointe(to, subject, body, attachment);
    }

    private void sendSmsNotification(String phoneNumber, String message) {
        SmsSender smsSender = new SmsSender();
        smsSender.sendSMS(phoneNumber, message);
    }

    private String generateUniqueReference() {
        String reference;
        do {
            reference = generateReference();
        } while (!isReferenceUnique(reference));
        return reference;
    }

    private String generateReference() {
        Random random = new Random();
        return "RF-" + random.nextInt(10000); // Format reference
    }

    private boolean isReferenceUnique(String reference) {
        String query = "SELECT COUNT(*) FROM facture WHERE refFacture = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, reference);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // True if no matching reference found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Integer fetchDemandeIdByReference(String reference) {
        return fetchIdByReference("SELECT id_demande FROM demande WHERE reference = ?", reference);
    }

    private Integer fetchDevisIdByReference(String referenceDevis) {
        return fetchIdByReference("SELECT id_devis FROM devis WHERE referenceDevis = ?", referenceDevis);
    }

    private Integer fetchIdByReference(String query, String reference) {
        Integer id = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, reference);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private static final Color LIGHT_GRAY = new DeviceRgb(211, 211, 211); // RGB for light gray

    private String generatePDF(String montant, String refFacture, Integer demandeId) {
        String pdfFilePath = "facture.pdf"; // Define the path to save the PDF
        try {
            PdfWriter writer = new PdfWriter(pdfFilePath);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Title
            document.add(new Paragraph("Facture Details")
                    .setBold()
                    .setFontSize(24)
                    .setMarginBottom(20));

            // Create a table
            Table table = new Table(new float[]{1, 2});
            table.setWidth(UnitValue.createPercentValue(100));
            table.setMarginBottom(20);

            // Add header row
            table.addHeaderCell(new Cell().add(new Paragraph("Field").setBold()).setBackgroundColor(LIGHT_GRAY));
            table.addHeaderCell(new Cell().add(new Paragraph("Value").setBold()).setBackgroundColor(LIGHT_GRAY));

            // Add data rows
            table.addCell("Montant:");
            table.addCell(montant);
            table.addCell("Référence Facture:");
            table.addCell(refFacture);

            // Fetch additional details from demande
            Demande demandeDetails = fetchDemandeDetails(demandeId);
            if (demandeDetails != null) {
                table.addCell("Type:");
                table.addCell(demandeDetails.getType());
                table.addCell("Description:");
                table.addCell(demandeDetails.getDescription());

                table.addCell("Date Demande:");
                table.addCell(demandeDetails.getDateDemande().toString());
            }

            document.add(table);
            document.add(new Paragraph("Thank you for your business!")
                    .setItalic()
                    .setFontSize(14)
                    .setMarginTop(20));

            document.close();
            System.out.println("PDF created successfully!");
            return pdfFilePath; // Path to the PDF

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            showErrorDialog("File Error", "Could not create PDF file: " + e.getMessage());
            return null;
        }
    }

    private Demande fetchDemandeDetails(Integer demandeId) {
        Demande demande = null;
        String query = "SELECT type, description, demandeur_id, date_demande FROM demande WHERE id_demande = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, demandeId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                demande = new Demande();
                demande.setType(rs.getString("type"));
                demande.setDescription(rs.getString("description"));
                demande.setDemandeurId(rs.getInt("demandeur_id"));
                demande.setDateDemande(rs.getDate("date_demande").toLocalDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return demande;
    }

    @FXML
    private void Retour(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AdminCrud.fxml"));
        switchScene(event, root);
    }

    private void switchScene(ActionEvent event, Parent root) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleFetchDevis() {
        String selectedReference = comboBoxDemande.getValue();
        if (selectedReference != null) {
            Integer demandeId = fetchDemandeIdByReference(selectedReference);
            if (demandeId != null) {
                comboBoxDevis.getItems().clear();
                List<String> devisReferences = fetchDevisReferencesByDemandeId(demandeId);
                comboBoxDevis.getItems().addAll(devisReferences);

                labelDevis.setText(devisReferences.isEmpty() ? "No devis found for this demande." : "Devis references loaded.");
            } else {
                labelDevis.setText("No matching id_demande found.");
            }
        } else {
            labelDevis.setText("Please select a demande reference.");
        }
    }

    private List<String> fetchDevisReferencesByDemandeId(Integer demandeId) {
        List<String> devisReferences = new ArrayList<>();
        String query = "SELECT referenceDevis FROM devis WHERE id_demande = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, demandeId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                devisReferences.add(rs.getString("referenceDevis"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return devisReferences;
    }

    private void showErrorDialog(String title, String message) {
        // Implement a method to show error dialogs to the user
        System.err.println(title + ": " + message); // Replace with JavaFX Alert dialog
    }

    // Demande class to hold fetched data
    public static class Demande {
        private String type;
        private String description;
        private int demandeurId;
        private LocalDate dateDemande;

        // Getters and setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public int getDemandeurId() { return demandeurId; }
        public void setDemandeurId(int demandeurId) { this.demandeurId = demandeurId; }
        public LocalDate getDateDemande() { return dateDemande; }
        public void setDateDemande(LocalDate dateDemande) { this.dateDemande = dateDemande; }
    }
}