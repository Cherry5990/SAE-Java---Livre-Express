import java.util.Scanner;
public class ExecutableMenu{


    public static void menuPrincipal(){
        System.out.println("┌──────────────────────────────────────────┐");        
        System.out.println("│ Bienvenue sur l'application Vallé Libre  │");
        System.out.println("│ 1:pour vous connecter en tant que Client │");
        System.out.println("│ 2:pour vous connecter en tant que Vendeur│");
        System.out.println("│ 3:pour vous connecter en tant qu'Admin   │");
        System.out.println("│  Appuyez sur Q pour quitter l'appli      │");
        System.out.println("└──────────────────────────────────────────┘");   
        
        Scanner scan = new Scanner(System.in);
        String  action = scan.nextLine();
        switch (action) {
            case "Q":
            case "q":{
                System.out.println("Vous quitter l'application");
                break;}          
            case "1":
                ExecutableMenu.menuClient();
                break;
            case "2":
                ExecutableMenu.menuVendeur();
                break;
            case "3":
                 System.out.println("A faire");
                break;
            default:
            System.out.println("Veuillez rentrer une commande valide");           
                break;
        }
    }


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
                ExecutableMenu.sousMenuLivreRecommande();
                break;
            case "2":
                ExecutableMenu.sousMenuConsulterCatalogue();;
                break;
            case "3":
                ExecutableMenu.sousMenuPasserUneCommande();
                break;
            default:
            System.out.println("Veuillez rentrer une commande valide");
            ExecutableMenu.menuClient();        
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
                 ExecutableMenu.menuClient();
                break;}          
            case "M":
            case "m":{
                System.out.println("Vous retournez au menu principal");
                ExecutableMenu.menuPrincipal();
                break;}
            default:
            System.out.println("Veuillez rentrer une commande valide");
            ExecutableMenu.sousMenuLivreRecommande();           
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
                 ExecutableMenu.menuClient();
                break;}          
            case "M":
            case "m":{
                System.out.println("Vous retournez au menu principal");
                ExecutableMenu.menuPrincipal();
                break;}
            default:
            System.out.println("Veuillez rentrer une commande valide");
            ExecutableMenu.sousMenuConsulterCatalogue();           
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
                 ExecutableMenu.menuClient();
                break;}          
            case "M":
            case "m":{
                System.out.println("Vous retournez au menu principal");
                ExecutableMenu.menuPrincipal();
                break;}
            default:
            System.out.println("Veuillez rentrer une commande valide");
            ExecutableMenu.sousMenuPasserUneCommande();           
                break;
        }
    }
    

    public static void menuVendeur(){
        System.out.println("┌─────────────────────────────────────────┐");        
        System.out.println("│ Vous êtes connectés en tant que Vendeur │");
        System.out.println("│         Que voulez vous faire?          │");
        System.out.println("│ 1: ajouté un livre au stocks            │");
        System.out.println("│ 2: mettre à jour la qte dispo d'un livre│");
        System.out.println("│ 3: verifier la disponibilité d'un livre │");
        System.out.println("│ 4: passer une commande pour un client   │");
        System.out.println("│ 5: transferer un livre                  │");
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
                System.out.println("A faire");
                ExecutableMenu.menuVendeur();
                break;
            case "2":
                System.out.println("A faire");
                ExecutableMenu.menuVendeur();
                break;
            case "3":
                System.out.println("A faire");
                ExecutableMenu.menuVendeur();
                break;
            case "4":
                System.out.println("A faire");
                ExecutableMenu.menuVendeur();
                break;
            case "5":
                System.out.println("A faire");
                ExecutableMenu.menuVendeur();
                break;

            default:
            System.out.println("Veuillez rentrer une commande valide");
            ExecutableMenu.menuClient();        
                break;
        }
    }  

    public static void menuAdmin(){
        System.out.println("┌─────────────────────────────────────────┐");        
        System.out.println("│ Vous êtes connectés en tant que Vendeur │");
        System.out.println("│         Que voulez vous faire?          │");
        System.out.println("│ 1: créer un compte vendeur              │");
        System.out.println("│ 2: ajouter une nouvelle librairie       │");
        System.out.println("│ 3: gérer les stocks globaux             │");
        System.out.println("│ 4: Consulter les statistiques de ventes │");
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
                System.out.println("A faire");
                ExecutableMenu.menuAdmin();
                break;
            case "2":
                System.out.println("A faire");
                ExecutableMenu.menuAdmin();
                break;
            case "3":
                System.out.println("A faire");
                ExecutableMenu.menuAdmin();
                break;
            case "4":
                System.out.println("A faire");
                ExecutableMenu.menuAdmin();
                break;
            default:
            System.out.println("Veuillez rentrer une commande valide");
            ExecutableMenu.menuClient();        
                break;
        }
    }  



    
    public static void main(String[] args) {
        
        ExecutableMenu.menuPrincipal();
    }
}
    
