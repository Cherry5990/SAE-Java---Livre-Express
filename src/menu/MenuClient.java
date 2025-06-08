package menu;
import BD.*;
import modele.Client;
import modele.Commande;
import modele.Magasin;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuClient {
    private static final Scanner scan = new Scanner(System.in); // Scanner unique

    private static Commande commande = null;

    public static void connexionClient(ConnexionMySQL con){
        System.out.println("┌───────────────────────────────┐");
        System.out.println("│Veuillez saisir un id de Client│");
        System.out.println("└───────────────────────────────┘"); 
        String action = scan.nextLine().trim();
        ClientBD clientBD = new ClientBD(con);
        Client client = null;
        // Vérifie si 'action' est un nombre entier
        try {
            client = clientBD.getClient(Integer.parseInt(action));
        }
        catch(Exception e){
            System.out.println("Veuillez rentrer un nombre");
            MenuClient.connexionClient(con);
        }

        System.out.println("Etes vous bien " + client.getPrenom() + " " + client.getNom()+"?");
        System.out.println("[C] Confirmer    [N] Non");
        System.out.println("[M] Menu principale");
        String action2 = scan.nextLine().toLowerCase().trim();
        switch (action2) {
            case "c":
                MenuClient.menuClient(con,client);
                break;        
            case "n":
                MenuClient.connexionClient(con);
                break;
            case "m":
                ExecutableMenu.menuPrincipal(con);
                break;
            default:
            System.out.println("Veuillez rentrer une commande valide");
            MenuClient.connexionClient(con);        
                break;
        }
    }




    
    public static void menuClient(ConnexionMySQL con,Client client){
        MagasinBD magBD = new MagasinBD(con);
        System.out.println("┌─────────────────────────────────────────┐");        
        System.out.println("│  Vous êtes connectés en tant que Client │");
        System.out.println("│         Que voulez vous faire?          │");
        System.out.println("│ 1 - voir vos livres recommandés         │");
        System.out.println("│ 2 - se connecter à un magasin           │");
        System.out.println("│ Q - revenir en arrière                  │");
        System.out.println("│ M - revenir au menu principal           │");
        System.out.println("└─────────────────────────────────────────┘");   
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "m":
                System.out.println("Vous retournez au menu principal");
                ExecutableMenu.menuPrincipal(con);
                break; 
            case "q":
                System.out.println("Vous retournez au menu de connexion pour client");
                MenuClient.connexionClient(con);
                break;        
            case "1":
                MenuClient.sousMenuLivreRecommande(con,client);
                break;
            case "2":
                Integer mag = MenuClient.chosirUnMagasin(con, magBD);
                MenuClient.sousMenuMagasin(con, client,magBD,mag);
                break;
            default:
            System.out.println("Veuillez rentrer une commande valide");
            MenuClient.menuClient(con,client);        
                break;
        }
    }

    //A finir
    public static void sousMenuLivreRecommande(ConnexionMySQL con,Client client){
        System.out.println("┌───────────────────────────────────────────────┐");        
        System.out.println("│    Vos livres recommandés sont les suivant    │");
        System.out.println("│         [Inserer livres recommande]           │");
        System.out.println("│       Rentrez Q pour revenir en arriere       │");
        System.out.println("│    Rentrez M pour revenir au menu principal   │");
        System.out.println("└───────────────────────────────────────────────┘"); 
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "q":
                System.out.println("Vous retournez au menu Client");
                MenuClient.menuClient(con,client);
                break;          
            case "m":
                System.out.println("Vous retournez au menu principal");
                ExecutableMenu.menuPrincipal(con);
                break;
            default:
            System.out.println("Veuillez rentrer une commande valide");
            MenuClient.sousMenuLivreRecommande(con,client);           
                break;
        }
    }
    

    public static Integer chosirUnMagasin(ConnexionMySQL con,MagasinBD magBD){
        System.out.println(magBD.afficheMagasins());
        System.out.println("┌───────────────────────────────────────────┐"); 
        System.out.println("│   Veuillez saisir le numéro du magasin:   │");
        System.out.println("└───────────────────────────────────────────┘"); 
        String action = scan.nextLine().toLowerCase().trim();
        Integer mag = null;
        if (Integer.parseInt(action)>0 && Integer.parseInt(action)<=magBD.maxIdMagasin()){
            mag = Integer.parseInt(action);
            System.out.println("Magasin choisi numéro "+mag);
        }
        return mag;
    }

    //A finir
    public static void sousMenuMagasin(ConnexionMySQL con,Client client,MagasinBD magBD,int mag){
        System.out.println("┌─────────────────────────────────────────────┐");        
        System.out.println("│        Vous êtes connectés au magasin:      │");
        System.out.println("│           "+magBD.avoirNom(mag)+"           │");
        System.out.println("│   1 - Consulter le catalogue                │");
        System.out.println("│   2 - Chercher un livre                     │");
        System.out.println("│   3 - Passer une commande                   │");
        System.out.println("│   Q - Retour au menu client                 │");
        System.out.println("└─────────────────────────────────────────────┘"); 
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "2":
                MenuClient.sousMenuRecherche(con,client,magBD,mag);
                break;
            case "1":
                MenuClient.sousMenuCatalogue(con, client,magBD,mag,0,10);
                break;
            case "3":
                MenuClient.sousMenuPasserUneCommande(con,client,magBD,mag);
                break;
            case "q":
                System.out.println("Vous retournez au menu Client");
                 MenuClient.menuClient(con,client);
                break;       
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuClient.sousMenuMagasin(con,client, magBD, mag);          
                break;
        }
    }

    public static void sousMenuCatalogue(ConnexionMySQL con,Client client,MagasinBD magBD,int mag,int debut,int fin){
        System.out.println("┌────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                   Catalogue des livres                             │");
        System.out.println("├────────────────────────────────────────────────────────────────────┤");
        System.out.println(magBD.voirStock(mag, debut, fin));
        System.out.println("├────────────────────────────────────────────────────────────────────┤");
        System.out.println("  Affichage : " + (debut + 1) + " - " + fin + "                 ");
        System.out.println("  [C] Page suivante   [R] Page précédente   [Q] Retour        ");
        System.out.println("└────────────────────────────────────────────────────────────────────┘");
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "c":
                if(fin<magBD.maxPossederLivre(mag)){
                    MenuClient.sousMenuCatalogue(con,client,magBD,mag,debut+10,fin+10);
                }
                else{
                    System.out.println("Vous êtes déjà à la fin du catalogue.");
                    MenuClient.sousMenuCatalogue(con, client,magBD, mag, debut, fin);
                }
                break;
            case "r":
                if (debut>=10){
                    MenuClient.sousMenuCatalogue(con,client,magBD,mag,debut-10,fin-10);
                }
                else{
                    System.out.println("Vous êtes déjà au début du catalogue.");
                    MenuClient.sousMenuCatalogue(con,client, magBD, mag, debut, fin);
                }
                break;
            case "q":
                System.out.println("Vous retournez au menu Client");
                MenuClient.sousMenuMagasin(con,client,magBD,mag);
                break;      
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuClient.sousMenuCatalogue(con,client,magBD,mag,debut,fin);           
                break;
        }
    }

    //A finir
    public static void sousMenuPasserUneCommande(ConnexionMySQL con,Client client,MagasinBD magBD,int mag){
        try{
            commande = new Commande(mag, null, false, false, client, magBD.getMagasin(mag));
        }
        catch(SQLException e){
            System.out.println("problème de magasin"+e.getMessage());
            MenuClient.sousMenuMagasin(con, client, magBD, mag);
            return;
        }
        System.out.println("┌───────────────────────────────────────────────┐");        
        System.out.println("│             Passer une commande               │");
        System.out.println("│   1 - Ajouter un livre au panier              │");
        System.out.println("│   2 - Voir le panier                          │");
        System.out.println("│   3 - Valider la commande                     │");
        System.out.println("│   Q - Retour au menu magasin                  │");
        System.out.println("└───────────────────────────────────────────────┘"); 
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "1":
                
                break;       
            case "2":

                break;
            case "3":

                break;
            case "q":
                MenuClient.sousMenuMagasin(con,client,magBD,mag);
                break;
            default:
            System.out.println("Veuillez rentrer une commande valide");
            MenuClient.sousMenuPasserUneCommande(con,client, magBD, mag);         
                break;
        }
    }

    public static void sousMenuRecherche(ConnexionMySQL con,Client client,MagasinBD magBD,int mag){
        System.out.println("┌────────────────────────────────────────────┐"); 
        System.out.println("│Tapez le nom d'un livre ou juste une partie:│"); 
        System.out.println("└────────────────────────────────────────────┘");
        
        String action = scan.nextLine().toLowerCase().trim();


        System.out.println("┌───────────────────────────────────────────────┐");        
        System.out.println("│    Listes des livres                          │");
        System.out.println("└───────────────────────────────────────────────┘");
        System.out.println(magBD.rechercheLivre(mag,action));
        System.out.println("┌───────────────────────────────────────────────┐");
        System.out.println("│  C - nouvelle recherche                       │");
        System.out.println("│  Q - revenir en arriere                       │");
        System.out.println("└───────────────────────────────────────────────┘");
        String action2 = scan.nextLine().toLowerCase().trim();
        switch (action2) {
            case "c":{
                MenuClient.sousMenuRecherche(con,client, magBD, mag);
                break;}          
            case "q":{
                System.out.println("Vous retournez au menu principal");
                MenuClient.sousMenuMagasin(con,client,magBD,mag);
                break;}
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuClient.sousMenuRecherche(con,client, magBD, mag);        
                break;
        }
    }


}
