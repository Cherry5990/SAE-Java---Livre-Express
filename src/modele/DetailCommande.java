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

    public void setQte(int qte){this.qte=qte;}

    public void setPrix(Double prix){this.prixVente=prix;}

    @Override
    public String toString(){
        return "Ligne " + this.numlig + ": commande du livre " + this.livre.getTitre() + " au prix de " + this.prixVente + " en " + this.qte + " exemplaires.";
    }

    @Override
    public boolean equals(Object o){
        if (o == null){return false;}
        if (o == this){return true;}
        if (!(o instanceof DetailCommande)){return false;}
        DetailCommande c = (DetailCommande) o;
        return c.getNumlig() == this.numlig;
    }

    @Override
    public int hashCode(){
        return this.numlig * 673;
    }

}
