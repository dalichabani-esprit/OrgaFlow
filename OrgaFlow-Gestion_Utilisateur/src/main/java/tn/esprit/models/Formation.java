package tn.esprit.models;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.sql.Date;

public class Formation {
    private int idFormation;
    private String nom;
    private String description;
    private int duree;
    private Date dateDebut;
    private Date dateFin;
    private String categorie;
    @ManyToOne(fetch = FetchType.EAGER)
    private Formateur formateur;

    public Formation() {}
    public Formation(String nom, String description, int duree,
                     Date dateDebut, Date dateFin, String categorie, Formateur formateur) {
        this.nom = nom;
        this.description = description;
        this.duree = duree;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.categorie = categorie;
        this.formateur = formateur;
    }

    public Formation(int idFormation, String nom, String description, int duree,
                     Date dateDebut, Date dateFin, String categorie, Formateur formateur) {
        this.idFormation = idFormation;
        this.nom = nom;
        this.description = description;
        this.duree = duree;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.categorie = categorie;
        this.formateur = formateur;
    }



    public Formation(int idFormation, String nom, String categorie, String description, int duree, Date dateDebut, Date dateFin) {
    }

    public int getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(int idFormation) {
        this.idFormation = idFormation;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Formateur getFormateur() {
        return formateur;
    }

    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }

    @Override
    public String toString() {
        return "Formation{" +
                "id=" + idFormation +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", durée=" + duree +
                ", date début=" + dateDebut +
                ", date fin=" + dateFin +
                ", catégorie='" + categorie + '\'' +
                ", formateur=" + formateur +
                '}';
    }
}
