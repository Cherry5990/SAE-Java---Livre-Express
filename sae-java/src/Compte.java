public abstract class Compte {
    protected int id;
    protected String nom;
    protected String prenom;
    protected String mdp;

    public Compte(int id, String nom,String prenom, String mdp){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mdp = mdp;
    }

    public abstract int getId();
    public abstract String getNom();
    public abstract String getPrenom();
    public abstract String getMdp();
    
}
