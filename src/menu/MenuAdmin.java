package menu;

import java.util.Scanner;

import BD.ConnexionMySQL;
import BD.MagasinBD;
import BD.VendeurBD;
import modele.Admin;

public class MenuAdmin {
    private static final Scanner scan = new Scanner(System.in, "UTF-8"); // Scanner unique
    private static MagasinBD magasinBD;
    private static VendeurBD vendeurBD;

    public static void connexionAdmin(ConnexionMySQL con){
        System.out.println("┌────────────────────────────────────────────────────────────┐");        
        System.out.println("│ Etes vous sure de vouloir vous connecter en tant qu'Admin? │");
        System.out.println("│ [C] continuer  [Q] retour en arriere                       │");
        System.out.println("└────────────────────────────────────────────────────────────┘");
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "c":
                magasinBD = new MagasinBD(con);
                vendeurBD = new VendeurBD(con);
                MenuAdmin.menuAdmin(con);
                break;
            case "q":
                ExecutableMenu.menuPrincipal(con);
                break;
            default:
                break;
        }

    }



    public static void menuAdmin(ConnexionMySQL con){
        System.out.println("┌─────────────────────────────────────────┐");        
        System.out.println("│ Vous êtes connectés en tant qu'Admin    │");
        System.out.println("│         Que voulez vous faire?          │");
        System.out.println("│ 1: créer un compte vendeur              │");
        System.out.println("│ 2: ajouter une nouvelle librairie       │");
        System.out.println("│ 3: gérer les stocks globaux             │");
        System.out.println("│ 4: Consulter les statistiques de ventes │");
        System.out.println("│    Rentrez Q pour revenir en arriere    │");
        System.out.println("└─────────────────────────────────────────┘");   
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
                System.out.println("A faire");
                MenuAdmin.menuAdmin(con);
                break;
            case "4":
                System.out.println("A faire");
                MenuAdmin.menuAdmin(con);
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
        System.out.println("│ Creer un compte vendeur:     │");
        System.out.println("│ Rentrer le prenom du vendeur │");
        System.out.println("│ Q - revenir en arriere       │");
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
        System.out.println("│ Creer un compte vendeur:     │");
        System.out.println("│ Rentrer le nom du vendeur    │");
        System.out.println("│ Q - revenir en arriere       │");
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
        System.out.println("│ Creer un compte vendeur:          │");
        System.out.println("│ Rentrer l'id du magasin du vendeur│");
        System.out.println("│ Q - revenir en arriere            │");
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
        }
    }

    public static void creeCompteVendeurValider(ConnexionMySQL con, String Prenom, String Nom, int idmag){
        System.out.println("┌───────────────────────────────────┐");        
        System.out.println("│ Creer un compte vendeur:          │");
        System.out.println("│ Confirmer vous l'ajout du vendeur │");
        System.out.println("└───────────────────────────────────┘"); 
        System.out.println( Nom + " " + Prenom + " au magasin d'id "+ idmag );
        System.out.println("┌───────────────────────────────────┐"); 
        System.out.println("│ C - confirmer                     │");
        System.out.println("│ Q - retour en arriere             │");
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
        System.out.println("│ Q - revenir en arriere         │");
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
        System.out.println("│ Q - revenir en arriere                         │");
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
        System.out.println("│ C - confirmer                     │");
        System.out.println("│ Q - retour en arriere             │");
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
    } //Fin deuxieme partie 
}
