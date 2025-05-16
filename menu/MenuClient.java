import java.util.Scanner;

public class MenuClient {
    
    
    public static void menuClient(){
        System.out.println("┌─────────────────────────────────────────┐");        
        System.out.println("│  Vous êtes connectés en tant que Client │");
        System.out.println("│         Que voulez vous faire?          │");
        System.out.println("│ 1:  voir vos livres recommandés         │");
        System.out.println("│ 2:  consulter le catalogue              │");
        System.out.println("│ 3:  passer une commande                 │");
        System.out.println("│  Appuyez sur Q pour revenir en arriere  │");
        System.out.println("└─────────────────────────────────────────┘");   
        Scanner scan = new Scanner(System.in);
        String action = scan.nextLine();
        switch (action) {
            case "Q":
            case "q":{
                System.out.println("Vous retournez au menu principal");
                ExecutableMenu.menuPrincipal();
                break;}          
            case "1":
                MenuClient.sousMenuLivreRecommande();
                break;
            case "2":
                MenuClient.sousMenuConsulterCatalogue();;
                break;
            case "3":
                MenuClient.sousMenuPasserUneCommande();
                break;
            default:
            System.out.println("Veuillez rentrer une commande valide");
            MenuClient.menuClient();        
                break;
        }
    }  

    //A finir
    public static void sousMenuLivreRecommande(){
        System.out.println("┌───────────────────────────────────────────────┐");        
        System.out.println("│    Vos livres recommandés sont les suivant    │");
        System.out.println("│         [Inserer livres recommande]           │");
        System.out.println("│  Appuyez sur Q pour revenir en arriere        │");
        System.out.println("│  Appuyez sur M pour revenir au menu principal │");
        System.out.println("└───────────────────────────────────────────────┘"); 
        Scanner scan = new Scanner(System.in);
        String action = scan.nextLine();
        switch (action) {
            case "Q":
            case "q":{
                System.out.println("Vous retournez au menu Client");
                 MenuClient.menuClient();
                break;}          
            case "M":
            case "m":{
                System.out.println("Vous retournez au menu principal");
                ExecutableMenu.menuPrincipal();
                break;}
            default:
            System.out.println("Veuillez rentrer une commande valide");
            MenuClient.sousMenuLivreRecommande();           
                break;
        }
        
        
    }
    //A finir
    public static void sousMenuConsulterCatalogue(){
        System.out.println("┌───────────────────────────────────────────────┐");        
        System.out.println("│  Vous êtes sur le menu catalogue de livres    │");
        System.out.println("│           [Inserer catalogues livres]         │");
        System.out.println("│  Appuyez sur Q pour revenir en arriere        │");
        System.out.println("│  Appuyez sur M pour revenir au menu principal │");
        System.out.println("└───────────────────────────────────────────────┘"); 
        Scanner scan = new Scanner(System.in);
        String action = scan.nextLine();
        switch (action) {
            case "Q":
            case "q":{
                System.out.println("Vous retournez au menu Client");
                 MenuClient.menuClient();
                break;}          
            case "M":
            case "m":{
                System.out.println("Vous retournez au menu principal");
                ExecutableMenu.menuPrincipal();
                break;}
            default:
            System.out.println("Veuillez rentrer une commande valide");
            MenuClient.sousMenuConsulterCatalogue();           
                break;
        }
    }
    //A finir
    public static void sousMenuPasserUneCommande(){
        System.out.println("┌───────────────────────────────────────────────┐");        
        System.out.println("│       Vous êtes sur le menu de commande       │");
        System.out.println("│              [A faire]                        │");
        System.out.println("│  Appuyez sur Q pour revenir en arriere        │");
        System.out.println("│  Appuyez sur M pour revenir au menu principal │");
        System.out.println("└───────────────────────────────────────────────┘"); 
        Scanner scan = new Scanner(System.in);
        String action = scan.nextLine();
        switch (action) {
            case "Q":
            case "q":{
                System.out.println("Vous retournez au menu Client");
                 MenuClient.menuClient();
                break;}          
            case "M":
            case "m":{
                System.out.println("Vous retournez au menu principal");
                ExecutableMenu.menuPrincipal();
                break;}
            default:
            System.out.println("Veuillez rentrer une commande valide");
            MenuClient.sousMenuPasserUneCommande();           
                break;
        }
    }
}
