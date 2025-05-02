public abstract class Compte {
    protected int id;
    protected String nom;
    protected String prenom;
    protected String mdp;
    protected Reseau reseau;

    public Compte(int id, String nom,String prenom, String mdp,Reseau reseau){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mdp = mdp;
        this.reseau = reseau;
    }

    public abstract int getId();
    public abstract String getNom();
    public abstract String getPrenom();
    public abstract String getMdp();
    public abstract Reseau getReseau();
    
}
