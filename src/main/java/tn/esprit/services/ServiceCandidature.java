package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidature;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCandidature implements IService<Candidature> {
    private final Connection cnx ;

    public ServiceCandidature() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    //EXistance de Candidat

    private boolean candidatExists(int candidatID) {
        ServiceCandidat sc = new ServiceCandidat();
        return sc.getById(candidatID) != null;
    }

    //Offre existe
    private boolean offreExists(int offreID) {
        ServiceOffreEmploi of = new ServiceOffreEmploi();
        return of.getById(offreID) != null;
    }



    @Override
    public void add(Candidature candidature) {
        // Vérifier si le candidat et l'offre existent avant d'ajouter la candidature
        if (!candidatExists(candidature.getCandidatID())) {
            System.out.println("Erreur : Le candidat avec ID " + candidature.getCandidatID() + " n'existe pas !");
            return;
        }

        if (!offreExists(candidature.getOffreID())) {
            System.out.println("Erreur : L'offre avec ID " + candidature.getOffreID() + " n'existe pas !");
            return;
        }

        String qry = "INSERT INTO `candidature`( `candidat_id`, `offre_id`, `date_candidature`, `statut`) VALUES (?,?,?,?)";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, candidature.getCandidatID());
            pstm.setInt(2, candidature.getOffreID());
            pstm.setDate(3, candidature.getDate_candidature());
            pstm.setString(4, candidature.getStatutCandidature());

            pstm.executeUpdate();
            System.out.println(" Candidature ajoutée avec succès !");
        } catch (SQLException e) {
            System.out.println(" Erreur lors de l'ajout de la candidature : " + e.getMessage());
        }
    }


    @Override
    public List<Candidature> getAll() {

            //create Qry sql
            //execution
            //Mapping data


            List<Candidature> candidatures = new ArrayList<>();
            String qry ="SELECT * FROM `candidature`";

            try {
                Statement stm = cnx.createStatement();
                ResultSet rs = stm.executeQuery(qry);

                while (rs.next()){
                    Candidature ca = new Candidature();
                    ca.setIdCandidature(rs.getInt("id"));
                    ca.setCandidatID(rs.getInt("candidat_id"));
                    ca.setOffreID(rs.getInt("offre_id"));
                    ca.setStatutCandidature(rs.getString("statut"));
                    ca.setDate_candidature(rs.getDate("date_candidature"));



                    candidatures.add(ca);
                }



            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return candidatures;
    }

    @Override
    public void update(Candidature candidature) {
        String qry = "UPDATE `candidature` SET `candidat_id` = ?, `offre_id` = ?, `date_candidature` = ?, `statut` = ? WHERE `id` = ?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, candidature.getCandidatID());
            pstm.setInt(2, candidature.getOffreID());
            pstm.setDate(3, candidature.getDate_candidature());
            pstm.setString(4, candidature.getStatutCandidature());
            pstm.setInt(5, candidature.getIdCandidature());


            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("La candidature a été mis à jour avec succès !");
            } else {
                System.out.println("Aucune candidature trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Candidature getById(int id) {
        String qry = "SELECT * FROM `candidature` WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                Candidature c = new Candidature();
                c.setIdCandidature(rs.getInt("id"));
                c.setCandidatID(rs.getInt("candidat_id"));
                c.setOffreID(rs.getInt("offre_id"));
                c.setDate_candidature(rs.getDate("date_candidature"));
                c.setStatutCandidature(rs.getString("statut"));


                return c;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de candidature : " + e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(Candidature candidature) {
        // Vérifier si la candidature existe avant de la supprimer
        if (getById(candidature.getIdCandidature()) == null) {
            System.out.println("Aucune candidature trouvé avec l'ID " + candidature.getIdCandidature() + ". Suppression annulée.");
            return;
        }

        String qry = "DELETE FROM `candidature` WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, candidature.getIdCandidature());

            int rowsDeleted = pstm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("La candidature avec l'ID " + candidature.getIdCandidature() + " a été suppriméeavec succès !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de candidature : " + e.getMessage());
        }

    }
}
