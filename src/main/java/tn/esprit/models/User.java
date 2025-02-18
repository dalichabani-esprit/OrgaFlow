/*package tn.esprit.models;

import java.sql.Date;

public class User {
    private int iduser;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String role;
    private String type_utilisateur;
    private Date dateCreation;

    //  Constructeur pour la création (sans ID)
    public User(String nom, String prenom, String email, String motDePasse, String role, String type_utilisateur) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
        this.type_utilisateur = type_utilisateur;
        this.dateCreation = new Date(System.currentTimeMillis()); // Date par défaut
    }

    //  Constructeur pour la récupération depuis la BDD (avec ID)
    public User(int iduser, String nom, String prenom, String email, String motDePasse, String role, String type_utilisateur, Date dateCreation) {
        this(nom, prenom, email, motDePasse, role, type_utilisateur);
        this.iduser = iduser;
        this.dateCreation = dateCreation;
    }

    //  Getters et Setters
    public int getIduser() { return iduser; }
    public void setIduser(int iduser) { this.iduser = iduser; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getType_utilisateur() { return type_utilisateur; }
    public void setType_utilisateur(String type_uttilisateur) { this.type_utilisateur = type_uttilisateur; }

    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }

    @Override
    public String toString() {
        return "User{" +
                "iduser=" + iduser +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", typeUtilisateur='" + type_utilisateur + '\'' +
                ", dateCreation=" + dateCreation +
                '}';
    }
}
*/