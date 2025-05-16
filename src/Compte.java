public abstract class Compte {
    protected int id;
    protected String nom;
    protected String prenom;
    protected Reseau reseau;

    public Compte(int id, String nom,String prenom,Reseau reseau){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.reseau = reseau;
    }

    public abstract int getId();
    public abstract String getNom();
    public abstract String getPrenom();
    public abstract Reseau getReseau();
    
}
