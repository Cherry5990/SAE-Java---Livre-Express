package modele;

public class Admin extends Compte{
    
    public Admin(int id, String nom,String prenom,Reseau reseau){
        super(id,nom,prenom,reseau);
    }

    public int getId(){return super.id;}
    public String getNom(){return this.nom;}
    public String getPrenom(){return this.prenom;}
    public Reseau getReseau(){return this.reseau;}
}
