#!/bin/bash
# filepath: /home/nathan/Documents/SAE-Java---Livre-Express/script.sh

DBNAME="java"  # À adapter : le nom de ta base
MAIN_CLASS="menu.ExecutableMenu" # Classe principale Java

echo "Création de la base de données si besoin..."
mysql --defaults-file=.my.cnf -e "CREATE DATABASE IF NOT EXISTS $DBNAME;"

echo "Création des tables..."
mysql --defaults-file=.my.cnf $DBNAME < creationBD.sql

echo "Insertion des données..."
mysql --defaults-file=.my.cnf $DBNAME < insertions.sql

echo "Compilation du projet Java..."
mkdir -p bin
javac -d bin -cp "lib/*:src" $(find src -name "*.java")

echo "Lancement de l'application..."
java -cp "lib/*:bin" $MAIN_CLASS