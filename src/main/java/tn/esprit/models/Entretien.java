package tn.esprit.models;

import java.sql.Date;
import java.sql.Time;

public class Entretien {
    private int idEntret,candidature_id;
    private String lieuEntret,interviewerEntret,notes;
    private Date dateEntret;
    private Time timeEntret;

    public Entretien() {}

    public Entretien(int idEntret, int candidature_id, String lieuEntret, String interviewerEntret, String notes, Date dateEntret, Time timeEntret) {
        this.idEntret = idEntret;
        this.candidature_id = candidature_id;
        this.lieuEntret = lieuEntret;
        this.interviewerEntret = interviewerEntret;
        this.notes = notes;
        this.dateEntret = dateEntret;
        this.timeEntret = timeEntret;
    }

    public Entretien(int candidature_id, String lieuEntret, String interviewerEntret, String notes, Date dateEntret, Time timeEntret) {
        this.candidature_id = candidature_id;
        this.lieuEntret = lieuEntret;
        this.interviewerEntret = interviewerEntret;
        this.notes = notes;
        this.dateEntret = dateEntret;
        this.timeEntret = timeEntret;
    }

    public int getIdEntret() {
        return idEntret;
    }

    public void setIdEntret(int idEntret) {
        this.idEntret = idEntret;
    }

    public int getCandidature_id() {
        return candidature_id;
    }

    public void setCandidature_id(int candidature_id) {
        this.candidature_id = candidature_id;
    }

    public String getLieuEntret() {
        return lieuEntret;
    }

    public void setLieuEntret(String lieuEntret) {
        this.lieuEntret = lieuEntret;
    }

    public String getInterviewerEntret() {
        return interviewerEntret;
    }

    public void setInterviewerEntret(String interviewerEntret) {
        this.interviewerEntret = interviewerEntret;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDateEntret() {
        return dateEntret;
    }

    public void setDateEntret(Date dateEntret) {
        this.dateEntret = dateEntret;
    }

    public Time getTimeEntret() {
        return timeEntret;
    }

    public void setTimeEntret(Time timeEntret) {
        this.timeEntret = timeEntret;
    }

    @Override
    public String toString() {
        return "Entretien{" +
                "idEntret=" + idEntret +
                ", candidature_id=" + candidature_id +
                ", lieuEntret='" + lieuEntret + '\'' +
                ", interviewerEntret='" + interviewerEntret + '\'' +
                ", notes='" + notes + '\'' +
                ", dateEntret=" + dateEntret +
                ", timeEntret=" + timeEntret +
                "}\n";
    }
}
