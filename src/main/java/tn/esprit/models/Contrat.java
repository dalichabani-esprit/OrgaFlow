package tn.esprit.models;

import java.util.Date;
import java.util.Objects;

public class Contrat {

    private int idContrat;
    private int idEmploye;
    private String typeContrat; // CDI, CDD, Stage, Freelance
    private Date dateDebut;
    private Date dateFin;
    private boolean periodeEssai;
    private boolean renouvelable;
    private double salaire;
    private String status; // Actif, Terminé, Renouvelé

    public Contrat() {
    }

    public Contrat(int idContrat) {
        this.idContrat = idContrat;
    }

    public Contrat(int idContrat, int idEmploye, String typeContrat, Date dateDebut, Date dateFin, boolean periodeEssai, boolean renouvelable, double salaire, String status) {
        this.idContrat = idContrat;
        this.idEmploye = idEmploye;
        this.typeContrat = typeContrat;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.periodeEssai = periodeEssai;
        this.renouvelable = renouvelable;
        this.salaire = salaire;
        this.status = status;
    }

    public Contrat(int idEmploye, String typeContrat, Date dateDebut, Date dateFin, boolean periodeEssai, boolean renouvelable, double salaire, String status) {
        this.idEmploye = idEmploye;
        this.typeContrat = typeContrat;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.periodeEssai = periodeEssai;
        this.renouvelable = renouvelable;
        this.salaire = salaire;
        this.status = status;
    }

    public int getIdContrat() {
        return idContrat;
    }

    public void setIdContrat(int idContrat) {
        this.idContrat = idContrat;
    }

    public int getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(int idEmploye) {
        this.idEmploye = idEmploye;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contrat contrat = (Contrat) o;
        return idContrat == contrat.idContrat &&
                idEmploye == contrat.idEmploye &&
                periodeEssai == contrat.periodeEssai &&
                renouvelable == contrat.renouvelable &&
                Double.compare(contrat.salaire, salaire) == 0 &&
                Objects.equals(typeContrat, contrat.typeContrat) &&
                Objects.equals(dateDebut, contrat.dateDebut) &&
                Objects.equals(dateFin, contrat.dateFin) &&
                Objects.equals(status, contrat.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idContrat, idEmploye, typeContrat, dateDebut, dateFin, periodeEssai, renouvelable, salaire, status);
    }

    @Override
    public String toString() {
        return "Contrat => id: " + idContrat + " | idEmploye: " + idEmploye + " | typeContrat: " + typeContrat +
                " | dateDebut: " + dateDebut + " | dateFin: " + dateFin + " | periodeEssai: " + periodeEssai +
                " | renouvelable: " + renouvelable + " | salaire: " + salaire + " | status: " + status + "\n";
    }
}
