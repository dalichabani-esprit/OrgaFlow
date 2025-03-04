package tn.esprit.utils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tn.esprit.models.Contrat;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
public class ExcelExport {
    public static void exportContratsToExcel(List<Contrat> contrats, String filePath) throws IOException {
        // Créer un nouveau classeur Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Contrats");

        // Créer l'en-tête
        Row headerRow = sheet.createRow(0);
        String[] columns = {"ID", "Type de Contrat", "Date de Début", "Date de Fin", "Période d'Essai", "Renouvelable", "Salaire", "Statut"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Remplir les données
        int rowNum = 1;
        for (Contrat contrat : contrats) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(contrat.getIdContrat());
            row.createCell(1).setCellValue(contrat.getTypeContrat());
            row.createCell(2).setCellValue(contrat.getDateDebut().toString());
            row.createCell(3).setCellValue(contrat.getDateFin().toString());
            row.createCell(4).setCellValue(contrat.isPeriodeEssai());
            row.createCell(5).setCellValue(contrat.isRenouvelable());
            row.createCell(6).setCellValue(contrat.getSalaire());
            row.createCell(7).setCellValue(contrat.getStatut());
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
