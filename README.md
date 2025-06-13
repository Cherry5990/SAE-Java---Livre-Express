# 📚 Livre Express

Bienvenue dans **Livre Express**, une application Java de gestion de librairie. Elle permet de gérer les **stocks**, **commandes**, **clients**, **vendeurs** et **administrateurs** à travers une interface en ligne de commande.

---

## 🗂️ Structure du projet

- `src/` : Code source Java, organisé en packages (`modele`, `BD`, `menu`, etc.).
- `lib/` : Bibliothèques externes requises (JDBC, JUnit, Hamcrest).
- `bin/` : Fichiers compilés `.class`.
- `test/` : Tests unitaires (JUnit).
- `.vscode/` : Configuration pour Visual Studio Code.
- `creationBD.sql` & `insertions.sql` : Scripts SQL pour créer et remplir la base de données.
- `README.md` : Ce fichier.

---

## ✅ Prérequis

- **Java**
- [**MariaDB**](https://mariadb.org/) ou **MySQL**
- Les bibliothèques du dossier `lib/` :
  - `mariadb-java-client`
- **un bash linux** pour le script

---

## 🚀 Lancement du projet

1. Configurez le fichier `.my.cnf` à la racine du projet avec vos identifiants de base de données :

    ```ini
    [client]
    user=VotreNomUtilisateur
    password=VotreMotDePasse
    host=AdresseDuServeur
    ```

2. Depuis la racine du projet, exécutez :

    ```bash
    ./lancement.sh
    ```

    Cela :
    - Crée la base de données (si vous avez les droits)
    - Exécute les scripts SQL
    - Compile le projet
    - Génère la **Javadoc**
    - Lance l'application dans le terminal

---

### ⚠️ Cas particulier : Pas de droit de création de base de données

Si vous **n'avez pas les droits** pour créer une base de données :

1. Ouvrez le fichier `lancement.sh`
2. Repérez la ligne suivante :

    ```bash
    DBNAME=$"java"
    ```

3. Remplacez `"java"` par le nom de **votre** base de données existante.

4. Relancez le script avec :

    ```bash
    ./lancement.sh
    ```