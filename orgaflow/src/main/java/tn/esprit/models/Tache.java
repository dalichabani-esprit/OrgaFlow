package tn.esprit.models;

public class Tache {
    private int id, id_projet;
    private String nom, description, date_debut, date_fin, statut;

    public Tache() {}

    public Tache(int id, String nom, String description, String date_debut, String date_fin, String statut, int id_projet) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.statut = statut;
        this.id_projet = id_projet;
    }

    public Tache(String nom, String description, String date_debut, String date_fin, String statut, int id_projet) {
        this.nom = nom;
        this.description = description;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.statut = statut;
        this.id_projet = id_projet;
    }

    public Tache(int id) {
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

    public int getId_projet() {return this.id_projet;}
    public void setId_projet(int id_projet) {this.id_projet = id_projet;}

    @Override
    public String toString() {
        return "Tache => id: " + id + " nom: " + nom + " date_debut: " + date_debut +
                " date_fin: " + date_fin + " description: " + description + " statut: " + statut +
                " id_projet: " + id_projet + "\n";
    }

}
