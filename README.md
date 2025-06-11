# Livre Express

Bienvenue sur le projet **Livre Express** : une application Java de gestion de librairies, permettant la gestion des stocks, des commandes, des clients, des vendeurs et des administrateurs.

## Structure du projet

- `src/` : Contient le code source Java organisé en packages (`modele`, `BD`, `menu`, etc.).
- `lib/` : Bibliothèques externes nécessaires (JDBC, JUnit, Hamcrest).
- `bin/` : Fichiers compilés (.class).
- `test/` : Tests unitaires JUnit.
- `.vscode/` : Configuration pour Visual Studio Code.
- `creationBD.sql`, `insertions.sql` : Scripts SQL pour créer et remplir la base de données.
- `README.md` : Ce fichier.
- `Sujet.pdf` : Sujet du projet.

## Prérequis

- Java 8 ou supérieur
- [MariaDB](https://mariadb.org/) ou MySQL pour la base de données
- Les librairies présentes dans le dossier `lib/` (JUnit, Hamcrest, MariaDB JDBC)

## Compilation et exécution

Pour compiler le projet et lancer les tests unitaires :

javac -d bin -cp .:lib/junit-4.13.2.jar:src test/TestLivreExpress.java 
java -cp .:lib/hamcrest-2.2.jar:lib/junit-4.13.2.jar:bin org.junit.runner.JUnitCore TestLivreExpress

