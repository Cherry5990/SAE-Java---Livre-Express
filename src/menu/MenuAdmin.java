package menu;

import java.util.Scanner;

import BD.ConnexionMySQL;

public class MenuAdmin {
    public static void menuAdmin(ConnexionMySQL con){
        System.out.println("┌─────────────────────────────────────────┐");        
        System.out.println("│ Vous êtes connectés en tant que Vendeur │");
        System.out.println("│         Que voulez vous faire?          │");
        System.out.println("│ 1: créer un compte vendeur              │");
        System.out.println("│ 2: ajouter une nouvelle librairie       │");
        System.out.println("│ 3: gérer les stocks globaux             │");
        System.out.println("│ 4: Consulter les statistiques de ventes │");
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
                MenuAdmin.menuAdmin(con);
                break;
            case "2":
                System.out.println("A faire");
                MenuAdmin.menuAdmin(con);
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
}
