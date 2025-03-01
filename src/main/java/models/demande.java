package models;

import java.util.Date;

public class demande {
    private int id_demande;
    private String type;
    private String description;
    private int demandeur_id;
    private Date date_demande;
    private String statut;

    public demande() {}

    public demande(int id_demande, String type, String description, int demandeur_id, Date date_demande, String statut) {
        this.id_demande = id_demande;
        this.type = type;
        this.description = description;
        this.demandeur_id = demandeur_id;
        this.date_demande = date_demande;
        this.statut = statut;
    }


    public demande( String type, String description, int demandeur_id, Date date_demande, String statut) {

        this.type = type;
        this.description = description;
        this.demandeur_id = demandeur_id;
        this.date_demande = date_demande;
        this.statut = statut;
    }

    public demande(int id_demande, String description , Date date_demande , String statut,String type) {
        this.id_demande = id_demande;
        this.description = description;
        this.date_demande = date_demande;
        this.statut = statut;
        this.type = type;
    }


    public demande(int id_demande, String type , String description , Date date_demande , String statut) {
        this.id_demande = id_demande;
        this.description = description;
        this.date_demande = date_demande;
        this.statut = statut;
        this.type = type;
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
                '}';
    }
}