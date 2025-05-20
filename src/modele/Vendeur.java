package modele;

public class Vendeur extends Compte{
    private Magasin librairie;


    public Vendeur(int id, String nom,String prenom,Reseau reseau, Magasin librairie){
        super(id,nom,prenom,reseau);
        this.librairie = librairie;
    }

    public int getId(){return super.id;}
    public String getNom(){return this.nom;}
    public String getPrenom(){return this.prenom;}
    public Reseau getReseau(){return this.reseau;}
    public Magasin getMagasin(){return this.librairie;}

    public void ajouteLivre(int isbn, String titre, int nbPages, String datePubli, double prix,int qte){
        this.librairie.ajouteLivre(new Livre(isbn,titre,nbPages,datePubli,prix), qte);
    }
    
}
