package tn.esprit.test;

import tn.esprit.services.*;


public class Main {
    public static void main(String[] args) {

        ServiceProjet sp = new ServiceProjet();
        //sp.add(new Projet("projet 2", "projet dev", "04/01/01", "02/02/02", "DOING"));
        //sp.update(new Projet(1, "projet 210", "test", "01/01/01", "02/02/02", "TODO"));

        System.out.println("Liste des projets");
        System.out.println(sp.getAll());


        ServiceTache st = new ServiceTache();
        //st.add(new Tache("tache 1", "première tache du projet", "01/01/01", "02/02/02", "DOING", 1));
        //st.update(new Tache(1, "première tache du projet", "test", "01/01/01", "02/02/02", "TODO", 1));
        //st.delete(new Tache(1));

        System.out.println("Liste des taches");
        System.out.println(st.getAll());

    }
}