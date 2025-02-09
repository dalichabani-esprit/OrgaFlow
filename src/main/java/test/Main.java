package test;

import models.Formateur;
import models.Formation;
import services.ServiceFormateur;
import services.ServiceFormation;

import java.sql.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ServiceFormateur sf = new ServiceFormateur();
        ServiceFormation sfm = new ServiceFormation();

        // 🔹 AJOUT DE DEUX FORMATEURS
        Formateur f1 = new Formateur(0, "Alice", "Dupont", "alice@email.com", "0612345678", "Informatique");
        Formateur f2 = new Formateur(0, "Bob", "Martin", "bob@email.com", "0623456789", "Développement Web");

        sf.add(f1);
        sf.add(f2);

        // 🔹 AFFICHAGE DES FORMATEURS
        List<Formateur> formateurs = sf.getAll();
        System.out.println("📌 Liste des formateurs :");
        for (Formateur formateur : formateurs) {
            System.out.println(formateur);
        }

        if (formateurs.size() >= 2) {
            // Mise à jour du premier formateur
            Formateur formateurToUpdate = formateurs.get(0);
            formateurToUpdate.setNom("Alice Martin");
            formateurToUpdate.setEmail("alice.martin@email.com");
            sf.update(formateurToUpdate);

            // Mise à jour du deuxième formateur
            Formateur formateurToUpdate2 = formateurs.get(1);
            formateurToUpdate2.setNom("Bob Johnson");
            formateurToUpdate2.setEmail("bob.johnson@email.com");
            sf.update(formateurToUpdate2);

            // 🔹 AFFICHAGE APRÈS MISE À JOUR
            System.out.println("\n📌 Formateurs après mise à jour :");
            for (Formateur formateur : sf.getAll()) {
                System.out.println(formateur);
            }

            // 🔹 AJOUT DE DEUX FORMATIONS
            Formation formation1 = new Formation(0, "Java", "Cours avancé", 30,
                    Date.valueOf("2025-03-01"), Date.valueOf("2025-03-30"), "Développement", formateurToUpdate.getIdFormateur());

            Formation formation2 = new Formation(0, "React", "Développement Web Moderne", 40,
                    Date.valueOf("2025-04-01"), Date.valueOf("2025-04-30"), "Web", formateurToUpdate2.getIdFormateur());

            sfm.add(formation1);
            sfm.add(formation2);

            // 🔹 AFFICHAGE DES FORMATIONS
            List<Formation> formations = sfm.getAll();
            System.out.println("\n📌 Liste des formations :");
            for (Formation frm : formations) {
                System.out.println(frm);
            }

            if (formations.size() >= 2) {
                // Mise à jour de la première formation
                Formation formationToUpdate = formations.get(0);
                formationToUpdate.setNom("Java SE Avancé");
                formationToUpdate.setDescription("Formation approfondie sur Java SE");
                sfm.update(formationToUpdate);

                // Mise à jour de la deuxième formation
                Formation formationToUpdate2 = formations.get(1);
                formationToUpdate2.setNom("React JS Moderne");
                formationToUpdate2.setDescription("Formation complète sur React et Next.js");
                sfm.update(formationToUpdate2);

                // 🔹 AFFICHAGE APRÈS MISE À JOUR
                System.out.println("\n📌 Formations après mise à jour :");
                for (Formation frm : sfm.getAll()) {
                    System.out.println(frm);
                }

                // 🔹 SUPPRESSION D'UNE FORMATION
               // sfm.delete(formationToUpdate);
               // sfm.delete(formationToUpdate2);

                // 🔹 AFFICHAGE APRÈS SUPPRESSION DES FORMATIONS
                System.out.println("\n📌 Formations après suppression :");
                for (Formation frm : sfm.getAll()) {
                    System.out.println(frm);
                }
            }

            // 🔹 SUPPRESSION DES FORMATEURS
           // sf.delete(formateurToUpdate);
           // sf.delete(formateurToUpdate2);

            // 🔹 AFFICHAGE APRÈS SUPPRESSION DES FORMATEURS
            System.out.println("\n📌 Formateurs après suppression :");
            for (Formateur formateur : sf.getAll()) {
                System.out.println(formateur);
            }
        }
    }
}
