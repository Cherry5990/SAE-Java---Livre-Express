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

import javax.swing.text.StyledEditorKit.BoldAction;

public class MenuClient {
    private static final Scanner scan = new Scanner(System.in); // Scanner unique
    private static ClientBD clientBD = null;
    private static MagasinBD magBD = null;
    private static CommandeBD comBD = null;
    private static Client client = null;
    private static Magasin magasin = null;
    private static Commande commande = null;


    public static void connexionClient(ConnexionMySQL con){
        System.out.println("┌───────────────────────────────┐");
        System.out.println("│Veuillez saisir un id de Client│");
        System.out.println("│ [Q] pour revenir en arrière   │");
        System.out.println("└───────────────────────────────┘"); 
        String action = scan.nextLine().trim();
        switch (action) {
            case "q":
                ExecutableMenu.menuPrincipal(con);
                break;
            default:
                magBD = new MagasinBD(con);
                clientBD = new ClientBD(con);
                comBD = new CommandeBD(con);
                clientBD = new ClientBD(con);


                // Vérifie si 'action' est un nombre entier
                try {
                    client = clientBD.getClient(Integer.parseInt(action));
                }
                catch(Exception e){
                    System.out.println("Veuillez rentrer un nombre");
                    MenuClient.connexionClient(con);
                }
            
                System.out.println("Etes vous bien " + client.getPrenom() + " " + client.getNom()+"?");
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
                break;
        }
    }




    
    public static void menuClient(ConnexionMySQL con){
        System.out.println("┌─────────────────────────────────────────┐");        
        System.out.println("│  Vous êtes connectés en tant que Client │");
        System.out.println("│         Que voulez vous faire?          │");
        System.out.println("│ 1 - voir vos livres recommandés         │");
        System.out.println("│ 2 - se connecter à un magasin           │");
        System.out.println("│ Q - revenir en arrière                  │");
        System.out.println("└─────────────────────────────────────────┘");   
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "q":
                System.out.println("Vous retournez au menu de connexion pour client");
                MenuClient.connexionClient(con);
                break;        
            case "1":
                MenuClient.sousMenuLivreRecommande(con);
                break;
            case "2":
                MenuClient.chosirUnMagasin(con);
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
        try{
            System.out.println(clientBD.getRecommandationClient(client.getId()));
        }
        catch(SQLException e){
            System.out.println("problème recommandation : " +e.getMessage());
        }
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
    

    public static void chosirUnMagasin(ConnexionMySQL con){
        System.out.println(magBD.afficheMagasins());
        System.out.println("┌───────────────────────────────────────────┐"); 
        System.out.println("│   Veuillez saisir le numéro du magasin:   │");
        System.out.println("└───────────────────────────────────────────┘"); 
        String action = scan.nextLine().toLowerCase().trim();
        Integer mag = null;
        try{
            if (Integer.parseInt(action)>0 && Integer.parseInt(action)<=magBD.maxIdMagasin()){
                mag = Integer.parseInt(action);
                System.out.println("Magasin choisi numéro "+mag);
                magasin = magBD.getMagasin(mag);
            }
            else{
                System.out.println("Numéro de magasin mauvais !");
                String skip = scan.nextLine().toLowerCase().trim();
                MenuClient.chosirUnMagasin(con);
            }
        }
        catch(SQLException e){
            System.out.println("Problème avec magBD.getMagasin ou maxID");
            String skip = scan.nextLine().toLowerCase().trim();
            MenuClient.chosirUnMagasin(con);
        }
        catch(NumberFormatException e){
            System.out.println("Veuillez entrer un numéro de magasin valide !");
            String skip = scan.nextLine().toLowerCase().trim();
            MenuClient.chosirUnMagasin(con);
        }
        MenuClient.sousMenuMagasin(con);
    }

    //A finir
    public static void sousMenuMagasin(ConnexionMySQL con){
        commande = new Commande(0, null, true, false, client, magasin);
        System.out.println("┌─────────────────────────────────────────────┐");        
        System.out.println("│        Vous êtes connectés au magasin:      │");
        System.out.println("│           "+magasin.getNomMagasin()+"         │");
        System.out.println("│   1 - Consulter le catalogue                │");
        System.out.println("│   2 - Chercher un livre                     │");
        System.out.println("│   3 - Passer commande                       │");
        System.out.println("│   Q - Retour au menu client                 │");
        System.out.println("└─────────────────────────────────────────────┘"); 
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "2":
                MenuClient.sousMenuRecherche(con);
                break;
            case "1":
                MenuClient.sousMenuCatalogue(con,0,10);
                break;
            case "3":
                MenuClient.sousMenuPasserUneCommande(con);
                break;
            case "q":
                System.out.println("Vous retournez au menu Client");
                 MenuClient.menuClient(con);
                break;       
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuClient.sousMenuMagasin(con);          
                break;
        }
    }

