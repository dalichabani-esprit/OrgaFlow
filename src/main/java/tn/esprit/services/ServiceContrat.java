package tn.esprit.services;

import tn.esprit.models.Contrat;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceContrat {

    private static Connection cnx;

    public ServiceContrat() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    // Ajouter un contrat
    public void add(Contrat contrat) {
        String qry = "INSERT INTO contrats (type_contrat, date_debut, date_fin, periode_essai, renouvelable, salaire, statut) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, contrat.getTypeContrat());
            pstm.setDate(2, new Date(contrat.getDateDebut().getTime()));
            pstm.setDate(3, contrat.getDateFin() != null ? new Date(contrat.getDateFin().getTime()) : null);
            pstm.setBoolean(4, contrat.isPeriodeEssai());
            pstm.setBoolean(5, contrat.isRenouvelable());
            pstm.setDouble(6, contrat.getSalaire());
            pstm.setString(7, contrat.getStatut());

            pstm.executeUpdate();
            System.out.println("Contrat ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du contrat : " + e.getMessage());
        }
    }

    // Récupérer tous les contrats
    public static List<Contrat> getAll() {
        List<Contrat> contrats = new ArrayList<>();
        String qry = "SELECT * FROM contrats";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Contrat contrat = new Contrat();
                contrat.setIdContrat(rs.getInt("id_contrat"));
                contrat.setTypeContrat(rs.getString("type_contrat"));
                contrat.setDateDebut(rs.getDate("date_debut"));
                contrat.setDateFin(rs.getDate("date_fin"));
                contrat.setPeriodeEssai(rs.getBoolean("periode_essai"));
                contrat.setRenouvelable(rs.getBoolean("renouvelable"));
                contrat.setSalaire(rs.getDouble("salaire"));
                contrat.setStatut(rs.getString("statut"));

                contrats.add(contrat);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des contrats : " + e.getMessage());
        }
        return contrats;
    }

    // Mettre à jour un contrat
    public void update(Contrat contrat) {
        String qry = "UPDATE contrats SET type_contrat = ?, date_debut = ?, date_fin = ?, periode_essai = ?, renouvelable = ?, salaire = ?, statut = ? WHERE id_contrat = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, contrat.getTypeContrat());
            pstm.setDate(2, new Date(contrat.getDateDebut().getTime()));
            pstm.setDate(3, contrat.getDateFin() != null ? new Date(contrat.getDateFin().getTime()) : null);
            pstm.setBoolean(4, contrat.isPeriodeEssai());
            pstm.setBoolean(5, contrat.isRenouvelable());
            pstm.setDouble(6, contrat.getSalaire());
            pstm.setString(7, contrat.getStatut());
            pstm.setInt(8, contrat.getIdContrat());

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
