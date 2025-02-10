package tn.esprit.utils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeUtils {

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public static Time stringToTime(String timeString) {
        try {
            long ms = timeFormat.parse(timeString).getTime(); // Convertit en millisecondes
            return new Time(ms);
        } catch (ParseException e) {
            System.out.println("Erreur : Format de l'heure invalide. Utiliser HH:mm:ss");
            return null;
        }
    }

    public static String timeToString(Time time) {
        return timeFormat.format(time);
    }
}

