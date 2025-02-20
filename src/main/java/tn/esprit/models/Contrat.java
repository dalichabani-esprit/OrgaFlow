package tn.esprit.models;

import java.util.Date;
import java.util.Objects;

public class Contrat {
    private int idContrat;
    private String typeContrat;
    private Date dateDebut;
    private Date dateFin;
    private boolean periodeEssai;
    private boolean renouvelable;
    private double salaire;
    private String statut;

    // Constructeurs
    public Contrat() {
    }

    public Contrat(int idContrat) {
        this.idContrat = idContrat;
    }

    public Contrat(int idContrat, String typeContrat, Date dateDebut, Date dateFin, boolean periodeEssai, boolean renouvelable, double salaire, String statut) {
        this.idContrat = idContrat;
        this.typeContrat = typeContrat;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.periodeEssai = periodeEssai;
        this.renouvelable = renouvelable;
        this.salaire = salaire;
        this.statut = statut;
    }

    public Contrat(String typeContrat, Date dateDebut, Date dateFin, boolean periodeEssai, boolean renouvelable, double salaire, String statut) {
        this.typeContrat = typeContrat;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.periodeEssai = periodeEssai;
        this.renouvelable = renouvelable;
        this.salaire = salaire;
        this.statut = statut;
    }

    // Getters et Setters
    public int getIdContrat() {
        return idContrat;
    }

    public void setIdContrat(int idContrat) {
        this.idContrat = idContrat;
    }


    public String getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(String typeContrat) {
        this.typeContrat = typeContrat;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public boolean isPeriodeEssai() {
        return periodeEssai;
    }

    public void setPeriodeEssai(boolean periodeEssai) {
        this.periodeEssai = periodeEssai;
    }

    public boolean isRenouvelable() {
        return renouvelable;
    }

    public void setRenouvelable(boolean renouvelable) {
        this.renouvelable = renouvelable;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    // Méthodes equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contrat contrat = (Contrat) o;
        return idContrat == contrat.idContrat &&
                periodeEssai == contrat.periodeEssai &&
                renouvelable == contrat.renouvelable &&
                Double.compare(contrat.salaire, salaire) == 0 &&
                Objects.equals(typeContrat, contrat.typeContrat) &&
                Objects.equals(dateDebut, contrat.dateDebut) &&
                Objects.equals(dateFin, contrat.dateFin) &&
                Objects.equals(statut, contrat.statut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idContrat, typeContrat, dateDebut, dateFin, periodeEssai, renouvelable, salaire, statut);
    }

    // Méthode toString
    @Override
    public String toString() {
        return "Contrat{" +
                "idContrat=" + idContrat +
                ", typeContrat='" + typeContrat + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", periodeEssai=" + periodeEssai +
                ", renouvelable=" + renouvelable +
                ", salaire=" + salaire +
                ", status='" + statut + '\'' +
                '}';
    }
}
