public class Admin extends Compte{
    
    public Admin(int id, String nom,String prenom, String mdp,Reseau reseau){
        super(id,nom,prenom,mdp,reseau);
    }

    public int getId(){return super.id;}
    public String getNom(){return this.nom;}
    public String getPrenom(){return this.prenom;}
    public String getMdp(){return this.mdp;}
    public Reseau getReseau(){return this.reseau;}
}
