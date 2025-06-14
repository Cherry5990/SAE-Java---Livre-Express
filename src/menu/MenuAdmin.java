package menu;

import java.util.Scanner;

import BD.AdminBD;
import BD.ConnexionMySQL;
import BD.MagasinBD;
import BD.ReseauBD;
import BD.VendeurBD;
import modele.Admin;

public class MenuAdmin {
    private static final Scanner scan = new Scanner(System.in, "UTF-8"); // Scanner unique
    private static MagasinBD magasinBD;
    private static VendeurBD vendeurBD;
    private static AdminBD adminBD;
    private static ReseauBD reseauBD;

    // Menu de connexion en tant qu'Admin
    public static void connexionAdmin(ConnexionMySQL con){
        System.out.println("┌────────────────────────────────────────────────────────────┐");        
        System.out.println("│ Êtes vous sure de vouloir vous connecter en tant qu'Admin? │");
        System.out.println("│ [C] continuer  [Q] Retour en arriere                       │");
        System.out.println("└────────────────────────────────────────────────────────────┘");
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "c":
                MenuAdmin.magasinBD = new MagasinBD(con);
                MenuAdmin.vendeurBD = new VendeurBD(con);
                MenuAdmin.adminBD = new AdminBD(con);
                MenuAdmin.reseauBD = new ReseauBD(con);
                MenuAdmin.menuAdmin(con);
                break;
            case "q":
                ExecutableMenu.menuPrincipal(con);
                break;
            default:
                System.out.println("Veuillez rentrez une commande valide");
                String skip = scan.nextLine();
                MenuAdmin.connexionAdmin(con);
                break;
        }

    }


    // Menu principal de l'Admin
    public static void menuAdmin(ConnexionMySQL con){
        System.out.println("┌──────────────────────────────────────────┐");        
        System.out.println("│ Vous êtes connecté en tant qu'Admin      │");
        System.out.println("│         Que voulez vous faire?           │");
        System.out.println("│ 1 - Créer un compte vendeur              │");
        System.out.println("│ 2 - Ajouter une nouvelle librairie       │");
        System.out.println("│ 3 - Gérer les stocks globaux             │");
        System.out.println("│ 4 - Consulter les statistiques de ventes │");
        System.out.println("│ Q - Revenir en arriere                   │");
        System.out.println("└──────────────────────────────────────────┘");   
        String action = scan.nextLine();
        switch (action) {
            case "q":{
                System.out.println("Vous retournez au menu principal");
                ExecutableMenu.menuPrincipal(con);
                break;}          
            case "1":
                MenuAdmin.sousMenuCreeCompteVendeur(con);
                break;
            case "2":
                MenuAdmin.sousMenuAjouterLibrairie(con);
                break;
            case "3":
                MenuAdmin.sousMenuGererStocksGlobaux(con);
                break;
            case "4":
                MenuAdmin.sousMenuStatsDeVente(con);
                break;
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuAdmin.menuAdmin(con);      
                break;
        }
    } 

    //Première Partie - Creer un compte vendeur
    public static void sousMenuCreeCompteVendeur(ConnexionMySQL con){
        System.out.println("┌──────────────────────────────┐");        
        System.out.println("│ Créer un compte vendeur:     │");
        System.out.println("│ Rentrer le prénom du vendeur │");
        System.out.println("│ Q - revenir en arrière       │");
        System.out.println("└──────────────────────────────┘"); 
        String entrerPrenom = scan.nextLine().trim();
        switch (entrerPrenom) {
            case "q":
            case "Q":
                MenuAdmin.menuAdmin(con);
                break;
            default:
                MenuAdmin.creeCompteVendeurDemandeNom(con, entrerPrenom);
                break;
        }
    }

    public static void creeCompteVendeurDemandeNom(ConnexionMySQL con, String Prenom){
        System.out.println("┌──────────────────────────────┐");        
        System.out.println("│ Créer un compte vendeur:     │");
        System.out.println("│ Rentrer le nom du vendeur    │");
        System.out.println("│ Q - revenir en arrière       │");
        System.out.println("│ M - Menu Admin               │");
        System.out.println("└──────────────────────────────┘"); 
        String entrerNom = scan.nextLine().trim();
        switch (entrerNom) {
            case "q":
            case "Q":
                MenuAdmin.sousMenuCreeCompteVendeur(con);
                break;
            case "m":
            case "M":
                MenuAdmin.menuAdmin(con);
                break;
            default:
                MenuAdmin.creeCompteVendeurDemandeIdmag(con,Prenom,entrerNom);
                break;
        }
    }

    public static void creeCompteVendeurDemandeIdmag(ConnexionMySQL con, String Prenom, String Nom){
        System.out.println("┌───────────────────────────────────┐");        
        System.out.println("│ Créer un compte vendeur:          │");
        System.out.println("│ Rentrer l'id du magasin du vendeur│");
        System.out.println("│ Q - Revenir en arrière            │");
        System.out.println("│ M - Menu Admin                    │");
        System.out.println("└───────────────────────────────────┘"); 
        String entrerIdmag = scan.nextLine().toLowerCase().trim();
        switch (entrerIdmag) {
            case "q":
                MenuAdmin.sousMenuCreeCompteVendeur(con);
                break;
            case "m":
                MenuAdmin.menuAdmin(con);
                break;
            default:
                try{
                    int idmagMax = magasinBD.maxIdMagasin();
                    int idmag = Integer.parseInt(entrerIdmag);
                    if(idmag > idmagMax){
                        System.out.println("Aucun magasin n'a comme id " + idmag);
                        String saut = scan.nextLine();
                        MenuAdmin.creeCompteVendeurDemandeIdmag(con,Prenom,Nom);
                    }
                    MenuAdmin.creeCompteVendeurValider(con,Prenom,Nom, idmag);
                    break;
                }catch(Exception e){
                System.out.println("Veuillez rentrer un nombre");
                MenuAdmin.creeCompteVendeurDemandeIdmag(con, Prenom, Nom);
                }
                break;
        }
    }

    public static void creeCompteVendeurValider(ConnexionMySQL con, String Prenom, String Nom, int idmag){
        System.out.println("┌───────────────────────────────────┐");        
        System.out.println("│ Créer un compte vendeur:          │");
        System.out.println("│ Confirmer vous l'ajout du vendeur │");
        System.out.println("└───────────────────────────────────┘"); 
        System.out.println( Nom + " " + Prenom + " au magasin d'id "+ idmag );
        System.out.println("┌───────────────────────────────────┐"); 
        System.out.println("│ C - Confirmer                     │");
        System.out.println("│ Q - Retour en arrière             │");
        System.out.println("│ M - Menu Admin                    │");
        System.out.println("└───────────────────────────────────┘"); 
        String entrer = scan.nextLine().toLowerCase().trim();
        switch (entrer) {
            case "q":
                MenuAdmin.sousMenuCreeCompteVendeur(con);
                break;
            case "m":
                MenuAdmin.menuAdmin(con);
                break;
            case "c":
                try {
                    vendeurBD.insererVendeur(Prenom, Nom, idmag);
                    System.out.println( "Le compte de " + Nom + " " + Prenom + " à bien été crée et associé au magasin d'id "+ idmag );
                    String saut = scan.nextLine();
                    MenuAdmin.sousMenuCreeCompteVendeur(con);
                } catch (java.sql.SQLException e) {
                    System.out.println("Erreur lors de l'insertion du vendeur : " + e.getMessage());
                }
                break;
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuAdmin.creeCompteVendeurValider(con,Prenom,Nom, idmag);        
                break;
        }
    }
    //Fin première partie 

    //Deuxième Partie - ajouter une librairie
    public static void sousMenuAjouterLibrairie(ConnexionMySQL con){
        System.out.println("┌────────────────────────────────┐");        
        System.out.println("│ Ajouter une librairie:         │");
        System.out.println("│ Rentrer le nom de la librairie │");
        System.out.println("│ Q - Revenir en arrière         │");
        System.out.println("└────────────────────────────────┘"); 
        String entrerNom = scan.nextLine().trim();
        switch (entrerNom) {
            case "q":
            case "Q":
                MenuAdmin.menuAdmin(con);
                break;
            default:
                MenuAdmin.ajouteLibrairieNom(con,entrerNom);
                break;
        }
    }

    private static void ajouteLibrairieNom(ConnexionMySQL con, String nom){
        System.out.println("┌────────────────────────────────────────────────┐");        
        System.out.println("│ Ajouter une librairie:                         │");
        System.out.println("│ Rentrer le nom de la ville où est la librairie │");
        System.out.println("│ Q - Revenir en arrière                         │");
        System.out.println("│ M - Menu Admin                                 │");
        System.out.println("└────────────────────────────────────────────────┘"); 
        String entrerNomVille = scan.nextLine().trim();
        switch (entrerNomVille) {
            case "m":
            case "M":
                MenuAdmin.menuAdmin(con);
                break;
            case "q":
            case "Q":
                MenuAdmin.sousMenuAjouterLibrairie(con);
                break;
            default:
                MenuAdmin.ajouteLibrairieValide(con,nom, entrerNomVille);
                break;
        }
    }

    private static void ajouteLibrairieValide(ConnexionMySQL con, String nom, String nomVille){
        System.out.println("┌───────────────────────────────────────┐");        
        System.out.println("│ Ajouter une librairie:                │");
        System.out.println("│ Confirmer vous l'ajout de la librairie│");
        System.out.println("└───────────────────────────────────────┘"); 
        System.out.println( nom + " basé à " + nomVille );
        System.out.println("┌───────────────────────────────────┐"); 
        System.out.println("│ C - Confirmer                     │");
        System.out.println("│ Q - Retour en arrière             │");
        System.out.println("│ M - Menu Admin                    │");
        System.out.println("└───────────────────────────────────┘"); 
        String entrer = scan.nextLine().toLowerCase().trim();
        switch (entrer) {
            case "m":
                MenuAdmin.menuAdmin(con);
                break;
            case "q":
                MenuAdmin.ajouteLibrairieNom(con, nom);
                break;
            case "c":
                try {
                    magasinBD.insererMagasin(nom, nomVille);
                    System.out.println( "Le magasin " + nom + " basé à " + nomVille + " a bien été ajouté au réseau" );
                    String saut = scan.nextLine();
                    MenuAdmin.sousMenuAjouterLibrairie(con);
                } catch (java.sql.SQLException e) {
                    System.out.println("Erreur lors de l'insertion de la librairie : " + e.getMessage());
                }
                break;
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuAdmin.ajouteLibrairieValide(con,nom, nomVille);        
                break;
        }
    }
    //Fin deuxieme partie 

    //Quatrieme Partie - consulter les statistique de vente
    public static void sousMenuStatsDeVente(ConnexionMySQL con){
        System.out.println("┌──────────────────────────────────────────────────────┐");        
        System.out.println("│ Menu statistiques de vente                           │");
        System.out.println("│ Quelle statistique voulez vous consulter             │");
        System.out.println("│ 1 - Chiffre d'affaire                                │");
        System.out.println("│ 2 - Livres les plus vendus                           │");
        System.out.println("│ 3 - Comparaison ventes en ligne et ventes en magasin │");
        System.out.println("│ 4 - Valeur du stock par magasin                      │");
        System.out.println("│ Q - Revenir en arrière                               │");
        System.out.println("└──────────────────────────────────────────────────────┘");   
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "q":
                MenuAdmin.menuAdmin(con);
                break;
            case "1":
                MenuAdmin.chiffreDAffaire(con);
                break;
            case "2":
                MenuAdmin.livresLesPlusVendu(con);
            case "3":
                MenuAdmin.venteEnLigneContreMagasin(con);
                break;
            case "4":
                MenuAdmin.valeurStock(con);
                break;
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuAdmin.sousMenuStatsDeVente(con);
                break;
        }

    }

    public static void chiffreDAffaire(ConnexionMySQL con){
        System.out.println("┌───────────────────────────────────┐");        
        System.out.println("│ Menu chiffre d'affaire            │");
        System.out.println("│ 1 - CA total tous les temps       │");
        System.out.println("│ 2 - CA total par année            │");
        System.out.println("│ 3 - CA par magasin tous les temps │");
        System.out.println("│ 4 - CA par magasin par année      │");
        System.out.println("│ Q - Revenir en arrière            │");
        System.out.println("└───────────────────────────────────┘");  
        String entrer = scan.nextLine().toLowerCase().trim();
        switch (entrer) {
            case "q":
                MenuAdmin.menuAdmin(con);
                break;
            case "1":
                System.out.println(adminBD.chiffreDAffaireTotalToutTemps());
                MenuAdmin.appuyerEntrer(con);
                MenuAdmin.chiffreDAffaire(con);
                break;
            case "3":
                System.out.println(adminBD.chiffreDAffaireMagasinToutTemps());
                MenuAdmin.appuyerEntrer(con);
                MenuAdmin.chiffreDAffaire(con);
                break;
            case "2":
                System.out.println(adminBD.chiffreDAffaireTotalParAns());
                MenuAdmin.appuyerEntrer(con);
                MenuAdmin.chiffreDAffaire(con);
                break;
            case "4":
                System.out.println("┌───────────────────────────┐");        
                System.out.println("│ Rentrez l'année souhaitée │");
                System.out.println("└───────────────────────────┘"); 
                try{
                    String entrerAnne = scan.nextLine();
                    Integer anne = Integer.parseInt(entrerAnne);
                    System.out.println(adminBD.chiffreDAffaireMagasinParAns(anne));
                    MenuAdmin.appuyerEntrer(con);
                    MenuAdmin.chiffreDAffaire(con);
                }catch(Exception e){
                    System.out.println("Veuillez rentrer un nombre");
                    MenuAdmin.chiffreDAffaire(con);
                }
                break;
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuAdmin.chiffreDAffaire(con);   
                break;
        }
    }

    public static void appuyerEntrer(ConnexionMySQL con){
        System.out.println("┌────────────────────────────────────────────┐");        
        System.out.println("│ Appuyez sur entrer pour revenir en arrière │");
        System.out.println("└────────────────────────────────────────────┘"); 
        String saut = scan.nextLine();
    }

    public static void livresLesPlusVendu(ConnexionMySQL con) {
        System.out.println("┌────────────────────────────────┐");
        System.out.println("│ Les 10 livres les plus vendus  │");
        System.out.println("│ 1 - Total tous les temps       │");
        System.out.println("│ 2 - Total par année            │");
        System.out.println("│ 3 - Par magasin tous les temps │");
        System.out.println("│ 4 - Par magasin par année      │");
        System.out.println("│ Q - Revenir en arrière         │");
        System.out.println("└────────────────────────────────┘");

        String entrer = scan.nextLine().toLowerCase().trim();
        try {
            switch (entrer) {
                case "q":
                    MenuAdmin.menuAdmin(con);
                    break;
                case "1":
                    System.out.println(adminBD.livresLesPlusVendusTotalToutTemps());
                    MenuAdmin.appuyerEntrer(con);
                    MenuAdmin.livresLesPlusVendu(con);
                    break;
                case "3":
                    System.out.println("┌───────────────────────────────┐");
                    System.out.println("│ Rentrer l'id du magasin       │");
                    System.out.println("└───────────────────────────────┘");
                    String entrerIdMag = scan.nextLine();
                    int idMag = Integer.parseInt(entrerIdMag);
                    System.out.println(adminBD.livresLesPlusVendusParMagasinToutTemps(idMag));
                    MenuAdmin.appuyerEntrer(con);
                    MenuAdmin.livresLesPlusVendu(con);
                    break;
                case "2":
                    System.out.println("┌───────────────────────────┐");
                    System.out.println("│ Rentrer l'annee souhaitée │");
                    System.out.println("└───────────────────────────┘");
                    String entrerAnne = scan.nextLine();
                    int annee = Integer.parseInt(entrerAnne);
                    System.out.println(adminBD.livresLesPlusVendusTotalParAns(annee));
                    MenuAdmin.appuyerEntrer(con);
                    MenuAdmin.livresLesPlusVendu(con);
                    break;
                case "4":
                    System.out.println("┌───────────────────────────────┐");
                    System.out.println("│ Rentrer l'id du magasin       │");
                    System.out.println("└───────────────────────────────┘");
                    String entrerIdMag2 = scan.nextLine();
                    int idMag2 = Integer.parseInt(entrerIdMag2);
                    System.out.println("┌───────────────────────────┐");
                    System.out.println("│ Rentrer l'annee souhaitée │");
                    System.out.println("└───────────────────────────┘");
                    String entrerAnne4 = scan.nextLine();
                    int annee4 = Integer.parseInt(entrerAnne4);
                    System.out.println(adminBD.livresLesPlusVendusParMagasinParAns(idMag2, annee4));
                    MenuAdmin.appuyerEntrer(con);
                    MenuAdmin.livresLesPlusVendu(con);
                    break;
                default:
                    System.out.println("Veuillez rentrer une commande valide");
                    MenuAdmin.livresLesPlusVendu(con);
                    break;
            }
        } catch (Exception e) {
            System.out.println("Veuillez rentrer un nombre");
            MenuAdmin.livresLesPlusVendu(con);
        }
    }

    public static void venteEnLigneContreMagasin(ConnexionMySQL con){
        System.out.println("┌───────────────────────────────────┐");        
        System.out.println("│ Ventes en ligne contre en magasin │");
        System.out.println("│ 1 - Tous les temps                │");
        System.out.println("│ 2 - Par annee                     │");
        System.out.println("│ 3 - Par magasin tous les temps    │");
        System.out.println("│ 4 - Par magasin par année         │");
        System.out.println("│ Q - Revenir en arrière            │");
        System.out.println("└───────────────────────────────────┘");  
        String entrer = scan.nextLine().toLowerCase().trim();
        switch (entrer) {
            case "q":
                MenuAdmin.menuAdmin(con);
                break;
            case "1":
                System.out.println(adminBD.ventesLigneContreMagasinToutTemps());
                MenuAdmin.appuyerEntrer(con);
                MenuAdmin.venteEnLigneContreMagasin(con);
                break;
            case "2":
                System.out.println(adminBD.ventesLigneContreMagasinParAns());
                MenuAdmin.appuyerEntrer(con);
                MenuAdmin.venteEnLigneContreMagasin(con);
                break;
            case "3":
                System.out.println(adminBD.ventesLigneContreMagasinParMagasinTousTemps());
                MenuAdmin.appuyerEntrer(con);
                MenuAdmin.venteEnLigneContreMagasin(con);
                break;
            case "4":
                System.out.println("┌───────────────────────────┐");        
                System.out.println("│ Rentrez l'annee souhaité  │");
                System.out.println("└───────────────────────────┘"); 
                try{
                    String entrerAnne = scan.nextLine();
                    Integer anne = Integer.parseInt(entrerAnne);
                    System.out.println(adminBD.ventesLigneContreMagasinParMagasinParAns(anne));
                    MenuAdmin.appuyerEntrer(con);
                    MenuAdmin.venteEnLigneContreMagasin(con);
                }catch(Exception e){
                    System.out.println("Veuillez rentrer un nombre");
                    MenuAdmin.venteEnLigneContreMagasin(con);
                }
                break;
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuAdmin.venteEnLigneContreMagasin(con);   
                break;
        }
    }

    public static void valeurStock(ConnexionMySQL con){
        System.out.println(adminBD.valeurStockTotal());
        System.out.println("Celle par magasin est :");
        System.out.println(adminBD.valeurStockParMagasin());
        MenuAdmin.appuyerEntrer(con);
        MenuAdmin.sousMenuStatsDeVente(con);
    }
    //Fin quatrieme Partie 

    //Troisieme Partie - gerer les stocks globaux
    public static void sousMenuGererStocksGlobaux(ConnexionMySQL con){
        System.out.println("┌───────────────────────────────────┐");        
        System.out.println("│ Gerer les stocks globaux          │");
        System.out.println("│ 1 - Voir stocks                   │");
        System.out.println("│ 2 - Transférer stocks             │");
        System.out.println("│ Q - Revenir en arrière            │");
        System.out.println("└───────────────────────────────────┘"); 
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "q":
                MenuAdmin.menuAdmin(con);
                break;
            case "1":
                MenuAdmin.voirStocks(con);
                break;
            case "2":
                MenuAdmin.transfererStocks(con);
                break;
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuAdmin.sousMenuGererStocksGlobaux(con);
                break;
        }
    }

    public static void voirStocks(ConnexionMySQL con) {
        System.out.println("┌─────────────────────────────────────────────────────────┐");        
        System.out.println("│                     Voir les stocks:                    │");
        System.out.println("│ Entrer l'id du magasin dont vous voulez voir les stocks │");
        System.out.println("│ Entrez 0 pour voir les stocks de tout le réseau         │");
        System.out.println("│ Q - revenir en arriere                                  │");
        System.out.println("└─────────────────────────────────────────────────────────┘"); 
        String entrer = scan.nextLine().toLowerCase().trim();
        switch (entrer) {
            case "q":
                MenuAdmin.menuAdmin(con);
                break;
            case "0":
                MenuAdmin.stocksReseau(con, 0, 10);
                break;
            default:
                try{int idmag = Integer.parseInt(entrer);
                if (idmag > 0 && idmag <= magasinBD.maxIdMagasin()) {
                    MenuAdmin.stocksMagasin(con, idmag, 0, 10);
                }else {
                    System.out.println("Aucun magasin n'a comme id " + idmag);
                    String saut = scan.nextLine();
                    MenuAdmin.voirStocks(con);
                }
                break;
                } catch (Exception e) {
                System.out.println("Veuillez rentrer un nombre");
                MenuAdmin.voirStocks(con);
        }
        }
        
    }

    public static void stocksReseau(ConnexionMySQL con, int debut, int fin){
        
        System.out.println("┌────────────────────────────────────────────────────────────────────┐");
        System.out.println("│            Catalogue des livres du réseau                          │");
        System.out.println("├────────────────────────────────────────────────────────────────────┤");
        System.out.println(reseauBD.voirStockReseau(debut, fin));
        System.out.println("├────────────────────────────────────────────────────────────────────┤");
        System.out.println("  Affichage : " + (debut + 1) + " - " + fin + "                 ");
        System.out.println("  [C] Page suivante   [R] Page précédente   [Q] Retour        ");
        System.out.println("└────────────────────────────────────────────────────────────────────┘");
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "c":
                if(fin<reseauBD.maxPossederLivreReseau()){
                    MenuAdmin.stocksReseau(con, debut+10,fin+10);
                }
                else{
                    System.out.println("Vous êtes déjà à la fin du catalogue.");
                    MenuAdmin.stocksReseau(con, debut, fin);
                }
                break;
            case "r":
                if (debut>=10){
                    MenuAdmin.stocksReseau(con, debut-10,fin-10);
                }
                else{
                    System.out.println("Vous êtes déjà au début du catalogue.");
                    MenuAdmin.stocksReseau(con, debut, fin);
                }
                break;
            case "q":
                MenuAdmin.voirStocks(con);
                break;      
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuAdmin.stocksReseau(con, debut, fin);
        MenuAdmin.voirStocks(con);
                break;
        }
       
    }

    public static void stocksMagasin(ConnexionMySQL con,int idmag, int debut,int fin){
        System.out.println("┌────────────────────────────────────────────────────────────────────┐");
        System.out.println("│            Catalogue des livres du magasin " + idmag +"                     │");
        System.out.println("├────────────────────────────────────────────────────────────────────┤");
        System.out.println(magasinBD.voirStock(idmag, debut, fin));
        System.out.println("├────────────────────────────────────────────────────────────────────┤");
        System.out.println("  Affichage : " + (debut + 1) + " - " + fin + "                 ");
        System.out.println("  [C] Page suivante   [R] Page précédente   [Q] Retour        ");
        System.out.println("└────────────────────────────────────────────────────────────────────┘");
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "c":
                if(fin<magasinBD.maxPossederLivre(idmag)){
                    MenuAdmin.stocksMagasin(con,idmag, debut+10,fin+10);
                }
                else{
                    System.out.println("Vous êtes déjà à la fin du catalogue.");
                    MenuAdmin.stocksMagasin(con, idmag,debut, fin);
                }
                break;
            case "r":
                if (debut>=10){
                    MenuAdmin.stocksMagasin(con, idmag ,debut-10,fin-10);
                }
                else{
                    System.out.println("Vous êtes déjà au début du catalogue.");
                    MenuAdmin.stocksMagasin(con,idmag, debut, fin);
                }
                break;
            case "q":
                MenuAdmin.voirStocks(con);
                break;      
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuAdmin.stocksMagasin(con,idmag , debut,fin);           
                break;
        }
    }

    public static void transfererStocks(ConnexionMySQL con){
        System.out.println("┌───────────────────────────────────────────────┐");
        System.out.println("│  Transférer un livre d'un magasin à un autre  │");
        System.out.println("│  Rentrer l'id du magasin qui donnera le livre │");
        System.out.println("│  Q - Revenir en arrière                       │");
        System.out.println("└───────────────────────────────────────────────┘");
        String entrerIdMagDonneur = scan.nextLine().toLowerCase().trim();
        switch (entrerIdMagDonneur) {
            case "q":
                MenuAdmin.sousMenuGererStocksGlobaux(con);
                break;
            default:
                try {
                    int idMagDonneur = Integer.parseInt(entrerIdMagDonneur);
                    if (idMagDonneur > 0 && idMagDonneur <= magasinBD.maxIdMagasin()) {
                        MenuAdmin.transfererStocksDemandeLivre(con, idMagDonneur);
                    } else {
                        System.out.println("Aucun magasin n'a comme id " + idMagDonneur);
                        String saut = scan.nextLine();
                        MenuAdmin.transfererStocks(con);
                    }
                } catch (Exception e) {
                    System.out.println("Veuillez rentrer un nombre");
                    String saut = scan.nextLine();
                    MenuAdmin.transfererStocks(con);
                }
                break;
        }
    }

    public static void transfererStocksDemandeLivre(ConnexionMySQL con, int idMagDonneur){
        System.out.println("┌──────────────────────────────────────────────────────────────┐");
        System.out.println("│ Pour transférer un livre, vous aurez besoin de son ISBN      │");
        System.out.println("│ Rentrez le nom du livre dont vous souhaitez connaitre l'ISBN │");
        System.out.println("│ Vous pouvez ne mettre qu'une partie du nom                   │");
        System.out.println("│ Q - Revenir en arrière                                       │");
        System.out.println("└──────────────────────────────────────────────────────────────┘");
        String entrerNomLivre = scan.nextLine().toLowerCase().trim();
        switch (entrerNomLivre) {
            case "q":
                MenuAdmin.transfererStocks(con);
                break;
            default:
                String livres = magasinBD.rechercheLivre(idMagDonneur, entrerNomLivre);
                if (livres.isEmpty()) {
                    System.out.println("Aucun livre ne correspond à votre recherche");
                    String saut = scan.nextLine();
                    MenuAdmin.transfererStocksDemandeLivre(con, idMagDonneur);
                } else {
                    System.out.println(livres);
                    MenuAdmin.transfererStocksDemandeIsbn(con, idMagDonneur);
                }
                break;
        }
    }
    
    public static void transfererStocksDemandeIsbn(ConnexionMySQL con, int idMagDonneur){
        System.out.println("┌───────────────────────────────────────────────────────┐"); 
        System.out.println("│Rentrez l'ISBN du livre que vous voulez transférer     │"); 
        System.out.println("│Rentrez les 13 chiffres sans espaces                   │"); 
        System.out.println("│[R] Faire une nouvelle recherche [M] Menu gérer stocks │");
        System.out.println("└───────────────────────────────────────────────────────┘");
        String entrerIsbn = scan.nextLine().toLowerCase().trim();
        switch (entrerIsbn) {
            case "r":
                MenuAdmin.transfererStocksDemandeLivre(con, idMagDonneur);
                break;
            case "m":
                MenuAdmin.sousMenuGererStocksGlobaux(con);
                break;
            default:
                if(magasinBD.existeLivre(entrerIsbn,idMagDonneur)){
                    MenuAdmin.transfererStocksDemandeIdMagReceveur(con, idMagDonneur, entrerIsbn);
                } else {
                    System.out.println("Le livre " + entrerIsbn + " n'est pas dans le magasin d'id " + idMagDonneur);
                    String saut = scan.nextLine();
                    MenuAdmin.transfererStocksDemandeIsbn(con, idMagDonneur);
                }
                break;
        }
    }

    public static void transfererStocksDemandeIdMagReceveur(ConnexionMySQL con, int idMagDonneur, String isbn){
        System.out.println("┌──────────────────────────────────────────────────────────────┐");
        System.out.println("│ Pour transférer le livre, vous devez choisir un magasin      │");
        System.out.println("│ Rentrer l'id du magasin qui recevra le livre                 │");
        System.out.println("│ Q - Revenir en arrière                                       │");
        System.out.println("│ M - Menu gérer stocks                                        │");
        System.out.println("└──────────────────────────────────────────────────────────────┘");
        String entrerIdMagReceveur = scan.nextLine().toLowerCase().trim();
        switch (entrerIdMagReceveur) {
            case "q":
                MenuAdmin.transfererStocksDemandeIsbn(con, idMagDonneur);
                break;
            case "m":
                MenuAdmin.sousMenuGererStocksGlobaux(con);
                break;
            default:
                try {
                    int idMagReceveur = Integer.parseInt(entrerIdMagReceveur);
                    if (idMagReceveur == idMagDonneur) {
                        System.out.println("Vous ne pouvez pas transférer un livre à son propre magasin");
                        String saut = scan.nextLine();
                        MenuAdmin.transfererStocksDemandeIdMagReceveur(con, idMagDonneur, isbn);
                    }
                    else if (idMagReceveur > 0 && idMagReceveur <= magasinBD.maxIdMagasin()) {
                        MenuAdmin.demandeQte(con, idMagDonneur, idMagReceveur, isbn);
                    } else {
                        System.out.println("Aucun magasin n'a comme id " + idMagReceveur);
                        String saut = scan.nextLine();
                        MenuAdmin.transfererStocksDemandeIdMagReceveur(con, idMagDonneur, isbn);
                    }
                } catch (Exception e) {
                    System.out.println("Veuillez rentrer un nombre");
                    String saut = scan.nextLine();
                    MenuAdmin.transfererStocksDemandeIdMagReceveur(con, idMagDonneur, isbn);
                }
                break;
        }
    }

    public static void demandeQte(ConnexionMySQL con, int idMagDonneur, int idMagReceveur, String isbn) {
        System.out.println("┌──────────────────────────────────────────────────────────────┐");
        System.out.println("│ Rentrez la quantité de livre à transférer                    │");
        System.out.println("│ Q - Revenir en arrière                                       │");
        System.out.println("│ M - Menu gerer stocks                                        │");
        System.out.println("└──────────────────────────────────────────────────────────────┘");
        String entrerQte = scan.nextLine().toLowerCase().trim();
        switch (entrerQte) {
            case "q":
                MenuAdmin.transfererStocksDemandeIdMagReceveur(con, idMagDonneur, isbn);
                break;
            case "m":
                MenuAdmin.sousMenuGererStocksGlobaux(con);
                break;
            default:
                try {
                    int qte = Integer.parseInt(entrerQte);
                    if (qte <= 0 || qte > magasinBD.getQte(isbn, idMagDonneur)) {
                        System.out.println("La quantité doit être supérieure à 0");
                        String saut = scan.nextLine();
                        MenuAdmin.demandeQte(con, idMagDonneur, idMagReceveur, isbn);
                    } else {
                        MenuAdmin.transfererStocksValider(con, idMagDonneur, idMagReceveur, isbn, qte);
                    }
                } catch (Exception e) {
                    System.out.println("Veuillez rentrer un nombre");
                    String saut = scan.nextLine();
                    MenuAdmin.demandeQte(con, idMagDonneur, idMagReceveur, isbn);
                }
                break;
        }
    }

    public static void transfererStocksValider(ConnexionMySQL con, int idMagDonneur, int idMagReceveur, String isbn,int qte){
        System.out.println("┌────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│ Confirmer vous le transfert de "+ qte + " exemplaire du livre d'id " + isbn+"    │");
        System.out.println("│du magasin d'id " + idMagDonneur + " vers le magasin d'id " + idMagReceveur + "?                                   │");
        System.out.println("└────────────────────────────────────────────────────────────────────────────┘");
        System.out.println("┌───────────────────────────────────┐"); 
        System.out.println("│ C - Confirmer                     │");
        System.out.println("│ Q - Retour en arrière             │");
        System.out.println("│ M - Menu gérer stocks             │");
        System.out.println("└───────────────────────────────────┘"); 
        String entrer = scan.nextLine().toLowerCase().trim();
        switch (entrer) {
            case "q":
                MenuAdmin.demandeQte(con, idMagDonneur, idMagReceveur, isbn);
                break;
            case "m":
                MenuAdmin.sousMenuGererStocksGlobaux(con);
                break;
            case "c":
                MenuAdmin.transfererLivre(con,idMagDonneur, idMagReceveur, isbn, qte);
                System.out.println(qte + " exemplaires du livre " + isbn + " a bien été transféré du magasin d'id " + idMagDonneur + " vers le magasin d'id " + idMagReceveur);
                String saut = scan.nextLine();
                MenuAdmin.sousMenuGererStocksGlobaux(con);
                break;
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuAdmin.transfererStocksValider(con,idMagDonneur,idMagReceveur,isbn, qte);        
                break;
        }
    }

    public static void transfererLivre(ConnexionMySQL con, int idMagDonneur, int idMagReceveur, String isbn, int qte) {
        try {
            int qteD = magasinBD.getQte(isbn, idMagDonneur) -qte ;
            int qteR = magasinBD.getQte(isbn, idMagReceveur) + qte;
            magasinBD.miseAJourQuantite(isbn, qteD, idMagDonneur);
            if (magasinBD.existeLivre(isbn, idMagReceveur)){
                magasinBD.miseAJourQuantite(isbn, qteR, idMagReceveur);
            } else {
                magasinBD.ajouterQte(isbn, qteR, idMagReceveur);
            }

        } catch (java.sql.SQLException e) {
            System.out.println("Erreur lors du transfert du livre : " + e.getMessage());
        }
    }
    //Fin troisieme partie 

        

}



