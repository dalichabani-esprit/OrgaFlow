package tn.esprit.test;

import tn.esprit.models.Contrat;
import tn.esprit.models.Reclamation;
import tn.esprit.services.ServiceContrat;
import tn.esprit.services.ServiceReclamation;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        ServiceContrat serviceContrat = new ServiceContrat();
        ServiceReclamation serviceReclamation = new ServiceReclamation();


        //  Ajouter un contrat

        Contrat contrat1 = new Contrat(
                "CDD",
                new Date(122, 8, 1),  // 2022-09-01
                new Date(125, 8, 1),  // 2025-09-01
                true,
                true,
                1230,
                "Actif"
        );
        //serviceContrat.add(contrat1);



        //  Modifier un contrat
        /*
        Contrat contratModifie = new Contrat(
                1,
                "CDI",
                new Date(120, 5, 15),  // 2020-06-15
                null,                  // CDI peut ne pas avoir de date de fin
                false,
                false,
                2500,
                "Actif"
        );

         */
        //serviceContrat.update(contratModifie);

        //  Supprimer un contrat
        //serviceContrat.delete(new Contrat(1));



        //  Afficher tous les contrats
        System.out.println("Liste des contrats :");
        System.out.println(serviceContrat.getAll());



        //  Ajouter une réclamation
        Reclamation reclamation1 = new Reclamation(
                "sujet 3",
                2,
                "Problème salaire",
                new Date(01-01-01),
                "Rejetée"
        );

        serviceReclamation.add(reclamation1);




        //  Modifier une réclamation

        Reclamation reclamationModifiee = new Reclamation(
                3,
                2,
                "sujet 2",
                "Problème salaire",
                new Date(01-01-01),
                "Rejetée"
        );
        //serviceReclamation.update(reclamationModifiee);





        //  Supprimer une réclamation
        //serviceReclamation.delete(new Reclamation(3));

        //  Afficher toutes les réclamations
        System.out.println(" Liste des réclamations :");
        System.out.println(serviceReclamation.getAll());
    }

}

