package menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import BD.ConnexionMySQL;

public class ExecutableMenu{

    public static void connexionBD(){
        System.out.println("┌──────────────────────────────────────────┐"); 
        System.out.println("│ Bienvenue sur l'application Vallé Libre  │");
        System.out.println("│ Veuillez rentrer nom d'utilisateur mysql │");
        System.out.println("└──────────────────────────────────────────┘");   
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try{
        String entre = reader.readLine();
        ExecutableMenu.connexionBD2(entre);
        }
        catch(IOException e){
            System.out.println("Veuillez rentrer une commande valide");
            ExecutableMenu.connexionBD();
        }
    }

    public static void connexionBD2(String user){
        System.out.println("┌────────────────────────────────────────────────┐"); 
        System.out.println("│ Veuillez maintenant rentrer votre mot de passe │");
        System.out.println("└────────────────────────────────────────────────┘");   
        
        try{
        ConnexionMySQL con = new ConnexionMySQL();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String entre = reader.readLine();
        con.connecter("localhost", "sae_java", user, entre);
        ExecutableMenu.menuPrincipal(con);
        }
        catch(IOException e){
            System.out.println("Veuillez rentrer une commande valide");
            ExecutableMenu.connexionBD();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
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
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try{
        String entre = reader.readLine();
        switch (entre) {
            case "q":{
                System.out.println("Vous quitter l'application");
                break;}          
            case "1":
                MenuClient.connexionClient(con);
                break;
            case "2":
                System.out.println("A faire");
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
        catch(IOException e){
            System.out.println("Veuillez rentrer une commande valide");
            menuPrincipal(con);
        }
    }

    public static void main(String[] args) {
        
        ExecutableMenu.connexionBD();
    }
}
    
