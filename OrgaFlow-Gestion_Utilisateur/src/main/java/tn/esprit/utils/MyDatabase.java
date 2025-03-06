package tn.esprit.utils;
//DB PARAM

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabase {
    private static  MyDatabase instance  ;

    private final String URL ="jdbc:mysql://localhost:3306/orgaflowdb";
    private  final String USER="root";
    private final String PASSWORD="";

    //var
    private Connection connexion;



    private MyDatabase(){
        try {
            connexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("connexion established")   ;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static MyDatabase getInstance(){
        if(instance == null)
            instance=new MyDatabase();
        return instance;
    }
    public  Connection getConnection(){
        return  connexion;
    }
    public Connection getCnx() {
        try {
            if (connexion == null || connexion.isClosed()) {
                connexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Reconnection to the database...");
            }
        } catch (SQLException e) {
            System.out.println("Error checking connection: " + e.getMessage());
        }
        return connexion;
    }
}