package tn.esprit.models;

import java.util.Date;
import java.util.Objects;

public class Employe {

    private int idEmploye;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String email;
    private String telephone;
    private String poste;
    private String departement;
    private Date dateEmbauche;
    private String statut;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employe employe = (Employe) o;
        return idEmploye == employe.idEmploye && Objects.equals(nom, employe.nom) && Objects.equals(prenom, employe.prenom) && Objects.equals(dateNaissance, employe.dateNaissance) && Objects.equals(email, employe.email) && Objects.equals(telephone, employe.telephone) && Objects.equals(poste, employe.poste) && Objects.equals(departement, employe.departement) && Objects.equals(dateEmbauche, employe.dateEmbauche) && Objects.equals(statut, employe.statut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmploye, nom, prenom, dateNaissance, email, telephone, poste, departement, dateEmbauche, statut);
    }

    @Override
    public String toString() {
        return "Employe => id: " + idEmploye + " nom: " + nom + " prenom: " + prenom +
                " date_de_naissance: " + dateNaissance + " email: " + email + " telephone: " +
                telephone + " poste: " + poste + " statut: " + statut +
                " departement: " + departement + " dateEmbauche: " + dateEmbauche + "\n";
    }

    public Employe() {
    }

    public Employe(int idEmploye) {
        this.idEmploye = idEmploye;
    }

    public Employe(int idEmploye, String nom, String prenom, Date dateNaissance, String email, String telephone, String poste, String departement, Date dateEmbauche, String statut) {
        this.idEmploye = idEmploye;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.telephone = telephone;
        this.poste = poste;
        this.departement = departement;
        this.dateEmbauche = dateEmbauche;
        this.statut = statut;
    }

    public Employe(String nom, String prenom, Date dateNaissance, String email, String telephone, String poste, String departement, Date dateEmbauche, String statut) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.telephone = telephone;
        this.poste = poste;
        this.departement = departement;
        this.dateEmbauche = dateEmbauche;
        this.statut = statut;
    }

    public int getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(int idEmploye) {
        this.idEmploye = idEmploye;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public Date getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(Date dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

}

