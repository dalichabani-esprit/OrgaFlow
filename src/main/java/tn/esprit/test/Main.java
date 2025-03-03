package tn.esprit.test;

import tn.esprit.models.*;
import tn.esprit.services.ServiceUser;

import java.sql.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ServiceUser serviceUser = new ServiceUser();

        System.out.println("======== DÉMARRAGE DE L'APPLICATION ========\n");

        // Ajouter un administrateur avec un mot de passe haché
        Admin admin = new Admin("gfdzadf", "Yarzdya", "afzmail.com", "secure123");
        serviceUser.add(admin);
        System.out.println("Administrateur ajouté avec succès.\n");

        // Ajouter un employé avec un mot de passe haché
        Employes employe = new Employes(
                "thea", "mado", "shmple.com", "password123",
                "3000", "Finance", new Date(System.currentTimeMillis())
        );
        serviceUser.add(employe);
        System.out.println("Employé ajouté avec succès.\n");

        // Ajouter un candidat avec un mot de passe haché
        Candidat candidat = new Candidat(
                "Bienvenu", "fqsqlssomou", "h@gzfeeqmail.com", "secuepass",
                new Date(System.currentTimeMillis()), "en attente", "cv"
        );
        serviceUser.add(candidat);
        System.out.println("Candidat ajouté avec succès.\n");

        //  Vérification du mot de passe pour un utilisateur existant
        String emailTest = "aegsdessfzmail.com";
        String motDePasseTest = "secure123";

        boolean isValid = serviceUser.isMotDePasseCorrect(emailTest, motDePasseTest);
        System.out.println("\nTest de connexion pour l'admin (" + emailTest + ") : " +
                (isValid ? "Mot de passe correct " : "Mot de passe incorrect "));

        // Afficher tous les utilisateurs avant modification
        System.out.println("\nListe des utilisateurs enregistrés :");
        List<User> users = serviceUser.getAll();
        for (User user : users) {
            System.out.println(user);
        }

        // Mise à jour d'un utilisateur (Employé ou Candidat)
        User use = serviceUser.getByIduser(20);

        if (use != null) {
            if (use instanceof Employes) {
                employe = (Employes) use;
                employe.setSalaire(String.valueOf(4500.0));
                employe.setDepartement("Developpeur");
                serviceUser.update(employe);
                System.out.println("\nEmployé mis à jour avec succès : " + employe);
            } else if (use instanceof Candidat) {
                candidat = (Candidat) use;
                candidat.setStatutCandidat("Embauché");
                candidat.setCvCandidat("Nouveau_cv.pdf");
                serviceUser.update(candidat);
                System.out.println("\nCandidat mis à jour avec succès : " + candidat);
            } else {
                System.out.println("\nL'utilisateur avec l'ID 20 n'est ni un employé ni un candidat.");
            }
        } else {
            System.out.println("\nUtilisateur avec l'ID 20 non trouvé.");
        }

        // Suppression d'un utilisateur (Employé ou Candidat)
        User userToDelete = serviceUser.getByIduser(19);

        if (userToDelete != null) {
            if (userToDelete instanceof Employes) {
                serviceUser.delete(userToDelete);
                System.out.println("\nEmployé supprimé avec succès : " + userToDelete);
            } else if (userToDelete instanceof Candidat) {
                serviceUser.delete(userToDelete);
                System.out.println("\nCandidat supprimé avec succès : " + userToDelete);
            } else {
                System.out.println("\nErreur : L'utilisateur avec l'ID 19 n'est ni un employé ni un candidat.");
            }
        } else {
            System.out.println("\nErreur : Utilisateur avec l'ID 19 non trouvé.");
        }


        System.out.println("\nListe des utilisateurs après modification :");
        users = serviceUser.getAll();
        for (User user : users) {
            System.out.println(user);
        }

        System.out.println("\n======= FIN DU PROGRAMME ========");
    }
}
