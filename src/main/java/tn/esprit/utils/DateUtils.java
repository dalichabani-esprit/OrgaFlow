package tn.esprit.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class DateUtils {

    /**
     * Convertit une chaîne de caractères en java.sql.Date.
     *
     * @param dateString La chaîne de caractères représentant la date (format "dd/MM/yyyy").
     * @return Un objet java.sql.Date correspondant à la date fournie.
     * @throws IllegalArgumentException Si la chaîne de caractères n'est pas au bon format.
     */
    public static Date convertStringToSqlDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            java.util.Date parsedDate = format.parse(dateString); // Convertir en java.util.Date
            return new Date(parsedDate.getTime()); // Convertir en java.sql.Date
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Format de date invalide. Utilisez le format 'dd/MM/yyyy'.");
        }
    }
}