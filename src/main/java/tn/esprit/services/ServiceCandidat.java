package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidat;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCandidat implements IService<Candidat> {

    private final Connection cnx ;

    public ServiceCandidat() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(Candidat candidat) {

        //create Qry SQL
        //execute Qry
        String qry ="INSERT INTO `candidat`(`nom`, `prenom`, `email`,`telephone`,`cv`,`date_inscription`,`statut`) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1,candidat.getNomCandidat());
            pstm.setString(2, candidat.getPrenomCandidat());
            pstm.setString(3,candidat.getEmailCandidat());
            pstm.setInt(4,candidat.getTelephoneCandidat());
            pstm.setString(5,candidat.getCvCandidat());
            pstm.setDate(6,candidat.getDateCandidat());
            pstm.setString(7,candidat.getStatutCandidat());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Candidat> getAll() {
        //create Qry sql
        //execution
        //Mapping data


        List<Candidat> candidats = new ArrayList<>();
        String qry ="SELECT * FROM `candidat`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()){
                Candidat c = new Candidat();
                c.setIdCandidat(rs.getInt("id"));
                c.setNomCandidat(rs.getString("nom"));
                c.setPrenomCandidat(rs.getString("prenom"));
                c.setEmailCandidat(rs.getString("email"));
                c.setTelephoneCandidat(rs.getInt("telephone"));
                c.setCvCandidat(rs.getString("cv"));
                c.setDateCandidat(rs.getDate("date_inscription"));
                c.setStatutCandidat(rs.getString("statut"));


                candidats.add(c);
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return candidats;
    }

    @Override
    public void update(Candidat candidat) {
        String qry = "UPDATE `candidat` SET `nom` = ?, `prenom` = ?, `email` = ?, `telephone` = ?, `cv` = ?, `date_inscription` = ?, `statut` = ? WHERE `id` = ?";

        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, candidat.getNomCandidat());
            pstm.setString(2, candidat.getPrenomCandidat());
            pstm.setString(3, candidat.getEmailCandidat());
            pstm.setInt(4, candidat.getTelephoneCandidat());
            pstm.setString(5, candidat.getCvCandidat());
            pstm.setDate(6, candidat.getDateCandidat());
            pstm.setString(7, candidat.getStatutCandidat());
            pstm.setInt(8, candidat.getIdCandidat()); // L'ID pour identifier le candidat

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Le candidat a été mis à jour avec succès !");
            } else {
                System.out.println("Aucun candidat trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }




    @Override
    public Candidat getById(int id) {
        String qry = "SELECT * FROM `candidat` WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                Candidat c = new Candidat();
                c.setIdCandidat(rs.getInt("id"));
                c.setNomCandidat(rs.getString("nom"));
                c.setPrenomCandidat(rs.getString("prenom"));
                c.setEmailCandidat(rs.getString("email"));
                c.setTelephoneCandidat(rs.getInt("telephone"));
                c.setCvCandidat(rs.getString("cv"));
                c.setDateCandidat(rs.getDate("date_inscription"));
                c.setStatutCandidat(rs.getString("statut"));

                return c;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du candidat : " + e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(Candidat candidat) {
        // Vérifier si le candidat existe
        if (getById(candidat.getIdCandidat()) == null) {
            System.out.println("Aucun candidat trouvé avec l'ID " + candidat.getIdCandidat() + ". Suppression annulée.");
            return;
        }

        String qry = "DELETE FROM `candidat` WHERE `id` = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, candidat.getIdCandidat());

            int rowsDeleted = pstm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Le candidat avec l'ID " + candidat.getIdCandidat() + " a été supprimé avec succès !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du candidat : " + e.getMessage());
        }
    }


}




