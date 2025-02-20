package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Tache;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceTache implements IService<Tache> {
    private Connection cnx ;

    public ServiceTache(){
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(Tache tache) {
        //create Qry SQL
        //execute Qry
        String qry ="INSERT INTO `tache` (`nom`, `description`, `date_debut`, `date_fin`, `statut`, `id_projet`) VALUES(?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1,tache.getNom());
            pstm.setString(2, tache.getDescription());
            pstm.setString(3,tache.getDate_debut());
            pstm.setString(4,tache.getDate_fin());
            pstm.setString(5,tache.getStatut());
            pstm.setInt(6,tache.getId_projet());



            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Tache> getAll() {
        //create Qry sql
        //execution
        //Mapping data


        List<Tache> taches = new ArrayList<>();
        String qry ="SELECT * FROM `tache`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()){
                Tache t = new Tache();
                t.setId(rs.getInt("id"));
                t.setNom(rs.getString("nom"));
                t.setDescription(rs.getString("description"));
                t.setDate_debut(rs.getString("date_debut"));
                t.setDate_fin(rs.getString("date_fin"));
                t.setStatut(rs.getString("statut"));
                t.setId_projet(rs.getInt("id_projet"));

                taches.add(t);
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return taches;
    }

    @Override
    public void update(Tache tache) {
        String qry ="UPDATE `tache` SET `nom`=?, `description`=?, `date_debut`=?, `date_fin`=?, `statut`=?, `id_projet`=? WHERE `id`=?;";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1,tache.getNom());
            pstm.setString(2, tache.getDescription());
            pstm.setString(3,tache.getDate_debut());
            pstm.setString(4,tache.getDate_fin());
            pstm.setString(5,tache.getStatut());
            pstm.setInt(6,tache.getId_projet());
            pstm.setInt(7,tache.getId());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void delete(Tache tache) {
        String qry ="DELETE FROM `tache` WHERE `id` = ?;";
        try {

            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1,tache.getId());
            pstm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
