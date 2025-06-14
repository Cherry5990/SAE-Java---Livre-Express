package menu;

import java.sql.SQLException;
import java.util.Scanner;

import BD.ClientBD;
import BD.ConnexionMySQL;
import BD.LivreBD;
import BD.MagasinBD;
import BD.ReseauBD;
import BD.VendeurBD;
import modele.Client;
import modele.Commande;
import modele.DetailCommande;
import modele.Livre;
import BD.CommandeBD;
import modele.Vendeur;

public class MenuVendeur {
    private static final Scanner scan = new Scanner(System.in, "UTF-8"); // Scanner unique
    private static Vendeur vendeur;
    private static Commande commande;
    private static VendeurBD vendeurBD;
    private static LivreBD livreBD;
    private static MagasinBD magasinBD;
    private static ReseauBD reseauBD;
    private static ClientBD clientBD;
    private static CommandeBD comBD;

    // Menu de connexion du vendeur
    public static void connexionVendeur(ConnexionMySQL con){
        System.out.println("┌────────────────────────────────┐");
        System.out.println("│Veuillez saisir un id de Vendeur│");
        System.out.println("│ [Q] pour revenir en arrière    │");
        System.out.println("└────────────────────────────────┘"); 
        String action = scan.nextLine().trim();
        switch (action) {
            case "q":
                ExecutableMenu.menuPrincipal(con);
                break;
            default:
                MenuVendeur.vendeurBD = new VendeurBD(con);
                MenuVendeur.livreBD = new LivreBD(con);
                MenuVendeur.magasinBD = new MagasinBD(con);
                MenuVendeur.reseauBD = new ReseauBD(con);
                MenuVendeur.clientBD = new ClientBD(con);
                MenuVendeur.comBD = new CommandeBD(con);

                try {
                    MenuVendeur.vendeur = vendeurBD.getVendeur(Integer.parseInt(action));
                }

                catch(Exception e){
                    System.out.println("Veuillez rentrer un nombre");
                    MenuVendeur.connexionVendeur(con);
                break;
            }
        }
        
        System.out.println("Etes vous bien " + MenuVendeur.vendeur.getPrenom() + " " + MenuVendeur.vendeur.getNom()+"?");
        System.out.println("[C] Confirmer    [N] Non");
        System.out.println("[M] Menu principale");
        String action2 = scan.nextLine().toLowerCase().trim();
        switch (action2) {
            case "c":
                MenuVendeur.menuVendeur(con);
                break;        
            case "n":
                MenuVendeur.connexionVendeur(con);
                break;
            case "m":
                ExecutableMenu.menuPrincipal(con);
                break;
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuVendeur.connexionVendeur(con);        
                break;
        }
    }


    // Menu principal du vendeur
    public static void menuVendeur(ConnexionMySQL con){
        System.out.println("┌──────────────────────────────────────────┐");     
        System.out.println("│ Magasin "+ vendeur.getMagasin().getNomMagasin()+"        │");   
        System.out.println("│ Vous êtes connectés en tant que Vendeur  │");
        System.out.println("│         Que voulez vous faire?           │");
        System.out.println("│ 1 - ajouter un livre au stocks           │");
        System.out.println("│ 2 - mettre à jour la qte dispo d'un livre│");
        System.out.println("│ 3 - verifier la disponibilité d'un livre │");
        System.out.println("│ 4 - passer une commande pour un client   │");
        System.out.println("│ 5 - transferer un livre                  │");
        System.out.println("│ Q - revenir en arrière                   │");
        System.out.println("│ M - revenir au menu principal            │");
        System.out.println("└──────────────────────────────────────────┘");   
        String action = scan.nextLine().toLowerCase().trim();
        switch (action) {
            case "q":
                MenuVendeur.connexionVendeur(con);
                break;       
            case "m":
                ExecutableMenu.menuPrincipal(con);
                break;
            case "1":
                MenuVendeur.sousMenuAjouterLivre(con);
                break;
            case "2":
                MenuVendeur.sousMenuMajQte(con);
                break;
            case "3":
                MenuVendeur.sousMenuVerifDispo(con);
                break;
            case "4":
                MenuVendeur.sousMenuCommande(con);
                break;
            case "5":
                MenuVendeur.sousMenuTransfererLivre(con);
                break;

            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuVendeur.menuVendeur(con);       
                break;
        }
    }  

