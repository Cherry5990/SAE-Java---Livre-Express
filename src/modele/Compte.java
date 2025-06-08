package modele;

public abstract class Compte {
    protected int id;
    protected String nom;
    protected String prenom;

    public Compte(int id, String nom,String prenom){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public abstract int getId();
    public abstract String getNom();
    public abstract String getPrenom();
    
}
