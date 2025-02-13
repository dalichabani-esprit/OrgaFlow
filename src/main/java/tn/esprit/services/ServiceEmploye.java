package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Employe;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEmploye implements IService<Employe> {
    private Connection cnx;

    public ServiceEmploye() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    @Override
    public void add(Employe employe) {
        String qry = "INSERT INTO `employes`(`nom`, `prenom`, `date_naissance`, `email`, `telephone`, `poste`, `departement`, `date_embauche`, `statut`) VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, employe.getNom());
            pstm.setString(2, employe.getPrenom());
            pstm.setDate(3, new java.sql.Date(employe.getDateNaissance().getTime()));
            pstm.setString(4, employe.getEmail());
            pstm.setString(5, employe.getTelephone());
            pstm.setString(6, employe.getPoste());
            pstm.setString(7, employe.getDepartement());
            pstm.setDate(8, new java.sql.Date(employe.getDateEmbauche().getTime()));
            pstm.setString(9, employe.getStatut());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public List<Employe> getAll() {
        List<Employe> employes = new ArrayList<>();
        String qry = "SELECT * FROM `employes`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Employe e = new Employe();
                e.setIdEmploye(rs.getInt("id_employe"));
                e.setNom(rs.getString("nom"));
                e.setPrenom(rs.getString("prenom"));
                e.setDateNaissance(rs.getDate("date_naissance"));
                e.setEmail(rs.getString("email"));
                e.setTelephone(rs.getString("telephone"));
                e.setPoste(rs.getString("poste"));
                e.setDepartement(rs.getString("departement"));
                e.setDateEmbauche(rs.getDate("date_embauche"));
                e.setStatut(rs.getString("statut"));

                employes.add(e);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return employes;
    }

    @Override
    public void update(Employe employe) {
        String qry = "UPDATE `employes` SET `nom`=?, `prenom`=?, `date_naissance`=?, `email`=?, `telephone`=?, `poste`=?, `departement`=?, `date_embauche`=?, `statut`=? WHERE `id_employe`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setString(1, employe.getNom());
            pstm.setString(2, employe.getPrenom());
            pstm.setDate(3, new java.sql.Date(employe.getDateNaissance().getTime()));
            pstm.setString(4, employe.getEmail());
            pstm.setString(5, employe.getTelephone());
            pstm.setString(6, employe.getPoste());
            pstm.setString(7, employe.getDepartement());
            pstm.setDate(8, new java.sql.Date(employe.getDateEmbauche().getTime()));
            pstm.setString(9, employe.getStatut());
            pstm.setInt(10, employe.getIdEmploye());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Employe employe) {
        String qry = "DELETE FROM `employes` WHERE `id_employe`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, employe.getIdEmploye());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
