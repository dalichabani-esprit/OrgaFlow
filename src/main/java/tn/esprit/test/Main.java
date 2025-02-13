package tn.esprit.test;

import tn.esprit.models.Contrat;
import tn.esprit.models.Employe;
import tn.esprit.services.ServiceContrat;
import tn.esprit.services.ServiceEmploye;

import java.util.Date;

import tn.esprit.utils.MyDatabase;

public class Main {
    public static void main(String[] args) {
        //ServiceEmploye se = new ServiceEmploye();
        ServiceContrat sc = new ServiceContrat();

        /* Ajouter Contrat
        sc.add(new Contrat(
                3,
                "CDD",
                new Date(2022, 9, 1),
                new Date(2025, 9, 1),
                true,
                true,
                1230,
                "Terminé"
        ));
        */


        // Modifier Contrat
        sc.update(new Contrat(
                1,
                3,
                "CDD",
                new Date(2022, 9, 1),
                new Date(2025, 9, 1),
                true,
                true,
                1230,
                "Terminé"
        ));

        // Supprimer contrat
        sc.delete(new Contrat(2));

        // Afficher les contrats
        System.out.println(sc.getAll());


        /* Ajouter un employé
        se.add(new Employe(
                "Chabani",
                "Mohamed Ali",
                new Date(1995 - 1900, 5, 15),  // Date : année - 1900, mois (0-11), jour
                "chabani.dali@example.com",
                "87654321",
                "Développeur Python",
                "IT",
                new Date(2022 - 1900, 9, 1),
                "Actif"
        ));
        */


      /* Modifier employé
      se.update(new Employe(
              3,
              "Chabani",
              "Mohamed Aziz",
              new Date(1995 - 1900, 5, 15),  // Date : année - 1900, mois (0-11), jour
              "chabani.dali@example.com",
              "87654321",
              "Développeur C++",
              "IT",
              new Date(2022 - 1900, 9, 1),
              "Actif"
      ));
      */

        /* Supprimer Employé
        se.delete(new Employe(2));
        */


        /* Afficher tous les employés
        System.out.println(se.getAll());
         */
    }
}
