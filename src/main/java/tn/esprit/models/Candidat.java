package tn.esprit.models;

import java.sql.Date;

public class Candidat {

    private int idCandidat;
    private String cvCandidat,statutCandidat;
    private Date dateCandidat;


    public Candidat() {}

    public Candidat(Date dateCandidat, String statutCandidat, String cvCandidat, int idCandidat) {
        this.dateCandidat = dateCandidat;
        this.statutCandidat = statutCandidat;

        this.cvCandidat = cvCandidat;

        this.idCandidat = idCandidat;
    }

    public Candidat(Date dateCandidat, String statutCandidat, String cvCandidat) {
        this.dateCandidat = dateCandidat;
        this.statutCandidat = statutCandidat;

        this.cvCandidat = cvCandidat;

    }

    public int getIdCandidat() {
        return idCandidat;
    }

    public void setIdCandidat(int idCandidat) {
        this.idCandidat = idCandidat;
    }


    public String getCvCandidat() {
        return cvCandidat;
    }

    public void setCvCandidat(String cvCandidat) {
        this.cvCandidat = cvCandidat;
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
                ", cvCandidat='" + cvCandidat + '\'' +
                ", statutCandidat='" + statutCandidat + '\'' +
                ", dateCandidat=" + dateCandidat +
                "}\n";
    }


}

//package tn.esprit.models;

/*import java.sql.Date;

public class Candidat extends User {
    private Date dateCandidature;
    private String statutCandidat;
    private String CvCandidat;

    //  Constructeur pour la création
    public Candidat(String nom, String prenom, String email, String motDePasse, Date dateCandidature, String statutCandidat,String CvCandidat) {
        super(nom, prenom, email, motDePasse,  "candidat");
        this.dateCandidature = dateCandidature;
        this.statutCandidat = statutCandidat;
        this.CvCandidat = CvCandidat;
    }

    //  Constructeur pour la récupération depuis la BDD
    public Candidat(int iduser, String nom, String prenom, String email, String motDePasse, Date dateCandidature, String statutCandidat,String CvCandidat) {
        super(iduser, nom, prenom, email, motDePasse, "candidat");
        this.dateCandidature = dateCandidature;
        this.statutCandidat = statutCandidat;
        this.CvCandidat = CvCandidat;
    }

    public Candidat() {

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