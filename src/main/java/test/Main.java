package test;

import models.demande;
import models.devis;
import models.facture;
import services.ServiceDemande;
import services.ServiceDevis;
import services.ServiceFacture;

import java.sql.Date;

public class Main {
    public static void main(String[] args) {

        ServiceDemande sd = new ServiceDemande();
        ServiceDevis sv = new ServiceDevis();
        ServiceFacture sf = new ServiceFacture();

        // === 1️⃣ AJOUT DE DEMANDES ===
        demande d1 = new demande(1, "materiel", "Besoin des ordinateurs", 101, Date.valueOf("2025-01-10"), "En attente");
        demande d2 = new demande(2, "Service", "Demande de formation", 102, Date.valueOf("2025-01-12"), "Validée");

        sd.add(d1);
        sd.add(d2);

        // === 2️⃣ AJOUT DE DEVIS LIÉS AUX DEMANDES ===
        devis v1 = new devis(1, d1.getId_demande(), 1200, Date.valueOf("2025-01-25"), "En attente");
        devis v2 = new devis(2, d2.getId_demande(), 1800, Date.valueOf("2025-01-26"), "Accepté");

        sv.add(v1);
        sv.add(v2);

        // === 3️⃣ AJOUT DE FACTURES LIÉES AUX DEVIS ===
        facture f1 = new facture(1, v1.getId_devis(), d1.getId_demande(), 1250, Date.valueOf("2025-02-01"), "Non payée", 10);
        facture f2 = new facture(2, v2.getId_devis(), d2.getId_demande(), 1850, Date.valueOf("2025-02-02"), "Payée", 20);

        sf.add(f1);
        sf.add(f2);

        // === 4️⃣ AFFICHAGE DE TOUTES LES DONNÉES ===
        System.out.println("Liste des demandes :");
        for (demande d : sd.getAll()) {
            System.out.println(d);
        }

        System.out.println("Liste des devis :");
        for (devis v : sv.getAll()) {
            System.out.println(v);
        }

        System.out.println(" Liste des factures :");
        for (facture f : sf.getAll()) {
            System.out.println(f);
        }
/*
        // === 5️⃣ MISE À JOUR DES DONNÉES ===
        if (sf.getAll().size() >= 2) {
            // Mise à jour d'une demande
            demande demandeToUpdate = sd.getAll().get(0);
            demandeToUpdate.setStatut("Validée");
            sd.update(demandeToUpdate);

            // Mise à jour d'un devis
            devis devisToUpdate = sv.getAll().get(0);
            devisToUpdate.setMontant_estime(1300.0f);
            devisToUpdate.setStatut("Accepté");
            sv.update(devisToUpdate);

            // Mise à jour d'une facture
            facture factureToUpdate = sf.getAll().get(0);
            factureToUpdate.setMontant_final(1300.0f);
            factureToUpdate.setStatut("Payée");
            sf.update(factureToUpdate);
        }

        // === 6️⃣ AFFICHAGE APRÈS MISE À JOUR ===
        System.out.println("\n🔄 Données après mise à jour :");
        System.out.println("📜 Demandes mises à jour :");
        for (demande d : sd.getAll()) {
            System.out.println(d);
        }

        System.out.println("\n📜 Devis mis à jour :");
        for (devis v : sv.getAll()) {
            System.out.println(v);
        }

        System.out.println("\n📜 Factures mises à jour :");
        for (facture f : sf.getAll()) {
            System.out.println(f);
        }

        // === 7️⃣ SUPPRESSION (Optionnel, mis en commentaire) ===
        // sd.delete(demandeToUpdate);
        // sv.delete(devisToUpdate);
        // sf.delete(factureToUpdate);

        System.out.println("\n🗑 Données après suppression :");
        System.out.println("📜 Demandes : " + sd.getAll());
        System.out.println("📜 Devis : " + sv.getAll());
        System.out.println("📜 Factures : " + sf.getAll()); */
    }
}