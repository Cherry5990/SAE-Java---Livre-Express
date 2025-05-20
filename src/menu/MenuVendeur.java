package menu;

import java.util.Scanner;

public class MenuVendeur {
    
    
    public static void menuVendeur(){
        System.out.println("┌─────────────────────────────────────────┐");        
        System.out.println("│ Vous êtes connectés en tant que Vendeur │");
        System.out.println("│         Que voulez vous faire?          │");
        System.out.println("│ 1: ajouté un livre au stocks            │");
        System.out.println("│ 2: mettre à jour la qte dispo d'un livre│");
        System.out.println("│ 3: verifier la disponibilité d'un livre │");
        System.out.println("│ 4: passer une commande pour un client   │");
        System.out.println("│ 5: transferer un livre                  │");
        System.out.println("│    Rentrez Q pour revenir en arriere    │");
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
                MenuVendeur.menuVendeur();
                break;
            case "2":
                System.out.println("A faire");
                MenuVendeur.menuVendeur();
                break;
            case "3":
                System.out.println("A faire");
                MenuVendeur.menuVendeur();
                break;
            case "4":
                System.out.println("A faire");
                MenuVendeur.menuVendeur();
                break;
            case "5":
                System.out.println("A faire");
                MenuVendeur.menuVendeur();
                break;

            default:
            System.out.println("Veuillez rentrer une commande valide");
            MenuClient.menuClient();        
                break;
        }
    }  

    public static void sousMenuAjouterLivre(){
        System.out.println("┌─────────────────────────────────────────────┐");        
        System.out.println("│ Vous êtes sur le menu d'ajout de livre      │");
        System.out.println("│  Rentrez 1 pour lancer la procédure d'ajout │");
        System.out.println("│   Attention, une fois la procédure lancer   │");
        System.out.println("│       vous ne pourrez pas la quitter        │");
        System.out.println("│      Rentrez Q pour revenir en arriere      │");
        System.out.println("└─────────────────────────────────────────────┘");   
        Scanner scan = new Scanner(System.in);
        String action = scan.nextLine();
        switch (action) {
            case "Q":
            case "q":{
                System.out.println("Vous retournez au menu principal");
                ExecutableMenu.menuPrincipal();
                break;}          
            default:
            System.out.println("Veuillez rentrer une commande valide");
            MenuClient.menuClient();        
                break;
        }
    }





}
