package models;

import java.sql.Date;

public class devis {
    private int id_devis;
    private int id_demande;
    private float montant_estime;
    private Date date_devis;
    private String statut;
    private String referenceDevis; // Ajout de l'attribut referenceDevis

    public devis() {}

    public devis(int id_devis, int id_demande, float montant_estime, Date date_devis, String statut, String referenceDevis) {
        this.id_devis = id_devis;
        this.id_demande = id_demande;
        this.montant_estime = montant_estime;
        this.date_devis = date_devis;
        this.statut = statut;
        this.referenceDevis = referenceDevis; // Initialisation de referenceDevis
    }

    public devis(int id_devis, float montant_estime, Date date_devis, String statut, String referenceDevis) {
        this.id_devis = id_devis;
        this.montant_estime = montant_estime;
        this.date_devis = date_devis;
        this.statut = statut;
        this.referenceDevis = referenceDevis; // Initialisation de referenceDevis
    }


    public devis(float montant_estime, Date date_devis, String statut) {

        this.montant_estime = montant_estime;
        this.date_devis = date_devis;
        this.statut = statut;

    }







    // Getters et Setters
    public int getId_devis() {
        return id_devis;
    }
    public void setId_devis(int id_devis) {
        this.id_devis = id_devis;
    }

    public int getId_demande() {
        return id_demande;
    }
    public void setId_demande(int id_demande) {
        this.id_demande = id_demande;
    }

    public float getMontant_estime() {
        return montant_estime;
    }
    public void setMontant_estime(float montant_estime) {
        this.montant_estime = montant_estime;
    }

    public Date getDate_devis() {
        return date_devis;
    }
    public void setDate_devis(Date date_devis) {
        this.date_devis = date_devis;
    }

    public String getStatut() {
        return statut;
    }
    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getReferenceDevis() {
        return referenceDevis; // Getter pour referenceDevis
    }
    public void setReferenceDevis(String referenceDevis) {
        this.referenceDevis = referenceDevis; // Setter pour referenceDevis
    }

    @Override
    public String toString() {
        return "devis{" +
                "id devis=" + id_devis +
                ", id demande=" + id_demande +
                ", montant estime=" + montant_estime +
                ", date devis=" + date_devis +
                ", statut='" + statut + '\'' +
                ", reference devis='" + referenceDevis + '\'' + // Ajout de referenceDevis
                '}';
    }
}