package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidat;
import tn.esprit.models.OffreEmploi;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceOffreEmploi implements IService<OffreEmploi> {
    private final Connection cnx ;
    public ServiceOffreEmploi() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(OffreEmploi offreEmploi) {
        //create Qry SQL
        //execute Qry
        String qry ="INSERT INTO `offreemploi`(`titre`, `description`, `departement`,`date_publication`,`date_limite`,`statut`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1,offreEmploi.getTitreOffre());
            pstm.setString(2, offreEmploi.getDescriptionOffre());
            pstm.setString(3,offreEmploi.getDepartementOffre());
            pstm.setDate(4,offreEmploi.getDate_publicationOffre());
            pstm.setDate(5,offreEmploi.getDate_limiteOffre());
            pstm.setString(6,offreEmploi.getStatutOffre());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<OffreEmploi> getAll() {
        //create Qry sql
        //execution
        //Mapping data


        List<OffreEmploi> offreemplois = new ArrayList<>();
        String qry ="SELECT * FROM `offreemploi`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()){
                OffreEmploi o = new OffreEmploi();
               o.setIdOffre(rs.getInt("id"));
               o.setTitreOffre(rs.getString("titre"));
               o.setDescriptionOffre(rs.getString("description"));
               o.setDepartementOffre(rs.getString("departement"));
               o.setDate_publicationOffre(rs.getDate("date_publication"));
               o.setDate_limiteOffre(rs.getDate("date_limite"));
               o.setStatutOffre(rs.getString("statut"));


                offreemplois.add(o);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return offreemplois;
    }

    @Override
    public void update(OffreEmploi offreEmploi) {
        String qry = "UPDATE `offreemploi` SET `titre` = ?, `description` = ?, `departement` = ?, `date_publication` = ? ,`date_limite` = ? ,`statut` = ? WHERE `id` = ?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, offreEmploi.getTitreOffre());
            pstm.setString(2, offreEmploi.getDescriptionOffre());
            pstm.setString(3, offreEmploi.getDepartementOffre());
            pstm.setDate(4, offreEmploi.getDate_publicationOffre());
            pstm.setDate(5, offreEmploi.getDate_limiteOffre());
            pstm.setString(6, offreEmploi.getStatutOffre());
            pstm.setInt(7, offreEmploi.getIdOffre());

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("L'offre a été mise à jour avec succès !");
            } else {
                System.out.println("Aucune offre trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public OffreEmploi getById(int id) {
        String qry = "SELECT * FROM `offreemploi` WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                OffreEmploi o= new OffreEmploi();
                o.setIdOffre(rs.getInt("id"));
                o.setTitreOffre(rs.getString("titre"));
                o.setDescriptionOffre(rs.getString("description"));
                o.setDepartementOffre(rs.getString("departement"));
                o.setDate_publicationOffre(rs.getDate("date_publication"));
                o.setDate_limiteOffre(rs.getDate("date_limite"));
                o.setStatutOffre(rs.getString("statut"));

                return o;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'offre : " + e.getMessage());
        }
        return null; // Retourne null si n'existe pas
    }

    @Override
    public void delete(OffreEmploi offreEmploi) {
        // Vérifier si le candidat existe avant de le supprimer
        if (getById(offreEmploi.getIdOffre()) == null) {
            System.out.println("Aucune aoffre trouvée avec l'ID " + offreEmploi.getIdOffre() + ". Suppression annulée.");
            return;
        }

        String qry = "DELETE FROM `offreemploi` WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, offreEmploi.getIdOffre());

            int rowsDeleted = pstm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("L'offre avec l'ID " + offreEmploi.getIdOffre() + " a été supprimée avec succès !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'offre : " + e.getMessage());
        }

    }
}