    public static void sousMenuCatalogue(ConnexionMySQL con,int debut,int fin){
        System.out.println("┌────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                   Catalogue des livres                             │");
        System.out.println("├────────────────────────────────────────────────────────────────────┤");
        System.out.println(magBD.voirStock(magasin.getIdMagasin(), debut, fin));
        System.out.println("├────────────────────────────────────────────────────────────────────┤");
        System.out.println("  Affichage : " + (debut + 1) + " - " + fin + "                 ");
        System.out.println("  [C] Page suivante   [R] Page précédente   [Q] Retour        ");
        System.out.println("└────────────────────────────────────────────────────────────────────┘");
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "c":
                if(fin<magBD.maxPossederLivre(magasin.getIdMagasin())){
                    MenuClient.sousMenuCatalogue(con,debut+10,fin+10);
                }
                else{
                    System.out.println("Vous êtes déjà à la fin du catalogue.");
                    MenuClient.sousMenuCatalogue(con, debut, fin);
                }
                break;
            case "r":
                if (debut>=10){
                    MenuClient.sousMenuCatalogue(con,debut-10,fin-10);
                }
                else{
                    System.out.println("Vous êtes déjà au début du catalogue.");
                    MenuClient.sousMenuCatalogue(con, debut, fin);
                }
                break;
            case "q":
                System.out.println("Vous retournez au menu Client");
                MenuClient.sousMenuMagasin(con);
                break;      
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuClient.sousMenuCatalogue(con,debut,fin);           
                break;
        }
    }

    public static void sousMenuRecherche(ConnexionMySQL con){
        System.out.println("┌────────────────────────────────────────────┐"); 
        System.out.println("│Tapez le nom d'un livre ou juste une partie:│"); 
        System.out.println("└────────────────────────────────────────────┘");
        
        String action = scan.nextLine().toLowerCase().trim();


        System.out.println("┌───────────────────────────────────────────────┐");        
        System.out.println("│    Listes des livres                          │");
        System.out.println("└───────────────────────────────────────────────┘");
        System.out.println(magBD.rechercheLivre(magasin.getIdMagasin(),action));
        System.out.println("┌───────────────────────────────────────────────┐");
        System.out.println("│  C - nouvelle recherche                       │");
        System.out.println("│  Q - revenir en arriere                       │");
        System.out.println("└───────────────────────────────────────────────┘");
        String action2 = scan.nextLine().toLowerCase().trim();
        switch (action2) {
            case "c":{
                MenuClient.sousMenuRecherche(con);
                break;}          
            case "q":{
                System.out.println("Vous retournez au menu principal");
                MenuClient.sousMenuMagasin(con);
                break;}
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuClient.sousMenuRecherche(con);        
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
                System.out.println("┌───────────────────────────────────────────────┐");
                System.out.println("│ Attention cela va supprimer votre commmande   │");
                System.out.println("│   [C] confirmer                               │");
                System.out.println("│   [A] Annuler                                 │");
                System.out.println("└───────────────────────────────────────────────┘");
                String action2 = scan.nextLine().toLowerCase().trim(); 
                switch (action2) {
                    case "c":
                        try{
                            for(DetailCommande dc:commande.getDetailCommandes()){
                                magBD.ajouterQte(dc.getLivre().getIsbn(), dc.getQte(), magasin.getIdMagasin());
                            }
                        }
                        catch(SQLException e){
                            System.out.println("Problème de code");
                        }
                        MenuClient.sousMenuMagasin(con);
                        break;
                    case "a":
                        MenuClient.sousMenuPasserUneCommande(con);
                        break;
                    default:
                        System.out.println("Insertion incorrecte !");
                        System.out.println("Retour à la commande");
                        String skip = scan.nextLine().toLowerCase().trim(); 
                        MenuClient.sousMenuPasserUneCommande(con);
                        break;
                }
                break;
            default:
            System.out.println("Veuillez rentrer une commande valide");
            MenuClient.sousMenuPasserUneCommande(con);         
                break;
        }
    }

    public static  void sousMenuAjouterUnLivreAuPanier(ConnexionMySQL con){
        System.out.println("┌──────────────────────────────────────────────────────────────┐");
        System.out.println("│ Rentrer le nom du livre que vous souhaitez ajouter au panier │");
        System.out.println("│ Q - pour revenir en arrière                                  │");
        System.out.println("└──────────────────────────────────────────────────────────────┘"); 
        String action = scan.nextLine().trim();
        switch (action){
            case "q":
                MenuClient.sousMenuPasserUneCommande(con);
                break;
            default:
                try {
                    Livre livre = comBD.verifLivreExiste(action,magasin.getIdMagasin());

                    if (livre == null){
                        System.out.println("Navré, mais "+magasin.getNomMagasin()+" n'a le livre que vous souhaité");
                        String saut = scan.nextLine().trim();
                        MenuClient.sousMenuAjouterUnLivreAuPanier(con);
                    } 
                    else {
                        String nomLivre = livre.getTitre();
                        double prixLivre = livre.getPrix();
                        System.out.println("Souhaitez-vous ajouter " + nomLivre + " qui coûte " + prixLivre + "euros");
                        System.out.println("[C] Confirmer    [N'importe quelle touche] Non");

                        String verif = scan.nextLine().toLowerCase().trim();
                        switch (verif) {
                            case "c":
                                int stock = comBD.avoirStockLivre(livre, magasin.getIdMagasin());
                                System.out.println("Combien d'exemplaire voulez-vous commander?\nAttention vous ne pouvez pas commander plus de "+stock+" exemplaires");
                                String qte = scan.nextLine().trim();
                                Integer qteInt = Integer.parseInt(qte.trim());
                                if (qteInt <= stock && qteInt>0) {
                                    commande.ajouteLivre(livre, qteInt);
                                    magBD.miseAJourQuantite(livre.getIsbn(),stock-qteInt,magasin.getIdMagasin());
                                    System.out.println("Livre ajouté un votre commande avec succés");
                                }
                                else{
                                    System.out.println("Nous sommes navré, nous n'avous que " + stock +" exemplaires de " + nomLivre + " en stock");
                                    System.out.println("Veuillez refaire votre ajout au panier avec une quatité inférieur à celle en stock");
                                    String saut = scan.nextLine().trim();
                                }
                                MenuClient.sousMenuAjouterUnLivreAuPanier(con);
                                break;
                            default:
                                MenuClient.sousMenuAjouterUnLivreAuPanier(con);
                        }
                    }
                }
                catch (SQLException e) {
                    System.out.println("Erreur lors de l'accès à la base de données : " + e.getMessage());
                    MenuClient.sousMenuAjouterUnLivreAuPanier(con);
                }
                catch (NumberFormatException e) {
                    System.out.println("La quantité d'exemplaire souhaité doit être un nombre entier");
                    MenuClient.sousMenuAjouterUnLivreAuPanier(con);
                    System.out.println("La quantité d'exemplaire souhaité doit être un nombre entier");
                }   
        }   

    }

    public static  void sousMenuVoirLePanier(ConnexionMySQL con){
        System.out.println("┌──────────────────────────────────────────────────────────────────┐");
        System.out.println("│ Voici les livre figurant dans votre panier:                      │");
        int somme = 0;
        for(DetailCommande dc : commande.getDetailCommandes()){
            System.out.printf("│ %2d | %-30s | %6.2f € | %3d exemplaire(s) │%n",
                dc.getNumlig(),
                dc.getLivre().getTitre(),
                dc.getPrixVente(),
                dc.getQte()
            );
            somme+=dc.getPrixVente()*dc.getQte();
        }
        System.out.println("│  Prix Total : "+somme+"€                                             │");
        System.out.println("│  Q - revenir en arrière                                          │");
        System.out.println("└──────────────────────────────────────────────────────────────────┘");
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "q":
                MenuClient.sousMenuPasserUneCommande(con);
                break;
            default:
                System.out.println("Veuillez rentrer une commande valide");
                break;
        } 
    }


    public static void sousMenuValiderLaCommande(ConnexionMySQL con){
        System.out.println("┌──────────────────────────────────────────────────────────────────┐");
        System.out.println("│  Souhaitez-vous :                                                │");
        System.out.println("│  [1] Retirer votre commande en magasin                           │");
        System.out.println("│  [2] Être livré à votre adresse :" + client.getAdresse() + ", " + client.getCodePostal() + " " + client.getVille() + ")");
        System.out.println("│  [Q] Revenir en arrière                                          │");
        System.out.println("└──────────────────────────────────────────────────────────────────┘");
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "q":
                MenuClient.sousMenuPasserUneCommande(con);
                break;
            case "1":
                commande.setLivraison(false);
                System.out.println("┌────────────────────────────┐");
                System.out.println("│ Changement prit en compte  │");
                System.out.println("└────────────────────────────┘");
                String skip = scan.nextLine().toLowerCase().trim();
                break;
            case "2":
            commande.setLivraison(true);
                System.out.println("┌────────────────────────────┐");
                System.out.println("│ Changement prit en compte  │");
                System.out.println("└────────────────────────────┘");
                String skip2 = scan.nextLine().toLowerCase().trim();
                break;
            default:
                System.out.println("Veuillez rentrer une commande valide");
                String skip3 = scan.nextLine().toLowerCase().trim();
                MenuClient.sousMenuValiderLaCommande(con);
                break;
        }
        Boolean fini = true;
        try{
            while (fini){
                System.out.println("┌──────────────────────────────────────────────────────────────┐");
                System.out.println("│ Etes vous sur de vouloir validez votre commande ?            │");
                System.out.println("│ [V] Visulier la comannde                                     │");
                System.out.println("│ [C] confirmer                  [Q] Revenir en arrière        │");
                System.out.println("└──────────────────────────────────────────────────────────────┘"); 
                String action2 = scan.nextLine().toLowerCase().trim();
                switch (action2) {
                    case "c":
                        if (commande.isEnLigne()){
                            comBD.insererCommande(commande);
                            System.out.println("┌────────────────────────────────────────────────────────────────┐");
                            System.out.println("│ Très bien, votre commande sera livré sous un delai de 5 jours  │");
                            System.out.println("│                    Merci pour votre commande!                  │");
                            System.out.println("└────────────────────────────────────────────────────────────────┘");
                            String skip3 = scan.nextLine().toLowerCase().trim();
                            fini = false;
                        }
                        else{
                            comBD.insererCommande(commande);
                            System.out.println("┌──────────────────────────────────────────────────────────────────────────┐");
                            System.out.println("│ Très bien, vous pouvez passer quand vous voulez pour retirer vos livres  │");
                            System.out.println("│                        Merci pour votre commande!                        │");
                            System.out.println("└──────────────────────────────────────────────────────────────────────────┘");
                            String skip3 = scan.nextLine().toLowerCase().trim();
                            fini = false;
                        }
                        MenuClient.sousMenuMagasin(con);
                        break;
                    case "q":
                        MenuClient.sousMenuPasserUneCommande(con);
                        break;
                    case "v":
                        int somme = 0;
                        for(DetailCommande dc : commande.getDetailCommandes()){
                            System.out.println("┌──────────────────────────────────────────────────────────────────┐");
                            System.out.println("│ Voici les livre figurant dans votre panier:                      │");
                            System.out.printf("│ %2d | %-30s | %6.2f € | %3d exemplaire(s) │%n",
                                dc.getNumlig(),
                                dc.getLivre().getTitre(),
                                dc.getPrixVente(),
                                dc.getQte()
                            );
                            somme+=dc.getPrixVente()*dc.getQte();
                        }
                        System.out.println("│  Prix Total : "+somme+"€                                             │");
                        System.out.println("│  Q - revenir en arrière                                          │");
                        System.out.println("└──────────────────────────────────────────────────────────────────┘");
                        String skip3 = scan.nextLine().toLowerCase().trim();
                        break;
                    default:
                        System.out.println("Veuillez entrer une commande valide");
                        String skip = scan.nextLine().toLowerCase().trim();
                        MenuClient.sousMenuValiderLaCommande(con);
                        break;
                }   
            }
        }
        catch(SQLException e){
            System.out.println("La commande ne peut pas être inserer");
        }
        MenuClient.sousMenuMagasin(con);
    }

}