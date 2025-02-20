package tn.esprit.models;

import java.sql.Date;

public class Employes extends User {
    private String salaire;
    private String departement;
    private Date dateEmbauche;

    //  Constructeur pour la création
    public Employes(String nom, String prenom, String email, String motDePasse, String salaire, String departement, Date dateEmbauche) {
        super(nom, prenom, email, motDePasse,  "employe");
        this.salaire = salaire;
        this.departement = departement;
        this.dateEmbauche = dateEmbauche;
    }
    //  Constructeur pour la récupération depuis la BDD
    public Employes(int iduser, String nom, String prenom, String email, String motDePasse, String salaire, String departement, Date dateEmbauche) {
        super(iduser, nom, prenom, email, motDePasse, "employe");
        this.salaire = salaire;
        this.departement = departement;
        this.dateEmbauche = dateEmbauche;
    }

    public Employes() {

    }


    public String getSalaire() {
        return salaire;
    }

    public void setSalaire(String salaire) {
        this.salaire = salaire;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public Date getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(Date dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    @Override
    public String toString() {
        return super.toString() + " Employes{" +
                "salaire=" + salaire +
                ", departement='" + departement + '\'' +
                ", dateEmbauche=" + dateEmbauche +
                '}';
    }
}