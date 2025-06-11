package modele;

public class Posseder {
    private Magasin magasin;
    private Livre livre;
    private int qte;

    public Posseder(Magasin mag,Livre livre,int qte){
        this.magasin = mag;
        this.livre = livre;
        this.qte = qte;
    }

    public int getQte(){return this.qte;}

    public Livre getLivre(){return this.livre;}

    public Magasin getMagasin(){return this.magasin;}
}