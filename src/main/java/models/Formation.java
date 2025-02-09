package models;

import java.sql.Date;

public class Formation {
    private int idFormation;
    private String nom;
    private String description;
    private int duree;
    private Date dateDebut;
    private Date dateFin;
    private String categorie;
    private int idFormateur;

    public Formation() {}

    public Formation(int idFormation, String nom, String description, int duree,
                     Date dateDebut, Date dateFin, String categorie, int idFormateur) {
        this.idFormation = idFormation;
        this.nom = nom;
        this.description = description;
        this.duree = duree;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.categorie = categorie;
        this.idFormateur = idFormateur;
    }

    public int getIdFormation() { return idFormation; }
    public void setIdFormation(int idFormation) { this.idFormation = idFormation; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getDuree() { return duree; }
    public void setDuree(int duree) { this.duree = duree; }

    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }

    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public int getIdFormateur() { return idFormateur; }
    public void setIdFormateur(int idFormateur) { this.idFormateur = idFormateur; }

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
                ", id formateur=" + idFormateur +
                '}';
    }
}
