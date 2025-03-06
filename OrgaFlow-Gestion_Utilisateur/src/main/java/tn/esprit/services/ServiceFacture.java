package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Candidat;
import tn.esprit.models.Employes;
import tn.esprit.models.facture;
import tn.esprit.utils.MyDatabase;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceFacture implements IService<facture> {
    private Connection cnx;

    public ServiceFacture() {
        cnx = MyDatabase.getInstance().getConnection();
    }

    public void add(facture facture) {
        String query = "INSERT INTO facture (id_devis, id_demande, montant_final, date_facture, statut, destinataire_id) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, facture.getId_devis());
            pstm.setInt(2, facture.getId_demande());
            pstm.setFloat(3, facture.getMontant_final());
            pstm.setDate(4, facture.getDate_facture());
            pstm.setString(5, facture.getStatut());
            pstm.setInt(6, facture.getDestinataire_id());

            pstm.executeUpdate();
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                facture.setId_facture(rs.getInt(1));
            }
            System.out.println("Facture ajoutée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la facture : " + e.getMessage());
        }
    }

    public List<facture> getAll() {
        List<facture> factures = new ArrayList<>();
        String query = "SELECT * FROM facture";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                facture f = new facture(
                        rs.getInt("id_facture"),
                        rs.getInt("id_devis"),
                        rs.getInt("id_demande"),
                        rs.getFloat("montant_final"),
                        rs.getDate("date_facture"),
                        rs.getString("statut"),
                        rs.getInt("destinataire_id")
                );
                factures.add(f);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des factures : " + e.getMessage());
        }
        return factures;
    }

    public void update(facture facture) {
        String query = "UPDATE facture SET id_devis=?, id_demande=?, montant_final=?, date_facture=?, statut=?, destinataire_id=? WHERE id_facture=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, facture.getId_devis());
            pstm.setInt(2, facture.getId_demande());
            pstm.setFloat(3, facture.getMontant_final());
            pstm.setDate(4, facture.getDate_facture());
            pstm.setString(5, facture.getStatut());
            pstm.setInt(6, facture.getDestinataire_id());
            pstm.setInt(7, facture.getId_facture());

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Facture mise à jour avec succès !");
            } else {
                System.out.println("Aucune facture trouvée avec cet ID !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la facture : " + e.getMessage());
        }
    }

    public void delete(facture facture) {
        String query = "DELETE FROM facture WHERE id_facture=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setInt(1, facture.getId_facture());

            int rowsDeleted = pstm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Facture supprimée avec succès !");
            } else {
                System.out.println("Aucune facture trouvée avec cet ID !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la facture : " + e.getMessage());
        }
    }

    @Override
    public facture getByIduser(int i) {
        return null;
    }

    @Override
    public boolean emailExiste(String email) {
        return false;
    }

    @Override
    public int getTotalCandidats() {
        return 0;
    }

    @Override
    public int getTotalEmployes() {
        return 0;
    }

    @Override
    public List<Candidat> searchCandidatsByKeyword(String keyword) {
        return null;
    }

    @Override
    public List<Employes> searchEmployesByKeyword(String keyword) {
        return null;
    }

    @Override
    public facture getByEmail(String email) {
        return null;
    }

    @Override
    public boolean isMotDePasseCorrect(String email, String motDePasse) {
        return false;
    }

    @Override
    public String getRoleByEmail(String email) {
        return null;
    }
}