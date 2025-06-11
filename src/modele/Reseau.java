package modele;

import java.util.ArrayList;
import java.util.List;

public class Reseau {
    private List<Magasin> magasins;
    private List<Vendeur> vendeurs;
    private List<Admin> admins;
    private List<Client> clients;
    private int idMag;
    private int idCompte;

    public Reseau(){
        this.magasins = new ArrayList<>();
        this.vendeurs = new ArrayList<>();
        this.admins = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.idMag = 1;
        this.idCompte = 1;
    }

    public List<Magasin> getMagasins(){return this.magasins;}
    public List<Vendeur> getVendeurs(){return this.vendeurs;}
    public List<Admin> getAdmins(){return this.admins; }
    public List<Client> getClients(){return this.clients;}
    public int getIdMag(){return this.idMag;}
    public int getIdCompte(){return this.idCompte;}

    // méthodes création compte sur le réseau de librairie
    public void ajouteMagasin(String nom,String ville){
        this.magasins.add(new Magasin(this.idMag, nom, ville));
        this.idMag+=1;
    }

    public void ajouteMagasinExistant(Magasin magasin){
        this.magasins.add(magasin);
        this.idMag+=1;
    }

    public void ajouteVendeur(String nom,String prenom,int idMag){
        for (Magasin mag:this.magasins){
            if (mag.getIdMagasin()==idMag){
                this.vendeurs.add(new Vendeur(this.idCompte, nom,prenom, mag));
                this.idCompte +=1;
            }
        }
    }

    public void ajouteClient(String nom,String prenom,String adresse,String codePostal,String ville){
        this.clients.add(new Client(this.idCompte, nom ,prenom,adresse, codePostal, ville));
        this.idCompte+=1;
    }

    public void ajouteAdmin(String nom,String prenom){
        this.admins.add(new Admin(this.idCompte, nom, prenom));
        this.idCompte+=1;
    }

    public Vendeur getVendeur(String nom,String prenom){
        for(Vendeur v:this.vendeurs){
            if (v.getPrenom().equals(prenom) && v.getNom().equals(nom)){
                return v;
            }
        }
        return null;
    }

    public List<Livre> onVousRecommande(Client client){
        List<Livre> recommandations = new ArrayList<>();

        // On récupére d'abord la liste des livres commandes par le client passé en paramètres
        List<Livre> livreDuClient = new ArrayList<>();

        for (Commande cmd : client.getCommandes()){
            for (DetailCommande ligne : cmd.getDetailCommandes()){
                Livre livre = ligne.getLivre();
                if (!livreDuClient.contains(livre)){
                    livreDuClient.add(livre);
                }
            }
        }

        for (Client autreClient : this.clients){
            if (!autreClient.equals(client)){
                // La liste des livres 
                List<Livre> livresAutreClient = new ArrayList<>();
                for (Commande cmd : autreClient.getCommandes()){
                    for (DetailCommande ligne : cmd.getDetailCommandes()){
                        Livre livre = ligne.getLivre();
                        if (!livresAutreClient.contains(livre)){
                            livresAutreClient.add(livre);
                        }
                    }
                } 
                for (Livre livre : livresAutreClient){
                    if (livreDuClient.contains(livre)){
                        for (Livre l : livresAutreClient){
                            if (!livreDuClient.contains(l) && !recommandations.contains(l)){
                                recommandations.add(l);
                            }
                        }
                    }
                }
            }
        }
        return recommandations;
    }

    public void deplaceStock(Magasin src,Magasin target,int qte,Livre livre){
        src.dimimueQte(livre, qte);
        target.augmenteQte(livre, qte);
    }

    public void majStock(Magasin magasin,Livre livre,int qte){
        if (qte>0){
            magasin.augmenteQte(livre, qte);
        }
        else{
            magasin.dimimueQte(livre, qte);
        }
    }

}

