package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Projet;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProjet implements IService<Projet> {
    private Connection cnx ;

    public ServiceProjet(){
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(Projet projet) {
        //create Qry SQL
        //execute Qry
        String qry ="INSERT INTO `projet` (`nom`, `description`, `date_debut`, `date_fin`, `statut`) VALUES(?, ?, ?, ?, ?);";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1,projet.getNom());
            pstm.setString(2, projet.getDescription());
            pstm.setString(3,projet.getDate_debut());
            pstm.setString(4,projet.getDate_fin());
            pstm.setString(5,projet.getStatut());


            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Projet> getAll() {
        //create Qry sql
        //execution
        //Mapping data


        List<Projet> personnes = new ArrayList<>();
        String qry ="SELECT * FROM `projet`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()){
                Projet p = new Projet();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setDescription(rs.getString("description"));
                p.setDate_debut(rs.getString("date_debut"));
                p.setDate_fin(rs.getString("date_fin"));
                p.setStatut(rs.getString("statut"));

                personnes.add(p);
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return personnes;
    }

    @Override
    public void update(Projet projet) {
        String qry ="UPDATE `projet` SET `nom`=?, `description`=?, `date_debut`=?, `date_fin`=?, `statut`=? WHERE `id`=?;";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1,projet.getNom());
            pstm.setString(2, projet.getDescription());
            pstm.setString(3,projet.getDate_debut());
            pstm.setString(4,projet.getDate_fin());
            pstm.setString(5,projet.getStatut());
            pstm.setInt(6,projet.getId());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void delete(Projet projet) {
        String qry ="DELETE FROM `projet` WHERE `id` = ?;";
        try {

            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1,projet.getId());
            pstm.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
