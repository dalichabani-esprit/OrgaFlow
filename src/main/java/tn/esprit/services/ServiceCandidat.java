package tn.esprit.services;

import tn.esprit.models.Candidat;
import tn.esprit.models.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServiceCandidat extends ServiceUser {

    public void ajouterCandidat(Candidat candidat) {
        System.out.println("Ajout du candidat : " + candidat);
        super.add(candidat);
    }

    public void mettreAJourCandidat(Candidat candidat) {
        if (!exists(candidat.getIduser())) {
            System.out.println("Le candidat avec l'ID " + candidat.getIduser() + " n'existe pas.");
            return;
        }

        super.update(candidat);
        System.out.println("Candidat mis à jour avec succès : " + candidat);
    }


    public void supprimerCandidat(Candidat candidat) {
        System.out.println("Suppression du candidat : " + candidat);
        super.delete(candidat);
    }

    public Candidat obtenirCandidatParId(int iduser) {
        Optional<User> userOpt = Optional.ofNullable(super.getByIduser(iduser));

        return userOpt.filter(user -> user instanceof Candidat)
                .map(user -> (Candidat) user)
                .orElse(null);
    }

    public List<Candidat> getAllCandidats() {
        return super.getAll()
                .stream()
                .filter(user -> user instanceof Candidat)
                .map(user -> (Candidat) user)
                .collect(Collectors.toList());
    }
}
