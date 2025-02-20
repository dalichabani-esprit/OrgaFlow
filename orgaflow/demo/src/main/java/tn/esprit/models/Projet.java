package tn.esprit.models;

public class Projet {
    private int id;
    private String nom, description, date_debut, date_fin, statut;

    public Projet() {}

    public Projet(int id, String nom, String description, String date_debut, String date_fin, String statut) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.statut = statut;
    }

    public Projet(String nom, String description, String date_debut, String date_fin, String statut) {
        this.nom = nom;
        this.description = description;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.statut = statut;
    }

    public Projet(int id) {
        // Constructeur seulement créé pour supprimer des entités avec l'id
        this.id = id;
    }

    public int getId() {return this.id;}
    public void setId(int id) {this.id = id;}

    public String getNom() {return this.nom;}
    public void setNom(String nom) {this.nom = nom;}

    public String getDate_debut() {return this.date_debut;}
    public void setDate_debut(String date_debut) {this.date_debut = date_debut;}

    public String getDate_fin() {return this.date_fin;}
    public void setDate_fin(String date_fin) {this.date_fin = date_fin;}

    public String getDescription() {return this.description;}
    public void setDescription(String description) {this.description = description;}

    public String getStatut() {return this.statut;}
    public void setStatut(String statut) {this.statut = statut;}

    @Override
    public String toString() {
        return "id: " + id + " nom: " + nom + " date_debut: " + date_debut +
                " date_fin: " + date_fin + " description: " + description + " statut: " + statut + "\n";
    }

}
