public class Client extends Compte{
    
    private String adresse;
    private String codePostal;
    private String ville;

    public Client(int id, String nom,String prenom, String mdp, String adresse, String codePostal, String ville){
        super(id,nom,prenom,mdp);
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public int getId(){
        return super.id;
    }

    public String getNom(){
        return this.nom;
    }

    public String getPrenom(){
        return this.prenom;
    }
    public String getMdp(){
        return this.mdp;
    }
}

