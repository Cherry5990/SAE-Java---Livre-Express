package menu;
import BD.*;
import java.util.Scanner;

public class MenuClient {
    private static final Scanner scan = new Scanner(System.in); // Scanner unique
    
    public static void menuClient(ConnexionMySQL con){
        MagasinBD magBD = new MagasinBD(con);
        System.out.println("┌─────────────────────────────────────────┐");        
        System.out.println("│  Vous êtes connectés en tant que Client │");
        System.out.println("│         Que voulez vous faire?          │");
        System.out.println("│ 1:  voir vos livres recommandés         │");
        System.out.println("│ 2:  consulter le catalogue              │");
        System.out.println("│ 3:  passer une commande                 │");
        System.out.println("│    Rentrez Q pour revenir en arriere    │");
        System.out.println("└─────────────────────────────────────────┘");   
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "Q":
            case "q":{
                System.out.println("Vous retournez au menu principal");
                ExecutableMenu.menuPrincipal();
                break;}          
            case "1":
                MenuClient.sousMenuLivreRecommande(con);
                break;
            case "2":
                MenuClient.sousMenuConsulterCatalogue(con,magBD);
                break;
            case "3":
                MenuClient.sousMenuPasserUneCommande(con);
                break;
            default:
            System.out.println("Veuillez rentrer une commande valide");
            MenuClient.menuClient(con);        
                break;
        }
    }  

    //A finir
    public static void sousMenuLivreRecommande(ConnexionMySQL con){
        System.out.println("┌───────────────────────────────────────────────┐");        
        System.out.println("│    Vos livres recommandés sont les suivant    │");
        System.out.println("│         [Inserer livres recommande]           │");
        System.out.println("│       Rentrez Q pour revenir en arriere       │");
        System.out.println("│    Rentrez M pour revenir au menu principal   │");
        System.out.println("└───────────────────────────────────────────────┘"); 
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "Q":
            case "q":{
                System.out.println("Vous retournez au menu Client");
                 MenuClient.menuClient(con);
                break;}          
            case "M":
            case "m":{
                System.out.println("Vous retournez au menu principal");
                ExecutableMenu.menuPrincipal();
                break;}
            default:
            System.out.println("Veuillez rentrer une commande valide");
            MenuClient.sousMenuLivreRecommande(con);           
                break;
        }
    }

    public static Integer chosirUnMagasin(ConnexionMySQL con,MagasinBD magBD){
        System.out.println(magBD.afficheMagasins());
        System.out.println("Veuillez saisir le numéro du magasin :");
        String action = scan.nextLine().toLowerCase().trim();
        Integer mag = null;
        if (Integer.parseInt(action)>0 && Integer.parseInt(action)<=magBD.maxIdMagasin()){
            mag = Integer.parseInt(action);
            System.out.println("Magsin choisi numéro "+mag);
        }
        return mag;
    }

    //A finir
    public static void sousMenuConsulterCatalogue(ConnexionMySQL con,MagasinBD magBD){
        int mag = chosirUnMagasin(con,magBD);
        System.out.println("┌───────────────────────────────────────────────┐");        
        System.out.println("│  Vous êtes sur le menu catalogue de livres    │");
        System.out.println(String.format("│           du magasin %-25s│", magBD.avoirNom(mag)));
        System.out.println("│           1: chercher un livre                │");
        System.out.println("│           2: parcourir les livres             │");
        System.out.println("│       Rentrez Q pour revenir en arriere       │");
        System.out.println("│    Rentrez M pour revenir au menu principal   │");
        System.out.println("└───────────────────────────────────────────────┘"); 
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "1":{
                //a faire
                break;
            }
            case "2":{
                MenuClient.sousMenuCatalogue(con, magBD,mag,0,10);
                break;
            }
            case "q":{
                System.out.println("Vous retournez au menu Client");
                 MenuClient.menuClient(con);
                break;
            }          
            case "m":{
                System.out.println("Vous retournez au menu principal");
                ExecutableMenu.menuPrincipal();
                break;
            }
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuClient.sousMenuConsulterCatalogue(con,magBD);           
                break;
        }
    }

    public static void sousMenuCatalogue(ConnexionMySQL con,MagasinBD magBD,int mag,int debut,int fin){
        System.out.println("┌───────────────────────────────────────────────┐");        
        System.out.println("│    Listes des livres                          │");
        System.out.println(magBD.voirStock(mag,debut,fin));
        System.out.println("│         "+debut+"-"+fin+"                       │");
        System.out.println("│       Rentrez C pour continuer                │");
        System.out.println("│       Rentrez R pour revenir en arrière       │");
        System.out.println("│       Rentrez Q pour revenir en arriere       │");
        System.out.println("└───────────────────────────────────────────────┘"); 
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "c":
                if(fin<magBD.maxPossederLivre(mag)){
                    MenuClient.sousMenuCatalogue(con,magBD,mag,debut+10,fin+10);
                }
                else{
                    System.out.println("Vous êtes déjà à la fin du catalogue.");
                    MenuClient.sousMenuCatalogue(con, magBD, mag, debut, fin);
                }
                break;
            case "r":
                if (debut>=10){
                    MenuClient.sousMenuCatalogue(con,magBD,mag,debut-10,fin-10);
                }
                else{
                    System.out.println("Vous êtes déjà au début du catalogue.");
                    MenuClient.sousMenuCatalogue(con, magBD, mag, debut, fin);
                }
                break;
            case "q":
                System.out.println("Vous retournez au menu Client");
                MenuClient.menuClient(con);
                break;      
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuClient.sousMenuCatalogue(con,magBD,mag,debut,fin);           
                break;
        }
    }

    //A finir
    public static void sousMenuPasserUneCommande(ConnexionMySQL con){
        System.out.println("┌───────────────────────────────────────────────┐");        
        System.out.println("│       Vous êtes sur le menu de commande       │");
        System.out.println("│              [A faire]                        │");
        System.out.println("│    Rentrez Q pour revenir en arriere          │");
        System.out.println("│    Rentrez M pour revenir au menu principal   │");
        System.out.println("└───────────────────────────────────────────────┘"); 
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "Q":
            case "q":{
                System.out.println("Vous retournez au menu Client");
                 MenuClient.menuClient(con);
                break;}          
            case "M":
            case "m":{
                System.out.println("Vous retournez au menu principal");
                ExecutableMenu.menuPrincipal();
                break;}
            default:
            System.out.println("Veuillez rentrer une commande valide");
            MenuClient.sousMenuPasserUneCommande(con);           
                break;
        }
    }


}
