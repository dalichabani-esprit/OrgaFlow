package tn.esprit.utils;

import tn.esprit.models.Projet;
import tn.esprit.models.Tache;
import tn.esprit.services.*;

import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;


import java.io.FileNotFoundException;
import java.util.List;


public class PdfGen {
    public static void generateProjetPdf(String dest) throws FileNotFoundException {
        ServiceProjet sp = new ServiceProjet();
        // Create a PdfWriter
        PdfWriter writer = new PdfWriter(dest);

        // Create a PdfDocument
        PdfDocument pdf = new PdfDocument(writer);

        // Create a Document
        Document document = new Document(pdf);

        // Add title
        document.add(new Paragraph(dest));

        // Create a table with 5 columns (for the properties of Projet)
        float[] columnWidths = {2, 2, 2, 4, 2}; // Example column widths
        Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();

        // Add table header
        table.addHeaderCell(new Cell().add(new Paragraph("Nom")));
        table.addHeaderCell(new Cell().add(new Paragraph("Date Debut")));
        table.addHeaderCell(new Cell().add(new Paragraph("Date Fin")));
        table.addHeaderCell(new Cell().add(new Paragraph("Description")));
        table.addHeaderCell(new Cell().add(new Paragraph("Statut")));


        List<Projet> projets = sp.getAll();
        // Loop through the list and add each item to the document
        for (Projet p : projets) {
            table.addCell(new Cell().add(new Paragraph(p.getNom())));
            table.addCell(new Cell().add(new Paragraph(p.getDate_debut())));
            table.addCell(new Cell().add(new Paragraph(p.getDate_fin())));
            table.addCell(new Cell().add(new Paragraph(p.getDescription())));
            table.addCell(new Cell().add(new Paragraph(p.getStatut())));
        }

        // Add the table to the document
        document.add(table);

        // Close the document
        document.close();

    }


    public static void generateTachePdf(String dest) throws FileNotFoundException {
        ServiceTache st = new ServiceTache();
        // Create a PdfWriter
        PdfWriter writer = new PdfWriter(dest);

        // Create a PdfDocument
        PdfDocument pdf = new PdfDocument(writer);

        // Create a Document
        Document document = new Document(pdf);

        // Add title
        document.add(new Paragraph(dest));

        // Create a table with 5 columns (for the properties of Projet)
        float[] columnWidths = {2, 2, 2, 4, 2}; // Example column widths
        Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();

        // Add table header
        table.addHeaderCell(new Cell().add(new Paragraph("Nom")));
        table.addHeaderCell(new Cell().add(new Paragraph("Date Debut")));
        table.addHeaderCell(new Cell().add(new Paragraph("Date Fin")));
        table.addHeaderCell(new Cell().add(new Paragraph("Description")));
        table.addHeaderCell(new Cell().add(new Paragraph("Statut")));


        List<Tache> taches = st.getAll();
        // Loop through the list and add each item to the document
        for (Tache t : taches) {
            table.addCell(new Cell().add(new Paragraph(t.getNom())));
            table.addCell(new Cell().add(new Paragraph(t.getDate_debut())));
            table.addCell(new Cell().add(new Paragraph(t.getDate_fin())));
            table.addCell(new Cell().add(new Paragraph(t.getDescription())));
            table.addCell(new Cell().add(new Paragraph(t.getStatut())));
        }

        // Add the table to the document
        document.add(table);

        // Close the document
        document.close();

    }
}
