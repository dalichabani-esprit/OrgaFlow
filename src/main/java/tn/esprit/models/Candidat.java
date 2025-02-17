package tn.esprit.models;

import java.sql.Date;

public class Candidat extends User {
    private Date dateCandidature;
    private String statutCandidat;
    private String CvCandidat;

    //  Constructeur pour la création
    public Candidat(String nom, String prenom, String email, String motDePasse, Date dateCandidature, String statutCandidat,String CvCandidat) {
        super(nom, prenom, email, motDePasse, "CANDIDAT", "candidat");
        this.dateCandidature = dateCandidature;
        this.statutCandidat = statutCandidat;
        this.CvCandidat = CvCandidat;
    }

    //  Constructeur pour la récupération depuis la BDD
    public Candidat(int iduser, String nom, String prenom, String email, String motDePasse, Date dateCandidature, String statutCandidat,String CvCandidat) {
        super(iduser, nom, prenom, email, motDePasse, "CANDIDAT", "candidat", null);
        this.dateCandidature = dateCandidature;
        this.statutCandidat = statutCandidat;
        this.CvCandidat = CvCandidat;
    }

    public Date getDateCandidature() {
        return dateCandidature;
    }

    public void setDateCandidature(Date dateCandidature) {
        this.dateCandidature = dateCandidature;
    }

    public String getStatutCandidat() {
        return statutCandidat;
    }

    public void setStatutCandidat(String statutCandidat) {
        this.statutCandidat = statutCandidat;
    }
    public String getCvCandidat() {
        return CvCandidat;
    }

    public void setCvCandidat(String cvCandidat) {
        CvCandidat = cvCandidat;
    }

    @Override
    public String toString() {
        return "Candidat{" +
                "dateCandidature=" + dateCandidature +
                ", statutCandidat='" + statutCandidat + '\'' +
                ", CvCandidat='" + CvCandidat + '\'' +
                '}';
    }
}
