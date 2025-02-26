package tn.esprit.test;

import tn.esprit.services.*;


public class Main {
    public static void main(String[] args) {

        ServiceProjet sp = new ServiceProjet();
        System.out.println(sp.getIdByNom("Projet java"));
    }
}