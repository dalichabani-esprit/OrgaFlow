package services;

import interfaces.IService;
import models.devis;
import utils.MyDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDevis implements IService<devis> {
    private Connection cnx;

    public ServiceDevis() {
        cnx = MyDatabase.getInstance().getConnection();
    }


    // Méthode pour obtenir la connexion
    public Connection getConnection() {
        return cnx;
    }
    public void add(devis devis) {
        String query = "INSERT INTO devis (id_demande, montant_estime, date_devis, statut, referenceDevis) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, devis.getId_demande());
            pstm.setFloat(2, devis.getMontant_estime());
            pstm.setDate(3, new java.sql.Date(devis.getDate_devis().getTime()));
            pstm.setString(4, devis.getStatut());
            pstm.setString(5, devis.getReferenceDevis()); // Ajouter la référence

            pstm.executeUpdate();
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                devis.setId_devis(rs.getInt(1));
            }
            System.out.println("Devis ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du devis : " + e.getMessage());
        }
    }

    public List<devis> getAll() {
        List<devis> devisList = new ArrayList<>();
        String query = "SELECT * FROM devis";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                devis d = new devis(
                        rs.getInt("id_devis"),
                        rs.getInt("id_demande"),
                        rs.getFloat("montant_estime"),
                        rs.getDate("date_devis"),
                        rs.getString("statut"),
                        rs.getString("referenceDevis") // Récupérer la référence
                );
                devisList.add(d);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des devis : " + e.getMessage());
        }
        return devisList;
    }

    public void update(devis devis) {
        String query = "UPDATE devis SET id_demande=?, montant_estime=?, date_devis=?, statut=?, referenceDevis=? WHERE id_devis=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, devis.getId_demande());
            pstm.setFloat(2, devis.getMontant_estime());
            pstm.setDate(3, new java.sql.Date(devis.getDate_devis().getTime()));
            pstm.setString(4, devis.getStatut());
            pstm.setString(5, devis.getReferenceDevis()); // Ajouter la référence
            pstm.setInt(6, devis.getId_devis());

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Devis mis à jour avec succès !");
            } else {
                System.out.println("Aucun enregistrement trouvé avec cet ID !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du devis : " + e.getMessage());
        }
    }

    public void delete(devis devis) {
        String query = "DELETE FROM devis WHERE id_devis=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, devis.getId_devis());

            int rowsDeleted = pstm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Devis supprimé avec succès !");
            } else {
                System.out.println("Aucun enregistrement trouvé avec cet ID !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du devis : " + e.getMessage());
        }
    }
}