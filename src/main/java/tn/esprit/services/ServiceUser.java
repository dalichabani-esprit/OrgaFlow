package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.*;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser implements IService<User> {
    private final Connection cnx;

    public ServiceUser() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    // Vérification si l'email existe déjà
    public boolean emailExiste(String email) {
        String qry = "SELECT COUNT(*) FROM utilisateur WHERE email = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setString(1, email);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true; // Email déjà utilisé
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification de l'email : " + e.getMessage());
        }
        return false;
    }

    @Override
    public void add(User user) {
        if (emailExiste(user.getEmail())) {
            System.out.println("L'email " + user.getEmail() + " est déjà utilisé !");
            return; // Stopper l'ajout
        }

        String qry;
        boolean isAdmin = user instanceof Admin;

        // Pour l'Admin, ne pas insérer le type_utilisateur
        if (isAdmin) {
            qry = "INSERT INTO utilisateur (nom, prenom, email, motDePasse, role, type_utilisateur, dateCreation) VALUES (?, ?, ?, ?, ?, ?, NOW())";
        } else {
            qry = "INSERT INTO utilisateur (nom, prenom, email, motDePasse, role, type_utilisateur, dateCreation, dateEmbauche, salaire, departement, dateCandidature, statutCandidat, CvCandidat) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?)";
        }

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setString(1, user.getNom());
            pstm.setString(2, user.getPrenom());
            pstm.setString(3, user.getEmail());
            pstm.setString(4, user.getMotDePasse());
            pstm.setString(5, user.getRole());

            if (isAdmin) {
                pstm.setString(6, "admin"); // Définir type_utilisateur comme 'admin' pour les administrateurs
            } else {
                String typeUtilisateur = (user instanceof Employes) ? "employe" : "candidat";
                pstm.setString(6, typeUtilisateur); // Ajouter type_utilisateur uniquement pour employe et candidat

                if (user instanceof Employes) {
                    Employes employe = (Employes) user;
                    pstm.setDate(7, employe.getDateEmbauche());
                    pstm.setDouble(8, employe.getSalaire());
                    pstm.setString(9, employe.getDepartement());
                    pstm.setNull(10, Types.DATE);
                    pstm.setNull(11, Types.VARCHAR);
                    pstm.setNull(12, Types.VARCHAR);
                } else if (user instanceof Candidat) {
                    Candidat candidat = (Candidat) user;
                    pstm.setNull(7, Types.DATE);
                    pstm.setNull(8, Types.DOUBLE);
                    pstm.setNull(9, Types.VARCHAR);
                    pstm.setDate(10, candidat.getDateCandidature());
                    pstm.setString(11, candidat.getStatutCandidat());
                    pstm.setString(12, candidat.getCvCandidat());
                }
            }

            pstm.executeUpdate();
            System.out.println("Utilisateur ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
        }
    }
    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String qry = "SELECT * FROM utilisateur";

        try (Statement stm = cnx.createStatement(); ResultSet rs = stm.executeQuery(qry)) {
            while (rs.next()) {
                users.add(createUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
        }
        return users;
    }

    @Override
    public boolean update(User user) {
        String qry = "UPDATE utilisateur SET nom = ?, prenom = ?, email = ?, role = ?, type_utilisateur = ?, " +
                "salaire = ?, departement = ?, dateEmbauche = ?, dateCandidature = ?, statutCandidat = ?, CvCandidat=? " +
                "WHERE iduser = ?";

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setString(1, user.getNom());
            pstm.setString(2, user.getPrenom());
            pstm.setString(3, user.getEmail());
            pstm.setString(4, user.getRole());

            // Définir `type_utilisateur` ici avant d'ajouter les autres champs
            pstm.setString(5, user.getType_utilisateur());

            // Initialisation des valeurs des champs supplémentaires
            if (user instanceof Employes) {
                Employes employe = (Employes) user;
                pstm.setDouble(6, employe.getSalaire());
                pstm.setString(7, employe.getDepartement());
                pstm.setDate(8, employe.getDateEmbauche() != null ? employe.getDateEmbauche() : null);
                pstm.setNull(9, Types.DATE);
                pstm.setNull(10, Types.VARCHAR);
                pstm.setNull(11, Types.VARCHAR);
            } else if (user instanceof Candidat) {
                Candidat candidat = (Candidat) user;
                pstm.setNull(6, Types.DOUBLE);
                pstm.setNull(7, Types.VARCHAR);
                pstm.setNull(8, Types.DATE);
                pstm.setDate(9, candidat.getDateCandidature() != null ? candidat.getDateCandidature() : null);
                pstm.setString(10, candidat.getStatutCandidat());
                pstm.setString(11, candidat.getCvCandidat());
            } else { // Si c'est un Admin ou un User sans champs supplémentaires
                pstm.setNull(6, Types.DOUBLE);
                pstm.setNull(7, Types.VARCHAR);
                pstm.setNull(8, Types.DATE);
                pstm.setNull(9, Types.DATE);
                pstm.setNull(10, Types.VARCHAR);
                pstm.setNull(11, Types.VARCHAR);
            }

            pstm.setInt(12, user.getIduser());

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Mise à jour réussie pour l'utilisateur : " + user);
                return true;
            } else {
                System.out.println("Aucune mise à jour effectuée, ID non trouvé.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean exists(int iduser) {
        String qry = "SELECT COUNT(*) FROM utilisateur WHERE iduser = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, iduser);
            ResultSet rs = pstm.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void delete(User user) {
        if (user == null || user.getIduser() <= 0) {
            System.out.println("Erreur : Impossible de supprimer un utilisateur invalide !");
            return;
        }

        User existingUser = getByIduser(user.getIduser());

        if (existingUser == null) {
            System.out.println("Erreur : L'utilisateur avec ID " + user.getIduser() + " n'existe pas !");
            return;
        }

        String qry = "DELETE FROM utilisateur WHERE iduser = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, user.getIduser());
            int rowsDeleted = pstm.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Utilisateur supprimé avec succès !");
            } else {
                System.out.println("Aucun utilisateur supprimé.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    @Override
    public User getByIduser(int iduser) {
        if (iduser <= 0) {
            System.out.println("Utilisateur avec ID " + iduser + " non trouvé.");
        }
        String qry = "SELECT * FROM utilisateur WHERE iduser = ?";
        User user = null;

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, iduser);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    user = createUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
        }
        return user;
    }

    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("iduser");
        String nom = rs.getString("nom");
        String prenom = rs.getString("prenom");
        String email = rs.getString("email");
        String motDePasse = rs.getString("motDePasse");
        String role = rs.getString("role");
        String type_utilisateur = rs.getString("type_utilisateur");
        Date dateCreation = rs.getDate("dateCreation");

        switch (type_utilisateur) {
            case "admin":
                return new Admin(id, nom, prenom, email, motDePasse, type_utilisateur);
            case "employe":
                double salaire = rs.getDouble("salaire");
                String departement = rs.getString("departement");
                Date dateEmbauche = rs.getDate("dateEmbauche");
                return new Employes(id, nom, prenom, email, motDePasse, salaire, departement, dateEmbauche);
            case "candidat":
                Date dateCandidature = rs.getDate("dateCandidature");
                String statutCandidat = rs.getString("statutCandidat");
                String CvCandidat= rs.getString("CvCandidat");
                return new Candidat(id, nom, prenom, email, motDePasse, dateCandidature, statutCandidat, CvCandidat);
            default:
                return null;
        }
    }
}
