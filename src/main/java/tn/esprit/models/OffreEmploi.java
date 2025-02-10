package tn.esprit.models;
import java.sql.Date;

public class OffreEmploi {
    private int idOffre;
    private String titreOffre,descriptionOffre,departementOffre,StatutOffre;
    private Date date_publicationOffre,date_limiteOffre;


    public OffreEmploi() {}

    public OffreEmploi(int idOffre, String titreOffre, String descriptionOffre, String departementOffre, Date date_publicationOffre, Date date_limiteOffre, String statutOffre) {
        this.idOffre = idOffre;
        this.titreOffre = titreOffre;
        this.descriptionOffre = descriptionOffre;
        this.departementOffre = departementOffre;
        this.date_publicationOffre = date_publicationOffre;
        this.date_limiteOffre = date_limiteOffre;
        StatutOffre = statutOffre;
    }

    public OffreEmploi(String statutOffre, Date date_limiteOffre, Date date_publicationOffre, String departementOffre, String descriptionOffre, String titreOffre) {
        StatutOffre = statutOffre;
        this.date_limiteOffre = date_limiteOffre;
        this.date_publicationOffre = date_publicationOffre;
        this.departementOffre = departementOffre;
        this.descriptionOffre = descriptionOffre;
        this.titreOffre = titreOffre;
    }

    public int getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public String getStatutOffre() {
        return StatutOffre;
    }

    public void setStatutOffre(String statutOffre) {
        StatutOffre = statutOffre;
    }

    public Date getDate_limiteOffre() {
        return date_limiteOffre;
    }

    public void setDate_limiteOffre(Date date_limiteOffre) {
        this.date_limiteOffre = date_limiteOffre;
    }

    public Date getDate_publicationOffre() {
        return date_publicationOffre;
    }

    public void setDate_publicationOffre(Date date_publicationOffre) {
        this.date_publicationOffre = date_publicationOffre;
    }

    public String getDepartementOffre() {
        return departementOffre;
    }

    public void setDepartementOffre(String departementOffre) {
        this.departementOffre = departementOffre;
    }

    public String getDescriptionOffre() {
        return descriptionOffre;
    }

    public void setDescriptionOffre(String descriptionOffre) {
        this.descriptionOffre = descriptionOffre;
    }

    public String getTitreOffre() {
        return titreOffre;
    }

    public void setTitreOffre(String titreOffre) {
        this.titreOffre = titreOffre;
    }

    @Override
    public String toString() {
        return "OffreEmploi{" +
                "idOffre=" + idOffre +
                ", titreOffre='" + titreOffre + '\'' +
                ", descriptionOffre='" + descriptionOffre + '\'' +
                ", departementOffre='" + departementOffre + '\'' +
                ", date_publicationOffre=" + date_publicationOffre +
                ", date_limiteOffre=" + date_limiteOffre +
                ", StatutOffre='" + StatutOffre + '\'' +
                "}\n";
    }
}

