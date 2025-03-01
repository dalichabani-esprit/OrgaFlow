package models;

import java.sql.Date;

public class facture {
    private int id_facture;
    private int id_devis;
    private int id_demande;
    private float montant_final;
    private Date date_facture;
    private String statut;
    private int destinataire_id;

    public facture() {}

    public facture(int id_facture, int id_devis, int id_demande, float montant_final, Date date_facture, String statut, int destinataire_id) {
        this.id_facture = id_facture;
        this.id_devis = id_devis;
        this.id_demande = id_demande;
        this.montant_final = montant_final;
        this.date_facture = date_facture;
        this.statut = statut;
        this.destinataire_id = destinataire_id;
    }



    public facture(int id_facture , float montant_final, Date date_facture, String statut) {
        this.id_facture = id_facture;
        this.id_devis = id_devis;
        this.id_demande = id_demande;
        this.montant_final = montant_final;
        this.date_facture = date_facture;
        this.statut = statut;
        this.destinataire_id = destinataire_id;
    }



    public facture(int id_facture , float montant_final, Date date_facture, String statut , int destinataire_id) {
        this.id_facture = id_facture;
        this.id_devis = id_devis;
        this.id_demande = id_demande;
        this.montant_final = montant_final;
        this.date_facture = date_facture;
        this.statut = statut;
        this.destinataire_id = destinataire_id;
    }










    public int getId_facture() {
        return id_facture;
    }
    public void setId_facture(int id_facture) {
        this.id_facture = id_facture;
    }

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

    public float getMontant_final() {
        return montant_final;
    }
    public void setMontant_final(float montant_final) {
        this.montant_final = montant_final;
    }

    public Date getDate_facture() {
        return date_facture;
    }
    public void setDate_facture(Date date_facture) {
        this.date_facture = date_facture;
    }

    public String getStatut() {
        return statut;
    }
    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getDestinataire_id() {
        return destinataire_id;
    }
    public void setDestinataire_id(int destinataire_id) {
        this.destinataire_id = destinataire_id;
    }

    @Override
    public String toString() {
        return "facture{" +
                "id facture=" + id_facture +
                ", id devis=" + id_devis +
                ", id demande=" + id_demande +
                ", montant final=" + montant_final +
                ", date facture=" + date_facture +
                ", statut='" + statut + '\'' +
                ", destinataire id=" + destinataire_id +
                '}';
    }
}
