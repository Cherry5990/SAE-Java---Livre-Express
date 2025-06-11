package modele;

public class Vendeur extends Compte{
    private Magasin librairie;


    public Vendeur(int id, String nom,String prenom, Magasin librairie){
        super(id,nom,prenom);
        this.librairie = librairie;
    }

    public int getId(){return super.id;}
    public String getNom(){return this.nom;}
    public String getPrenom(){return this.prenom;}
    public Magasin getMagasin(){return this.librairie;}
}
