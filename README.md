# OrgaFlow - RH

OrgaFlow est une application bureautique complète de gestion d'entreprise développée avec JavaFX et MySQL. Elle offre un ensemble d'outils pour gérer efficacement les différentes facettes d'une entreprise.

## Installation

1. Cloner le repository :
   ```bash
   git clone [URL_DU_REPO]
   ```

2. Importer le projet dans votre IDE Java préféré (IntelliJ IDEA, Eclipse, NetBeans, etc.).

3. Configurer la connexion à la base de données MySQL dans le fichier de configuration (ex : `config.properties` ou directement dans le code).

4. Lancer l'application :
   - Depuis l'IDE : Exécutez la classe principale (ex : `Main.java`).
   - Depuis la ligne de commande :
     ```bash
     ./gradlew run
     ```
     ou
     ```bash
     mvn javafx:run
     ```

## Fonctionnalités

| Module | Fonctionnalités Simples | Fonctionnalités Avancées |
|--------|------------------------|-------------------------|
| **Gestion des Utilisateurs** | • Inscription et connexion<br>• Gestion des profils utilisateurs<br>• Réinitialisation de mot de passe | • Administration des utilisateurs<br>• Gestion des rôles et permissions<br>• Blocage/déblocage de comptes |
| **Gestion des Projets** | • Création et suivi de projets<br>• Attribution des tâches | • Suivi de l'avancement<br>• Gestion des ressources<br>• Rapports de projet |
| **Gestion des Ressources Humaines** | • Gestion des offres d'emploi<br>• Suivi des candidatures<br>• Planification des entretiens | • Gestion des contrats<br>• Suivi des formations<br>• Évaluation des performances |
| **Gestion Financière** | • Création de devis<br>• Gestion des factures | • Suivi des paiements<br>• Génération de rapports financiers<br>• Gestion des réclamations |
| **Communication** | • Système de messagerie<br>• Chat en temps réel | • Chatbot d'assistance<br>• Gestion des salles de chat<br>• Notifications en temps réel |
| **Formation** | • Catalogue des formations<br>• Inscription aux formations | • Suivi des formateurs<br>• Évaluation des formations<br>• Gestion des certifications |
| **Support Client** | • Système de contact<br>• Gestion des demandes | • Suivi des réclamations<br>• Système de tickets<br>• Historique des interactions |

## Technologies Utilisées
- JavaFX (Interface graphique)
- JDBC (Connexion à la base de données)
- MySQL (Base de données)
- Maven ou Gradle (Gestion de projet)
- JFoenix ou ControlsFX (Composants UI avancés)
- Java 17 ou supérieur

## Configuration Requise
- Java 17 ou supérieur
- Maven ou Gradle
- IDE Java (IntelliJ IDEA, Eclipse, NetBeans, etc.)
- Base de données MySQL

## Collaborateurs
Chabani Mohamed Ali  
Boulifa Mohamed Aziz  
Chagouani Yassine  
Homri Manel  
Solomou Bienvenu  
Traore Rachid
