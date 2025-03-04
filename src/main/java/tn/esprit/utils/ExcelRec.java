package tn.esprit.utils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tn.esprit.models.Reclamation;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
public class ExcelRec {
    public static void exportReclamationsToExcel(List<Reclamation> reclamations, String filePath) throws IOException {
        // Créer un nouveau classeur Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Réclamations");

        // Créer l'en-tête
        Row headerRow = sheet.createRow(0);
        String[] columns = {"ID", "ID Contrat", "Sujet", "Description", "Date de Soumission", "Statut"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Remplir les données
        int rowNum = 1;
        for (Reclamation reclamation : reclamations) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(reclamation.getIdReclamation());
            row.createCell(1).setCellValue(reclamation.getIdContrat());
            row.createCell(2).setCellValue(reclamation.getSujet());
            row.createCell(3).setCellValue(reclamation.getDescription());
            row.createCell(4).setCellValue(reclamation.getDateSoumission().toString());
            row.createCell(5).setCellValue(reclamation.getStatut());
        }

        // Ajuster la largeur des colonnes
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Sauvegarder le fichier Excel
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }

        // Fermer le classeur
        workbook.close();
    }
}
