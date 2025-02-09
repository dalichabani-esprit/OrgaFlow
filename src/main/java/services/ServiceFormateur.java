package services;

import models.Formateur;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceFormateur {
    private Connection cnx;

    public ServiceFormateur() {
        cnx = MyDatabase.getInstance().getConnection();
    }

    public void add(Formateur formateur) {
        String query = "INSERT INTO formateur (nom, prenom, email, telephone, specialite) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, formateur.getNom());
            pstm.setString(2, formateur.getPrenom());
            pstm.setString(3, formateur.getEmail());
            pstm.setString(4, formateur.getTelephone());
            pstm.setString(5, formateur.getSpecialite());

            pstm.executeUpdate();
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                formateur.setIdFormateur(rs.getInt(1));
            }
            System.out.println("✅ Formateur ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout du formateur : " + e.getMessage());
        }
    }

    public List<Formateur> getAll() {
        List<Formateur> formateurs = new ArrayList<>();
        String query = "SELECT * FROM formateur";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                Formateur f = new Formateur(
                        rs.getInt("id_formateur"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("specialite")
                );
                formateurs.add(f);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des formateurs : " + e.getMessage());
        }
        return formateurs;
    }

    public void update(Formateur formateur) {
        String query = "UPDATE formateur SET nom=?, prenom=?, email=?, telephone=?, specialite=? WHERE id_formateur=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, formateur.getNom());
            pstm.setString(2, formateur.getPrenom());
            pstm.setString(3, formateur.getEmail());
            pstm.setString(4, formateur.getTelephone());
            pstm.setString(5, formateur.getSpecialite());
            pstm.setInt(6, formateur.getIdFormateur());

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Formateur mis à jour avec succès !");
            } else {
                System.out.println("❌ Aucun formateur trouvé avec cet ID !");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la mise à jour du formateur : " + e.getMessage());
        }
    }

    public void delete(Formateur formateur) {
        String query = "DELETE FROM formateur WHERE id_formateur=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, formateur.getIdFormateur());

            int rowsDeleted = pstm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Formateur supprimé avec succès !");
            } else {
                System.out.println("❌ Aucun formateur trouvé avec cet ID !");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression du formateur : " + e.getMessage());
        }
    }
}
