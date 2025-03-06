package tn.esprit.models;

import java.util.Date;

public class demande {
    private int id_demande;
    private String type;
    private String description;
    private int demandeur_id;
    private Date date_demande;
    private String statut;
    private String reference; // Nouvel attribut

    public demande() {}

    public demande(int id_demande, String type, String description, int demandeur_id, Date date_demande, String statut, String reference) {
        this.id_demande = id_demande;
        this.type = type;
        this.description = description;
        this.demandeur_id = demandeur_id;
        this.date_demande = date_demande;
        this.statut = statut;
        this.reference = reference; // Initialisation
    }

    public demande(String type, String description, int demandeur_id, Date date_demande, String statut, String reference) {
        this.type = type;
        this.description = description;
        this.demandeur_id = demandeur_id;
        this.date_demande = date_demande;
        this.statut = statut;
        this.reference = reference; // Initialisation
    }

    public demande(int id_demande, String description, Date date_demande, String statut, String type, String reference) {
        this.id_demande = id_demande;
        this.description = description;
        this.date_demande = date_demande;
        this.statut = statut;
        this.type = type;
        this.reference = reference; // Initialisation
    }

    public demande(int id_demande, String type, String description, Date date_demande, String statut, String reference) {
        this.id_demande = id_demande;
        this.description = description;
        this.date_demande = date_demande;
        this.statut = statut;
        this.type = type;
        this.reference = reference; // Initialisation
    }


    public demande( String type, String description, Date date_demande, String statut, String reference) {
        this.type = type;
        this.description = description;
        this.date_demande = date_demande;
        this.statut = statut;
        this.reference = reference; // Initialisation
    }

    public demande( String reference, String type, String description, Date date_demande, String statut) {
        this.type = type;
        this.description = description;
        this.date_demande = date_demande;
        this.statut = statut;
        this.reference = reference; // Initialisation
    }








    // Getters et Setters pour l'attribut reference
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getId_demande() {
        return id_demande;
    }

    public void setId_demande(int id_demande) {
        this.id_demande = id_demande;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDemandeur_id() {
        return demandeur_id;
    }

    public void setDemandeur_id(int demandeur_id) {
        this.demandeur_id = demandeur_id;
    }

    public Date getDate_demande() {
        return date_demande;
    }

    public void setDate_demande(Date date_demande) {
        this.date_demande = date_demande;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "demande{" +
                "id demande=" + id_demande +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", demandeur id=" + demandeur_id +
                ", date demande=" + date_demande +
                ", statut='" + statut + '\'' +
                ", reference='" + reference + '\'' + // Ajout de reference
                '}';
    }
}