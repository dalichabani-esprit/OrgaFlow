package services;

import models.Formation;
import utils.MyDatabase;
import models.Formateur;

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
        try (PreparedStatement pstm = cnx.prepareStatement(query)) {
            pstm.setString(1, formation.getNom());
            pstm.setString(2, formation.getDescription());
            pstm.setInt(3, formation.getDuree());
            pstm.setDate(4, formation.getDateDebut());
            pstm.setDate(5, formation.getDateFin());
            pstm.setString(6, formation.getCategorie());
            pstm.setInt(7, formation.getFormateur().getIdFormateur());  // Accès à l'ID du formateur

            pstm.executeUpdate();
            System.out.println("Formation ajoutée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la formation : " + e.getMessage());
        }
    }

    public List<Formation> getAll() {
        List<Formation> formations = new ArrayList<>();
        String query = "SELECT * FROM formation";
        try (Statement stm = cnx.createStatement();
             ResultSet rs = stm.executeQuery(query)) {

            while (rs.next()) {
                int idFormateur = rs.getInt("id_formateur");  // ID du formateur
                Formateur formateur = getFormateurById(idFormateur);  // Récupérer l'objet Formateur

                Formation formation = new Formation(
                        rs.getInt("id_formation"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getInt("duree"),
                        rs.getDate("date_debut"),
                        rs.getDate("date_fin"),
                        rs.getString("categorie"),
                        formateur  // Associer l'objet Formateur
                );
                formations.add(formation);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des formations : " + e.getMessage());
        }
        return formations;
    }

    private Formateur getFormateurById(int idFormateur) {
        // Récupérer un formateur en fonction de son ID (vous pouvez ajuster cette méthode selon votre base de données)
        Formateur formateur = null;
        String query = "SELECT * FROM formateur WHERE id_formateur=?";
        try (PreparedStatement pstm = cnx.prepareStatement(query)) {
            pstm.setInt(1, idFormateur);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                formateur = new Formateur(
                        rs.getInt("id_formateur"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("specialite")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du formateur : " + e.getMessage());
        }
        return formateur;
    }

    public void update(Formation formation) {
        String query = "UPDATE formation SET nom=?, description=?, duree=?, date_debut=?, date_fin=?, categorie=?, id_formateur=? WHERE id_formation=?";
        try (PreparedStatement pstm = cnx.prepareStatement(query)) {
            pstm.setString(1, formation.getNom());
            pstm.setString(2, formation.getDescription());
            pstm.setInt(3, formation.getDuree());
            pstm.setDate(4, formation.getDateDebut());
            pstm.setDate(5, formation.getDateFin());
            pstm.setString(6, formation.getCategorie());
            pstm.setInt(7, formation.getFormateur().getIdFormateur());  // Accès à l'ID du formateur
            pstm.setInt(8, formation.getIdFormation());

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Formation mise à jour avec succès !");
            } else {
                System.out.println("Aucune formation trouvée avec cet ID !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la formation : " + e.getMessage());
        }
    }

    public void delete(Formation formation) {
        String query = "DELETE FROM formation WHERE id_formation=?";
        try (PreparedStatement pstm = cnx.prepareStatement(query)) {
            pstm.setInt(1, formation.getIdFormation());

            int rowsDeleted = pstm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Formation supprimée avec succès !");
            } else {
                System.out.println("Aucune formation trouvée avec cet ID !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la formation : " + e.getMessage());
        }
    }
}
