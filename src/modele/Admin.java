package modele;

public class Admin extends Compte{
    
    public Admin(int id, String nom,String prenom){
        super(id,nom,prenom);
    }

    public int getId(){return super.id;}
    public String getNom(){return this.nom;}
    public String getPrenom(){return this.prenom;}
}
