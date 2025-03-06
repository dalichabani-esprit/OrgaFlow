/*package tn.esprit.services;

import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Admin;
import tn.esprit.models.Candidat;
import tn.esprit.models.Employes;
import tn.esprit.models.User;
import tn.esprit.utils.MyDatabase;
import tn.esprit.utils.PasswordHasher;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ServiceUser implements IService<User> {
    private final Connection cnx;

    public ServiceUser() {
        cnx = MyDatabase.getInstance().getCnx();
    }

    public boolean emailExiste(String email) {
        String qry = "SELECT COUNT(*) FROM utilisateur WHERE email = ?";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setString(1, email);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
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
            return;
        }

        // Vérifie si le mot de passe est déjà haché (éviter le double hachage)
        String hashedPassword = user.getMotDePasse();
        if (!hashedPassword.startsWith("$2a$")) {  // BCrypt haché commence toujours par "$2a$"
            hashedPassword = PasswordHasher.hashPassword(user.getMotDePasse());
        }

        String qry;
        if (user instanceof Employes) {
            qry = "INSERT INTO utilisateur (nom, prenom, email, motDePasse, role, salaire, departement, dateEmbauche) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        } else if (user instanceof Candidat) {
            qry = "INSERT INTO utilisateur (nom, prenom, email, motDePasse, role, dateCandidature, statutCandidat, CvCandidat) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        } else { // Admin
            qry = "INSERT INTO utilisateur (nom, prenom, email, motDePasse, role) VALUES (?, ?, ?, ?, ?)";
        }

        try (PreparedStatement pstm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, user.getNom());
            pstm.setString(2, user.getPrenom());
            pstm.setString(3, user.getEmail());
            pstm.setString(4, hashedPassword);  // ✅ Enregistre le mot de passe haché
            pstm.setString(5, user.getRole());

            if (user instanceof Employes) {
                Employes employe = (Employes) user;
                pstm.setString(6, employe.getSalaire());
                pstm.setString(7, employe.getDepartement());
                pstm.setDate(8, employe.getDateEmbauche());
            } else if (user instanceof Candidat) {
                Candidat candidat = (Candidat) user;
                pstm.setDate(6, candidat.getDateCandidature());
                pstm.setString(7, candidat.getStatutCandidat());
                pstm.setString(8, candidat.getCvCandidat());
            }

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        System.out.println("Utilisateur ajouté avec succès avec ID : " + generatedId);
                    }
                }
            }
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
    public void update(User user) {
        String qry = "UPDATE utilisateur SET nom = ?, prenom = ?, email = ?, role = ?, salaire = ?, departement = ?, dateEmbauche = ?, dateCandidature = ?, statutCandidat = ?, CvCandidat = ? WHERE iduser = ?";

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setString(1, user.getNom());
            pstm.setString(2, user.getPrenom());
            pstm.setString(3, user.getEmail());
            pstm.setString(4, user.getRole());

            if (user instanceof Employes) {
                Employes employe = (Employes) user;
                pstm.setString(5, employe.getSalaire());
                pstm.setString(6, employe.getDepartement());
                pstm.setDate(7, employe.getDateEmbauche() != null ? employe.getDateEmbauche() : null);
                pstm.setNull(8, Types.DATE);
                pstm.setNull(9, Types.VARCHAR);
                pstm.setNull(10, Types.VARCHAR);
            } else if (user instanceof Candidat) {
                Candidat candidat = (Candidat) user;
                pstm.setNull(5, Types.DOUBLE);
                pstm.setNull(6, Types.VARCHAR);
                pstm.setNull(7, Types.DATE);
                pstm.setDate(8, candidat.getDateCandidature() != null ? candidat.getDateCandidature() : null);
                pstm.setString(9, candidat.getStatutCandidat());
                pstm.setString(10, candidat.getCvCandidat());
            } else { // Si c'est un Admin ou un User sans champs supplémentaires
                pstm.setNull(5, Types.DOUBLE);
                pstm.setNull(6, Types.VARCHAR);
                pstm.setNull(7, Types.DATE);
                pstm.setNull(8, Types.DATE);
                pstm.setNull(9, Types.VARCHAR);
                pstm.setNull(10, Types.VARCHAR);
            }

            pstm.setInt(11, user.getIduser());

            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Mise à jour réussie pour l'utilisateur : " + user);
            } else {
                System.out.println("Aucune mise à jour effectuée, ID non trouvé.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getById(int id) {
        return null;
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

        if ("admin".equals(role)) {
            return new Admin(id, nom, prenom, email, motDePasse);
        } else if ("employe".equals(role)) {
            String salaire = rs.getString("salaire");
            String departement = rs.getString("departement");
            Date dateEmbauche = rs.getDate("dateEmbauche");
            return new Employes(id, nom, prenom, email, motDePasse, salaire, departement, dateEmbauche);
        } else if ("candidat".equals(role)) {
            Date dateCandidature = rs.getDate("dateCandidature");
            String statutCandidat = rs.getString("statutCandidat");
            String CvCandidat = rs.getString("CvCandidat");
            return new Candidat(id, nom, prenom, email, motDePasse, dateCandidature, statutCandidat, CvCandidat);
        }

        return null;
    }

    public int getTotalCandidats() {
        String query = "SELECT COUNT(*) FROM utilisateur WHERE role = 'candidat'";
        try (PreparedStatement stmt = cnx.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des candidats : " + e.getMessage());
        }
        return 0;
    }

    public int getTotalEmployes() {
        String query = "SELECT COUNT(*) FROM utilisateur WHERE role = 'employe'";
        try (PreparedStatement stmt = cnx.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);  // Retourne le nombre d'employés
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des employés : " + e.getMessage());
        }
        return 0;
    }

    public List<Candidat> searchCandidatsByKeyword(String keyword) {
        List<Candidat> candidats = new ArrayList<>();
        String qry = "SELECT * FROM utilisateur WHERE role = 'candidat' AND (nom LIKE ? OR prenom LIKE ? OR email LIKE ?)";

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            String searchPattern = "%" + keyword + "%";
            pstm.setString(1, searchPattern);
            pstm.setString(2, searchPattern);
            pstm.setString(3, searchPattern);

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    candidats.add(createCandidatFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche des candidats : " + e.getMessage());
        }

        return candidats;
    }

    private Candidat createCandidatFromResultSet(ResultSet rs) throws SQLException {
        return new Candidat(
                rs.getInt("iduser"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("email"),
                rs.getString("motDePasse"),
                rs.getDate("dateCandidature"),
                rs.getString("statutCandidat"),
                rs.getString("CvCandidat")
        );
    }

    public List<Employes> searchEmployesByKeyword(String keyword) {
        List<Employes> employes = new ArrayList<>();
        String qry = "SELECT * FROM utilisateur WHERE role = 'employe' AND (nom LIKE ? OR prenom LIKE ? OR email LIKE ?)";

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            String searchPattern = "%" + keyword + "%";
            pstm.setString(1, searchPattern);
            pstm.setString(2, searchPattern);
            pstm.setString(3, searchPattern);

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    employes.add(createEmployeFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche des employés : " + e.getMessage());
        }

        return employes;
    }

    private Employes createEmployeFromResultSet(ResultSet rs) throws SQLException {
        return new Employes(
                rs.getInt("iduser"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("email"),
                rs.getString("motDePasse"),
                rs.getString("salaire"),
                rs.getString("departement"),
                rs.getDate("dateEmbauche")
        );
    }

    public User getByEmail(String email) {
        String qry = "SELECT * FROM utilisateur WHERE email = ?";
        User user = null;  // C'est cette variable qui sera retournée

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setString(1, email);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("role"); // Supposons que tu as une colonne qui identifie le type d'utilisateur
                    if ("candidat".equals(role)) {
                        user = new Candidat(  // Assigner à `user`
                                rs.getInt("iduser"),
                                rs.getString("nom"),
                                rs.getString("prenom"),
                                rs.getString("email"),
                                rs.getString("motDePasse"),
                                rs.getDate("dateCandidature"),
                                rs.getString("statutCandidat"),
                                rs.getString("CvCandidat")
                        );
                    } else if ("employe".equals(role)) {
                        user = new Employes(  // Assigner à `user`
                                rs.getInt("iduser"),
                                rs.getString("nom"),
                                rs.getString("prenom"),
                                rs.getString("email"),
                                rs.getString("motDePasse"),
                                rs.getString("salaire"),
                                rs.getString("departement"),
                                rs.getDate("dateEmbauche")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'utilisateur par email : " + e.getMessage());
        }

        return user;
    }


    public void registerUser(String nom, String motDepasse) {
        String hashedPassword = PasswordHasher.hashPassword(motDepasse);
    }

    public boolean authenticateUser(String nom, String enteredmotDepasse, String storedHashedmotDepasse) {
        return PasswordHasher.verifyPassword(enteredmotDepasse, storedHashedmotDepasse);
    }


    public boolean isMotDePasseCorrect(String email, String motDePasse) {
        User user = getUserByEmail(email);

        if (user == null) {
            System.out.println("Utilisateur introuvable pour l'email : " + email);
            return false;
        }

        if (user.getMotDePasse() == null) {
            System.out.println("Le mot de passe est null pour l'utilisateur : " + email);
            return false;
        }

        return PasswordHasher.verifyPassword(motDePasse, user.getMotDePasse());
    }



    public String getRoleByEmail(String email) {
        String role = null;
        String query = "SELECT role FROM utilisateur WHERE email = ?";

        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                role = rs.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return role;
    }

    public User getUserByEmail(String email) {
        User user = null;
        String query = "SELECT iduser, email, motDePasse FROM utilisateur WHERE email = ?";

        try {
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {  // Vérifie si un résultat est trouvé
                user = new User();
                user.setIduser(rs.getInt("iduser"));  // Vérifie que la colonne 'id' existe
                user.setEmail(rs.getString("email"));
                user.setMotDePasse(rs.getString("motDePasse"));

                System.out.println("Utilisateur trouvé : " + user.getEmail() + " - ID: " + user.getIduser());
            } else {
                System.out.println("Aucun utilisateur trouvé pour l'email : " + email);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'utilisateur par email : " + e.getMessage());
        }

        return user;
    }

    public boolean authenticateUser(String email, String enteredPassword) {
        User user = getUserByEmail(email);

        if (user == null) {
            System.out.println("Utilisateur introuvable !");
            return false;
        }

        return PasswordHasher.verifyPassword(enteredPassword, user.getMotDePasse());
    }

}*/