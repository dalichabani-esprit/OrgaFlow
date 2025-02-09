package services;

import models.Formation;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceFormation {
    private Connection cnx;

    public ServiceFormation() {
        cnx = MyDatabase.getInstance().getConnection();
    }

    public void add(Formation formation) {
        String query = "INSERT INTO formation (nom, description, duree, date_debut, date_fin, categorie, id_formateur) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, formation.getNom());
            pstm.setString(2, formation.getDescription());
            pstm.setInt(3, formation.getDuree());
            pstm.setDate(4, formation.getDateDebut());
            pstm.setDate(5, formation.getDateFin());
            pstm.setString(6, formation.getCategorie());
            pstm.setInt(7, formation.getIdFormateur());

            pstm.executeUpdate();
            System.out.println("✅ Formation ajoutée avec succès !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout de la formation : " + e.getMessage());
        }
    }

    public List<Formation> getAll() {
        List<Formation> formations = new ArrayList<>();
        String query = "SELECT * FROM formation";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                Formation formation = new Formation(
                        rs.getInt("id_formation"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getInt("duree"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        rs.getString("categorie"),
                        rs.getInt("id_formateur")
                );
                formations.add(formation);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des formations : " + e.getMessage());
        }
        return formations;
    }

    public void update(Formation formation) {
        String query = "UPDATE formation SET nom=?, description=?, duree=?, date_debut=?, date_fin=?, categorie=?, id_formateur=? WHERE id_formation=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, formation.getNom());
            pstm.setString(2, formation.getDescription());
            pstm.setInt(3, formation.getDuree());
            pstm.setDate(4, formation.getDateDebut());
            pstm.setDate(5, formation.getDateFin());
            pstm.setString(6, formation.getCategorie());
            pstm.setInt(7, formation.getIdFormateur());
            pstm.setInt(8, formation.getIdFormation());

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Formation mise à jour avec succès !");
            } else {
                System.out.println("❌ Aucune formation trouvée avec cet ID !");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la mise à jour de la formation : " + e.getMessage());
        }
    }

    public void delete(Formation formation) {
        String query = "DELETE FROM formation WHERE id_formation=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, formation.getIdFormation());

            int rowsDeleted = pstm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Formation supprimée avec succès !");
            } else {
                System.out.println("❌ Aucune formation trouvée avec cet ID !");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression de la formation : " + e.getMessage());
        }
    }
}
