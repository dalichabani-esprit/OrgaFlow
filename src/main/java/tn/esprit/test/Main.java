package tn.esprit.test;

import tn.esprit.models.*;
import tn.esprit.services.ServiceCandidat;
import tn.esprit.services.ServiceUser;

import java.sql.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ServiceUser serviceUser = new ServiceUser();
        ServiceCandidat serviceCandidat = new ServiceCandidat();

        System.out.println("======== DÉMARRAGE DE L'APPLICATION ========\n");

        //  Ajouter un administrateur
        Admin admin = new Admin("gfdzadf", "Yarzdya", "augwsddsjfaeifmail.com", "secure123");
        serviceUser.add(admin);
        System.out.println("Administrateur ajouté avec succès.\n");

        //  Ajouter un employé
        Employes employe = new Employes(
                "thea", "mado", "hmadcc@qdeample.com", "password123",
                3000.0, "Finance", new Date(System.currentTimeMillis())
        );
        serviceUser.add(employe);
        System.out.println(" Employé ajouté avec succès.\n");

        //  Ajouter un candidat
        Candidat candidat = new Candidat(
                "Bienvenu", "fqsqlssomou", "h@gdaddseqmail.com", "secuepass",
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

        /*employe = (Employes) serviceUser.getByIduser(2); // Récupérer directement l'employé

        if (employe != null) {
            // Si l'employé est trouvé, on met à jour ses informations
            employe.setSalaire(4500.0);
            employe.setDepartement("Developpeur");

            // Si tu souhaites mettre à jour l'employé dans la base de données, ajoute un appel à la méthode 'update'
            if (serviceUser.update(employe)) {
                System.out.println("\n Employé mis à jour avec succès : " + employe);
            } else {
                System.out.println("\n Erreur lors de la mise à jour de l'employé.");
            }
        } else {
            // Si l'employé n'est pas trouvé
            System.out.println("\n Employé avec l'ID 2 non trouvé.");
        }

// Suppression d'un utilisateur (Employé ou Candidat)
        User userToDelete = serviceUser.getByIduser(3); // Supposons que l'utilisateur a l'ID 2

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
                System.out.println("\n Erreur : L'utilisateur avec l'ID 3 n'est ni un employé ni un candidat.");
            }
        } else {
            System.out.println("\n Erreur : Utilisateur avec l'ID 3 non trouvé.");
        }*/
        //  Afficher tous les utilisateurs après mise à jour et suppression
        System.out.println(" Liste des utilisateurs après modification :");
        users = serviceUser.getAll();
        for (User user : users) {
            System.out.println(user);
        }



        System.out.println("\n======= FIN DU PROGRAMME ========");
    }
}
