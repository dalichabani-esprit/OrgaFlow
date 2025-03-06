package tn.esprit.models;

public class Formateur {
    private int idFormateur;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String specialite;

    // Constructeur par défaut
    public Formateur() {}

    // Constructeur avec paramètres
    public Formateur(int idFormateur, String nom, String prenom, String email, String telephone, String specialite) {
        this.idFormateur = idFormateur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.specialite = specialite;
    }

    // Getters et Setters
    public int getIdFormateur() { return idFormateur; }
    public void setIdFormateur(int idFormateur) { this.idFormateur = idFormateur; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    @Override
    public String toString() {
        return "Formateur{" +
                "id=" + idFormateur +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", téléphone='" + telephone + '\'' +
                ", spécialité='" + specialite + '\'' +
                '}';
    }
}
