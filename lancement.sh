#!/bin/bash

MYCNF=".my.cnf"
PROPERTIES_FILE="config.properties"
MAIN_CLASS="app.App"

# Fonction pour extraire une clé depuis .my.cnf
get_value() {
    grep -E "^$1=" "$MYCNF" | cut -d'=' -f2
}

# Lecture des infos depuis .my.cnf
USER=$(get_value user)
PASSWORD=$(get_value password)
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
else
    echo "✅ La base de données existe déjà."
fi

echo "🛠 Vérification de l'existence des insertions des données..."
INSERT_EXISTS=$(mysql --defaults-file=$MYCNF $DBNAME -e "show tables;" | grep "CLIENT") #-e est une option mysql qui signifie "execute"

if [ -z "$INSERT_EXISTS" ]; then
    echo "🧱 Création des tables..."
    mysql --defaults-file=$MYCNF $DBNAME < creationBD.sql

    echo "📦 Insertion des données initiales..."
    mysql --defaults-file=$MYCNF $DBNAME < insertions.sql
else
    echo "✅ Les données ont déjà été insérées (table CLIENT existe)."
fi

echo "🛠 Vérification de l'existence du dossier bin..."
if [ ! -d "bin" ]; then
    echo "🧮 Compilation du projet ..."
    find src -name "*.java" > sources.txt
    javac -d bin --module-path lib/javafx/ --add-modules javafx.controls,javafx.fxml -cp "lib/*:src" @sources.txt
    cp -r src/app/view bin/app/view
else
    echo "✅ Le dossier bin existe déjà."
fi

#echo "🛠 Vérification de l'existence du dossier doc..."
#if [ ! -d "doc" ]; then
#    echo "📃 génération de la javadoc..."
#    javadoc -d doc src/*/*.java --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls,javafx.fxml
#else
#    echo "✅ Le dossier doc existe déjà."
#fi

echo "🚀 Lancement de l'application..."
java --module-path lib/javafx/ --add-modules javafx.controls,javafx.fxml -cp "lib/*:bin" app.App
