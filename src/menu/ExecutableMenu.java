package menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

import BD.ConnexionMySQL;

public class ExecutableMenu{

    private static final Scanner scan = new Scanner(System.in);

    public static void connexionBD(){
        System.out.println("┌──────────────────────────────────────────┐"); 
        System.out.println("│ Bienvenue sur l'application Vallé Libre  │");
        System.out.println("│ Veuillez rentrer nom d'utilisateur mysql │");
        System.out.println("└──────────────────────────────────────────┘");   
        String action = scan.nextLine();
        ExecutableMenu.connexionBD2(action);
    }

    public static void connexionBD2(String user){
        System.out.println("┌────────────────────────────────────────────────┐"); 
        System.out.println("│ Veuillez maintenant rentrer votre mot de passe │");
        System.out.println("└────────────────────────────────────────────────┘");   
        try{
        ConnexionMySQL con = new ConnexionMySQL();
        String action = scan.nextLine();
        con.connecter("localhost", "sae_java", user, action);
        ExecutableMenu.menuPrincipal(con);
        }
        catch (SQLException e){
            System.out.println("─────────────────────────────────────────────────────────");
            System.out.println("Votre nom utilisateur ou votre mot de passe est incorrect");
            System.out.println("Veuillez reessayer");
            ExecutableMenu.connexionBD();
        }
        catch (ClassNotFoundException ex){
            System.out.println("Driver MySQL non trouvé!!!");
            System.exit(1);
        }
    }

    public static void menuPrincipal(ConnexionMySQL con){
        System.out.println("┌──────────────────────────────────────────┐");        
        System.out.println("│ Bienvenue sur l'application Vallé Libre  │");
        System.out.println("│   1 - connexion Client                   │");
        System.out.println("│   2 - connexion Vendeur                  │");
        System.out.println("│   3 - connexion Admin                    │");
        System.out.println("│   Q - quitter l'appli                    │");
        System.out.println("└──────────────────────────────────────────┘");   
        String action = scan.nextLine();;
        switch (action) {
            case "q":{
                System.out.println("Vous quitter l'application");
                break;}          
            case "1":
                MenuClient.connexionClient(con);
                break;
            case "2":
                MenuVendeur.connexionVendeur(con);
                break;
            case "3":
                System.out.println("A faire");
                break;
            default:
                System.out.println("Veuillez rentrer une commande valide");     
                menuPrincipal(con);      
                break;
            }
    }

    public static void main(String[] args) {
        
        ExecutableMenu.connexionBD();
    }
}
    
