
import java.util.Scanner;
public class ExecutableMenu{


    public static void menuPrincipal(){
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
                MenuClient.menuClient();
                break;
            case "2":
                MenuVendeur.menuVendeur();
                break;
            case "3":
                MenuAdmin.menuAdmin();
                break;
            default:
            System.out.println("Veuillez rentrer une commande valide");           
                break;
        }
    }

    public static void main(String[] args) {
        
        ExecutableMenu.menuPrincipal();
    }
}
    
