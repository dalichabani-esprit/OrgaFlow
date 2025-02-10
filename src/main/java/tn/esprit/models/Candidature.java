package tn.esprit.models;

import java.sql.Date;

public class Candidature {
    private int idCandidature,CandidatID,OffreID;
    private String statutCandidature;
    private Date date_candidature;

    public Candidature() {}

    public Candidature(int idCandidature, int candidatID, int offreID, String statutCandidature, Date date_candidature) {
        this.idCandidature = idCandidature;
        CandidatID = candidatID;
        OffreID = offreID;
        this.statutCandidature = statutCandidature;
        this.date_candidature = date_candidature;
    }

    public Candidature(int candidatID, int offreID, String statutCandidature, Date date_candidature) {
        CandidatID = candidatID;
        OffreID = offreID;
        this.statutCandidature = statutCandidature;
        this.date_candidature = date_candidature;
    }

    public int getIdCandidature() {
        return idCandidature;
    }

    public void setIdCandidature(int idCandidature) {
        this.idCandidature = idCandidature;
    }

    public Date getDate_candidature() {
        return date_candidature;
    }

    public void setDate_candidature(Date date_candidature) {
        this.date_candidature = date_candidature;
    }

    public String getStatutCandidature() {
        return statutCandidature;
    }

    public void setStatutCandidature(String statutCandidature) {
        this.statutCandidature = statutCandidature;
    }

    public int getOffreID() {
        return OffreID;
    }

    public void setOffreID(int offreID) {
        OffreID = offreID;
    }

    public int getCandidatID() {
        return CandidatID;
    }

    public void setCandidatID(int candidatID) {
        CandidatID = candidatID;
    }

    @Override
    public String toString() {
        return "Candidature{" +
                "idCandidature=" + idCandidature +
                ", CandidatID=" + CandidatID +
                ", OffreID=" + OffreID +
                ", statutCandidature='" + statutCandidature + '\'' +
                ", date_candidature=" + date_candidature +
                "}\n";
    }
}

