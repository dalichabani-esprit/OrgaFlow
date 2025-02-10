package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidat;
import tn.esprit.models.Candidature;
import tn.esprit.models.Entretien;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEntretien implements IService <Entretien> {
    private final Connection cnx ;
    public ServiceEntretien() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    //EXistance de Candidature
    private boolean candidatureExists(int candidature_id) {
        String qry = "SELECT COUNT(*) FROM `candidature` WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, candidature_id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification du candidature : " + e.getMessage());
        }
        return false;
    }

    @Override
    public void add(Entretien entretien) {
        // Vérifier si la candidature existe avant d'ajouter l'entretien
        if (!candidatureExists(entretien.getCandidature_id())) {
            System.out.println(" Erreur : La candidature avec ID " + entretien.getCandidature_id() + " n'existe pas !");
            return;
        }

        String qry = "INSERT INTO `entretien` (`candidature_id`, `date_entretien`, `heure_entretien`, `lieu`, `intervieweur`, `notes`) VALUES (?,?,?,?,?,?)";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, entretien.getCandidature_id());
            pstm.setDate(2, entretien.getDateEntret());
            pstm.setTime(3, entretien.getTimeEntret());
            pstm.setString(4, entretien.getLieuEntret());
            pstm.setString(5, entretien.getInterviewerEntret());
            pstm.setString(6, entretien.getNotes());

            pstm.executeUpdate();
            System.out.println(" Entretien ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println(" Erreur lors de l'ajout de l'entretien : " + e.getMessage());
        }
    }


    @Override
    public List<Entretien> getAll() {

        //create Qry sql
        //execution
        //Mapping data


        List<Entretien> entretiens = new ArrayList<>();
        String qry ="SELECT * FROM `entretien`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()){
                Entretien e = new Entretien();
                e.setIdEntret(rs.getInt("id"));
                e.setCandidature_id(rs.getInt("candidature_id"));
                e.setDateEntret(rs.getDate("date_entretien"));
                e.setTimeEntret(rs.getTime("heure_entretien"));
                e.setLieuEntret(rs.getString("lieu"));
                e.setInterviewerEntret(rs.getString("intervieweur"));
                e.setNotes(rs.getString("notes"));



                entretiens.add(e);
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return entretiens;
    }

    @Override
    public void update(Entretien entretien) {
        String qry = "UPDATE `entretien` SET `candidature_id` = ?, `date_entretien` = ?, `heure_entretien` = ?, `lieu` = ?,`intervieweur` = ?,`notes` = ? WHERE `id` = ?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, entretien.getCandidature_id());
            pstm.setDate(2, entretien.getDateEntret());
            pstm.setTime(3, entretien.getTimeEntret());
            pstm.setString(4, entretien.getLieuEntret());
            pstm.setString(4, entretien.getInterviewerEntret());
            pstm.setString(4, entretien.getNotes());
            pstm.setInt(5, entretien.getIdEntret());


            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("L'entretien a été mis à jour avec succès !");
            } else {
                System.out.println("Aucun entretien trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Entretien getById(int id) {
        String qry = "SELECT * FROM `entretien` WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                Entretien e = new Entretien();
                e.setIdEntret(rs.getInt("id"));
                e.setCandidature_id(rs.getInt("candidature_id"));
                e.setDateEntret(rs.getDate("date_entretien"));
                e.setTimeEntret(rs.getTime("heure_entretien"));
                e.setLieuEntret(rs.getString("lieu"));
                e.setInterviewerEntret(rs.getString("intervieweur"));
                e.setNotes(rs.getString("notes"));


                return e;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'entretien : " + e.getMessage());
        }
        return null; // Retourne null si  n'existe pas
    }

    @Override
    public void delete(Entretien entretien) {
        // Vérifier si le candidat existe avant de le supprimer
        if (getById(entretien.getIdEntret()) == null) {
            System.out.println("Aucun entretien trouvé avec l'ID " + entretien.getIdEntret() + ". Suppression annulée.");
            return;
        }

        String qry = "DELETE FROM `entretien` WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, entretien.getIdEntret());

            int rowsDeleted = pstm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("L'entretien avec l'ID " + entretien.getIdEntret() + " a été supprimé avec succès !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'entretien : " + e.getMessage());
        }

    }
}
