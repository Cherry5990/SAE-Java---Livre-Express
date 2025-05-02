public class Vendeur extends Compte{
    private Magasin librairie;


    public Vendeur(int id, String nom,String prenom, String mdp,Reseau reseau, Magasin librairie){
        super(id,nom,prenom,reseau);
        this.librairie = librairie;
    }

    public int getId(){return super.id;}
    public String getNom(){return this.nom;}
    public String getPrenom(){return this.prenom;}
    public String getMdp(){return this.mdp;}
    public Reseau getReseau(){return this.reseau;}
    public Magasin getMagasin(){return this.librairie;}
    
}
