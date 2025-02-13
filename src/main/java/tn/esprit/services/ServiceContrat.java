package tn.esprit.services;

import tn.esprit.models.Contrat;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceContrat {

    private Connection cnx;

    public ServiceContrat() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    // Ajouter un contrat
    public void add(Contrat contrat) {
        String qry = "INSERT INTO contrats (id_employe, type_contrat, date_debut, date_fin, periode_essai, renouvelable, salaire, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, contrat.getIdEmploye());
            pstm.setString(2, contrat.getTypeContrat());
            pstm.setDate(3, new java.sql.Date(contrat.getDateDebut().getTime()));
            pstm.setDate(4, contrat.getDateFin() != null ? new java.sql.Date(contrat.getDateFin().getTime()) : null);
            pstm.setBoolean(5, contrat.isPeriodeEssai());
            pstm.setBoolean(6, contrat.isRenouvelable());
            pstm.setDouble(7, contrat.getSalaire());
            pstm.setString(8, contrat.getStatus());

            pstm.executeUpdate();
            System.out.println("Contrat ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du contrat : " + e.getMessage());
        }
    }

    // Récupérer tous les contrats
    public List<Contrat> getAll() {
        List<Contrat> contrats = new ArrayList<>();
        String qry = "SELECT * FROM contrats";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Contrat contrat = new Contrat();
                contrat.setIdContrat(rs.getInt("id_contrat"));
                contrat.setIdEmploye(rs.getInt("id_employe"));
                contrat.setTypeContrat(rs.getString("type_contrat"));
                contrat.setDateDebut(rs.getDate("date_debut"));
                contrat.setDateFin(rs.getDate("date_fin"));
                contrat.setPeriodeEssai(rs.getBoolean("periode_essai"));
                contrat.setRenouvelable(rs.getBoolean("renouvelable"));
                contrat.setSalaire(rs.getDouble("salaire"));
                contrat.setStatus(rs.getString("status"));

                contrats.add(contrat);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des contrats : " + e.getMessage());
        }
        return contrats;
    }

    // Mettre à jour un contrat
    public void update(Contrat contrat) {
        String qry = "UPDATE contrats SET id_employe = ?, type_contrat = ?, date_debut = ?, date_fin = ?, periode_essai = ?, renouvelable = ?, salaire = ?, status = ? WHERE id_contrat = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, contrat.getIdEmploye());
            pstm.setString(2, contrat.getTypeContrat());
            pstm.setDate(3, new java.sql.Date(contrat.getDateDebut().getTime()));
            pstm.setDate(4, contrat.getDateFin() != null ? new java.sql.Date(contrat.getDateFin().getTime()) : null);
            pstm.setBoolean(5, contrat.isPeriodeEssai());
            pstm.setBoolean(6, contrat.isRenouvelable());
            pstm.setDouble(7, contrat.getSalaire());
            pstm.setString(8, contrat.getStatus());
            pstm.setInt(9, contrat.getIdContrat());

            pstm.executeUpdate();
            System.out.println("Contrat mis à jour avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du contrat : " + e.getMessage());
        }
    }

    // Supprimer un contrat
    public void delete(Contrat contrat) {
        String qry = "DELETE FROM contrats WHERE id_contrat = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, contrat.getIdContrat());

            pstm.executeUpdate();
            System.out.println("Contrat supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du contrat : " + e.getMessage());
        }
    }
}
