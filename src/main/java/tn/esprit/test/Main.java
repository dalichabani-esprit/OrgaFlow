package tn.esprit.test;

import tn.esprit.models.*;

import tn.esprit.services.ServiceUser;

import java.sql.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ServiceUser serviceUser = new ServiceUser();


        System.out.println("======== DÉMARRAGE DE L'APPLICATION ========\n");

        //  Ajouter un administrateur
        Admin admin = new Admin("gfdzadf", "Yarzdya", "aefvssfzmail.com", "secure123");
        serviceUser.add(admin);
        System.out.println("Administrateur ajouté avec succès.\n");

        //  Ajouter un employé
        Employes employe = new Employes(
                "thea", "mado", "hmadcc@qddsfeample.com", "password123",
                "3000", "Finance", new Date(System.currentTimeMillis())
        );
        serviceUser.add(employe);
        System.out.println(" Employé ajouté avec succès.\n");

        //  Ajouter un candidat
        Candidat candidat = new Candidat(
                "Bienvenu", "fqsqlssomou", "h@gdaddszreqmail.com", "secuepass",
                new Date(System.currentTimeMillis()), "en attente" , "cv"
        );
        serviceUser.add(candidat);
        System.out.println(" Candidat ajouté avec succès.\n");

        // Afficher tous les utilisateurs avant modification
        System.out.println("Liste des utilisateurs enregistrés :");
        List<User> users = serviceUser.getAll();
        for (User user : users) {
            System.out.println(user);
        }

      /*  User use = serviceUser.getByIduser(20); // Récupérer l'utilisateur par son ID

        if (use != null) {
            if (use instanceof Employes) {
                // Si c'est un employé, on met à jour ses informations
                 employe = (Employes) use;
                employe.setSalaire(4500.0);
                employe.setDepartement("Developpeur");

                // Mise à jour de l'employé dans la base de données
                serviceUser.update(employe);
                System.out.println("\n Employé mis à jour avec succès : " + employe);
            } else if (use instanceof Candidat) {
                // Si c'est un candidat, on met à jour ses informations
                candidat = (Candidat) use;
                candidat.setStatutCandidat("Embauché"); // Exemple : mise à jour de l'expérience
                candidat.setCvCandidat("Nouveau_cv.pdf"); // Exemple : mise à jour des compétences

                // Mise à jour du candidat dans la base de données
                serviceUser.update(candidat);
                System.out.println("\n Candidat mis à jour avec succès : " + candidat);
            } else {
                System.out.println("\n L'utilisateur avec l'ID 20 n'est ni un employé ni un candidat.");
            }
        } else {
            // Si l'utilisateur n'est pas trouvé
            System.out.println("\n Utilisateur avec l'ID 20 non trouvé.");
        }


// Suppression d'un utilisateur (Employé ou Candidat)
        User userToDelete = serviceUser.getByIduser(19); // Supposons que l'utilisateur a l'ID 2

        if (userToDelete != null) {
            // Vérifier si l'utilisateur est un Employé
            if (userToDelete instanceof Employes) {
                Employes employeToDelete = (Employes) userToDelete;
                serviceUser.delete(employeToDelete);
                System.out.println("\n Employé supprimé avec succès : " + employeToDelete);
            }
            // Vérifier si l'utilisateur est un Candidat
            else if (userToDelete instanceof Candidat) {
                Candidat candidatToDelete = (Candidat) userToDelete;
                serviceUser.delete(candidatToDelete);
                System.out.println("\n Candidat supprimé avec succès : " + candidatToDelete);
            } else {
                System.out.println("\n Erreur : L'utilisateur avec l'ID 19 n'est ni un employé ni un candidat.");
            }
        } else {
            System.out.println("\n Erreur : Utilisateur avec l'ID 19 non trouvé.");
        }
        //  Afficher tous les utilisateurs après mise à jour et suppression
        System.out.println(" Liste des utilisateurs après modification :");
        users = serviceUser.getAll();
        for (User user : users) {
            System.out.println(user);
        }



        System.out.println("\n======= FIN DU PROGRAMME ========");*/
    }
}