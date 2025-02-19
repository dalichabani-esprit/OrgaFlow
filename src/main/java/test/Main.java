package test;

import models.Formateur;
import models.Formation;
import services.ServiceFormateur;
import services.ServiceFormation;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ServiceFormateur sf = new ServiceFormateur();
        ServiceFormation sfm = new ServiceFormation();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println(" Menu :");
            System.out.println("1. Ajouter un formateur");
            System.out.println("2. Mettre à jour un formateur");
            System.out.println("3. Afficher tous les formateurs");
            System.out.println("4. Ajouter une formation");
            System.out.println("5. Mettre à jour une formation");
            System.out.println("6. Afficher toutes les formations");
            System.out.println("7. Supprimer un formateur");
            System.out.println("8. Supprimer une formation");
            System.out.println("9. Quitter");
            System.out.print("Choisissez une option : ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Nom du formateur : ");
                    String nom = scanner.nextLine();
                    System.out.print("Prénom du formateur : ");
                    String prenom = scanner.nextLine();
                    System.out.print("Email du formateur : ");
                    String email = scanner.nextLine();
                    System.out.print("Téléphone du formateur : ");
                    String telephone = scanner.nextLine();
                    System.out.print("Spécialité du formateur : ");
                    String specialite = scanner.nextLine();

                    Formateur formateur = new Formateur(0, nom, prenom, email, telephone, specialite);
                    sf.add(formateur);
                    System.out.println("✅ Formateur ajouté avec succès !");
                    break;

                case 2:
                    List<Formateur> formateursToUpdate = sf.getAll();
                    System.out.println("Choisissez un formateur à mettre à jour :");
                    for (int i = 0; i < formateursToUpdate.size(); i++) {
                        System.out.println((i + 1) + ". " + formateursToUpdate.get(i));
                    }
                    System.out.print("Choisissez un formateur par son numéro : ");
                    int indexFormateur = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (indexFormateur >= 0 && indexFormateur < formateursToUpdate.size()) {
                        Formateur formateurToUpdate = formateursToUpdate.get(indexFormateur);
                        System.out.print("Nouveau nom : ");
                        formateurToUpdate.setNom(scanner.nextLine());
                        System.out.print("Nouveau email : ");
                        formateurToUpdate.setEmail(scanner.nextLine());
                        sf.update(formateurToUpdate);
                        System.out.println(" Formateur mis à jour avec succès !");
                    } else {
                        System.out.println(" Formateur non trouvé.");
                    }
                    break;

                case 3:
                    List<Formateur> formateurs = sf.getAll();
                    System.out.println(" Liste des formateurs :");
                    for (Formateur formateurItem : formateurs) {
                        System.out.println(formateurItem);
                    }
                    break;

                case 4:
                    System.out.print("Nom de la formation : ");
                    String nomFormation = scanner.nextLine();
                    System.out.print("Description de la formation : ");
                    String descriptionFormation = scanner.nextLine();
                    System.out.print("Durée de la formation (en jours) : ");
                    int duree = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Date de début (YYYY-MM-DD) : ");
                    String dateDebut = scanner.nextLine();
                    System.out.print("Date de fin (YYYY-MM-DD) : ");
                    String dateFin = scanner.nextLine();
                    System.out.println("Choisissez un formateur :");
                    List<Formateur> formateursForFormation = sf.getAll();
                    for (int i = 0; i < formateursForFormation.size(); i++) {
                        System.out.println((i + 1) + ". " + formateursForFormation.get(i));
                    }
                    System.out.print("Choisissez un formateur par son numéro : ");
                    int indexFormateurFormation = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (indexFormateurFormation >= 0 && indexFormateurFormation < formateursForFormation.size()) {
                        Formateur formateurForFormation = formateursForFormation.get(indexFormateurFormation);
                        Formation formation = new Formation(0, nomFormation, descriptionFormation, duree,
                                Date.valueOf(dateDebut), Date.valueOf(dateFin), "Formation", formateurForFormation);
                        sfm.add(formation);
                        System.out.println(" Formation ajoutée avec succès !");
                    } else {
                        System.out.println(" Formateur non trouvé.");
                    }
                    break;

                case 5:
                    List<Formation> formationsToUpdate = sfm.getAll();
                    System.out.println("Choisissez une formation à mettre à jour :");
                    for (int i = 0; i < formationsToUpdate.size(); i++) {
                        System.out.println((i + 1) + ". " + formationsToUpdate.get(i));
                    }
                    System.out.print("Choisissez une formation par son numéro : ");
                    int indexFormation = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (indexFormation >= 0 && indexFormation < formationsToUpdate.size()) {
                        Formation formationToUpdate = formationsToUpdate.get(indexFormation);
                        System.out.print("Nouveau nom : ");
                        formationToUpdate.setNom(scanner.nextLine());
                        System.out.print("Nouvelle description : ");
                        formationToUpdate.setDescription(scanner.nextLine());
                        sfm.update(formationToUpdate);
                        System.out.println(" Formation mise à jour avec succès !");
                    } else {
                        System.out.println(" Formation non trouvée.");
                    }
                    break;

                case 6:
                    List<Formation> allFormations = sfm.getAll();
                    System.out.println(" Liste des formations :");
                    for (Formation formationItem : allFormations) {
                        System.out.println(formationItem);
                    }
                    break;

                case 7:
                    System.out.println("Choisissez un formateur à supprimer :");
                    List<Formateur> formateursToDelete = sf.getAll();
                    for (int i = 0; i < formateursToDelete.size(); i++) {
                        System.out.println((i + 1) + ". " + formateursToDelete.get(i));
                    }
                    System.out.print("Choisissez un formateur par son numéro : ");
                    int indexFormateurDelete = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (indexFormateurDelete >= 0 && indexFormateurDelete < formateursToDelete.size()) {
                        Formateur formateurToDelete = formateursToDelete.get(indexFormateurDelete);
                        sf.delete(1);
                        System.out.println(" Formateur supprimé avec succès !");
                    } else {
                        System.out.println(" Formateur non trouvé.");
                    }
                    break;

                case 8:
                    System.out.println("Choisissez une formation à supprimer :");
                    List<Formation> formationsToDelete = sfm.getAll();
                    for (int i = 0; i < formationsToDelete.size(); i++) {
                        System.out.println((i + 1) + ". " + formationsToDelete.get(i));
                    }
                    System.out.print("Choisissez une formation par son numéro : ");
                    int indexFormationDelete = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (indexFormationDelete >= 0 && indexFormationDelete < formationsToDelete.size()) {
                        Formation formationToDelete = formationsToDelete.get(indexFormationDelete);
                        sfm.delete(5);
                        System.out.println(" Formation supprimée avec succès !");
                    } else {
                        System.out.println(" Formation non trouvée.");
                    }
                    break;

                case 9:
                    System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println(" Option invalide. Veuillez réessayer.");
            }

        } while (choice != 9);

        scanner.close();
    }
}
