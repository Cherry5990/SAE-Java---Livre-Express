public class Commande {
    private int numCom;
    private String dateCom;
    private boolean enLigne;
    private boolean livraison;
    private Client client;
    private Magasin magasin;
    private DetailCommande detailCommande;

    public Commande(int numCom, String dateCom, boolean enLigne, boolean livraison, Client client, Magasin magasin, DetailCommande detailCommande) {
        this.numCom = numCom;
        this.dateCom = dateCom;
        this.enLigne = enLigne;
        this.livraison = livraison;
        this.client = client;
        this.magasin = magasin;
        this.detailCommande = detailCommande;
    }

    public int getNumCom() {return numCom;}
    public String getDateCom() {return dateCom;}
    public boolean isEnLigne() {return enLigne;}
    public boolean isLivraison() {return livraison;}
    public Client getClient() {return client;}

    public void setLivraison(boolean eL) {this.enLigne = eL;}
}