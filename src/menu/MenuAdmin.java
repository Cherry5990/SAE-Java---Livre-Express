package menu;

import java.util.Scanner;

import BD.AdminBD;
import BD.ConnexionMySQL;
import BD.MagasinBD;
import BD.VendeurBD;
import modele.Admin;

public class MenuAdmin {
    private static final Scanner scan = new Scanner(System.in, "UTF-8"); // Scanner unique
    private static MagasinBD magasinBD;
    private static VendeurBD vendeurBD;
    private static AdminBD adminBD;

    // Menu de connexion en tant qu'Admin
    public static void connexionAdmin(ConnexionMySQL con){
        System.out.println("┌────────────────────────────────────────────────────────────┐");        
        System.out.println("│ Etes vous sure de vouloir vous connecter en tant qu'Admin? │");
        System.out.println("│ [C] continuer  [Q] retour en arriere                       │");
        System.out.println("└────────────────────────────────────────────────────────────┘");
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "c":
                MenuAdmin.magasinBD = new MagasinBD(con);
                MenuAdmin.vendeurBD = new VendeurBD(con);
                MenuAdmin.adminBD = new AdminBD(con);
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
                break;
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
    }
    //Fin deuxieme partie 

    //Quatrieme Partie - consulter les statistique de vente

    public static void sousMenuStatsDeVente(ConnexionMySQL con){
        System.out.println("┌──────────────────────────────────────────────────────┐");        
        System.out.println("│ Menu statistiques de vente                           │");
        System.out.println("│ Quelle stat voulez vous consulter                    │");
        System.out.println("│ 1 - chiffre d'affaire                                │");
        System.out.println("│ 2 - livres les plus vendus                           │");
        System.out.println("│ 3 - Comparaison ventes en ligne et ventes en magasin │");
        System.out.println("│ 4 - Valeur du stock par magasin                      │");
        System.out.println("│ Q - revenir en arriere                               │");
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
        System.out.println("│ 1 - CA total tout les temps       │");
        System.out.println("│ 2 - CA total par annee            │");
        System.out.println("│ 3 - CA par magasin tout les temps │");
        System.out.println("│ 4 - CA par magasin par annee      │");
        System.out.println("│ Q - revenir en arriere            │");
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
                System.out.println("│ Rentrer l'annee souhaité  │");
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
        System.out.println("│ 1 - total tout les temps       │");
        System.out.println("│ 2 - total par annee            │");
        System.out.println("│ 3 - par magasin tout les temps │");
        System.out.println("│ 4 - par magasin par annee      │");
        System.out.println("│ Q - revenir en arriere         │");
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
        System.out.println("│ 1 - tout les temps                │");
        System.out.println("│ 2 - par annee                     │");
        System.out.println("│ 3 - par magasin tout les temps    │");
        System.out.println("│ 4 - par magasin par annee         │");
        System.out.println("│ Q - revenir en arriere            │");
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
                System.out.println("│ Rentrer l'annee souhaité  │");
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

}

