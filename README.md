# Livre Express

Bienvenue sur le projet **Livre Express** : une application Java de gestion de librairies, permettant la gestion des stocks, des commandes, des clients, des vendeurs et des administrateurs.

## Structure du projet

- `src/` : Contient le code source Java organisé en packages (`modele`, `BD`, `menu`, etc.).
- `lib/` : Bibliothèques externes nécessaires (JDBC, JUnit, Hamcrest).
- `bin/` : Fichiers compilés (.class).
- `test/` : Tests unitaires JUnit.
- `.vscode/` : Configuration pour Visual Studio Code.
- `creationBD.sql`, `insertions.sql` : Scripts SQL pour créer et remplir la base de données.
- `README.md` : lire ce fichier pour savoir comment lancer le projet

## Prérequis

- Java 8 ou supérieur
- [MariaDB](https://mariadb.org/) ou MySQL pour la base de données
- Les librairies présentes dans le dossier `lib/` (JUnit, Hamcrest, MariaDB JDBC)

## Pour lancer ce projet:
1. Aller dans le fichier .my.cnf est remplir les paramètres,c'est à dire comme ceci:  
    [client]  
    user=le nom utilisateur de votre BD  
    password=le mot de passe associer a cette utilisateur  
    host=sur quel serveur vous voulez que la BD créer  
2. éxecuter le fichier `lancement.sh` en allant à la racine du projet et en faisant cette commande: `./lancement.sh`  
-> Après cela vous pouvez utilisez librement notre application dans le terminal