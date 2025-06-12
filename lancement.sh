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
if [ ! -f "$MYCNF" ]; then  #-f Si le chemin existe ET si c'est un fichier régulier 
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


echo "🛠 Vérification de l'existence de la base de données..."
DB_EXISTS=$(mysql --defaults-file=$MYCNF -e "SHOW DATABASES LIKE '$DBNAME';" | grep "$DBNAME") #-e est une option mysql qui signifie "execute"

if [ -z "$DB_EXISTS" ]; then  #-z vérifie si une chaine est vide
    echo "🔍 La base de données n'existe pas. Création en cours..."
    mysql --defaults-file=$MYCNF -e "CREATE DATABASE $DBNAME;"
    
    echo "🧱 Création des tables..."
    mysql --defaults-file=$MYCNF $DBNAME < creationBD.sql

    echo "📦 Insertion des données initiales..."
    mysql --defaults-file=$MYCNF $DBNAME < insertions.sql
else
    echo "✅ La base de données existe déjà. Pas de création ni d'insertion de données."
fi
echo "🧮 Compilation du projet Java..."
mkdir -p bin
javac -d bin -cp "lib/*:src" src/*/*.java

echo "🚀 Lancement de l'application..."
java -cp "lib/*:bin" $MAIN_CLASS
