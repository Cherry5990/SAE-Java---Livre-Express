package modele;

import java.util.ArrayList;
import java.util.List;

public class CommandeTest {
    private int numCom;
    private String dateCom;
    private boolean enLigne;
    private boolean livraison;
    private Client client;
    private Reseau reseau;
    private List<DetailCommande> detailCommandes;
    private int ligne;

    public CommandeTest(int numCom, String dateCom, boolean enLigne, boolean livraison, Client client, Reseau reseau) {
        this.numCom = numCom;
        this.dateCom = dateCom;
        this.enLigne = enLigne;
        this.livraison = livraison;
        this.client = client;
        this.reseau = reseau;
        this.detailCommandes = new ArrayList<>();
        this.ligne = 1;
    }

    public int getNumCom() {return numCom;}
    public String getDateCom() {return dateCom;}
    public boolean isEnLigne() {return enLigne;}
    public boolean isLivraison() {return livraison;}
    public Client getClient() {return client;}
    public Reseau getReseau(){return this.reseau;}
    public List<DetailCommande> getDetailCommandes(){return this.detailCommandes;}

    public void setClient(Client c){
        this.client = c;
    }
    public void setLivraison(boolean eL) {this.enLigne = eL;}
    public void setReseau(Reseau reseau){this.reseau = reseau;}

    public double sommeCommande(){
        double somme=0;
        for(DetailCommande dc:this.detailCommandes){
            somme+=dc.getPrixVente();
        }
        return somme;
    }

    public boolean ajouteLivreReseau(Livre livre,int qte){
        for (Magasin m : reseau.getMagasins())
            for(Posseder pos : m.getPosseders()){
                if(pos.getQte() > 0){
                    this.detailCommandes.add(new DetailCommande(this.ligne, qte, livre));
                    this.ligne +=1;
                    return true;
                }
                
            }
        return false;
    }
    
}