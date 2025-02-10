package tn.esprit.test;

import tn.esprit.models.Candidat;
import tn.esprit.models.Candidature;
import tn.esprit.models.Entretien;
import tn.esprit.models.OffreEmploi;

import tn.esprit.services.ServiceCandidat;
import tn.esprit.services.ServiceCandidature;
import tn.esprit.services.ServiceEntretien;
import tn.esprit.services.ServiceOffreEmploi;


import tn.esprit.utils.DateUtils ;
import tn.esprit.utils.TimeUtils ;


public class Main {

    public static void main(String[] args) {

        /*ServiceCandidat sc = new ServiceCandidat();
        //Ajouter
        sc.add(new Candidat(DateUtils.convertStringToSqlDate("01/08/2025"),"embauché","traorrach5@gmail.com","Rachid","rachidcv.jpg","Traore",58273238));
        System.out.println(sc.getAll());
        //modifier
        Candidat c = sc.getById(4);
        if(c != null) {
            c.setPrenomCandidat("Aime Rachid");
        }
        sc.update(c);
        System.out.println(sc.getAll());
       // supprimer
        sc.delete(sc.getById(3));
        System.out.println(sc.getAll());*/


        /*ServiceOffreEmploi so = new ServiceOffreEmploi();
        so.add(new OffreEmploi("ouvert",DateUtils.convertStringToSqlDate("30/03/2025"),DateUtils.convertStringToSqlDate("01/03/2025"),"Communication","Recherche Urgente","Offre D'emploi"));
        System.out.println(so.getAll());
        so.delete(so.getById(3));
        System.out.println(so.getAll());*/

        /*ServiceCandidature sca = new ServiceCandidature();
        sca.add(new Candidature(3,3,"en attente",DateUtils.convertStringToSqlDate("15/03/2025")));
        System.out.println(sca.getAll());
        sca.delete(sca.getById(3));
        System.out.println(sca.getAll());*/

       /* ServiceEntretien se = new ServiceEntretien();
        se.add(new Entretien(1,"tunis","ALI","a retenir",DateUtils.convertStringToSqlDate("30/03/2025"),TimeUtils.stringToTime("15:30:00")));
        System.out.println(se.getAll());
        se.delete(se.getById(3));
        System.out.println(se.getAll());*/
    }
}
