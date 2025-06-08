package menu;

import java.sql.SQLException;
import java.util.Scanner;

import BD.ConnexionMySQL;
public class ExecutableMenu{


    public static void menuPrincipal(){
        try{
            ConnexionMySQL con = new ConnexionMySQL();
            con.connecter("localhost", "java");

            System.out.println("┌──────────────────────────────────────────┐");        
            System.out.println("│ Bienvenue sur l'application Vallé Libre  │");
            System.out.println("│ 1:pour vous connecter en tant que Client │");
            System.out.println("│ 2:pour vous connecter en tant que Vendeur│");
            System.out.println("│ 3:pour vous connecter en tant qu'Admin   │");
            System.out.println("│      Rentrez Q pour quitter l'appli      │");
            System.out.println("└──────────────────────────────────────────┘");   

            Scanner scan = new Scanner(System.in);
            String  action = scan.nextLine();
            switch (action) {
                case "Q":
                case "q":{
                    System.out.println("Vous quitter l'application");
                    break;}          
                case "1":
                    MenuClient.connexionClient(con);
                    break;
                case "2":
                    MenuVendeur.menuVendeur(con);
                    break;
                case "3":
                    MenuAdmin.menuAdmin(con);
                    break;
                default:
                System.out.println("Veuillez rentrer une commande valide");           
                    break;
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        catch (ClassNotFoundException ex){
            System.out.println("Driver MySQL non trouvé!!!");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        
        ExecutableMenu.menuPrincipal();
    }
}
    