    public static void sousMenuCommande(ConnexionMySQL con){
        System.out.println("┌─────────────────────────────────────────────┐");        
        System.out.println("│ Veuillez rentrer le nom de la personne      │");
        System.out.println("└─────────────────────────────────────────────┘"); 
        String nom = scan.nextLine().trim();
        System.out.println("┌─────────────────────────────────────────────┐");        
        System.out.println("│ Veuillez rentrer le prenom de la personne   │");
        System.out.println("└─────────────────────────────────────────────┘"); 
        String prenom = scan.nextLine().trim();
        try{
            String recherche = clientBD.rechercheClient(prenom, nom);
            if(recherche ==null){
                System.out.println("Aucun utilisateur avec ce nom et prénom");
                String skip= scan.nextLine();
                MenuVendeur.sousMenuCommande(con);
            }
            else{
                System.out.println(recherche);
            }
        }
        catch(SQLException e){
            System.out.println("Recherche invalide");
            String skip = scan.nextLine();
            MenuVendeur.sousMenuCommande(con);
        }
        System.out.println("┌─────────────────────────────────────────────┐");        
        System.out.println("│ Veuillez rentrer l'id de la personne        │");
        System.out.println("└─────────────────────────────────────────────┘"); 
        String id = scan.nextLine().toLowerCase().trim();
        Client client = null;
        try{
            client = clientBD.getClient(Integer.parseInt(id));
        }
        catch(SQLException e){
            System.out.println("id qui n'existe pas");
            String skip = scan.nextLine();
            MenuVendeur.sousMenuCommande(con);
        }
        catch(NumberFormatException ex){
            System.out.println("Veuillez rentrez un nombre ou un id existant");
            String skip = scan.nextLine();
            MenuVendeur.sousMenuCommande(con);
        }
        commande = new Commande(0, null, false, false, client, vendeur.getMagasin());
        MenuVendeur.sousMenuPasserUneCommande(con);
    }

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
                MenuVendeur.sousMenuAjouterUnLivreAuPanier(con);
                break;       
            case "2":
                MenuVendeur.sousMenuVoirLePanier(con);
                break;
            case "3":
                MenuVendeur.sousMenuValiderLaCommande(con);
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
                                magasinBD.ajouterQte(dc.getLivre().getIsbn(), dc.getQte(), vendeur.getMagasin().getIdMagasin());
                            }
                        }
                        catch(SQLException e){
                            System.out.println("Problème de code");
                        }
                        MenuVendeur.menuVendeur(con);
                        break;
                    case "a":
                        MenuVendeur.sousMenuPasserUneCommande(con);
                        break;
                    default:
                        System.out.println("Insertion incorrecte !");
                        System.out.println("Retour à la commande");
                        String skip = scan.nextLine().toLowerCase().trim(); 
                        MenuVendeur.sousMenuPasserUneCommande(con);
                        break;
                }
                break;
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuVendeur.sousMenuPasserUneCommande(con);         
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
                MenuVendeur.sousMenuPasserUneCommande(con);
                break;
            default:
                try {
                    Livre livre = comBD.verifLivreExiste(action,vendeur.getMagasin().getIdMagasin());

                    if (livre == null){
                        System.out.println("Navré, mais "+vendeur.getMagasin().getIdMagasin()+" n'a le livre que vous souhaité");
                        String saut = scan.nextLine().trim();
                        MenuVendeur.sousMenuAjouterUnLivreAuPanier(con);
                    } 
                    else {
                        String nomLivre = livre.getTitre();
                        double prixLivre = livre.getPrix();
                        System.out.println("Souhaitez-vous ajouter " + nomLivre + " qui coûte " + prixLivre + "euros");
                        System.out.println("[C] Confirmer    [N'importe quelle touche] Non");

                        String verif = scan.nextLine().toLowerCase().trim();
                        switch (verif) {
                            case "c":
                                int stock = comBD.avoirStockLivre(livre, vendeur.getMagasin().getIdMagasin());
                                System.out.println("Combien d'exemplaire voulez-vous commander?\nAttention vous ne pouvez pas commander plus de "+stock+" exemplaires");
                                String qte = scan.nextLine().trim();
                                Integer qteInt = Integer.parseInt(qte.trim());
                                if (qteInt <= stock && qteInt>0) {
                                    commande.ajouteLivre(livre, qteInt);
                                    magasinBD.miseAJourQuantite(livre.getIsbn(),stock-qteInt,vendeur.getMagasin().getIdMagasin());
                                    System.out.println("Livre ajouté un votre commande avec succés");
                                }
                                else{
                                    System.out.println("Nous sommes navré, nous n'avous que " + stock +" exemplaires de " + nomLivre + " en stock");
                                    System.out.println("Veuillez refaire votre ajout au panier avec une quatité inférieur à celle en stock");
                                    String saut = scan.nextLine().trim();
                                }
                                MenuVendeur.sousMenuAjouterUnLivreAuPanier(con);
                                break;
                            default:
                                MenuVendeur.sousMenuAjouterUnLivreAuPanier(con);
                                break;
                        }
                    }
                }
                catch (SQLException e) {
                    System.out.println("Erreur lors de l'accès à la base de données : " + e.getMessage());
                    MenuVendeur.sousMenuAjouterUnLivreAuPanier(con);
                }
                catch (NumberFormatException e) {
                    System.out.println("La quantité d'exemplaire souhaité doit être un nombre entier");
                    MenuVendeur.sousMenuAjouterUnLivreAuPanier(con);
                    System.out.println("La quantité d'exemplaire souhaité doit être un nombre entier");
                }
                break;   
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
                MenuVendeur.sousMenuPasserUneCommande(con);
                break;
            default:
                System.out.println("Veuillez rentrer une commande valide");
                break;
        } 
    }


    public static void sousMenuValiderLaCommande(ConnexionMySQL con){
        System.out.println("┌──────────────────────────────────────────────────────────────┐");
        System.out.println("│ Etes vous sur de vouloir validez votre commande ?            │");
        System.out.println("│ [V] Visulier la comannde                                     │");
        System.out.println("│ [C] confirmer                  [Q] Revenir en arrière        │");
        System.out.println("└──────────────────────────────────────────────────────────────┘"); 
        String action2 = scan.nextLine().toLowerCase().trim();
        switch (action2) {
            case "c":
                try{
                    comBD.insererCommande(commande);
                }
                catch(SQLException e){
                    System.out.println("Problème au niveau de la commande : "+e.getMessage());
                }
                System.out.println("┌────────────────────────────────────────────────────────────────┐");
                System.out.println("│           Très bien, la commande est enregistré                │");
                System.out.println("└────────────────────────────────────────────────────────────────┘");
                String skip3 = scan.nextLine().toLowerCase().trim();
                break;
            case "q":
                MenuVendeur.sousMenuPasserUneCommande(con);
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
                    somme+=dc.getPrixVente();
                }
                System.out.println("│  Prix Total : "+somme+"€                                             │");
                System.out.println("│  Q - revenir en arrière                                          │");
                System.out.println("└──────────────────────────────────────────────────────────────────┘");
                String skip4 = scan.nextLine();
                MenuVendeur.sousMenuValiderLaCommande(con);
                break;
            default:
                System.out.println("Veuillez entrer une commande valide");
                String skip = scan.nextLine();
                MenuVendeur.sousMenuValiderLaCommande(con);
                break;
        }   
        MenuVendeur.menuVendeur(con);
    }

    //Première Partie - ajouté un livre au stocks
    public static void sousMenuAjouterLivre(ConnexionMySQL con){
        System.out.println("┌────────────────────────────────────────────────────┐");        
        System.out.println("│ Vous êtes sur la page d'ajout d'un livre           │");
        System.out.println("│ Rentrer le nom du livre que vous souhaitez ajouter │");
        System.out.println("│ Q - revenir en arrière                             │");
        System.out.println("└────────────────────────────────────────────────────┘");
        String entrer = scan.nextLine().trim();
        switch (entrer) {
            case "q":
            case "Q":
                MenuVendeur.menuVendeur(con);
                break;
            default:
                try {
                    if(MenuVendeur.livreBD.verifLivreExisteDansMagasin(MenuVendeur.vendeur.getMagasin().getIdMagasin(),entrer)){
                        System.out.println("┌────────────────────────────────────────────────────────┐");        
                        System.out.println("│ Le livre que vous voulez ajouter existe déjà en stock  │");
                        System.out.println("│  1 - mettre à jour la qte dispo de ce livre            │");
                        System.out.println("│  2 - retourner à la page d'ajout de livre              │");
                        System.out.println("│  Q - retourner au menu Vendeur                         │");
                        System.out.println("└────────────────────────────────────────────────────────┘");
                        String entrer2 = scan.nextLine().toLowerCase().trim();
                        switch (entrer2) {
                            case "q":
                                MenuVendeur.menuVendeur(con);
                                break;
                            case "1":
                                MenuVendeur.sousMenuMajQte(con);
                                break;
                            case "2":
                                MenuVendeur.sousMenuAjouterLivre(con);
                                break;
                            default:
                                System.out.println("Veuillez rentrer une commande valide");
                                MenuVendeur.menuVendeur(con);       
                                break;
                        }
                    }
                    else{
                        MenuVendeur.procedureDAjout(con, entrer);
                    }
                } catch (java.sql.SQLException e) {
                    System.out.println("Erreur lors de la vérification du livre : " + e.getMessage());
                    MenuVendeur.sousMenuAjouterLivre(con);
                } catch(Exception e){
                    System.out.println("Veuillez rentrer un nombre");
                    MenuVendeur.sousMenuAjouterLivre(con);
                }
                break;
        }
        
    }
    
    public static void procedureDAjout(ConnexionMySQL con, String entrer){
        try {
            
            String isbn = MenuVendeur.livreBD.regardeSiISBNExiste(entrer);
            boolean isbnExiste = true;
            if (isbn == null){
            isbn = MenuVendeur.livreBD.maxIsbnPlus1();
            isbnExiste = false;
            }
            System.out.println("───────────────────────────────────────────────────────────────────────────────");
            System.out.println("Vous êtes sur le point de rajouter " + entrer + " dans les stocks");
            System.out.println("Ce livre aura comme identifiant: "+  isbn);
            System.out.println("───────────────────────────────────────────────────────────────────────────────");

            if(isbnExiste){
                System.out.println("Le livre existant déjà dans le réseau, les information de celui-ci sont déjà renseigné");
                Integer nbpages = MenuVendeur.livreBD.rechercheNbPagesLivre(isbn); 
                Double prix  = MenuVendeur.livreBD.recherchePrixLivre(isbn);
                Integer datePubli = MenuVendeur.livreBD.rechercheDatePubli(isbn);
                Integer qte = MenuVendeur.demandeQte(con);
                MenuVendeur.confirmationAjout(con, entrer, isbn,datePubli, nbpages, prix, true,qte);
            }
            else{
                MenuVendeur.demandeNbLigneEtPrix(con, entrer, isbn);
            }
        }catch (java.sql.SQLException e) {
                System.out.println("Erreur lors de la vérification du livre : " + e.getMessage());
                MenuVendeur.sousMenuAjouterLivre(con);
        }
    }

    public static void demandeNbLigneEtPrix(ConnexionMySQL con, String nom, String isbn){
        System.out.println("Le livre n'existant pas dans le réseau, vous allez devoir renseigné ces informations");
        System.out.println("[C] Pour continuer [Q] Pour revenir en arrière");
        String entrer = scan.nextLine().toLowerCase().trim();
        switch (entrer) {
            case "q":
                MenuVendeur.sousMenuAjouterLivre(con);
                break;
            case "c":
                System.out.println("─────────────────────────────────────────────────────────");
                System.out.println("Rentrer le nombre de page");
                System.out.println("Rentrer un nombre entier");
                System.out.println("Vous retournerez au menu d'ajout si vous faite une erreur");
                System.out.println("─────────────────────────────────────────────────────────");
                String entrerNbPage = scan.nextLine().trim();
                Integer nbpages = (Integer.parseInt(entrerNbPage));
                System.out.println("─────────────────────────────────────────────────────────");
                System.out.println("Rentrer la date de publication du livre");
                System.out.println("Rentrer uniquement l'année");
                System.out.println("Vous retournerez au menu d'ajout si vous faite une erreur");
                System.out.println("─────────────────────────────────────────────────────────");
                String entrerAnne = scan.nextLine().trim();
                Integer annee = (Integer.parseInt(entrerAnne));
                System.out.println("──────────────────────────────────────────────────────────────");
                System.out.println("Rentrer le prix du livre");
                System.out.println("Rentrer uniquement des chiffres ou un point pour les virgule");
                System.out.println("Vous retournerez au menu d'ajout si vous faite une erreur");
                System.out.println("──────────────────────────────────────────────────────────────");
                String entrerPrix = scan.nextLine().trim();
                Double prix = (Double.parseDouble(entrerPrix));
                Integer qte = MenuVendeur.demandeQte(con);
                MenuVendeur.confirmationAjout(con, nom, isbn, nbpages, annee, prix, false, qte);
                break;
            default:
            System.out.println("Veuillez rentrer une commande valide");
            MenuVendeur.demandeNbLigneEtPrix(con, nom, isbn);       
                break;
        }
    }

    public static Integer demandeQte(ConnexionMySQL con){
        System.out.println("─────────────────────────────────────────────────────────");
        System.out.println("Rentrer la quantité de livre qui sera ajouté");
        System.out.println("Rentrer un nombre entier");
        System.out.println("Vous retournerez au menu d'ajout si vous faite une erreur");
        System.out.println("─────────────────────────────────────────────────────────");
        String entrerQte = scan.nextLine().trim();
        Integer qte = (Integer.parseInt(entrerQte));
        return qte;

        
    }

    public static void confirmationAjout(ConnexionMySQL con, String titre, String isbn, Integer nbPages,Integer datePubli, Double prix, boolean existe, Integer qte){
        System.out.println("─────────────────────────────────────────────────────────────────────────────────");
        System.out.println("Vous êtes sur le point d'ajouter à votre magasin un livre avec les infos suivante");
        System.out.println("identifiant: " + isbn);
        System.out.println("Titre: " + titre);
        System.out.println("Le nombres de pages: " + nbPages);
        System.out.println("La date de publication: " + datePubli);
        System.out.println("Le prix: " + prix);
        System.out.println("La quantité " + qte);
        System.out.println("[C] Pour confirmer l'ajout  [Q] Pour annuler");
        System.out.println("─────────────────────────────────────────────────────────────────────────────────");
        String entrer = scan.nextLine().toLowerCase().trim();
        switch (entrer) {
            case "c":
                try {
                    if(existe){
                        MenuVendeur.magasinBD.ajouterQte(isbn, qte, vendeur.getMagasin().getIdMagasin());
                        System.out.println(titre + " a bien été ajouté dans le stock du magasin");
                    }
                    else{
                        MenuVendeur.livreBD.insererLivre(isbn, titre, nbPages, datePubli, prix);
                        MenuVendeur.magasinBD.ajouterQte(isbn, qte, vendeur.getMagasin().getIdMagasin());
                        System.out.println(titre + " a bien été ajouté dans le réseau");
                        System.out.println(titre + " a bien été ajouté dans le stock du magasin");
                        
                    }
                    System.out.println("appuyez sur entrer pour revenir au menu d'ajout de livre");
                    String saut = scan.nextLine();
                    MenuVendeur.sousMenuAjouterLivre(con);
                } catch (java.sql.SQLException e) {
                    System.out.println("Erreur lors de l'insertion du livre : " + e.getMessage());
                    MenuVendeur.sousMenuAjouterLivre(con);
                }
                break;
            case "q":
                MenuVendeur.sousMenuAjouterLivre(con);
                break;
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuVendeur.confirmationAjout(con, titre, isbn, nbPages, datePubli, prix, existe, qte);                
                break;
        }
    }
    //Fin de la Première Partie 

    //Deuxième Partie - mettre à jour la qte dispo d'un livre
    public static void sousMenuMajQte(ConnexionMySQL con){
        System.out.println("┌────────────────────────────────────────────────────┐"); 
        System.out.println("│ Vous êtes sur la page de de mise à jour de quantité│");
        System.out.println("│ Rentrez le nom d'un livre ou juste une partie:     │"); 
        System.out.println("│ [Q] retour au menu Vendeur                         │"); 
        System.out.println("└────────────────────────────────────────────────────┘");
        String entrer = scan.nextLine().trim();
        switch (entrer) {
            case "q":
            case "Q":
                MenuVendeur.menuVendeur(con);
                break;
            default:
                String reponse = MenuVendeur.magasinBD.rechercheLivre(vendeur.getMagasin().getIdMagasin(), entrer);
                System.out.println(reponse);
                System.out.println("┌──────────────────────────────────────────────┐"); 
                System.out.println("│[C] continuer [R] faire une nouvelle recherche│");
                System.out.println("│[Q] retour au menu Vendeur                    │");        
                System.out.println("└──────────────────────────────────────────────┘");
                String entrer2 = scan.nextLine().toLowerCase().trim();
                switch (entrer2) {
                    case "c":
                    MenuVendeur.MajQteDonnerIsbn(con);
                    break;
                case "r":
                    MenuVendeur.sousMenuMajQte(con);
                    break;
                case "q":
                    MenuVendeur.menuVendeur(con);
                    break;
                default:
                    System.out.println("Veuillez rentrer une commande valide");
                    MenuVendeur.sousMenuMajQte(con);                
                    break;
        }
                    break;
        }
        
    }


    public static void MajQteDonnerIsbn(ConnexionMySQL con){
        System.out.println("┌───────────────────────────────────────────────────────┐"); 
        System.out.println("│Rentrez l'isbn du livre dont vous voulez changer la qte│"); 
        System.out.println("│Rentrez les 13 chiffres sans espaces                   │"); 
        System.out.println("└───────────────────────────────────────────────────────┘");
        String entrerIsbn = scan.nextLine().trim();
        String titre = null;
        try {
            titre = livreBD.rechercheTitre(entrerIsbn);
            System.out.println("───────────────────────────────────────────────────────");
            System.out.println("Rentrez la nouvelle quantité qu'aura " + titre); 
            System.out.println("Veillez à rentrer un nombre entier"); 
            System.out.println("───────────────────────────────────────────────────────");
            String entrerQte = scan.nextLine().trim();
            Integer qte = (Integer.parseInt(entrerQte));
            MenuVendeur.MajQteDonnerValider(con, titre, qte, entrerIsbn);


        } catch (java.sql.SQLException e) {
            System.out.println("Erreur lors dans l'entrer de l'isbn");
            System.out.println("Veiller à bien rentrer 13 chiffres sans espaces");
            MenuVendeur.MajQteDonnerIsbn(con);
        } catch(Exception e){
                System.out.println("Veuillez rentrer un nombre");
                MenuVendeur.MajQteDonnerIsbn(con);
        }
    }

    public static void MajQteDonnerValider(ConnexionMySQL con, String titre, Integer qte, String isbn){
        System.out.println("───────────────────────────────────────────────────────"); 
        System.out.println("Confirmez vous la mise à jour de la qantité de: "); 
        System.out.println(titre +" à " + qte + " exemplaires?" ); 
        System.out.println("[C] confirmer   [Q] annuler"  ); 
        System.out.println("───────────────────────────────────────────────────────");
        String entrer = scan.nextLine().toLowerCase().trim();
        switch (entrer) {
            case "q":
                System.out.println("mise à jour annulée");
                MenuVendeur.sousMenuMajQte(con);
                break;
            case "c":
                MenuVendeur.magasinBD.miseAJourQuantite(isbn, qte, vendeur.getMagasin().getIdMagasin());
                System.out.println("──────────────────────────────────────────────────────────────────────────────────");
                System.out.println("La quantité de " + titre + " a bien été mise à jour à " + qte + " exemplaires");
                System.out.println("appuyer sur entrer pour retourner au menu mise à jour de quantité");
                System.out.println("──────────────────────────────────────────────────────────────────────────────────");
                String saut = scan.nextLine().trim();
                MenuVendeur.sousMenuMajQte(con);
            default:
                System.out.println("Veuillez rentrer une commande valide");
                MenuVendeur.MajQteDonnerValider(con, titre, qte, isbn);                
                break;
        }

    }
    //Fin de la deuxième Partie 

    
    //Troisieme Partie - verifier la disponibilité d'un livre

    public static void sousMenuVerifDispo(ConnexionMySQL con){
        System.out.println("┌────────────────────────────────────────────────────────┐");        
        System.out.println("│ Vous êtes sur la page de verification de disponibilité │");
        System.out.println("│ Rentrer le nom du livre que vous souhaitez vérifier    │");
        System.out.println("│ Ou juste une partie                                    │");
        System.out.println("│ Q - revenir en arrière                                 │");
        System.out.println("└────────────────────────────────────────────────────────┘");
        String entrer = scan.nextLine().trim();
        switch (entrer) {
            case "q":
            case "Q":
                MenuVendeur.menuVendeur(con);
                break;
            default:
                String reponse = MenuVendeur.magasinBD.rechercheLivre(vendeur.getMagasin().getIdMagasin(), entrer);
                System.out.println(reponse);
                System.out.println("┌────────────────────────────────────────────────────────────────────────────────────┐"); 
                System.out.println("│ Si aucun livre ne s'affiche, c'est que le livre n'est pas dispo dans votre magasin │");
                System.out.println("│ [R] faire une nouvelle recherche [Q] retour au menu Vendeur                        │");        
                System.out.println("└────────────────────────────────────────────────────────────────────────────────────┘");
                String entrer2 = scan.nextLine().toLowerCase().trim();
                switch(entrer2){
                case "r":
                    MenuVendeur.sousMenuVerifDispo(con);
                    break;
                case "q":
                    MenuVendeur.menuVendeur(con);
                    break;
                default:
                    System.out.println("Veuillez rentrer une commande valide");
                    MenuVendeur.sousMenuVerifDispo(con);                
                    break;
        }
                    break;
        }
    }

    //fin de la troisieme Partie 

    //Cinquieme Partie - transferer un livre


    public static void sousMenuTransfererLivre(ConnexionMySQL con){
        System.out.println("┌──────────────────────────────────────────────────────────────┐");        
        System.out.println("│ Vous êtes sur la page de transfert de livre                  │");
        System.out.println("│ Pour transferer un livre, vous aurez besoin de son Isbn      │");
        System.out.println("│ Rentrez le nom du livre dont vous souhaité connaitre l'isbn  │");
        System.out.println("│ Vous pouvez ne mettre qu'une partie du nom                   │");
        System.out.println("│ Q - revenir en arrière                                       │");
        System.out.println("└──────────────────────────────────────────────────────────────┘");
        String entrerTitre = scan.nextLine().trim();
        switch(entrerTitre){
            case "q":
            case "Q":
                MenuVendeur.menuVendeur(con);
                break;
            default:
                MenuVendeur.transfererLivreDemandeIsbn(con, entrerTitre);
                break;
        }
    }

    public static void transfererLivreDemandeIsbn(ConnexionMySQL con, String titre){
            String reponse = MenuVendeur.reseauBD.rechercheLivre(titre,vendeur.getMagasin().getIdMagasin());
            System.out.println(reponse);
            System.out.println("┌───────────────────────────────────────────────────────┐"); 
            System.out.println("│Rentrez l'isbn du livre que vous voulez transferer     │"); 
            System.out.println("│Rentrez les 13 chiffres sans espaces                   │"); 
            System.out.println("│[R] faire une nouvelle recherche [M] Menu Vendeur      │");
            System.out.println("└───────────────────────────────────────────────────────┘");
            String entrerIsbn = scan.nextLine().toLowerCase().trim();
            switch (entrerIsbn) {
                case "m":
                    MenuVendeur.menuVendeur(con);
                    break;
                case "r":
                    MenuVendeur.sousMenuTransfererLivre(con);
                    break;
                default:
                    MenuVendeur.transfererLivreDemandeIdMag(con, entrerIsbn, titre);
                    break;
            }
    }

    public static void transfererLivreDemandeIdMag(ConnexionMySQL con, String isbn, String titre){
        try {
            System.out.println("┌───────────────────────────────────────────────────────────────────────┐"); 
            System.out.println("│Voici les magasins ayant le livre souhaité en stock                    │"); 
            System.out.println("│S'il n'y a pas de valeur, c'est qu'aucun magasin n'a ce livre en stock │");
            System.out.println("└───────────────────────────────────────────────────────────────────────┘");
            String magasins = reseauBD.magasinsAyantLivre(vendeur.getMagasin().getIdMagasin(),isbn);
            System.out.println(magasins);
            System.out.println("┌───────────────────────────────────────────────────────────────┐");
            System.out.println("│Rentrez l'id du magasin duquel vous voulez transferer le livre │"); 
            System.out.println("│Rentrer uniquement un nombre                                   │"); 
            System.out.println("│[R] faire une nouvelle recherche [Q] retour en arriere         │");
            System.out.println("│[M] menu Vendeur                                               │");
            System.out.println("└───────────────────────────────────────────────────────────────┘");
            String entrerIdMag = scan.nextLine().toLowerCase().trim();
            switch(entrerIdMag){
                case "m":
                    MenuVendeur.menuVendeur(con);
                    break;
                case "r":
                    MenuVendeur.sousMenuTransfererLivre(con);
                    break;
                case "q":
                    MenuVendeur.transfererLivreDemandeIsbn(con, titre);
                    break;
                default:
                    Integer idmag = Integer.parseInt(entrerIdMag);
                    MenuVendeur.transfererLivreDemandeQte(con, idmag, isbn, titre);
                    break;
            }
        }catch(Exception e){
            System.out.println("Veuillez rentrer un nombre");
            MenuVendeur.sousMenuTransfererLivre(con);
        }
    }

    public static void transfererLivreDemandeQte(ConnexionMySQL con, Integer idmag, String isbn, String titre){
        try {
            System.out.println("┌────────────────────────────────────────────────────────┐"); 
            System.out.println("│Rentrez la quantité de livre que vous voulez transferer │"); 
            System.out.println("│[R] faire une nouvelle recherche [Q] retour en arriere  │");
            System.out.println("│[M] menu Vendeur                                        │");
            System.out.println("└────────────────────────────────────────────────────────┘");
            String entrerQte = scan.nextLine().toLowerCase().trim();
            switch(entrerQte){
                case "m":
                    MenuVendeur.menuVendeur(con);
                    break;
                case "r":
                    MenuVendeur.sousMenuTransfererLivre(con);
                    break;
                case "q":
                    MenuVendeur.transfererLivreDemandeIdMag(con,isbn,titre);
                    break;
                default:
                    Integer qteTransfere = Integer.parseInt(entrerQte);
                    Integer qteMagasinDonneur = magasinBD.getQte(isbn, idmag);
                    Integer idmagCourant = vendeur.getMagasin().getIdMagasin();
                    Integer qteMagasinReceveur =  magasinBD.getQte(isbn, idmagCourant);
                    if (qteMagasinDonneur < qteTransfere){
                        System.out.println("Vous ne pouvez pas transferer plus de livre que le magasin donneur n'a en stock");
                        String saut = scan.nextLine();
                        MenuVendeur.sousMenuTransfererLivre(con);
                        return;
                 }
                    qteMagasinDonneur -= qteTransfere;
                    magasinBD.miseAJourQuantite(isbn, qteMagasinDonneur, idmag);

                    if(qteMagasinReceveur == 0){
                        magasinBD.ajouterQte(isbn, qteTransfere, idmagCourant);
                    }
                    else{
                        qteMagasinReceveur += qteTransfere;
                        magasinBD.miseAJourQuantite(isbn, qteMagasinReceveur, idmagCourant);
                    }
                    System.out.println("┌─────────────────────────────────────────────┐"); 
                    System.out.println("│Le Transfert c'est effectuer sans problème   │"); 
                    System.out.println("└─────────────────────────────────────────────┘");
                    String action = scan.nextLine().toLowerCase().trim();
                    MenuVendeur.sousMenuTransfererLivre(con);
                
                break;
            }
        }catch(Exception e){
                    System.out.println("Veuillez rentrer un nombre");
                    MenuVendeur.sousMenuTransfererLivre(con);
        }       
    }
}
