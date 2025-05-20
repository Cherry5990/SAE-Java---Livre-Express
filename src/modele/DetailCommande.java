package modele;

public class DetailCommande {
    private int numlig;
    private int qte;
    private double prixVente;
    private Livre livre;

    public DetailCommande(int numlig,int qte,Livre livre){
        this.livre = livre;
        this.numlig=numlig;
        this.qte = qte;
        this.prixVente = qte*this.livre.getPrix();
    }

    public int getNumlig(){return this.numlig;}

    public double getPrixVente(){return this.prixVente;}

    public int getQte(){return this.qte;}

    public Livre getLivre(){return this.livre;}


}
