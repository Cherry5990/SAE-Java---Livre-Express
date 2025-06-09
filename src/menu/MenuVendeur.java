package menu;

import java.lang.invoke.VarHandle.VarHandleDesc;
import java.util.Scanner;


import BD.ConnexionMySQL;
import BD.VendeurBD;
import modele.Vendeur;

public class MenuVendeur {
    private static final Scanner scan = new Scanner(System.in); // Scanner unique
    private static Vendeur vendeur;
    private static VendeurBD vendeurBD;

    public static void connexionVendeur(ConnexionMySQL con){
        System.out.println("┌────────────────────────────────┐");
        System.out.println("│Veuillez saisir un id de Vendeur│");
        System.out.println("└────────────────────────────────┘"); 
        String action = scan.nextLine().trim();
        MenuVendeur.vendeurBD = new VendeurBD(con);

        // Vérifie si 'action' est un nombre entier
        try {
            MenuVendeur.vendeur = vendeurBD.getVendeur(Integer.parseInt(action));
        }

        catch(Exception e){
            System.out.println("Veuillez rentrer un nombre");
            MenuVendeur.connexionVendeur(con);
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
            MenuClient.connexionClient(con);        
                break;
        }
    }



    public static void menuVendeur(ConnexionMySQL con){
        System.out.println("┌──────────────────────────────────────────┐");        
        System.out.println("│ Vous êtes connectés en tant que Vendeur  │");
        System.out.println("│         Que voulez vous faire?           │");
        System.out.println("│ 1 - ajouté un livre au stocks            │");
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
            case "1":
                MenuVendeur.sousMenuAjouterLivre(con);
                break;
            case "2":
                System.out.println("A faire");
                MenuVendeur.menuVendeur(con);
                break;
            case "3":
                System.out.println("A faire");
                MenuVendeur.menuVendeur(con);
                break;
            case "4":
                System.out.println("A faire");
                MenuVendeur.menuVendeur(con);
                break;
            case "5":
                System.out.println("A faire");
                MenuVendeur.menuVendeur(con);
                break;

            default:
            System.out.println("Veuillez rentrer une commande valide");
            MenuVendeur.menuVendeur(con);       
                break;
        }
    }  

    //Finir la procédure d'ajout et diviser le code en plusieurs méthodes.
    public static void sousMenuAjouterLivre(ConnexionMySQL con){
        System.out.println("┌───────────────────────────────────────────┐");        
        System.out.println("│ Vous êtes sur la page d'ajout d'un livre  │");
        System.out.println("│        Rentrer le nom du livre            │");
        System.out.println("│      que vous souhaitez ajouter           │");
        System.out.println("│ Q - revenir en arrière                    │");
        System.out.println("└───────────────────────────────────────────┘");
        String entrer = scan.nextLine().trim();
        switch (entrer) {
            case "q":
            case "Q":
                MenuVendeur.menuVendeur(con);
                break;
            default:
            try {
                if(vendeurBD.verifLivreExisteDansMagasin(MenuVendeur.vendeur.getMagasin().getIdMagasin(),entrer)){
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
                    
                    String isbn = vendeurBD.regardeSiISBNExiste(entrer);
                    System.out.println("──────────────────────────────────────────────────────────────────────");
                    System.out.println("Vous êtes sur le point de rajouter " + entrer + " dans les stocks");
                    if (isbn == null){
                        isbn = vendeurBD.maxIsbn();
                    }
                    System.out.println("┌────────────────────────────────────────────────────────────────────┐"); 
                    System.out.println("│Ce livre aura comme identifiant: "+  isbn+ "                      │");
                    System.out.println("│Veuillez maintenant rentrer le nombres de pages que ce livre possède│");
                    System.out.println("│Faite bien attention à rentrer un nombre, sans aucune lettre        │");
                    System.out.println("│[Q] pour revenir au menu d'ajout                                    │");
                    System.out.println("└────────────────────────────────────────────────────────────────────┘");
                    String entrer3 = scan.nextLine().toLowerCase().trim();
                    switch (entrer3) {
                        case "q":
                            MenuVendeur.sousMenuAjouterLivre(con);
                            break;
                        default:
                        Integer nbpages = (Integer.parseInt(entrer3));
                        System.out.println("A Finir");
                        break;
                    }
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

    public static void sousMenuMajQte(ConnexionMySQL con){
        System.out.println("A faire");
        MenuVendeur.menuVendeur(con);
    }




}
