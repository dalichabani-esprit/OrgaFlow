package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabase {
    private static MyDatabase instance;

    private final String URL = "jdbc:mysql://localhost:3306/gestion_facturation";
    private final String USER = "root";
    private final String PASSWORD = "";
    private Connection connexion;


    private MyDatabase() {
        try {
            connexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion à la base de données établie !");
        } catch (SQLException ex) {
            System.err.println("Erreur de connexion : " + ex.getMessage());
        }
    }


    public static MyDatabase getInstance() {
        if (instance == null) {
            instance = new MyDatabase();
        }
        return instance;
    }


    public Connection getConnection() {
        return connexion;
    }
}