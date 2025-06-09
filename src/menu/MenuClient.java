package menu;
import BD.*;
import modele.Client;
import modele.Commande;
import modele.CommandeTest;
import modele.DetailCommande;
import modele.Livre;
import modele.Magasin;
import modele.Posseder;
import modele.Reseau;

import java.awt.Menu;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuClient {
    private static final Scanner scan = new Scanner(System.in); // Scanner unique

    private static CommandeTest commande = new CommandeTest(0, null, false, false, null, null);
    private static Client client = null;
    private static Reseau reseau = new Reseau();

    public static void connexionClient(ConnexionMySQL con){
        System.out.println("┌───────────────────────────────┐");
        System.out.println("│Veuillez saisir un id de Client│");
        System.out.println("└───────────────────────────────┘"); 
        String action = scan.nextLine().trim();
        ClientBD clientBD = new ClientBD(con);

        //Temporaire
        ReseauBD reseauBD = new ReseauBD(con);
        reseauBD.ajouterMagasins(MenuClient.reseau);
        if(commande.getReseau() == null){
            commande.setReseau(MenuClient.reseau);
        }


        // Vérifie si 'action' est un nombre entier
        try {
            MenuClient.client = clientBD.getClient(Integer.parseInt(action));
            commande.setClient(MenuClient.client);
        }
        catch(Exception e){
            System.out.println("Veuillez rentrer un nombre");
            MenuClient.connexionClient(con);
        }

        System.out.println("Etes vous bien " + MenuClient.client.getPrenom() + " " + MenuClient.client.getNom()+"?");
        System.out.println("[C] Confirmer    [N] Non");
        System.out.println("[M] Menu principale");
        String action2 = scan.nextLine().toLowerCase().trim();
        switch (action2) {
            case "c":
                MenuClient.menuClient(con);
                break;        
            case "n":
                MenuClient.connexionClient(con);
                break;
            case "m":
                ExecutableMenu.menuPrincipal(con);
                break;
            default:
            System.out.println("Veuillez rentrer une commande valide");
            MenuClient.connexionClient(con);        
                break;
        }
    }




    
    public static void menuClient(ConnexionMySQL con){
        MagasinBD magBD = new MagasinBD(con);
        System.out.println("┌─────────────────────────────────────────┐");        
        System.out.println("│  Vous êtes connectés en tant que Client │");
        System.out.println("│         Que voulez vous faire?          │");
        System.out.println("│ 1 - voir vos livres recommandés         │");
        System.out.println("│ 2 - se connecter à un magasin           │");
        System.out.println("│ 3 - passer une commande/panier          │");
        System.out.println("│ Q - revenir en arrière                  │");
        System.out.println("│ M - revenir au menu principal           │");
        System.out.println("└─────────────────────────────────────────┘");   
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "m":
                System.out.println("Vous retournez au menu principal");
                ExecutableMenu.menuPrincipal(con);
                break; 
            case "q":
                System.out.println("Vous retournez au menu de connexion pour client");
                MenuClient.connexionClient(con);
                break;        
            case "1":
                MenuClient.sousMenuLivreRecommande(con);
                break;
            case "2":
                Integer mag = MenuClient.chosirUnMagasin(con, magBD);
                MenuClient.sousMenuMagasin(con,magBD,mag);
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
            case "q":
                System.out.println("Vous retournez au menu Client");
                MenuClient.menuClient(con);
                break;          
            case "m":
                System.out.println("Vous retournez au menu principal");
                ExecutableMenu.menuPrincipal(con);
                break;
            default:
            System.out.println("Veuillez rentrer une commande valide");
            MenuClient.sousMenuLivreRecommande(con);           
                break;
        }
    }
    

    public static Integer chosirUnMagasin(ConnexionMySQL con,MagasinBD magBD){
        System.out.println(magBD.afficheMagasins());
        System.out.println("┌───────────────────────────────────────────┐"); 
        System.out.println("│   Veuillez saisir le numéro du magasin:   │");
        System.out.println("└───────────────────────────────────────────┘"); 
        String action = scan.nextLine().toLowerCase().trim();
        Integer mag = null;
        if (Integer.parseInt(action)>0 && Integer.parseInt(action)<=magBD.maxIdMagasin()){
            mag = Integer.parseInt(action);
            System.out.println("Magasin choisi numéro "+mag);
        }
        return mag;
    }

    //A finir
    public static void sousMenuMagasin(ConnexionMySQL con,MagasinBD magBD,int mag){
        System.out.println("┌─────────────────────────────────────────────┐");        
        System.out.println("│        Vous êtes connectés au magasin:      │");
        System.out.println("│           "+magBD.avoirNom(mag)+"           │");
        System.out.println("│   1 - Consulter le catalogue                │");
        System.out.println("│   2 - Chercher un livre                     │");
        System.out.println("│   Q - Retour au menu client                 │");
        System.out.println("└─────────────────────────────────────────────┘"); 
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "2":
                MenuClient.sousMenuRecherche(con,magBD,mag);
                break;
            case "1":
                MenuClient.sousMenuCatalogue(con,magBD,mag,0,10);
                break;
            case "q":
                System.out.println("Vous retournez au menu Client");
                 MenuClient.menuClient(con);
                break;       
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuClient.sousMenuMagasin(con, magBD, mag);          
                break;
        }
    }

    public static void sousMenuCatalogue(ConnexionMySQL con,MagasinBD magBD,int mag,int debut,int fin){
        System.out.println("┌────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                   Catalogue des livres                             │");
        System.out.println("├────────────────────────────────────────────────────────────────────┤");
        System.out.println(magBD.voirStock(mag, debut, fin));
        System.out.println("├────────────────────────────────────────────────────────────────────┤");
        System.out.println("  Affichage : " + (debut + 1) + " - " + fin + "                 ");
        System.out.println("  [C] Page suivante   [R] Page précédente   [Q] Retour        ");
        System.out.println("└────────────────────────────────────────────────────────────────────┘");
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "c":
                if(fin<magBD.maxPossederLivre(mag)){
                    MenuClient.sousMenuCatalogue(con,magBD,mag,debut+10,fin+10);
                }
                else{
                    System.out.println("Vous êtes déjà à la fin du catalogue.");
                    MenuClient.sousMenuCatalogue(con,magBD, mag, debut, fin);
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
                MenuClient.sousMenuMagasin(con,magBD,mag);
                break;      
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuClient.sousMenuCatalogue(con,magBD,mag,debut,fin);           
                break;
        }
    }

    public static void sousMenuRecherche(ConnexionMySQL con,MagasinBD magBD,int mag){
        System.out.println("┌────────────────────────────────────────────┐"); 
        System.out.println("│Tapez le nom d'un livre ou juste une partie:│"); 
        System.out.println("└────────────────────────────────────────────┘");
        
        String action = scan.nextLine().toLowerCase().trim();


        System.out.println("┌───────────────────────────────────────────────┐");        
        System.out.println("│    Listes des livres                          │");
        System.out.println("└───────────────────────────────────────────────┘");
        System.out.println(magBD.rechercheLivre(mag,action));
        System.out.println("┌───────────────────────────────────────────────┐");
        System.out.println("│  C - nouvelle recherche                       │");
        System.out.println("│  Q - revenir en arriere                       │");
        System.out.println("└───────────────────────────────────────────────┘");
        String action2 = scan.nextLine().toLowerCase().trim();
        switch (action2) {
            case "c":{
                MenuClient.sousMenuRecherche(con, magBD, mag);
                break;}          
            case "q":{
                System.out.println("Vous retournez au menu principal");
                MenuClient.sousMenuMagasin(con,magBD,mag);
                break;}
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuClient.sousMenuRecherche(con, magBD, mag);        
                break;
        }
    }

    //A finir
    public static void sousMenuPasserUneCommande(ConnexionMySQL con){
        System.out.println("┌───────────────────────────────────────────────┐");        
        System.out.println("│             Passer une commande               │");
        System.out.println("│   1 - Ajouter un livre au panier              │");
        System.out.println("│   2 - Voir le panier                          │");
        System.out.println("│   3 - Valider la commande                     │");
        System.out.println("│   Q - Retour au menu Client                   │");
        System.out.println("└───────────────────────────────────────────────┘"); 
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "1":
                MenuClient.sousMenuAjouterUnLivreAuPanier(con);
                 
                break;       
            case "2":
                MenuClient.sousMenuVoirLePanier(con);

                break;
            case "3":
                MenuClient.sousMenuValiderLaCommande(con);
                break;
            case "q":
                MenuClient.menuClient(con);
                break;
            default:
            System.out.println("Veuillez rentrer une commande valide");
            MenuClient.sousMenuPasserUneCommande(con);         
                break;
        }
    }

    public static  void sousMenuAjouterUnLivreAuPanier(ConnexionMySQL con){
        CommandeBD cBD = new CommandeBD(con);
        System.out.println("┌──────────────────────────────────────────────────────────────┐");        
        System.out.println("│ Rentrer le nom du livre que vous souhaitez ajouter au panier │");
        System.out.println("│ Q - pour revenir en arrière                                  │");
        System.out.println("└──────────────────────────────────────────────────────────────┘"); 
        String action = scan.nextLine().trim();
        switch (action) {
            case "q":
                MenuClient.sousMenuPasserUneCommande(con);
                break;
            default:
            try {
                Livre livre = cBD.verifLivreExiste(action);
                
                if (livre == null){
                System.out.println("Navré, mais aucune des librairies de Vallé Livre n'a le livre que vous souhaité");
                String saut = scan.nextLine().trim();
                MenuClient.sousMenuAjouterUnLivreAuPanier(con);
                } 
                else {
                //System.out.println(livre.getTitre());
                String nomLivre = livre.getTitre();
                double prixLivre = livre.getPrix();
                System.out.println("Souhaitez-vous ajouter " + nomLivre + " qui coûte " + prixLivre + "euros");
                System.out.println("[C] Confirmer    [N'importe quelle touche] Non");

                String verif = scan.nextLine().toLowerCase().trim();
                switch (verif) {
                    case "c":
                    System.out.println("Combien d'exemplaire voulez-vous commander?\nAttention vous ne pouvez pas commander plus de 10 exemplaires");
                    String qte = scan.nextLine().trim();
                    Integer qteInt = Integer.parseInt(qte.trim());
                    if (qteInt > 10) {
                        System.out.println("Vous ne pouvez pas commander plus de 10 exemplaires");
                        MenuClient.sousMenuAjouterUnLivreAuPanier(con);
                    }
                    if(commande.ajouteLivreReseau(livre, (int) qteInt) == false){
                        int qteDispo = 0;
                        for (Magasin mag : MenuClient.reseau.getMagasins() ){
                             for(Posseder pos : mag.getPosseders()){
                                qteDispo += pos.getQte();
                            }
                        }
                        System.out.println("Nous sommes navré, nous n'avous que " + qteDispo +" exemplaires de " + nomLivre + " en stock");
                        System.out.println("Veuillez refaire votre ajout au panier avec une quatité inférieur à celle en stock"); 
                        String saut = scan.nextLine().trim();
                        MenuClient.sousMenuAjouterUnLivreAuPanier(con);
                    }  
                    System.out.println(qte + " exemplaire de " + nomLivre + " a bien été ajouté au panier");
                    MenuClient.sousMenuAjouterUnLivreAuPanier(con);
                    break;
                    default:
                    System.out.println("Le livre n'est pas ajouté au panier\nVous revenez au menu ajouter un livre au panier");
                    MenuClient.sousMenuAjouterUnLivreAuPanier(con);
                    break;
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Erreur lors de l'accès à la base de données : " + e.getMessage());
                    MenuClient.sousMenuAjouterUnLivreAuPanier(con);
                } catch (NumberFormatException e) {
                    System.out.println("La quantité d'exemplaire souhaité doit être un nombre entier");
                    MenuClient.sousMenuAjouterUnLivreAuPanier(con);
                System.out.println("La quantité d'exemplaire souhaité doit être un nombre entier");
            }
            break;
        }
        
    }

    public static  void sousMenuVoirLePanier(ConnexionMySQL con){
        System.out.println("┌─────────────────────────────────────────────┐");        
        System.out.println("│ Voici les livre figurant dans votre panier: │");
        System.out.println("│  1 - valider la commande                    │");
        System.out.println("│  Q - revenir au menu passer une commande    │");
        System.out.println("└─────────────────────────────────────────────┘"); 
        List<DetailCommande> listeDC = commande.getDetailCommandes();
        if (listeDC.isEmpty()){
            System.out.println("Votre panier est vide");
            System.out.println("[A] pour ajouter un livre [Q] pour revenir en arriere");
            String action1 = scan.nextLine().toLowerCase().trim();
            switch (action1) {
                case "a":
                    MenuClient.sousMenuAjouterUnLivreAuPanier(con);
                    break;
                case "q":
                    MenuClient.sousMenuPasserUneCommande(con);
                    break;
            
                default:
                    System.out.println("Veuillez rentrer une commande valide");
                    MenuClient.sousMenuVoirLePanier(con); 
                    break;
            }
        }
        else{
            double prixTotal = 0;
            for (DetailCommande dc : listeDC){
                System.out.println(dc.getLivre().getTitre() + " " + dc.getQte()+" x "+dc.getLivre().getPrix() + " = " +dc.getPrixVente()+ "euros");
                prixTotal += dc.getPrixVente();
            }
            System.out.println("Prix total: " + prixTotal + " euros" );
            String action = scan.nextLine().toLowerCase().trim();
            switch (action) {
                case "1":
                    MenuClient.sousMenuValiderLaCommande(con);
                    break;
                case "q":
                    MenuClient.sousMenuPasserUneCommande(con);
                    break;
                default:
                    System.out.println("Veuillez rentrer une commande valide");
                    MenuClient.sousMenuVoirLePanier(con);         
                    break;
            }
        }
    }


    public static void sousMenuValiderLaCommande(ConnexionMySQL con){
        System.out.println("A faire");
    }

}
