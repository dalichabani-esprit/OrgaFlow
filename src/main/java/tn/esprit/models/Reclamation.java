package tn.esprit.models;

import java.util.Date;
import java.util.Objects;

public class Reclamation {
    private int idReclamation, idContrat;
    private String sujet;
    private String description;
    private Date dateSoumission;
    private String statut;

    // Constructeurs
    public Reclamation() {
    }

    public Reclamation(int idReclamation) {
        this.idReclamation = idReclamation;
    }

    public Reclamation(int idReclamation, int idContrat, String sujet, String description, Date dateSoumission, String statut) {
        this.idReclamation = idReclamation;
        this.idContrat = idContrat;
        this.sujet = sujet;
        this.description = description;
        this.dateSoumission = dateSoumission;
        this.statut = statut;
    }

    public Reclamation(String sujet, int idContrat, String description, Date dateSoumission, String statut) {
        this.idContrat = idContrat;
        this.sujet = sujet;
        this.description = description;
        this.dateSoumission = dateSoumission;
        this.statut = statut;
    }

    // Getters et Setters
    public int getIdReclamation() {
        return idReclamation;
    }

    public void setIdReclamation(int idReclamation) {
        this.idReclamation = idReclamation;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateSoumission() {
        return dateSoumission;
    }

    public void setDateSoumission(Date dateSoumission) {
        this.dateSoumission = dateSoumission;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getIdContrat() {
        return idContrat;
    }

    public void setIdContrat(int idContrat) {
        this.idContrat = idContrat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reclamation that = (Reclamation) o;
        return idReclamation == that.idReclamation && idContrat == that.idContrat && Objects.equals(sujet, that.sujet) && Objects.equals(description, that.description) && Objects.equals(dateSoumission, that.dateSoumission) && Objects.equals(statut, that.statut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReclamation, idContrat, sujet, description, dateSoumission, statut);
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "idReclamation=" + idReclamation +
                ", idContrat=" + idContrat +
                ", sujet='" + sujet + '\'' +
                ", description='" + description + '\'' +
                ", dateSoumission=" + dateSoumission +
                ", statut='" + statut + '\'' +
                '}';
    }
}
