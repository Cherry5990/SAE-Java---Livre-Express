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
        if (action.equals("Q") || action.equals("q")){System.out.println("Vous quitter l'application");}
        else if (action.equals("1") || action.equals("&")){
            ExecutableMenu.menuClient();
        }
        else if (action.equals("2")|| action.equals("é")){
            ExecutableMenu.menuVendeur();
        }

        else if (action.equals("3") || action.equals(String.valueOf('"'))){
            System.out.println("A faire");
            ExecutableMenu.menuPrincipal();
        }
        else{
            System.out.println("Veuillez rentrer une commande valide");
            ExecutableMenu.menuPrincipal();
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

        if(action.equals("Q") || action.equals("q")){
            ExecutableMenu.menuPrincipal();
        }
        else if (action.equals("1") || action.equals("&")){
            ExecutableMenu.sousMenuLivreRecommande();
        }                
        else if (action.equals("2")|| action.equals("é")){
            ExecutableMenu.sousMenuConsulterCatalogue();
        }
        else if (action.equals("3") || action.equals(String.valueOf('"'))){
            ExecutableMenu.sousMenuPasserUneCommande();
        }
        else{
            System.out.println("Veuillez rentrer une commande valide");
            ExecutableMenu.menuClient();                   
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
        if(action.equals("Q") || action.equals("q")){
            ExecutableMenu.menuClient();
        }
        else if(action.equals("M") || action.equals("m")){
            ExecutableMenu.menuPrincipal();
        }
        else {   
            System.out.println("Veuillez rentrer une commande valide");
            ExecutableMenu.sousMenuLivreRecommande();                   
        }
    }
    //A finir
    public static void sousMenuConsulterCatalogue(){
        System.out.println("┌───────────────────────────────────────────────┐");        
        System.out.println("│          Voici le catalogue de livres         │");
        System.out.println("│           [Inserer catalogues livres]         │");
        System.out.println("│  Appuyez sur Q pour revenir en arriere        │");
        System.out.println("│  Appuyez sur M pour revenir au menu principal │");
        System.out.println("└───────────────────────────────────────────────┘"); 
        Scanner scan = new Scanner(System.in);
        String action = scan.nextLine();
        if(action.equals("Q") || action.equals("q")){
            ExecutableMenu.menuClient();
        }
        else if(action.equals("M") || action.equals("m")){
            ExecutableMenu.menuPrincipal();
        }
        else {   
            System.out.println("Veuillez rentrer une commande valide");
            ExecutableMenu.sousMenuLivreRecommande();                   
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
        if(action.equals("Q") || action.equals("q")){
            ExecutableMenu.menuClient();
        }
        else if(action.equals("M") || action.equals("m")){
            ExecutableMenu.menuPrincipal();
        }
        else {   
            System.out.println("Veuillez rentrer une commande valide");
            ExecutableMenu.sousMenuLivreRecommande();                   
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

        if(action.equals("Q") || action.equals("q")){
            ExecutableMenu.menuPrincipal();
        }
        else if (action.equals("1") || action.equals("&")){
            System.out.println("A faire");
            ExecutableMenu.menuVendeur();
        }                
        else if (action.equals("2")|| action.equals("é")){
            System.out.println("A faire");
            ExecutableMenu.menuVendeur();
        }
        else if (action.equals("3") || action.equals(String.valueOf('"'))){
            System.out.println("A faire");
            ExecutableMenu.menuVendeur();
        }
        else if (action.equals("4")|| action.equals("'")){
            System.out.println("A faire");
            ExecutableMenu.menuVendeur();
        }
        else if (action.equals("5")|| action.equals("(")){
            System.out.println("A faire");
            ExecutableMenu.menuVendeur();
        }
        else{
            System.out.println("Veuillez rentrer une commande valide");
            ExecutableMenu.menuVendeur();                      
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

        if(action.equals("Q") || action.equals("q")){
            ExecutableMenu.menuPrincipal();
        }
        else if (action.equals("1") || action.equals("&")){
            System.out.println("A faire");
            ExecutableMenu.menuAdmin();
        }                
        else if (action.equals("2")|| action.equals("é")){
            System.out.println("A faire");
            ExecutableMenu.menuAdmin();
        }
        else if (action.equals("3") || action.equals(String.valueOf('"'))){
            System.out.println("A faire");
            ExecutableMenu.menuAdmin();
        }
        else if (action.equals("4")|| action.equals("'")){
            System.out.println("A faire");
            ExecutableMenu.menuAdmin();
        }
        else if (action.equals("5")|| action.equals("(")){
            System.out.println("A faire");
            ExecutableMenu.menuAdmin();
        }
        else{
            System.out.println("Veuillez rentrer une commande valide");
            ExecutableMenu.menuAdmin();                      
        }
    }  



    
    public static void main(String[] args) {
        
        ExecutableMenu.menuPrincipal();
    }
}
    
