package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Reclamation;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReclamation implements IService<Reclamation> {
    private Connection cnx;

    public ServiceReclamation() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(Reclamation reclamation) {
        String qry = "INSERT INTO `reclamations`(`sujet`, `description`, `date_creation`, `statut`, `idcontrat`) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, reclamation.getSujet());
            pstm.setString(2, reclamation.getDescription());
            pstm.setDate(3, new Date(reclamation.getDateSoumission().getTime()));
            pstm.setString(4, reclamation.getStatut());
            pstm.setInt(5, reclamation.getIdContrat());


            pstm.executeUpdate();
            System.out.println("Réclamation ajoutée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la réclamation : " + e.getMessage());
        }
    }

    @Override
    public List<Reclamation> getAll() {
        List<Reclamation> reclamations = new ArrayList<>();
        String qry = "SELECT * FROM `reclamations`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setIdReclamation(rs.getInt("id_reclamation"));
                reclamation.setSujet(rs.getString("sujet"));
                reclamation.setDescription(rs.getString("description"));
                reclamation.setDateSoumission(rs.getDate("date_creation"));
                reclamation.setStatut(rs.getString("statut"));
                reclamation.setIdContrat(rs.getInt("idcontrat"));

                reclamations.add(reclamation);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des réclamations : " + e.getMessage());
        }

        return reclamations;
    }

    @Override
    public void update(Reclamation reclamation) {
        String qry = "UPDATE `reclamations` SET `sujet`=?, `description`=?, `date_creation`=?, `statut`=?, `idcontrat` = ? WHERE `id_reclamation`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, reclamation.getSujet());
            pstm.setString(2, reclamation.getDescription());
            pstm.setDate(3, new Date(reclamation.getDateSoumission().getTime()));
            pstm.setString(4, reclamation.getStatut());
            pstm.setInt(5, reclamation.getIdContrat());
            pstm.setInt(6, reclamation.getIdReclamation());

            pstm.executeUpdate();
            System.out.println("Réclamation mise à jour avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la réclamation : " + e.getMessage());
        }
    }

    @Override
    public void delete(Reclamation reclamation) {
        String qry = "DELETE FROM `reclamations` WHERE `id_reclamation`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, reclamation.getIdReclamation());

            pstm.executeUpdate();
            System.out.println("Réclamation supprimée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la réclamation : " + e.getMessage());
        }
    }
}
