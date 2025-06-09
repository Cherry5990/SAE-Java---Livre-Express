package modele;

import java.util.ArrayList;
import java.util.List;

public class Commande {
    private int numCom;
    private String dateCom;
    private boolean enLigne;
    private boolean livraison;
    private Client client;
    private Magasin magasin;
    private List<DetailCommande> detailCommandes;
    private int ligne;

    public Commande(int numCom, String dateCom, boolean enLigne, boolean livraison, Client client, Magasin magasin) {
        this.numCom = numCom;
        this.dateCom = dateCom;
        this.enLigne = enLigne;
        this.livraison = livraison;
        this.client = client;
        this.magasin = magasin;
        this.detailCommandes = new ArrayList<>();
        this.ligne = 1;
    }

    public int getNumCom() {return numCom;}
    public String getDateCom() {return dateCom;}
    public boolean isEnLigne() {return enLigne;}
    public boolean isLivraison() {return livraison;}
    public Client getClient() {return client;}
    public Magasin getMagasin(){return this.magasin;}
    public List<DetailCommande> getDetailCommandes(){return this.detailCommandes;}

    public void setClient(Client c){
        this.client = c;
    }
    public void setLivraison(boolean eL) {this.enLigne = eL;}

    public void setEnLigne(boolean enLigne){
        this.enLigne = enLigne;
    }
    public double sommeComande(){
        double somme=0;
        for(DetailCommande dc:this.detailCommandes){
            somme+=dc.getPrixVente();
        }
        return somme;
    }

    public void ajouteLivre(Livre livre,int qte){
        this.detailCommandes.add(new DetailCommande(this.ligne, qte, livre));
        this.ligne +=1;
    }
    
}