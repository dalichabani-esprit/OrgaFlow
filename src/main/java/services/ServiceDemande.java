package services;

import interfaces.IService;
import models.demande;
import utils.MyDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDemande implements IService<demande> {
    private Connection cnx;

    public ServiceDemande() {
        cnx = MyDatabase.getInstance().getConnection();
    }

    public void add(demande demande) {
        String query = "INSERT INTO demande (type, description, demandeur_id, date_demande, statut) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, demande.getType());
            pstm.setString(2, demande.getDescription());
            pstm.setInt(3, demande.getDemandeur_id());
            pstm.setDate(4, new java.sql.Date(demande.getDate_demande().getTime()));
            pstm.setString(5, demande.getStatut());

            pstm.executeUpdate();
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                demande.setId_demande(rs.getInt(1));
            }
            System.out.println("Demande ajoutée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la demande : " + e.getMessage());
        }
    }

    public List<demande> getAll() {
        List<demande> demandes = new ArrayList<>();
        String query = "SELECT * FROM demande";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                demande d = new demande(
                        rs.getInt("id_demande"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getInt("demandeur_id"),
                        rs.getDate("date_demande"),
                        rs.getString("statut")
                );
                demandes.add(d);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des demandes : " + e.getMessage());
        }
        return demandes;
    }

    public void update(demande demande) {
        String query = "UPDATE demande SET type=?, description=?, demandeur_id=?, date_demande=?, statut=? WHERE id_demande=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, demande.getType());
            pstm.setString(2, demande.getDescription());
            pstm.setInt(3, demande.getDemandeur_id());
            pstm.setDate(4, new java.sql.Date(demande.getDate_demande().getTime()));
            pstm.setString(5, demande.getStatut());
            pstm.setInt(6, demande.getId_demande());

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Demande mise à jour avec succès !");
            } else {
                System.out.println("Aucune demande trouvée avec cet ID !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la demande : " + e.getMessage());
        }
    }

    public void delete(demande demande) {
        String query = "DELETE FROM demande WHERE id_demande=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, demande.getId_demande());

            int rowsDeleted = pstm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Demande supprimée avec succès !");
            } else {
                System.out.println("Aucune demande trouvée avec cet ID !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la demande : " + e.getMessage());
        }
    }
}