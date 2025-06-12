#!/bin/bash

MYCNF=".my.cnf"
PROPERTIES_FILE="config.properties"
MAIN_CLASS="menu.ExecutableMenu"

# Fonction pour extraire une clé depuis .my.cnf
get_value() {
    grep -E "^$1=" "$MYCNF" | cut -d'=' -f2
}

# Lecture des infos depuis .my.cnf
USER=$(get_value user)
PASSWORD=$(get_value password) # Pas utilisé ici, mais requis dans .my.cnf
HOST=$(get_value host)
DBNAME=$"java"

# Vérification que .my.cnf existe
if [ ! -f "$MYCNF" ]; then
    echo "❌ Le fichier $MYCNF n'existe pas. Crée-le d'abord avec les bons paramètres."
    exit 1
fi

echo "📄 Création de $PROPERTIES_FILE à partir de $MYCNF..."
cat <<EOF > $PROPERTIES_FILE
db.user=$USER
db.password=$PASSWORD
db.host=$HOST
db.database=$DBNAME
EOF

echo "✅ Fichier $PROPERTIES_FILE généré :"
cat $PROPERTIES_FILE

echo "🛠 Création de la base de données si besoin..."
mysql --defaults-file=$MYCNF -e "CREATE DATABASE IF NOT EXISTS $DBNAME;" -vvv
echo "Code de sortie: $?"

echo "🧱 Création des tables..."
mysql --defaults-file=$MYCNF $DBNAME < creationBD.sql

echo "📦 Insertion des données..."
mysql --defaults-file=$MYCNF $DBNAME < insertions.sql

echo "🧮 Compilation du projet Java..."
mkdir -p bin
javac -d bin -cp "lib/*:src" $(find src -name "*.java")

echo "🚀 Lancement de l'application..."
java -cp "lib/*:bin" $MAIN_CLASS
