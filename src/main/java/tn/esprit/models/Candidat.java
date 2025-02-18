package tn.esprit.models;

import java.sql.Date;

public class Candidat {

    private int idCandidat,telephoneCandidat;
    private String nomCandidat,cvCandidat,prenomCandidat,emailCandidat,statutCandidat;
    private Date dateCandidat;


    public Candidat() {}

    public Candidat(Date dateCandidat, String statutCandidat, String emailCandidat, String prenomCandidat, String cvCandidat, int telephoneCandidat, String nomCandidat, int idCandidat) {
        this.dateCandidat = dateCandidat;
        this.statutCandidat = statutCandidat;
        this.emailCandidat = emailCandidat;
        this.prenomCandidat = prenomCandidat;
        this.cvCandidat = cvCandidat;
        this.telephoneCandidat = telephoneCandidat;
        this.nomCandidat = nomCandidat;
        this.idCandidat = idCandidat;
    }

    public Candidat(Date dateCandidat, String statutCandidat, String emailCandidat, String prenomCandidat, String cvCandidat, String nomCandidat, int telephoneCandidat) {
        this.dateCandidat = dateCandidat;
        this.statutCandidat = statutCandidat;
        this.emailCandidat = emailCandidat;
        this.prenomCandidat = prenomCandidat;
        this.cvCandidat = cvCandidat;
        this.nomCandidat = nomCandidat;
        this.telephoneCandidat = telephoneCandidat;
    }

    public int getIdCandidat() {
        return idCandidat;
    }

    public void setIdCandidat(int idCandidat) {
        this.idCandidat = idCandidat;
    }

    public int getTelephoneCandidat() {
        return telephoneCandidat;
    }

    public void setTelephoneCandidat(int telephoneCandidat) {
        this.telephoneCandidat = telephoneCandidat;
    }

    public String getNomCandidat() {
        return nomCandidat;
    }

    public void setNomCandidat(String nomCandidat) {
        this.nomCandidat = nomCandidat;
    }

    public String getCvCandidat() {
        return cvCandidat;
    }

    public void setCvCandidat(String cvCandidat) {
        this.cvCandidat = cvCandidat;
    }

    public String getPrenomCandidat() {
        return prenomCandidat;
    }

    public void setPrenomCandidat(String prenomCandidat) {
        this.prenomCandidat = prenomCandidat;
    }

    public String getEmailCandidat() {
        return emailCandidat;
    }

    public void setEmailCandidat(String emailCandidat) {
        this.emailCandidat = emailCandidat;
    }

    public String getStatutCandidat() {
        return statutCandidat;
    }

    public void setStatutCandidat(String statutCandidat) {
        this.statutCandidat = statutCandidat;
    }

    public Date getDateCandidat() {
        return dateCandidat;
    }

    public void setDateCandidat(Date dateCandidat) {
        this.dateCandidat = dateCandidat;
    }

    @Override
    public String toString() {
        return "Candidat{" +
                "idCandidat=" + idCandidat +
                ", telephoneCandidat=" + telephoneCandidat +
                ", nomCandidat='" + nomCandidat + '\'' +
                ", cvCandidat='" + cvCandidat + '\'' +
                ", prenomCandidat='" + prenomCandidat + '\'' +
                ", emailCandidat='" + emailCandidat + '\'' +
                ", statutCandidat='" + statutCandidat + '\'' +
                ", dateCandidat=" + dateCandidat +
                "}\n";
    }


}/*
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
}*/
